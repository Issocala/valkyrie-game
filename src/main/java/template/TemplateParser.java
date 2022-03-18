package template;

public class TemplateParser {
    public static void parse(CustomByteBuffer cbb) {
        while (cbb.byteBuffer.hasRemaining()) {
            var tableName = cbb.getString();
            var length = cbb.getInt();
            for (int i = 0; i < length; i++) {
                switch (tableName) {
                    case "Test" -> TestTemplateHolder.addData(TestTemplate.parse(cbb));
                    case "SceneUnits" -> SceneUnitsTemplateHolder.addData(SceneUnitsTemplate.parse(cbb));
                    case "BuffData" -> BuffDataTemplateHolder.addData(BuffDataTemplate.parse(cbb));
                    case "SkillData" -> SkillDataTemplateHolder.addData(SkillDataTemplate.parse(cbb));
                    case "UnitData" -> UnitDataTemplateHolder.addData(UnitDataTemplate.parse(cbb));
                    case "SkillStep" -> SkillStepTemplateHolder.addData(SkillStepTemplate.parse(cbb));
                    case "UnitSelector" -> UnitSelectorTemplateHolder.addData(UnitSelectorTemplate.parse(cbb));
                    case "BaseFormula" -> BaseFormulaTemplateHolder.addData(BaseFormulaTemplate.parse(cbb));
                    case "InputOrder" -> InputOrderTemplateHolder.addData(InputOrderTemplate.parse(cbb));
                    case "UnitType" -> UnitTypeTemplateHolder.addData(UnitTypeTemplate.parse(cbb));
                    case "AIAction" -> AIActionTemplateHolder.addData(AIActionTemplate.parse(cbb));

                }
            }
        }
    }
}
