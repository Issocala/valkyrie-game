/* 由程序自动生成，请勿修改。*/
package template;

public record UnitDataTemplate(int id,String name,int[] skills,int collision_level,float collision_size,float unit_height,float max_hp,float armor,float atk,String x_acc,String y_acc,String model,int can_not_select,int has_x_angle,int death_delay_in_ms){

    public static UnitDataTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var name = cbb.getString();
        var skillsLength = cbb.getInt();
        var skills = new int[skillsLength];
        for (int i = 0; i < skillsLength; i++){
            skills[i] = cbb.getInt();
        }
        var collision_level = cbb.getInt();
        var collision_size = cbb.getFloat();
        var unit_height = cbb.getFloat();
        var max_hp = cbb.getFloat();
        var armor = cbb.getFloat();
        var atk = cbb.getFloat();
        var x_acc = cbb.getString();
        var y_acc = cbb.getString();
        var model = cbb.getString();
        var can_not_select = cbb.getInt();
        var has_x_angle = cbb.getInt();
        var death_delay_in_ms = cbb.getInt();

        var temp = new UnitDataTemplate(id,name,skills,collision_level,collision_size,unit_height,max_hp,armor,atk,x_acc,y_acc,model,can_not_select,has_x_angle,death_delay_in_ms);
        return temp;
    }
}

