/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class InputOrderTemplateHolder {
	private static final HashMap<Integer, InputOrderTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static InputOrderTemplate getData(int id){ return hash.get(id); }

	public static InputOrderTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "InputOrderTemplate not found id=" + id); }

	public static void addData(InputOrderTemplate data){
		hash.put(data.id(),data);
	}

	public static List<InputOrderTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
