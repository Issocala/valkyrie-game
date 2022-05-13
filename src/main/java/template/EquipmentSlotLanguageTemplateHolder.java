/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class EquipmentSlotLanguageTemplateHolder {
	private static final HashMap<Integer, EquipmentSlotLanguageTemplate> hash = new HashMap<>();

	public static EquipmentSlotLanguageTemplate getData(int id){ return hash.get(id); }

	public static void addData(EquipmentSlotLanguageTemplate data){
		hash.put(data.id(),data);
	}

	public static List<EquipmentSlotLanguageTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
