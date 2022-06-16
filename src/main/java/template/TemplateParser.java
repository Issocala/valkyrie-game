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
                    case "OrganismData" -> OrganismDataTemplateHolder.addData(OrganismDataTemplate.parse(cbb));
                    case "SceneData" -> SceneDataTemplateHolder.addData(SceneDataTemplate.parse(cbb));
                    case "AttributeTree" -> AttributeTreeTemplateHolder.addData(AttributeTreeTemplate.parse(cbb));
                    case "Item" -> ItemTemplateHolder.addData(ItemTemplate.parse(cbb));
                    case "Equip" -> EquipTemplateHolder.addData(EquipTemplate.parse(cbb));
                    case "EquipPotential" -> EquipPotentialTemplateHolder.addData(EquipPotentialTemplate.parse(cbb));
                    case "FightPassiveSkill" -> FightPassiveSkillTemplateHolder.addData(FightPassiveSkillTemplate.parse(cbb));
                    case "FightPassiveSkillProcess" -> FightPassiveSkillProcessTemplateHolder.addData(FightPassiveSkillProcessTemplate.parse(cbb));
                    case "Attribute" -> AttributeTemplateHolder.addData(AttributeTemplate.parse(cbb));
                    case "Global" -> GlobalTemplateHolder.addData(GlobalTemplate.parse(cbb));
                    case "Strengthen" -> StrengthenTemplateHolder.addData(StrengthenTemplate.parse(cbb));
                    case "StrengthenPerfect" -> StrengthenPerfectTemplateHolder.addData(StrengthenPerfectTemplate.parse(cbb));
                    case "StrengthenPolish" -> StrengthenPolishTemplateHolder.addData(StrengthenPolishTemplate.parse(cbb));
                    case "StrengthenSuit" -> StrengthenSuitTemplateHolder.addData(StrengthenSuitTemplate.parse(cbb));
                    case "OpenFunction" -> OpenFunctionTemplateHolder.addData(OpenFunctionTemplate.parse(cbb));
                    case "ItemInitGain" -> ItemInitGainTemplateHolder.addData(ItemInitGainTemplate.parse(cbb));
                    case "DropGroup" -> DropGroupTemplateHolder.addData(DropGroupTemplate.parse(cbb));
                    case "DropItem" -> DropItemTemplateHolder.addData(DropItemTemplate.parse(cbb));
                    case "GameInstance" -> GameInstanceTemplateHolder.addData(GameInstanceTemplate.parse(cbb));
                    case "Trigger" -> TriggerTemplateHolder.addData(TriggerTemplate.parse(cbb));
                    case "TriggerConditions" -> TriggerConditionsTemplateHolder.addData(TriggerConditionsTemplate.parse(cbb));
                    case "TriggerActions" -> TriggerActionsTemplateHolder.addData(TriggerActionsTemplate.parse(cbb));

                }
            }
        }
    }
}
