/* 由程序自动生成，请勿修改。*/
package template;

public record DropGroupTemplate(int id,int[][] times,int type,int[] indexItem){

    public static DropGroupTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var timesLength = cbb.getInt();
        var times = new int[timesLength][];
        for (int i = 0; i < timesLength; i++){
            var tempLength = cbb.getInt();
            times[i] = new int[tempLength];
            for (int j = 0; j < tempLength; j++){
                times[i][j] = cbb.getInt();
            }
        }
        var type = cbb.getInt();
        var indexItemLength = cbb.getInt();
        var indexItem = new int[indexItemLength];
        for (int i = 0; i < indexItemLength; i++){
            indexItem[i] = cbb.getInt();
        }

        var temp = new DropGroupTemplate(id,times,type,indexItem);
        return temp;
    }
}
