/* 由程序自动生成，请勿修改。*/
package template;

public record GlobalTemplate(int id,int value1,String value2,int[] value3,String[] value4,int[][] value5,String[][] value6){

    public static GlobalTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var value1 = cbb.getInt();
        var value2 = cbb.getString();
        var value3Length = cbb.getInt();
        var value3 = new int[value3Length];
        for (int i = 0; i < value3Length; i++){
            value3[i] = cbb.getInt();
        }
        var value4Length = cbb.getInt();
        var value4 = new String[value4Length];
        for (int i = 0; i < value4Length; i++){
            value4[i] = cbb.getString();
        }
        var value5Length = cbb.getInt();
        var value5 = new int[value5Length][];
        for (int i = 0; i < value5Length; i++){
            var tempLength = cbb.getInt();
            value5[i] = new int[tempLength];
            for (int j = 0; j < tempLength; j++){
                value5[i][j] = cbb.getInt();
            }
        }
        var value6Length = cbb.getInt();
        var value6 = new String[value6Length][];
        for (int i = 0; i < value6Length; i++){
            var tempLength = cbb.getInt();
            value6[i] = new String[tempLength];
            for (int j = 0; j < tempLength; j++){
                value6[i][j] = cbb.getString();
            }
        }

        var temp = new GlobalTemplate(id,value1,value2,value3,value4,value5,value6);
        return temp;
    }
}
