/* 由程序自动生成，请勿修改。*/
package template;

public record ItemTemplate(int id,byte type,byte quality,int level){

    public static ItemTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var type = cbb.get();
        var quality = cbb.get();
        var level = cbb.getInt();

        var temp = new ItemTemplate(id,type,quality,level);
        return temp;
    }
}
