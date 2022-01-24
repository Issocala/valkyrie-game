/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class SceneUnitsTemplateHolder {
	private static final HashMap<Integer, SceneUnitsTemplate> hash = new HashMap<>();

	public static SceneUnitsTemplate getData(int id){ return hash.get(id); }

	public static void addData(SceneUnitsTemplate data){
		hash.put(data.id(),data);
	}

	public static List<SceneUnitsTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
