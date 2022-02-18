/* 由程序自动生成，请勿修改。*/
package template;

public record SkillStepTemplate(int id, String name, int time_start_in_ms, int time_end_in_ms, int delta_time_in_ms,
                                String condition, int check_buff, int config_group_type, int select_target,
                                String select_target_group, String set_pos, float set_pos_max_dis, String x_move_speed,
                                String y_move_speed, int jump_in_air, int no_check_land_time_in_ms,
                                int check_ladder_time_in_ms, float turn_speed, String face_dir, int summon_unit_id,
                                String summon_pos, String summon_face, int summon_skill, String summon_skill_param,
                                int control_unit, int end_self_skill, int break_this_frame, int cast_skill,
                                String cast_skill_copy_unit_group, String play_anim, float anim_crossfade_time,
                                float anim_speed, String set_param_name, String set_param_formula, String action_group,
                                int repeat_action, int hp_damage_type, String hp_damage, String mana, int add_buff,
                                int buff_time_in_ms, int remove_buff, int only_remove_buff_by_me,
                                String partical_effect_name, int partical_scale, String lightning_name,
                                String lightning_pos_1, String lightning_pos_2, int set_cold_down_skill,
                                int set_cold_down_time_ms, String log_string) {

    public static SkillStepTemplate parse(CustomByteBuffer cbb) {
        var id = cbb.getInt();
        var name = cbb.getString();
        var time_start_in_ms = cbb.getInt();
        var time_end_in_ms = cbb.getInt();
        var delta_time_in_ms = cbb.getInt();
        var condition = cbb.getString();
        var check_buff = cbb.getInt();
        var config_group_type = cbb.getInt();
        var select_target = cbb.getInt();
        var select_target_group = cbb.getString();
        var set_pos = cbb.getString();
        var set_pos_max_dis = cbb.getFloat();
        var x_move_speed = cbb.getString();
        var y_move_speed = cbb.getString();
        var jump_in_air = cbb.getInt();
        var no_check_land_time_in_ms = cbb.getInt();
        var check_ladder_time_in_ms = cbb.getInt();
        var turn_speed = cbb.getFloat();
        var face_dir = cbb.getString();
        var summon_unit_id = cbb.getInt();
        var summon_pos = cbb.getString();
        var summon_face = cbb.getString();
        var summon_skill = cbb.getInt();
        var summon_skill_param = cbb.getString();
        var control_unit = cbb.getInt();
        var end_self_skill = cbb.getInt();
        var break_this_frame = cbb.getInt();
        var cast_skill = cbb.getInt();
        var cast_skill_copy_unit_group = cbb.getString();
        var play_anim = cbb.getString();
        var anim_crossfade_time = cbb.getFloat();
        var anim_speed = cbb.getFloat();
        var set_param_name = cbb.getString();
        var set_param_formula = cbb.getString();
        var action_group = cbb.getString();
        var repeat_action = cbb.getInt();
        var hp_damage_type = cbb.getInt();
        var hp_damage = cbb.getString();
        var mana = cbb.getString();
        var add_buff = cbb.getInt();
        var buff_time_in_ms = cbb.getInt();
        var remove_buff = cbb.getInt();
        var only_remove_buff_by_me = cbb.getInt();
        var partical_effect_name = cbb.getString();
        var partical_scale = cbb.getInt();
        var lightning_name = cbb.getString();
        var lightning_pos_1 = cbb.getString();
        var lightning_pos_2 = cbb.getString();
        var set_cold_down_skill = cbb.getInt();
        var set_cold_down_time_ms = cbb.getInt();
        var log_string = cbb.getString();

        var temp = new SkillStepTemplate(id, name, time_start_in_ms, time_end_in_ms, delta_time_in_ms, condition, check_buff, config_group_type, select_target, select_target_group, set_pos, set_pos_max_dis, x_move_speed, y_move_speed, jump_in_air, no_check_land_time_in_ms, check_ladder_time_in_ms, turn_speed, face_dir, summon_unit_id, summon_pos, summon_face, summon_skill, summon_skill_param, control_unit, end_self_skill, break_this_frame, cast_skill, cast_skill_copy_unit_group, play_anim, anim_crossfade_time, anim_speed, set_param_name, set_param_formula, action_group, repeat_action, hp_damage_type, hp_damage, mana, add_buff, buff_time_in_ms, remove_buff, only_remove_buff_by_me, partical_effect_name, partical_scale, lightning_name, lightning_pos_1, lightning_pos_2, set_cold_down_skill, set_cold_down_time_ms, log_string);
        return temp;
    }
}
