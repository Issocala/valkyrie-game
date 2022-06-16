/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FightSkillTemplateHolder {
	private static final HashMap<Integer, FightSkillTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static FightSkillTemplate getData(int id){ return hash.get(id); }

	public static FightSkillTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "FightSkillTemplate not found id=" + id); }

	public static void addData(FightSkillTemplate data){
		hash.put(data.id(),data);
	}

	public static List<FightSkillTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
