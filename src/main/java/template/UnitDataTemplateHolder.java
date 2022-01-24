/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class UnitDataTemplateHolder {
	private static final HashMap<Integer, UnitDataTemplate> hash = new HashMap<>();

	public static UnitDataTemplate getData(int id){ return hash.get(id); }

	public static void addData(UnitDataTemplate data){
		hash.put(data.id(),data);
	}

	public static List<UnitDataTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
