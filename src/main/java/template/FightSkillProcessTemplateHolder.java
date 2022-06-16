/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FightSkillProcessTemplateHolder {
	private static final HashMap<Integer, FightSkillProcessTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static FightSkillProcessTemplate getData(int id){ return hash.get(id); }

	public static FightSkillProcessTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "FightSkillProcessTemplate not found id=" + id); }

	public static void addData(FightSkillProcessTemplate data){
		hash.put(data.id(),data);
	}

	public static List<FightSkillProcessTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
