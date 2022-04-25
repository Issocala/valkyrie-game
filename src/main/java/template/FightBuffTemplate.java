/* 由程序自动生成，请勿修改。*/
package template;

public record FightBuffTemplate(int id,byte buffType,byte buffPeriodType,int buffEffectType,int buffGroup,int buffLevel,long buffDelay,byte buffCoverType,int buffCoverCount,String attributeMap,String MaxCoverAttributeMap,String parameter,long duration,String buffProcess){

    public static FightBuffTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var buffType = cbb.get();
        var buffPeriodType = cbb.get();
        var buffEffectType = cbb.getInt();
        var buffGroup = cbb.getInt();
        var buffLevel = cbb.getInt();
        var buffDelay = cbb.getLong();
        var buffCoverType = cbb.get();
        var buffCoverCount = cbb.getInt();
        var attributeMap = cbb.getString();
        var MaxCoverAttributeMap = cbb.getString();
        var parameter = cbb.getString();
        var duration = cbb.getLong();
        var buffProcess = cbb.getString();

        var temp = new FightBuffTemplate(id,buffType,buffPeriodType,buffEffectType,buffGroup,buffLevel,buffDelay,buffCoverType,buffCoverCount,attributeMap,MaxCoverAttributeMap,parameter,duration,buffProcess);
        return temp;
    }
}
