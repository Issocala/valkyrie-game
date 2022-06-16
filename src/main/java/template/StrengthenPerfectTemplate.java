/* 由程序自动生成，请勿修改。*/
package template;

public record StrengthenPerfectTemplate(int id,byte slot,int perfect,int[][] attr,int[][] polishAttr){

    public static StrengthenPerfectTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var slot = cbb.get();
        var perfect = cbb.getInt();
        var attrLength = cbb.getInt();
        var attr = new int[attrLength][];
        for (int i = 0; i < attrLength; i++){
            var tempLength = cbb.getInt();
            attr[i] = new int[tempLength];
            for (int j = 0; j < tempLength; j++){
                attr[i][j] = cbb.getInt();
            }
        }
        var polishAttrLength = cbb.getInt();
        var polishAttr = new int[polishAttrLength][];
        for (int i = 0; i < polishAttrLength; i++){
            var tempLength = cbb.getInt();
            polishAttr[i] = new int[tempLength];
            for (int j = 0; j < tempLength; j++){
                polishAttr[i][j] = cbb.getInt();
            }
        }

        var temp = new StrengthenPerfectTemplate(id,slot,perfect,attr,polishAttr);
        return temp;
    }
}
