/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class StrengthenPerfectTemplateHolder {
	private static final HashMap<Integer, StrengthenPerfectTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static StrengthenPerfectTemplate getData(int id){ return hash.get(id); }

	public static StrengthenPerfectTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "StrengthenPerfectTemplate not found id=" + id); }

	public static void addData(StrengthenPerfectTemplate data){
		hash.put(data.id(),data);
	}

	public static List<StrengthenPerfectTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
