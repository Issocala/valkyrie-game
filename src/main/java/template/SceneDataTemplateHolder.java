/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SceneDataTemplateHolder {
	private static final HashMap<Integer, SceneDataTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static SceneDataTemplate getData(int id){ return hash.get(id); }

	public static SceneDataTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "SceneDataTemplate not found id=" + id); }

	public static void addData(SceneDataTemplate data){
		hash.put(data.id(),data);
	}

	public static List<SceneDataTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
