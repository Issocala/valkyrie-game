package template;

import java.nio.*;
import java.nio.charset.StandardCharsets;

public class CustomByteBuffer {

    public ByteBuffer byteBuffer;
    public CustomByteBuffer(byte[] bytes){
        byteBuffer = ByteBuffer.allocate(bytes.length);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(bytes);
        byteBuffer.rewind();
    }

    public int getInt(){
        return byteBuffer.getInt();
    }

    public long getLong(){
        return byteBuffer.getLong();
    }

    public char getChar(){
        return byteBuffer.getChar();
    }

    public float getFloat(){
        return byteBuffer.getFloat();
    }

    public double getDouble(){
        return byteBuffer.getDouble();
    }

    public boolean getBoolean(){
        return  byteBuffer.get() != 0;
    }

    public byte get() { return byteBuffer.get(); }

    public short getShort() { return byteBuffer.getShort(); }

    public String getString(){

        int length = byteBuffer.getInt();
        byte[] bytes = new byte[length];
        byteBuffer.get(bytes,0,length);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
