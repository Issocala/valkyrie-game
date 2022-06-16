/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class StrengthenSuitTemplateHolder {
	private static final HashMap<Integer, StrengthenSuitTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static StrengthenSuitTemplate getData(int id){ return hash.get(id); }

	public static StrengthenSuitTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "StrengthenSuitTemplate not found id=" + id); }

	public static void addData(StrengthenSuitTemplate data){
		hash.put(data.id(),data);
	}

	public static List<StrengthenSuitTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
