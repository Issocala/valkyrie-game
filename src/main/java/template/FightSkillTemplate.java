/* 由程序自动生成，请勿修改。*/
package template;

public record FightSkillTemplate(int id,byte skillType,byte skillTargetType,byte skillAimType,long cdTime,int costHp,int costMp,int[] skillProcess,float[] rectangle){

    public static FightSkillTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var skillType = cbb.get();
        var skillTargetType = cbb.get();
        var skillAimType = cbb.get();
        var cdTime = cbb.getLong();
        var costHp = cbb.getInt();
        var costMp = cbb.getInt();
        var skillProcessLength = cbb.getInt();
        var skillProcess = new int[skillProcessLength];
        for (int i = 0; i < skillProcessLength; i++){
            skillProcess[i] = cbb.getInt();
        }
        var rectangleLength = cbb.getInt();
        var rectangle = new float[rectangleLength];
        for (int i = 0; i < rectangleLength; i++){
            rectangle[i] = cbb.getFloat();
        }

        var temp = new FightSkillTemplate(id,skillType,skillTargetType,skillAimType,cdTime,costHp,costMp,skillProcess,rectangle);
        return temp;
    }
}
