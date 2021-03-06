package application.data;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import application.module.common.data.entity.DataMessage;
import application.module.player.data.PlayerEntityData;
import application.module.player.fight.attribute.data.AttributeData;
import application.module.player.fight.buff.data.FightBuffData;
import application.module.player.fight.skill.data.SkillData;
import application.module.player.fight.state.data.StateData;
import application.module.scene.data.SceneData;
import application.module.user.data.UserData;
import com.cala.orm.OrmProcessor;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.DataInit;
import com.cala.orm.ddl.SchemaUpdate;
import com.cala.orm.message.DataBase;
import com.cala.orm.util.DbConnection;

import javax.management.RuntimeErrorException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;

import static com.cala.orm.cache.AbstractDataCacheManager.DATA_AND_DB_DISPATCHER;

/**
 * 数据缓存分发控制中心
 *
 * @author Luo Yong
 * @date 2021-11-19
 * @Source 1.0
 */
public class ActorDataDispatcher extends AbstractLoggingActor {

    /**
     * 创建props方法，方法名
     */
    public final static String PROPS_CREATE_METHOD = "create";

    /**
     * key: class
     * value: DbCacheManager Impl
     */
    private Map<Class<?>, ActorRef> class2DbCacheManagerMap;

    public record Message(Class<? extends AbstractDataCacheManager<?>> clazz, DataBase abstractEntityBase,
                          ActorRef sender) {
    }

    private ActorDataDispatcher() {
    }

    public static Props create() {
        return Props.create(ActorDataDispatcher.class, ActorDataDispatcher::new).withDispatcher(DATA_AND_DB_DISPATCHER);
    }

    @Override
    public void preStart() {
        class2DbCacheManagerMap = new HashMap<>();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DataAgent.Init$.class, init -> init())
                .match(DataMessage.RequestData.class, requestData -> requestData(requestData.clazz()))
                .match(DataMessage.RequestAllData.class, this::requestAllData)
                .build();
    }

    private void requestAllData(DataMessage.RequestAllData requestAllData) {
        sender().tell(new DataMessage.AllDataResult(Map.copyOf(this.class2DbCacheManagerMap)), self());
    }

    private void requestData(Class<?> clazz) {
        ActorRef actorRef = class2DbCacheManagerMap.get(clazz);
        if (Objects.isNull(actorRef)) {
            throw new RuntimeErrorException(new Error("requestData error, not " + clazz.getName()));
        }
        sender().tell(new DataMessage.DataResult(clazz, actorRef), self());
    }

    private void init() {
        Set<Class<?>> entityClazzs = new HashSet<>();
        //数据库表初始化
        OrmProcessor.INSTANCE.initOrmDefinitions("application", entityClazzs, "entity");
        //数据库连接初始化
        DbConnection.init();
        new SchemaUpdate().execute(DbConnection.getConnection(DbConnection.DB_USER), entityClazzs);

        //数据库对应表管理对象
        addCacheManagerMap();

    }

    private ActorRef getActor(Class<? extends AbstractDataCacheManager<?>> clazz) {
        ActorRef actorRef = class2DbCacheManagerMap.get(clazz);
        if (Objects.isNull(actorRef)) {
            log().error("ActorDataDispatcherImpl not register {}", clazz.getSimpleName());
        }
        return actorRef;
    }

    /**
     * 消息分发
     */
    private void sendMessage(Class<? extends AbstractDataCacheManager<?>> clazz, Object abstractEntityBase, ActorRef sender) {
        ActorRef actorRef = getActor(clazz);
        if (Objects.nonNull(actorRef)) {
            actorRef.tell(abstractEntityBase, sender);
        }
    }

    /**
     * 注册dbCacheManager
     *
     * @param clazz 对应类
     */
    private void add(Class<? extends AbstractDataCacheManager<?>> clazz) {
        String name = clazz.getName();
        ActorRef actorRef = class2DbCacheManagerMap.get(clazz);
        if (Objects.nonNull(actorRef)) {
            throw new RuntimeErrorException(new Error("重复注册缓存对象！"));
        }
        Method method;
        try {
            method = clazz.getMethod(PROPS_CREATE_METHOD);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(name + "并未创建create actor方法,方法名为" + PROPS_CREATE_METHOD);
        }
        try {
            actorRef = getContext().actorOf((Props) method.invoke(PROPS_CREATE_METHOD), name);
            actorRef.tell(new DataInit(), self());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        class2DbCacheManagerMap.put(clazz, actorRef);
    }

    /**
     * 添加数据库对应表对象方法
     */
    private void addCacheManagerMap() {
        add(PlayerEntityData.class);
        add(UserData.class);
        add(AttributeData.class);
        add(SkillData.class);
        add(StateData.class);
        add(SceneData.class);
        add(FightBuffData.class);
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    private final SupervisorStrategy strategy =
            new OneForOneStrategy(
                    10,
                    Duration.ofMinutes(1),
                    DeciderBuilder
//                            .match(ArithmeticException.class, e -> SupervisorStrategy.resume())
//                            .match(NullPointerException.class, e -> {
//                                sender().tell(new DataInit(), self());
//                                return SupervisorStrategy.restart();
//                            })
//                            .match(IllegalArgumentException.class, e -> {
//                                self().tell(DataAgent.Init$.MODULE$, self());
//                                return SupervisorStrategy.stop();
//                            })
                            .matchAny(o -> SupervisorStrategy.resume())
                            .build());
}
