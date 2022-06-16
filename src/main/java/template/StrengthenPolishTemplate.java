/* 由程序自动生成，请勿修改。*/
package template;

public record StrengthenPolishTemplate(int id,byte slot,int strengthenStep,int strengthenMaxLevel,int needPerfect,int[][] polishCost,int[][] addPerfect,int loseProtect){

    public static StrengthenPolishTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var slot = cbb.get();
        var strengthenStep = cbb.getInt();
        var strengthenMaxLevel = cbb.getInt();
        var needPerfect = cbb.getInt();
        var polishCostLength = cbb.getInt();
        var polishCost = new int[polishCostLength][];
        for (int i = 0; i < polishCostLength; i++){
            var tempLength = cbb.getInt();
            polishCost[i] = new int[tempLength];
            for (int j = 0; j < tempLength; j++){
                polishCost[i][j] = cbb.getInt();
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
        var loseProtect = cbb.getInt();

        var temp = new StrengthenPolishTemplate(id,slot,strengthenStep,strengthenMaxLevel,needPerfect,polishCost,addPerfect,loseProtect);
        return temp;
    }
}
