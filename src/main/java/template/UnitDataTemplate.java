/* 由程序自动生成，请勿修改。*/
package template;

public record UnitDataTemplate(int id,String name,int[] unit_type,int[] skills,int collision_level,float collision_size,float unit_height,float max_hp,float max_mana,float armor,float atk,float resistance,float gravity,String model,int has_x_angle,int death_delay_in_ms){

    public static UnitDataTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var name = cbb.getString();
        var unit_typeLength = cbb.getInt();
        var unit_type = new int[unit_typeLength];
        for (int i = 0; i < unit_typeLength; i++){
            unit_type[i] = cbb.getInt();
        }
        var skillsLength = cbb.getInt();
        var skills = new int[skillsLength];
        for (int i = 0; i < skillsLength; i++){
            skills[i] = cbb.getInt();
        }
        var collision_level = cbb.getInt();
        var collision_size = cbb.getFloat();
        var unit_height = cbb.getFloat();
        var max_hp = cbb.getFloat();
        var max_mana = cbb.getFloat();
        var armor = cbb.getFloat();
        var atk = cbb.getFloat();
        var resistance = cbb.getFloat();
        var gravity = cbb.getFloat();
        var model = cbb.getString();
        var has_x_angle = cbb.getInt();
        var death_delay_in_ms = cbb.getInt();

        var temp = new UnitDataTemplate(id,name,unit_type,skills,collision_level,collision_size,unit_height,max_hp,max_mana,armor,atk,resistance,gravity,model,has_x_angle,death_delay_in_ms);
        return temp;
    }
}
