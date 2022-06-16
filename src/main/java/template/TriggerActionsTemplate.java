/* 由程序自动生成，请勿修改。*/
package template;

public record TriggerActionsTemplate(int id,String data,String function){

    public static TriggerActionsTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var data = cbb.getString();
        var function = cbb.getString();

        var temp = new TriggerActionsTemplate(id,data,function);
        return temp;
    }
}
