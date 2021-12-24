/* 由程序自动生成，请勿修改。*/
package template;

public record BaseFormulaTemplate(int id,String formula){

    public static BaseFormulaTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var formula = cbb.getString();

        var temp = new BaseFormulaTemplate(id,formula);
        return temp;
    }
}

