/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class UnitDataTemplateHolder {
	private static final HashMap<Integer, UnitDataTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static UnitDataTemplate getData(int id){ return hash.get(id); }

	public static UnitDataTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "UnitDataTemplate not found id=" + id); }

	public static void addData(UnitDataTemplate data){
		hash.put(data.id(),data);
	}

	public static List<UnitDataTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
