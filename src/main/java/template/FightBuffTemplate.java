/* 由程序自动生成，请勿修改。*/
package template;

public record FightBuffTemplate(int id,byte buffType,byte buffPeriodType,int buffEffectType,int buffGroup,int buffLevel,long buffDelay,byte switchSceneRemove,byte deadRemove,byte logoutRemove,byte buffCoverType,int buffCoverCount,int[][] attributeMap,int[][] MaxCoverAttributeMap,String parameter,long duration,String buffProcess){

    public static FightBuffTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var buffType = cbb.get();
        var buffPeriodType = cbb.get();
        var buffEffectType = cbb.getInt();
        var buffGroup = cbb.getInt();
        var buffLevel = cbb.getInt();
        var buffDelay = cbb.getLong();
        var switchSceneRemove = cbb.get();
        var deadRemove = cbb.get();
        var logoutRemove = cbb.get();
        var buffCoverType = cbb.get();
        var buffCoverCount = cbb.getInt();
        var attributeMapLength = cbb.getInt();
        var attributeMap = new int[attributeMapLength][];
        for (int i = 0; i < attributeMapLength; i++){
            var tempLength = cbb.getInt();
            attributeMap[i] = new int[tempLength];
            for (int j = 0; j < tempLength; j++){
                attributeMap[i][j] = cbb.getInt();
            }
        }
        var MaxCoverAttributeMapLength = cbb.getInt();
        var MaxCoverAttributeMap = new int[MaxCoverAttributeMapLength][];
        for (int i = 0; i < MaxCoverAttributeMapLength; i++){
            var tempLength = cbb.getInt();
            MaxCoverAttributeMap[i] = new int[tempLength];
            for (int j = 0; j < tempLength; j++){
                MaxCoverAttributeMap[i][j] = cbb.getInt();
            }
        }
        var parameter = cbb.getString();
        var duration = cbb.getLong();
        var buffProcess = cbb.getString();

        var temp = new FightBuffTemplate(id,buffType,buffPeriodType,buffEffectType,buffGroup,buffLevel,buffDelay,switchSceneRemove,deadRemove,logoutRemove,buffCoverType,buffCoverCount,attributeMap,MaxCoverAttributeMap,parameter,duration,buffProcess);
        return temp;
    }
}
