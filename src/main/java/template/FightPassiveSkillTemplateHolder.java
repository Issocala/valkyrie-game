/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FightPassiveSkillTemplateHolder {
	private static final HashMap<Integer, FightPassiveSkillTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static FightPassiveSkillTemplate getData(int id){ return hash.get(id); }

	public static FightPassiveSkillTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "FightPassiveSkillTemplate not found id=" + id); }

	public static void addData(FightPassiveSkillTemplate data){
		hash.put(data.id(),data);
	}

	public static List<FightPassiveSkillTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
