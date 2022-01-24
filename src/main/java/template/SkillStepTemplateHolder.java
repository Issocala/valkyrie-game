/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class SkillStepTemplateHolder {
	private static final HashMap<Integer, SkillStepTemplate> hash = new HashMap<>();

	public static SkillStepTemplate getData(int id){ return hash.get(id); }

	public static void addData(SkillStepTemplate data){
		hash.put(data.id(),data);
	}

	public static List<SkillStepTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
