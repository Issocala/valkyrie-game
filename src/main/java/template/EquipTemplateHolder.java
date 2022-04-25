/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class EquipTemplateHolder {
	private static final HashMap<Integer, EquipTemplate> hash = new HashMap<>();

	public static EquipTemplate getData(int id){ return hash.get(id); }

	public static void addData(EquipTemplate data){
		hash.put(data.id(),data);
	}

	public static List<EquipTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
