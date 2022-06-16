/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SceneUnitsTemplateHolder {
	private static final HashMap<Integer, SceneUnitsTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static SceneUnitsTemplate getData(int id){ return hash.get(id); }

	public static SceneUnitsTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "SceneUnitsTemplate not found id=" + id); }

	public static void addData(SceneUnitsTemplate data){
		hash.put(data.id(),data);
	}

	public static List<SceneUnitsTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
