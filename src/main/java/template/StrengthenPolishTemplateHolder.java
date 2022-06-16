/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class StrengthenPolishTemplateHolder {
	private static final HashMap<Integer, StrengthenPolishTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static StrengthenPolishTemplate getData(int id){ return hash.get(id); }

	public static StrengthenPolishTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "StrengthenPolishTemplate not found id=" + id); }

	public static void addData(StrengthenPolishTemplate data){
		hash.put(data.id(),data);
	}

	public static List<StrengthenPolishTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
