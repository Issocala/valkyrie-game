/* 由程序自动生成，请勿修改。*/
package template;

public record EquipTemplate(int id,byte stage,byte slot,byte step,byte profession,byte gender,long[][] baseAttr,long[][] extraAttr,long[][] godAttr,long[][] potentialAttr){

    public static EquipTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var stage = cbb.get();
        var slot = cbb.get();
        var step = cbb.get();
        var profession = cbb.get();
        var gender = cbb.get();
        var baseAttrLength = cbb.getInt();
        var baseAttr = new long[baseAttrLength][];
        for (int i = 0; i < baseAttrLength; i++){
            var tempLength = cbb.getInt();
            baseAttr[i] = new long[tempLength];
            for (int j = 0; j < tempLength; j++){
                baseAttr[i][j] = cbb.getLong();
            }
        }
        var extraAttrLength = cbb.getInt();
        var extraAttr = new long[extraAttrLength][];
        for (int i = 0; i < extraAttrLength; i++){
            var tempLength = cbb.getInt();
            extraAttr[i] = new long[tempLength];
            for (int j = 0; j < tempLength; j++){
                extraAttr[i][j] = cbb.getLong();
            }
        }
        var godAttrLength = cbb.getInt();
        var godAttr = new long[godAttrLength][];
        for (int i = 0; i < godAttrLength; i++){
            var tempLength = cbb.getInt();
            godAttr[i] = new long[tempLength];
            for (int j = 0; j < tempLength; j++){
                godAttr[i][j] = cbb.getLong();
            }
        }
        var potentialAttrLength = cbb.getInt();
        var potentialAttr = new long[potentialAttrLength][];
        for (int i = 0; i < potentialAttrLength; i++){
            var tempLength = cbb.getInt();
            potentialAttr[i] = new long[tempLength];
            for (int j = 0; j < tempLength; j++){
                potentialAttr[i][j] = cbb.getLong();
            }
        }

        var temp = new EquipTemplate(id,stage,slot,step,profession,gender,baseAttr,extraAttr,godAttr,potentialAttr);
        return temp;
    }
}
