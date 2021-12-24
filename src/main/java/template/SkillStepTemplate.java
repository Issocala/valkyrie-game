/* 由程序自动生成，请勿修改。*/
package template;

public record SkillStepTemplate(int id,String name,int time_start_in_ms,int time_end_in_ms,int delta_time_in_ms,String condition,int check_buff,int config_group_type,int select_target,String select_target_group,String x_move_speed,String y_move_speed,int jump_in_air,int no_check_land_time_in_ms,int check_ladder_time_in_ms,String face_dir,float turn_speed,int summon_unit_id,String summon_pos,String summon_face,int summon_skill,String summon_target_pos,String summon_target_id,int end_self_skill,int break_this_frame,int cast_skill,String cast_skill_pos,String cast_skill_target,String play_anim,float anim_crossfade_time,float anim_speed,String log_string,String action_group,String hp_damage,int add_buff,int buff_time_in_ms,int remove_buff,String partical_effect_name,String partical_effect_item){

    public static SkillStepTemplate parse(CustomByteBuffer cbb){
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
        var x_move_speed = cbb.getString();
        var y_move_speed = cbb.getString();
        var jump_in_air = cbb.getInt();
        var no_check_land_time_in_ms = cbb.getInt();
        var check_ladder_time_in_ms = cbb.getInt();
        var face_dir = cbb.getString();
        var turn_speed = cbb.getFloat();
        var summon_unit_id = cbb.getInt();
        var summon_pos = cbb.getString();
        var summon_face = cbb.getString();
        var summon_skill = cbb.getInt();
        var summon_target_pos = cbb.getString();
        var summon_target_id = cbb.getString();
        var end_self_skill = cbb.getInt();
        var break_this_frame = cbb.getInt();
        var cast_skill = cbb.getInt();
        var cast_skill_pos = cbb.getString();
        var cast_skill_target = cbb.getString();
        var play_anim = cbb.getString();
        var anim_crossfade_time = cbb.getFloat();
        var anim_speed = cbb.getFloat();
        var log_string = cbb.getString();
        var action_group = cbb.getString();
        var hp_damage = cbb.getString();
        var add_buff = cbb.getInt();
        var buff_time_in_ms = cbb.getInt();
        var remove_buff = cbb.getInt();
        var partical_effect_name = cbb.getString();
        var partical_effect_item = cbb.getString();

        var temp = new SkillStepTemplate(id,name,time_start_in_ms,time_end_in_ms,delta_time_in_ms,condition,check_buff,config_group_type,select_target,select_target_group,x_move_speed,y_move_speed,jump_in_air,no_check_land_time_in_ms,check_ladder_time_in_ms,face_dir,turn_speed,summon_unit_id,summon_pos,summon_face,summon_skill,summon_target_pos,summon_target_id,end_self_skill,break_this_frame,cast_skill,cast_skill_pos,cast_skill_target,play_anim,anim_crossfade_time,anim_speed,log_string,action_group,hp_damage,add_buff,buff_time_in_ms,remove_buff,partical_effect_name,partical_effect_item);
        return temp;
    }
}

