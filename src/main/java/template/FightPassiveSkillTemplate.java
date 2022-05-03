/* 由程序自动生成，请勿修改。*/
package template;

public record FightPassiveSkillTemplate(int id,byte skillType,byte skillTriggerType,long cdTime,short weight,int costHp,int costMp,int[] skillProcess){

    public static FightPassiveSkillTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var skillType = cbb.get();
        var skillTriggerType = cbb.get();
        var cdTime = cbb.getLong();
        var weight = cbb.getShort();
        var costHp = cbb.getInt();
        var costMp = cbb.getInt();
        var skillProcessLength = cbb.getInt();
        var skillProcess = new int[skillProcessLength];
        for (int i = 0; i < skillProcessLength; i++){
            skillProcess[i] = cbb.getInt();
        }

        var temp = new FightPassiveSkillTemplate(id,skillType,skillTriggerType,cdTime,weight,costHp,costMp,skillProcess);
        return temp;
    }
}
