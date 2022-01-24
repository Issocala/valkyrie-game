/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class InputOrderTemplateHolder {
	private static final HashMap<Integer, InputOrderTemplate> hash = new HashMap<>();

	public static InputOrderTemplate getData(int id){ return hash.get(id); }

	public static void addData(InputOrderTemplate data){
		hash.put(data.id(),data);
	}

	public static List<InputOrderTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
