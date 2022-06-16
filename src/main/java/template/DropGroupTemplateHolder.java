/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DropGroupTemplateHolder {
	private static final HashMap<Integer, DropGroupTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static DropGroupTemplate getData(int id){ return hash.get(id); }

	public static DropGroupTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "DropGroupTemplate not found id=" + id); }

	public static void addData(DropGroupTemplate data){
		hash.put(data.id(),data);
	}

	public static List<DropGroupTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
