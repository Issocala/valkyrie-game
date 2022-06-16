/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AttributeTemplateHolder {
	private static final HashMap<Integer, AttributeTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static AttributeTemplate getData(int id){ return hash.get(id); }

	public static AttributeTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "AttributeTemplate not found id=" + id); }

	public static void addData(AttributeTemplate data){
		hash.put(data.id(),data);
	}

	public static List<AttributeTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
