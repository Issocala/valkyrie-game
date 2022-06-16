/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ItemInitGainTemplateHolder {
	private static final HashMap<Integer, ItemInitGainTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static ItemInitGainTemplate getData(int id){ return hash.get(id); }

	public static ItemInitGainTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "ItemInitGainTemplate not found id=" + id); }

	public static void addData(ItemInitGainTemplate data){
		hash.put(data.id(),data);
	}

	public static List<ItemInitGainTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
