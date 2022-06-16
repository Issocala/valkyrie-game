/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class OpenFunctionTemplateHolder {
	private static final HashMap<Integer, OpenFunctionTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static OpenFunctionTemplate getData(int id){ return hash.get(id); }

	public static OpenFunctionTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "OpenFunctionTemplate not found id=" + id); }

	public static void addData(OpenFunctionTemplate data){
		hash.put(data.id(),data);
	}

	public static List<OpenFunctionTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
