/* 由程序自动生成，请勿修改。*/
package template;

public record FightSkillTemplate(int id,byte skillType,byte skillTargetType,byte skillAimType,long cdTime,int costHp,int costMp,String skillProcess){

    public static FightSkillTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var skillType = cbb.get();
        var skillTargetType = cbb.get();
        var skillAimType = cbb.get();
        var cdTime = cbb.getLong();
        var costHp = cbb.getInt();
        var costMp = cbb.getInt();
        var skillProcess = cbb.getString();

        var temp = new FightSkillTemplate(id,skillType,skillTargetType,skillAimType,cdTime,costHp,costMp,skillProcess);
        return temp;
    }
}
