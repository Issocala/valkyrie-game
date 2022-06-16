/* 由程序自动生成，请勿修改。*/
package template;

public record TaskListTemplate(int id,String[][] condition,int[][] reward){

    public static TaskListTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var conditionLength = cbb.getInt();
        var condition = new String[conditionLength][];
        for (int i = 0; i < conditionLength; i++){
            var tempLength = cbb.getInt();
            condition[i] = new String[tempLength];
            for (int j = 0; j < tempLength; j++){
                condition[i][j] = cbb.getString();
            }
        }
        var rewardLength = cbb.getInt();
        var reward = new int[rewardLength][];
        for (int i = 0; i < rewardLength; i++){
            var tempLength = cbb.getInt();
            reward[i] = new int[tempLength];
            for (int j = 0; j < tempLength; j++){
                reward[i][j] = cbb.getInt();
            }
        }

        var temp = new TaskListTemplate(id,condition,reward);
        return temp;
    }
}
