/* 由程序自动生成，请勿修改。*/
package template;

public record SceneUnitsTemplate(int id,String scene,int time_in_ms,int unit_type,float[] pos,float face,int player){

    public static SceneUnitsTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var scene = cbb.getString();
        var time_in_ms = cbb.getInt();
        var unit_type = cbb.getInt();
        var pos = new float[] {cbb.getFloat(), cbb.getFloat() };
        var face = cbb.getFloat();
        var player = cbb.getInt();

        var temp = new SceneUnitsTemplate(id,scene,time_in_ms,unit_type,pos,face,player);
        return temp;
    }
}
