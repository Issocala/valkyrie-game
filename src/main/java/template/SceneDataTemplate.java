/* 由程序自动生成，请勿修改。*/
package template;

public record SceneDataTemplate(int id,byte type,int[] organisms,float[] birthPoint,int ai,int dropId){

    public static SceneDataTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var type = cbb.get();
        var organismsLength = cbb.getInt();
        var organisms = new int[organismsLength];
        for (int i = 0; i < organismsLength; i++){
            organisms[i] = cbb.getInt();
        }
        var birthPointLength = cbb.getInt();
        var birthPoint = new float[birthPointLength];
        for (int i = 0; i < birthPointLength; i++){
            birthPoint[i] = cbb.getFloat();
        }
        var ai = cbb.getInt();
        var dropId = cbb.getInt();

        var temp = new SceneDataTemplate(id,type,organisms,birthPoint,ai,dropId);
        return temp;
    }
}
