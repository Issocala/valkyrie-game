/* 由程序自动生成，请勿修改。*/
package template;

public record TestTemplate(int id,String type){

    public static TestTemplate parse(CustomByteBuffer cbb){
        var id = cbb.getInt();
        var type = cbb.getString();

        var temp = new TestTemplate(id,type);
        return temp;
    }
}

