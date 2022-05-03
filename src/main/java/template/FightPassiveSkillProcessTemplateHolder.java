/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class FightPassiveSkillProcessTemplateHolder {
	private static final HashMap<Integer, FightPassiveSkillProcessTemplate> hash = new HashMap<>();

	public static FightPassiveSkillProcessTemplate getData(int id){ return hash.get(id); }

	public static void addData(FightPassiveSkillProcessTemplate data){
		hash.put(data.id(),data);
	}

	public static List<FightPassiveSkillProcessTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
