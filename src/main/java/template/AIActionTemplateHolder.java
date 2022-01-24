/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class AIActionTemplateHolder {
	private static final HashMap<Integer, AIActionTemplate> hash = new HashMap<>();

	public static AIActionTemplate getData(int id){ return hash.get(id); }

	public static void addData(AIActionTemplate data){
		hash.put(data.id(),data);
	}

	public static List<AIActionTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
