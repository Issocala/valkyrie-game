/* 由程序自动生成，请勿修改。*/
package template;

public record ItemInitGainTemplate(int id,int[][] items){

    public static ItemInitGainTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var itemsLength = cbb.getInt();
        var items = new int[itemsLength][];
        for (int i = 0; i < itemsLength; i++){
            var tempLength = cbb.getInt();
            items[i] = new int[tempLength];
            for (int j = 0; j < tempLength; j++){
                items[i][j] = cbb.getInt();
            }
        }

        var temp = new ItemInitGainTemplate(id,items);
        return temp;
    }
}
