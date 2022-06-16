/* 由程序自动生成，请勿修改。*/
package template;

public record FightSkillProcessTemplate(int id,long delayTime,byte skillTargetType,byte skillAimType,String parameter,String attributeParameter,String function,byte triggerNum,int[] hitSound){

    public static FightSkillProcessTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var delayTime = cbb.getLong();
        var skillTargetType = cbb.get();
        var skillAimType = cbb.get();
        var parameter = cbb.getString();
        var attributeParameter = cbb.getString();
        var function = cbb.getString();
        var triggerNum = cbb.get();
        var hitSoundLength = cbb.getInt();
        var hitSound = new int[hitSoundLength];
        for (int i = 0; i < hitSoundLength; i++){
            hitSound[i] = cbb.getInt();
        }

        var temp = new FightSkillProcessTemplate(id,delayTime,skillTargetType,skillAimType,parameter,attributeParameter,function,triggerNum,hitSound);
        return temp;
    }
}
