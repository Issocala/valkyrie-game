/* 由程序自动生成，请勿修改。*/
package template;

public record StrengthenSuitTemplate(int id,int strengthenLevel,int[][] attr){

    public static StrengthenSuitTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var strengthenLevel = cbb.getInt();
        var attrLength = cbb.getInt();
        var attr = new int[attrLength][];
        for (int i = 0; i < attrLength; i++){
            var tempLength = cbb.getInt();
            attr[i] = new int[tempLength];
            for (int j = 0; j < tempLength; j++){
                attr[i][j] = cbb.getInt();
            }
        }

        var temp = new StrengthenSuitTemplate(id,strengthenLevel,attr);
        return temp;
    }
}
