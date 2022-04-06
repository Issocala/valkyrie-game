/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class FightBuffTemplateHolder {
	private static final HashMap<Integer, FightBuffTemplate> hash = new HashMap<>();

	public static FightBuffTemplate getData(int id){ return hash.get(id); }

	public static void addData(FightBuffTemplate data){
		hash.put(data.id(),data);
	}

	public static List<FightBuffTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
