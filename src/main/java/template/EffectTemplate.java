/* 由程序自动生成，请勿修改。*/
package template;

public record EffectTemplate(int id,long delayTime,String effectName,float[] offset,float[] scale,float[] rotation,float moveSpeed,long time,int loop,int loopTime,String bindTarget){

    public static EffectTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var delayTime = cbb.getLong();
        var effectName = cbb.getString();
        var offsetLength = cbb.getInt();
        var offset = new float[offsetLength];
        for (int i = 0; i < offsetLength; i++){
            offset[i] = cbb.getFloat();
        }
        var scaleLength = cbb.getInt();
        var scale = new float[scaleLength];
        for (int i = 0; i < scaleLength; i++){
            scale[i] = cbb.getFloat();
        }
        var rotationLength = cbb.getInt();
        var rotation = new float[rotationLength];
        for (int i = 0; i < rotationLength; i++){
            rotation[i] = cbb.getFloat();
        }
        var moveSpeed = cbb.getFloat();
        var time = cbb.getLong();
        var loop = cbb.getInt();
        var loopTime = cbb.getInt();
        var bindTarget = cbb.getString();

        var temp = new EffectTemplate(id,delayTime,effectName,offset,scale,rotation,moveSpeed,time,loop,loopTime,bindTarget);
        return temp;
    }
}
