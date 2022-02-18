/* 由程序自动生成，请勿修改。*/
package template;

import java.util.HashMap;
import java.util.List;

public class AttributeTreeTemplateHolder {
	private static final HashMap<Integer, AttributeTreeTemplate> hash = new HashMap<>();

	public static AttributeTreeTemplate getData(int id) {
		return hash.get(id);
	}

	public static void addData(AttributeTreeTemplate data) {
		hash.put(data.id(), data);
	}

	public static List<AttributeTreeTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
