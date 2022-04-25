package template;

public class TemplateParser {
    public static void parse(CustomByteBuffer cbb){
        while (cbb.byteBuffer.hasRemaining()){
            var tableName = cbb.getString();
            var length = cbb.getInt();
            for (int i = 0; i < length; i++) {
                switch(tableName) {
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
                    case "FightSkill" -> FightSkillTemplateHolder.addData(FightSkillTemplate.parse(cbb));
                    case "FightSkillProcess" -> FightSkillProcessTemplateHolder.addData(FightSkillProcessTemplate.parse(cbb));
                    case "FightBuff" -> FightBuffTemplateHolder.addData(FightBuffTemplate.parse(cbb));
                    case "Effect" -> EffectTemplateHolder.addData(EffectTemplate.parse(cbb));
                    case "OrganismData" -> OrganismDataTemplateHolder.addData(OrganismDataTemplate.parse(cbb));
                    case "SceneData" -> SceneDataTemplateHolder.addData(SceneDataTemplate.parse(cbb));
                    case "AttributeTree" -> AttributeTreeTemplateHolder.addData(AttributeTreeTemplate.parse(cbb));
                    case "Item" -> ItemTemplateHolder.addData(ItemTemplate.parse(cbb));
                    case "Equip" -> EquipTemplateHolder.addData(EquipTemplate.parse(cbb));

                }
            }
        }
    }
}
