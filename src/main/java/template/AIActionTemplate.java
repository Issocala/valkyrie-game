/* 由程序自动生成，请勿修改。*/
package template;

public record AIActionTemplate(int id,String name,int select_1,int select_2,int select_3,int select_4,int select_5,String condition,String exit_condition,String use_joystick,String joystick_dir,int prio_level){

    public static AIActionTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var name = cbb.getString();
        var select_1 = cbb.getInt();
        var select_2 = cbb.getInt();
        var select_3 = cbb.getInt();
        var select_4 = cbb.getInt();
        var select_5 = cbb.getInt();
        var condition = cbb.getString();
        var exit_condition = cbb.getString();
        var use_joystick = cbb.getString();
        var joystick_dir = cbb.getString();
        var prio_level = cbb.getInt();

        var temp = new AIActionTemplate(id,name,select_1,select_2,select_3,select_4,select_5,condition,exit_condition,use_joystick,joystick_dir,prio_level);
        return temp;
    }
}
