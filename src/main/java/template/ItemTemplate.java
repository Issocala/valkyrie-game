/* 由程序自动生成，请勿修改。*/
package template;

public record ItemTemplate(int id,String name,String desc,byte type,byte quality,int level){

    public static ItemTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var name = cbb.getString();
        var desc = cbb.getString();
        var type = cbb.get();
        var quality = cbb.get();
        var level = cbb.getInt();

        var temp = new ItemTemplate(id,name,desc,type,quality,level);
        return temp;
    }
}
