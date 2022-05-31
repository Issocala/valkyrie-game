/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class GlobalTemplateHolder {
	private static final HashMap<Integer, GlobalTemplate> hash = new HashMap<>();

	public static GlobalTemplate getData(int id){ return hash.get(id); }

	public static void addData(GlobalTemplate data){
		hash.put(data.id(),data);
	}

	public static List<GlobalTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
