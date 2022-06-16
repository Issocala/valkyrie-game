/* 由程序自动生成，请勿修改。*/
package template;

public record TriggerTemplate(int id,String[] triggerEvent,int[] triggerCondition,int[] triggerAction,int triggerCount){

    public static TriggerTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var triggerEventLength = cbb.getInt();
        var triggerEvent = new String[triggerEventLength];
        for (int i = 0; i < triggerEventLength; i++){
            triggerEvent[i] = cbb.getString();
        }
        var triggerConditionLength = cbb.getInt();
        var triggerCondition = new int[triggerConditionLength];
        for (int i = 0; i < triggerConditionLength; i++){
            triggerCondition[i] = cbb.getInt();
        }
        var triggerActionLength = cbb.getInt();
        var triggerAction = new int[triggerActionLength];
        for (int i = 0; i < triggerActionLength; i++){
            triggerAction[i] = cbb.getInt();
        }
        var triggerCount = cbb.getInt();

        var temp = new TriggerTemplate(id,triggerEvent,triggerCondition,triggerAction,triggerCount);
        return temp;
    }
}
