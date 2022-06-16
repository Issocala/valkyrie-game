/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ItemTemplateHolder {
	private static final HashMap<Integer, ItemTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static ItemTemplate getData(int id){ return hash.get(id); }

	public static ItemTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "ItemTemplate not found id=" + id); }

	public static void addData(ItemTemplate data){
		hash.put(data.id(),data);
	}

	public static List<ItemTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
