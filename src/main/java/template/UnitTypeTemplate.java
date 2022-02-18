/* 由程序自动生成，请勿修改。*/
package template;

public record UnitTypeTemplate(int id, String name, int[] skills) {

    public static UnitTypeTemplate parse(CustomByteBuffer cbb) {
        var id = cbb.getInt();
        var name = cbb.getString();
        var skillsLength = cbb.getInt();
        var skills = new int[skillsLength];
        for (int i = 0; i < skillsLength; i++) {
            skills[i] = cbb.getInt();
        }

        var temp = new UnitTypeTemplate(id, name, skills);
        return temp;
    }
}
