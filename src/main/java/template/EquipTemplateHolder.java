/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class EquipTemplateHolder {
	private static final HashMap<Integer, EquipTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static EquipTemplate getData(int id){ return hash.get(id); }

	public static EquipTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "EquipTemplate not found id=" + id); }

	public static void addData(EquipTemplate data){
		hash.put(data.id(),data);
	}

	public static List<EquipTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
