/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TriggerActionsTemplateHolder {
	private static final HashMap<Integer, TriggerActionsTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static TriggerActionsTemplate getData(int id){ return hash.get(id); }

	public static TriggerActionsTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "TriggerActionsTemplate not found id=" + id); }

	public static void addData(TriggerActionsTemplate data){
		hash.put(data.id(),data);
	}

	public static List<TriggerActionsTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
