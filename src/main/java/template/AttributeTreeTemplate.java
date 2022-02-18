/* 由程序自动生成，请勿修改。*/
package template;

public record AttributeTreeTemplate(int id, int fatherNodeId, String className) {

    public static AttributeTreeTemplate parse(CustomByteBuffer cbb) {
        var id = cbb.getInt();
        var fatherNodeId = cbb.getInt();
        var className = cbb.getString();

        var temp = new AttributeTreeTemplate(id, fatherNodeId, className);
        return temp;
    }
}
