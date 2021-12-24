/* 由程序自动生成，请勿修改。*/
package template;

public record SkillDataTemplate(int id,String name,int[] buffs,int[] steps,int skill_level,float cold_down,int need_buff,int prohibit_buff){

    public static SkillDataTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var name = cbb.getString();
        var buffsLength = cbb.getInt();
        var buffs = new int[buffsLength];
        for (int i = 0; i < buffsLength; i++){
            buffs[i] = cbb.getInt();
        }
        var stepsLength = cbb.getInt();
        var steps = new int[stepsLength];
        for (int i = 0; i < stepsLength; i++){
            steps[i] = cbb.getInt();
        }
        var skill_level = cbb.getInt();
        var cold_down = cbb.getFloat();
        var need_buff = cbb.getInt();
        var prohibit_buff = cbb.getInt();

        var temp = new SkillDataTemplate(id,name,buffs,steps,skill_level,cold_down,need_buff,prohibit_buff);
        return temp;
    }
}

