/* 由程序自动生成，请勿修改。*/
package template;

public record AttributeTemplate(int id,int ratio,float baseCal,float extraCal,int attributeType,int attributeDisplay,int order){

    public static AttributeTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var ratio = cbb.getInt();
        var baseCal = cbb.getFloat();
        var extraCal = cbb.getFloat();
        var attributeType = cbb.getInt();
        var attributeDisplay = cbb.getInt();
        var order = cbb.getInt();

        var temp = new AttributeTemplate(id,ratio,baseCal,extraCal,attributeType,attributeDisplay,order);
        return temp;
    }
}
