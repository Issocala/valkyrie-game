/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class FightPassiveSkillTemplateHolder {
	private static final HashMap<Integer, FightPassiveSkillTemplate> hash = new HashMap<>();

	public static FightPassiveSkillTemplate getData(int id){ return hash.get(id); }

	public static void addData(FightPassiveSkillTemplate data){
		hash.put(data.id(),data);
	}

	public static List<FightPassiveSkillTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
