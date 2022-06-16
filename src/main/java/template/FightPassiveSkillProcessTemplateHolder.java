/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FightPassiveSkillProcessTemplateHolder {
	private static final HashMap<Integer, FightPassiveSkillProcessTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static FightPassiveSkillProcessTemplate getData(int id){ return hash.get(id); }

	public static FightPassiveSkillProcessTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "FightPassiveSkillProcessTemplate not found id=" + id); }

	public static void addData(FightPassiveSkillProcessTemplate data){
		hash.put(data.id(),data);
	}

	public static List<FightPassiveSkillProcessTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
