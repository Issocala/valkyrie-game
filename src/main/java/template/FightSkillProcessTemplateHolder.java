/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class FightSkillProcessTemplateHolder {
	private static final HashMap<Integer, FightSkillProcessTemplate> hash = new HashMap<>();

	public static FightSkillProcessTemplate getData(int id){ return hash.get(id); }

	public static void addData(FightSkillProcessTemplate data){
		hash.put(data.id(),data);
	}

	public static List<FightSkillProcessTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
