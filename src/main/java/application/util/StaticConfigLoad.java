package application.util;

import application.module.fight.base.skill.FightSkillWrapContainer;
import com.cala.orm.util.ConfigLoad;
import com.typesafe.config.Config;
import template.CustomByteBuffer;
import template.TemplateParser;

/**
 * @author Luo Yong
 * @date 2021-12-24
 * @Source 1.0
 */
public class StaticConfigLoad {

    private static final String TEMPLATE_DATA_PATH = "gs-app.template-data-path";

    public static void init() {
        Config config = ConfigLoad.getConfig();
        byte[] bytes = FileUtils.toByteArray3(ClassLoader.getSystemResource(config.getString(TEMPLATE_DATA_PATH)).getPath());
        CustomByteBuffer customByteBuffer = new CustomByteBuffer(bytes);
        TemplateParser.parse(customByteBuffer);

        wrapInit();
    }

    //二次封装数据加载
    private static void wrapInit() {
        //技能数据二次包装
        FightSkillWrapContainer.init();
    }
}
