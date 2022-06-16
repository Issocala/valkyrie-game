/* 由程序自动生成，请勿修改。*/
package template;

public record StrengthenTemplate(int id,byte slot,int strengthenLevel,int maxPerfect,int[][] strengthenAttr,int[][] strengthenCost,int[][] addPerfect,byte needEquipStage){

    public static StrengthenTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var slot = cbb.get();
        var strengthenLevel = cbb.getInt();
        var maxPerfect = cbb.getInt();
        var strengthenAttrLength = cbb.getInt();
        var strengthenAttr = new int[strengthenAttrLength][];
        for (int i = 0; i < strengthenAttrLength; i++){
            var tempLength = cbb.getInt();
            strengthenAttr[i] = new int[tempLength];
            for (int j = 0; j < tempLength; j++){
                strengthenAttr[i][j] = cbb.getInt();
            }
        }
        var strengthenCostLength = cbb.getInt();
        var strengthenCost = new int[strengthenCostLength][];
        for (int i = 0; i < strengthenCostLength; i++){
            var tempLength = cbb.getInt();
            strengthenCost[i] = new int[tempLength];
            for (int j = 0; j < tempLength; j++){
                strengthenCost[i][j] = cbb.getInt();
            }
        }
        var addPerfectLength = cbb.getInt();
        var addPerfect = new int[addPerfectLength][];
        for (int i = 0; i < addPerfectLength; i++){
            var tempLength = cbb.getInt();
            addPerfect[i] = new int[tempLength];
            for (int j = 0; j < tempLength; j++){
                addPerfect[i][j] = cbb.getInt();
            }
        }
        var needEquipStage = cbb.get();

        var temp = new StrengthenTemplate(id,slot,strengthenLevel,maxPerfect,strengthenAttr,strengthenCost,addPerfect,needEquipStage);
        return temp;
    }
}
