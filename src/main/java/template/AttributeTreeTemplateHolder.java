/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AttributeTreeTemplateHolder {
	private static final HashMap<Integer, AttributeTreeTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static AttributeTreeTemplate getData(int id){ return hash.get(id); }

	public static AttributeTreeTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "AttributeTreeTemplate not found id=" + id); }

	public static void addData(AttributeTreeTemplate data){
		hash.put(data.id(),data);
	}

	public static List<AttributeTreeTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
