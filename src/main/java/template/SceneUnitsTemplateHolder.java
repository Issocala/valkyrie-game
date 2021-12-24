/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;

public class SceneUnitsTemplateHolder {
	private static HashMap<Integer, SceneUnitsTemplate> hash = new HashMap<>();

	public static SceneUnitsTemplate getData(int id){return hash.get(id);}

	public static void addData(SceneUnitsTemplate data){
		hash.put(data.id(),data);
	}
}

