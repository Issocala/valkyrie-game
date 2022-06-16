/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TriggerConditionsTemplateHolder {
	private static final HashMap<Integer, TriggerConditionsTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static TriggerConditionsTemplate getData(int id){ return hash.get(id); }

	public static TriggerConditionsTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "TriggerConditionsTemplate not found id=" + id); }

	public static void addData(TriggerConditionsTemplate data){
		hash.put(data.id(),data);
	}

	public static List<TriggerConditionsTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
