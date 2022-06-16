/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AIActionTemplateHolder {
	private static final HashMap<Integer, AIActionTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static AIActionTemplate getData(int id){ return hash.get(id); }

	public static AIActionTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "AIActionTemplate not found id=" + id); }

	public static void addData(AIActionTemplate data){
		hash.put(data.id(),data);
	}

	public static List<AIActionTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
