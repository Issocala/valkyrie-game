/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GlobalTemplateHolder {
	private static final HashMap<Integer, GlobalTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static GlobalTemplate getData(int id){ return hash.get(id); }

	public static GlobalTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "GlobalTemplate not found id=" + id); }

	public static void addData(GlobalTemplate data){
		hash.put(data.id(),data);
	}

	public static List<GlobalTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
