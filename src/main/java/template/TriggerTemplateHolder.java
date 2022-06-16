/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TriggerTemplateHolder {
	private static final HashMap<Integer, TriggerTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static TriggerTemplate getData(int id){ return hash.get(id); }

	public static TriggerTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "TriggerTemplate not found id=" + id); }

	public static void addData(TriggerTemplate data){
		hash.put(data.id(),data);
	}

	public static List<TriggerTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
