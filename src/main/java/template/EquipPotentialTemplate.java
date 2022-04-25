/* 由程序自动生成，请勿修改。*/
package template;

public record EquipPotentialTemplate(int id,byte quality,byte stage,int weight,byte grade){

    public static EquipPotentialTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var quality = cbb.get();
        var stage = cbb.get();
        var weight = cbb.getInt();
        var grade = cbb.get();

        var temp = new EquipPotentialTemplate(id,quality,stage,weight,grade);
        return temp;
    }
}
