/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class SceneDataTemplateHolder {
	private static final HashMap<Integer, SceneDataTemplate> hash = new HashMap<>();

	public static SceneDataTemplate getData(int id){ return hash.get(id); }

	public static void addData(SceneDataTemplate data){
		hash.put(data.id(),data);
	}

	public static List<SceneDataTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
