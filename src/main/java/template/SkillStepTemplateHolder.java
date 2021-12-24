/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;

public class SkillStepTemplateHolder {
	private static HashMap<Integer, SkillStepTemplate> hash = new HashMap<>();

	public static SkillStepTemplate getData(int id){return hash.get(id);}

	public static void addData(SkillStepTemplate data){
		hash.put(data.id(),data);
	}
}

