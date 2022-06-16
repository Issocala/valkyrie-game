/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SkillStepTemplateHolder {
	private static final HashMap<Integer, SkillStepTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static SkillStepTemplate getData(int id){ return hash.get(id); }

	public static SkillStepTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "SkillStepTemplate not found id=" + id); }

	public static void addData(SkillStepTemplate data){
		hash.put(data.id(),data);
	}

	public static List<SkillStepTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
