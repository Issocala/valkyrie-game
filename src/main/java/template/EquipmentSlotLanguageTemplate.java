/* 由程序自动生成，请勿修改。*/
package template;

public record EquipmentSlotLanguageTemplate(int id){

    public static EquipmentSlotLanguageTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();

        var temp = new EquipmentSlotLanguageTemplate(id);
        return temp;
    }
}
