/* 由程序自动生成，请勿修改。*/
package template;

import java.util.HashMap;
import java.util.List;

public class UnitTypeTemplateHolder {
    private static final HashMap<Integer, UnitTypeTemplate> hash = new HashMap<>();

    public static UnitTypeTemplate getData(int id) {
        return hash.get(id);
    }

    public static void addData(UnitTypeTemplate data) {
        hash.put(data.id(), data);
    }

    public static List<UnitTypeTemplate> getValues() {
        return List.copyOf(hash.values());
    }
}
