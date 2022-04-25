/* 由程序自动生成，请勿修改。*/
package template;

public record EquipPotentialTemplate(int id,byte quality,byte step,int weight,byte grade){

    public static EquipPotentialTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var quality = cbb.get();
        var step = cbb.get();
        var weight = cbb.getInt();
        var grade = cbb.get();

        var temp = new EquipPotentialTemplate(id,quality,step,weight,grade);
        return temp;
    }
}
