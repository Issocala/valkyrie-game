/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class StrengthenTemplateHolder {
	private static final HashMap<Integer, StrengthenTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static StrengthenTemplate getData(int id){ return hash.get(id); }

	public static StrengthenTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "StrengthenTemplate not found id=" + id); }

	public static void addData(StrengthenTemplate data){
		hash.put(data.id(),data);
	}

	public static List<StrengthenTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
