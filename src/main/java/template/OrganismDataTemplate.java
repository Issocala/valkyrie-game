/* 由程序自动生成，请勿修改。*/
package template;

public record OrganismDataTemplate(int id,byte type,String attributeMap,int[] skills,int ai){

    public static OrganismDataTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var type = cbb.get();
        var attributeMap = cbb.getString();
        var skillsLength = cbb.getInt();
        var skills = new int[skillsLength];
        for (int i = 0; i < skillsLength; i++){
            skills[i] = cbb.getInt();
        }
        var ai = cbb.getInt();

        var temp = new OrganismDataTemplate(id,type,attributeMap,skills,ai);
        return temp;
    }
}
