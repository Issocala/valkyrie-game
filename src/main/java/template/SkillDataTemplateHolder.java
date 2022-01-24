/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class SkillDataTemplateHolder {
	private static final HashMap<Integer, SkillDataTemplate> hash = new HashMap<>();

	public static SkillDataTemplate getData(int id){ return hash.get(id); }

	public static void addData(SkillDataTemplate data){
		hash.put(data.id(),data);
	}

	public static List<SkillDataTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
