/* 由程序自动生成，请勿修改。*/
package template;

public record BuffDataTemplate(int id,String name,int config_group_type,int interval_in_ms,int skill_user_is_creator,int[] old_status,int[] new_status,int trig_skill){

    public static BuffDataTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var name = cbb.getString();
        var config_group_type = cbb.getInt();
        var interval_in_ms = cbb.getInt();
        var skill_user_is_creator = cbb.getInt();
        var old_statusLength = cbb.getInt();
        var old_status = new int[old_statusLength];
        for (int i = 0; i < old_statusLength; i++){
            old_status[i] = cbb.getInt();
        }
        var new_statusLength = cbb.getInt();
        var new_status = new int[new_statusLength];
        for (int i = 0; i < new_statusLength; i++){
            new_status[i] = cbb.getInt();
        }
        var trig_skill = cbb.getInt();

        var temp = new BuffDataTemplate(id,name,config_group_type,interval_in_ms,skill_user_is_creator,old_status,new_status,trig_skill);
        return temp;
    }
}

