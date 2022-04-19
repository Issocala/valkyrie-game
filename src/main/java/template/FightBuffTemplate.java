/* 由程序自动生成，请勿修改。*/
package template;

public record FightBuffTemplate(int id,byte buffType,int buffEffectType,int buffGroup,int buffLevel,int buffCount,long buffDelay,byte buffCover,int buffCoverCount,String attributeMap,String parameter,String buffProcess){

    public static FightBuffTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var buffType = cbb.get();
        var buffEffectType = cbb.getInt();
        var buffGroup = cbb.getInt();
        var buffLevel = cbb.getInt();
        var buffCount = cbb.getInt();
        var buffDelay = cbb.getLong();
        var buffCover = cbb.get();
        var buffCoverCount = cbb.getInt();
        var attributeMap = cbb.getString();
        var parameter = cbb.getString();
        var buffProcess = cbb.getString();

        var temp = new FightBuffTemplate(id,buffType,buffEffectType,buffGroup,buffLevel,buffCount,buffDelay,buffCover,buffCoverCount,attributeMap,parameter,buffProcess);
        return temp;
    }
}
