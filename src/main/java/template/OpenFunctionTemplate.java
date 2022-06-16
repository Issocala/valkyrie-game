/* 由程序自动生成，请勿修改。*/
package template;

public record OpenFunctionTemplate(int id,int level,int transfer,int taskId,int openDay,int closeDay,int[] moneyShow){

    public static OpenFunctionTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var level = cbb.getInt();
        var transfer = cbb.getInt();
        var taskId = cbb.getInt();
        var openDay = cbb.getInt();
        var closeDay = cbb.getInt();
        var moneyShowLength = cbb.getInt();
        var moneyShow = new int[moneyShowLength];
        for (int i = 0; i < moneyShowLength; i++){
            moneyShow[i] = cbb.getInt();
        }

        var temp = new OpenFunctionTemplate(id,level,transfer,taskId,openDay,closeDay,moneyShow);
        return temp;
    }
}
