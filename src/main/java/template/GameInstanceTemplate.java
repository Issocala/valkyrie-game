/* 由程序自动生成，请勿修改。*/
package template;

public record GameInstanceTemplate(int id,int groupId,String layer,byte type,byte numType,String[] enterCondition,byte enterCount,String[] failedCondition,long leaveKeepTime,long kickoutTime,long duration,int sceneId,int[] taskList,int[] triggers){

    public static GameInstanceTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var groupId = cbb.getInt();
        var layer = cbb.getString();
        var type = cbb.get();
        var numType = cbb.get();
        var enterConditionLength = cbb.getInt();
        var enterCondition = new String[enterConditionLength];
        for (int i = 0; i < enterConditionLength; i++){
            enterCondition[i] = cbb.getString();
        }
        var enterCount = cbb.get();
        var failedConditionLength = cbb.getInt();
        var failedCondition = new String[failedConditionLength];
        for (int i = 0; i < failedConditionLength; i++){
            failedCondition[i] = cbb.getString();
        }
        var leaveKeepTime = cbb.getLong();
        var kickoutTime = cbb.getLong();
        var duration = cbb.getLong();
        var sceneId = cbb.getInt();
        var taskListLength = cbb.getInt();
        var taskList = new int[taskListLength];
        for (int i = 0; i < taskListLength; i++){
            taskList[i] = cbb.getInt();
        }
        var triggersLength = cbb.getInt();
        var triggers = new int[triggersLength];
        for (int i = 0; i < triggersLength; i++){
            triggers[i] = cbb.getInt();
        }

        var temp = new GameInstanceTemplate(id,groupId,layer,type,numType,enterCondition,enterCount,failedCondition,leaveKeepTime,kickoutTime,duration,sceneId,taskList,triggers);
        return temp;
    }
}
