/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class AttributeTemplateHolder {
	private static final HashMap<Integer, AttributeTemplate> hash = new HashMap<>();

	public static AttributeTemplate getData(int id){ return hash.get(id); }

	public static void addData(AttributeTemplate data){
		hash.put(data.id(),data);
	}

	public static List<AttributeTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
