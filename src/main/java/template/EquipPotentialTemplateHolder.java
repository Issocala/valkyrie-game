/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class EquipPotentialTemplateHolder {
	private static final HashMap<Integer, EquipPotentialTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static EquipPotentialTemplate getData(int id){ return hash.get(id); }

	public static EquipPotentialTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "EquipPotentialTemplate not found id=" + id); }

	public static void addData(EquipPotentialTemplate data){
		hash.put(data.id(),data);
	}

	public static List<EquipPotentialTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
