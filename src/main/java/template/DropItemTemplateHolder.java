/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DropItemTemplateHolder {
	private static final HashMap<Integer, DropItemTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static DropItemTemplate getData(int id){ return hash.get(id); }

	public static DropItemTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "DropItemTemplate not found id=" + id); }

	public static void addData(DropItemTemplate data){
		hash.put(data.id(),data);
	}

	public static List<DropItemTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
