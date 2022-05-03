/* 由程序自动生成，请勿修改。*/
package template;

public record FightPassiveSkillProcessTemplate(int id,long delayTime,byte skillTargetType,byte skillAimType,String parameter,String attributeParameter,int[][] learnAttributeMap,String function){

    public static FightPassiveSkillProcessTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var delayTime = cbb.getLong();
        var skillTargetType = cbb.get();
        var skillAimType = cbb.get();
        var parameter = cbb.getString();
        var attributeParameter = cbb.getString();
        var learnAttributeMapLength = cbb.getInt();
        var learnAttributeMap = new int[learnAttributeMapLength][];
        for (int i = 0; i < learnAttributeMapLength; i++){
            var tempLength = cbb.getInt();
            learnAttributeMap[i] = new int[tempLength];
            for (int j = 0; j < tempLength; j++){
                learnAttributeMap[i][j] = cbb.getInt();
            }
        }
        var function = cbb.getString();

        var temp = new FightPassiveSkillProcessTemplate(id,delayTime,skillTargetType,skillAimType,parameter,attributeParameter,learnAttributeMap,function);
        return temp;
    }
}
