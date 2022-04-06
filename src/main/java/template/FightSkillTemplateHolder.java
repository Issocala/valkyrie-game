/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class FightSkillTemplateHolder {
	private static final HashMap<Integer, FightSkillTemplate> hash = new HashMap<>();

	public static FightSkillTemplate getData(int id){ return hash.get(id); }

	public static void addData(FightSkillTemplate data){
		hash.put(data.id(),data);
	}

	public static List<FightSkillTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
