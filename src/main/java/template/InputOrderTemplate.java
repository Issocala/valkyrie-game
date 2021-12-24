/* 由程序自动生成，请勿修改。*/
package template;

public record InputOrderTemplate(int id,String name,int[] player_team,int[] input_id_joystick,int[] input_id_button,int[] unit_id,int[] on_land,int skill_id){

    public static InputOrderTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var name = cbb.getString();
        var player_teamLength = cbb.getInt();
        var player_team = new int[player_teamLength];
        for (int i = 0; i < player_teamLength; i++){
            player_team[i] = cbb.getInt();
        }
        var input_id_joystickLength = cbb.getInt();
        var input_id_joystick = new int[input_id_joystickLength];
        for (int i = 0; i < input_id_joystickLength; i++){
            input_id_joystick[i] = cbb.getInt();
        }
        var input_id_buttonLength = cbb.getInt();
        var input_id_button = new int[input_id_buttonLength];
        for (int i = 0; i < input_id_buttonLength; i++){
            input_id_button[i] = cbb.getInt();
        }
        var unit_idLength = cbb.getInt();
        var unit_id = new int[unit_idLength];
        for (int i = 0; i < unit_idLength; i++){
            unit_id[i] = cbb.getInt();
        }
        var on_landLength = cbb.getInt();
        var on_land = new int[on_landLength];
        for (int i = 0; i < on_landLength; i++){
            on_land[i] = cbb.getInt();
        }
        var skill_id = cbb.getInt();

        var temp = new InputOrderTemplate(id,name,player_team,input_id_joystick,input_id_button,unit_id,on_land,skill_id);
        return temp;
    }
}

