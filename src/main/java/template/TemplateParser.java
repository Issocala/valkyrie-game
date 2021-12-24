package template;

public class TemplateParser {
    public static void parse(CustomByteBuffer cbb){
        while (cbb.byteBuffer.hasRemaining()){
            var tableName = cbb.getString();
            var length = cbb.getInt();
            for (int i = 0; i < length; i++) {
                switch(tableName) {
                    case "Test":
                        var TestTemp = TestTemplate.parse(cbb);
                        TestTemplateHolder.addData(TestTemp);
                        break;
                    case "SceneUnits":
                        var SceneUnitsTemp = SceneUnitsTemplate.parse(cbb);
                        SceneUnitsTemplateHolder.addData(SceneUnitsTemp);
                        break;
                    case "BuffData":
                        var BuffDataTemp = BuffDataTemplate.parse(cbb);
                        BuffDataTemplateHolder.addData(BuffDataTemp);
                        break;
                    case "SkillData":
                        var SkillDataTemp = SkillDataTemplate.parse(cbb);
                        SkillDataTemplateHolder.addData(SkillDataTemp);
                        break;
                    case "UnitData":
                        var UnitDataTemp = UnitDataTemplate.parse(cbb);
                        UnitDataTemplateHolder.addData(UnitDataTemp);
                        break;
                    case "SkillStep":
                        var SkillStepTemp = SkillStepTemplate.parse(cbb);
                        SkillStepTemplateHolder.addData(SkillStepTemp);
                        break;
                    case "UnitSelector":
                        var UnitSelectorTemp = UnitSelectorTemplate.parse(cbb);
                        UnitSelectorTemplateHolder.addData(UnitSelectorTemp);
                        break;
                    case "BaseFormula":
                        var BaseFormulaTemp = BaseFormulaTemplate.parse(cbb);
                        BaseFormulaTemplateHolder.addData(BaseFormulaTemp);
                        break;
                    case "InputOrder":
                        var InputOrderTemp = InputOrderTemplate.parse(cbb);
                        InputOrderTemplateHolder.addData(InputOrderTemp);
                        break;

                }
            }
        }
    }
}

