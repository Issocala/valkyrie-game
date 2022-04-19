/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class EffectTemplateHolder {
	private static final HashMap<Integer, EffectTemplate> hash = new HashMap<>();

	public static EffectTemplate getData(int id){ return hash.get(id); }

	public static void addData(EffectTemplate data){
		hash.put(data.id(),data);
	}

	public static List<EffectTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
