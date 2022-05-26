package application.module.player.fight.attribute;

/**
 * @author Luo Yong
 * @date 2022-3-8
 * @Source 1.0
 */
public interface AttributeTemplateId {
    /*
    值类型
     */
    short MAX_HP = 100;
    short MAX_MP = 101;
    short ATTACK = 102;
    short MAGIC = 103;
    short ATTACK_DEFENCE = 104;
    short MAGIC_DEFENCE = 105;
    short ATTACK_PIERCE = 106;
    short MAGIC_PIERCE = 107;
    short ROLE_ATTACK = 108;
    short ROLE_DEFENCE = 109;
    short ROLE_PIERCE = 110;
    short HIT = 200;
    short MISS = 201;
    short CRIT = 202;
    short REDUCE_CRIT = 203;
    short TRUE_DAMAGE = 204;
    short REDUCE_TRUE_DAMAGE = 205;
    short RECOVER_HP = 206;
    short RECOVER_MP = 207;
    short ATTACK_SPEED = 208;
    short MOVE_SPEED = 209;
    short JUMP_SPEED = 210;



    /*
    万分比ratio类型
     */
    short MISS_RATIO = 300;
    short HIT_RATIO = 301;
    short CRIT_RATIO = 302;
    short REDUCE_CRIT_RATIO = 303;
    short CRIT_ADD_DAMAGE_RATIO = 304;
    short CRIT_REDUCE_DAMAGE_RATIO = 305;
    short DEAD_RATIO = 306;
    short REDUCE_DEAD_RATIO = 307;
    short DEAD_FIXED_DAMAGE_RATIO = 308;
    short REDUCE_DEAD_FIXED_DAMAGE_RATIO = 309;
    short BLOCK_RATIO = 310;
    short REDUCE_BLOCK_RATIO = 311;
    short BLOCK_ADD_DAMAGE_RATIO = 312;
    short BLOCK_REDUCE_DAMAGE_RATIO = 313;
    short IGNORE_DEFENCE_RATIO = 314;
    short REDUCE_IGNORE_DEFENCE_RATIO = 315;
    short SKILL_DAMAGE_RATIO = 317;
    short REDUCE_SKILL_DAMAGE_RATIO = 318;
    short BOSS_DAMAGE_RATIO = 319;
    short REDUCE_BOSS_DAMAGE_RATIO = 320;
    short PLAYER_DAMAGE_RATIO = 322;
    short REDUCE_PLAYER_DAMAGE_RATIO = 323;
    short FINAL_DAMAGE_RATIO = 324;
    short REDUCE_FINAL_DAMAGE_RATIO = 325;

    short BUFF_TIME_RATIO = 330;
    short REDUCE_ABNORMAL_STATE_TIME_RATIO = 331;
    short REDUCE_CONTROL_TIME_RATIO = 332;
    short REDUCE_REPEL_RATIO = 333;


    //加成属性
    short HP_PER_RATIO = 400;
    short MP_PER_RATIO = 401;
    short ROLE_ATTACK_PER_RATIO = 402;
    short ROLE_DEFENCE_PER_RATIO = 403;
    short ROLE_PIERCE_PER_RATIO = 404;
    short RECOVER_HP_PER_RATIO = 405;
    short RECOVER_MP_PER_RATIO = 406;
    short EXP_RATIO = 409;
    short ITEM_DROP_RATIO = 410;
    short COIN_RATIO = 411;
    short EQUIP_HP_PER_RATIO = 412;
    short EQUIP_ATTACK_PER_RATIO = 413;
    short EQUIP_DEFENCE_PER_RATIO = 414;
    short EQUIP_PIERCE_PER_RATIO = 415;
    short EQUIP_ALL_PER_RATIO = 416;
    short ATTACK_SPEED_RATIO = 454;
    short MOVE_SPEED_RATIO = 455;
    short JUMP_SPEED_RATIO = 456;




    /**
     * 战斗动态额外属性
     */
    short HP_EXTRA = 30000;
    short MP_EXTRA = 30001;
    short ATTACK_EXTRA = 30002;
    short MAGIC_EXTRA = 30003;
    short ATTACK_DEFENCE_EXTRA = 30004;
    short MAGIC_DEFENCE_EXTRA = 30005;
    short ATTACK_PIERCE_EXTRA = 30006;
    short MAGIC_PIERCE_EXTRA = 30007;
    short ROLE_ATTACK_EXTRA = 30008;
    short ROLE_DEFENCE_EXTRA = 30009;
    short ROLE_PIERCE_EXTRA = 30010;
    short VAR_HP = 30011;
    short VAR_MP = 30012;
    short HIT_EXTRA = 30013;
    short MISS_EXTRA = 30014;
    short CRIT_EXTRA = 30015;
    short REDUCE_CRIT_EXTRA = 30016;
    short TRUE_DAMAGE_EXTRA = 30017;
    short REDUCE_TRUE_DAMAGE_EXTRA = 30018;
    short RECOVER_HP_EXTRA = 30019;
    short RECOVER_MP_EXTRA = 30020;
    short ATTACK_SPEED_EXTRA = 30021;
    short MOVE_SPEED_EXTRA = 30022;
    short JUMP_SPEED_EXTRA = 30023;


    short DOU_QI = 30024;
    /**
     * 冰法印记
     */
    short ICE_MAGIC_FLAG = 30025;
    /**
     * 冰法被动蓝量抵扣伤害
     */
    short ICE_MAGIC_SHIELD = 30026;

    /**
     * boss伤害翻倍(万分比)
     */
    short BOSS_ADD_DAMAGE = 30027;

    /**
     * 捡到一块石头
     */
    short PICK_UP_STONE = 30028;
}
