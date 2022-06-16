/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BuffDataTemplateHolder {
	private static final HashMap<Integer, BuffDataTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static BuffDataTemplate getData(int id){ return hash.get(id); }

	public static BuffDataTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "BuffDataTemplate not found id=" + id); }

	public static void addData(BuffDataTemplate data){
		hash.put(data.id(),data);
	}

	public static List<BuffDataTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
