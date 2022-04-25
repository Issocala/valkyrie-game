/* 由程序自动生成，请勿修改。*/
package template;

public record EquipTemplate(int id,byte occupation,byte sex,byte slot,byte step,long[][] baseAttr,long[][] extraAttr,long[][] godAttr){

    public static EquipTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var occupation = cbb.get();
        var sex = cbb.get();
        var slot = cbb.get();
        var step = cbb.get();
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

        var temp = new EquipTemplate(id,occupation,sex,slot,step,baseAttr,extraAttr,godAttr);
        return temp;
    }
}
