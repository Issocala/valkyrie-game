/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class UnitSelectorTemplateHolder {
	private static final HashMap<Integer, UnitSelectorTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static UnitSelectorTemplate getData(int id){ return hash.get(id); }

	public static UnitSelectorTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "UnitSelectorTemplate not found id=" + id); }

	public static void addData(UnitSelectorTemplate data){
		hash.put(data.id(),data);
	}

	public static List<UnitSelectorTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
