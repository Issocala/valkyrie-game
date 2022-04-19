/* 由程序自动生成，请勿修改。*/
package template;

public record SceneDataTemplate(int id,byte type,int[] organisms){

    public static SceneDataTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var type = cbb.get();
        var organismsLength = cbb.getInt();
        var organisms = new int[organismsLength];
        for (int i = 0; i < organismsLength; i++){
            organisms[i] = cbb.getInt();
        }

        var temp = new SceneDataTemplate(id,type,organisms);
        return temp;
    }
}
