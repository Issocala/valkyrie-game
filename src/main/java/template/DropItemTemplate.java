/* 由程序自动生成，请勿修改。*/
package template;

public record DropItemTemplate(int id,int groupId,int dropType,int itemId,int dropNum,int[][] limit,int weight,int[][] news){

    public static DropItemTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var groupId = cbb.getInt();
        var dropType = cbb.getInt();
        var itemId = cbb.getInt();
        var dropNum = cbb.getInt();
        var limitLength = cbb.getInt();
        var limit = new int[limitLength][];
        for (int i = 0; i < limitLength; i++){
            var tempLength = cbb.getInt();
            limit[i] = new int[tempLength];
            for (int j = 0; j < tempLength; j++){
                limit[i][j] = cbb.getInt();
            }
        }
        var weight = cbb.getInt();
        var newsLength = cbb.getInt();
        var news = new int[newsLength][];
        for (int i = 0; i < newsLength; i++){
            var tempLength = cbb.getInt();
            news[i] = new int[tempLength];
            for (int j = 0; j < tempLength; j++){
                news[i][j] = cbb.getInt();
            }
        }

        var temp = new DropItemTemplate(id,groupId,dropType,itemId,dropNum,limit,weight,news);
        return temp;
    }
}
