/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class OrganismDataTemplateHolder {
	private static final HashMap<Integer, OrganismDataTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static OrganismDataTemplate getData(int id){ return hash.get(id); }

	public static OrganismDataTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "OrganismDataTemplate not found id=" + id); }

	public static void addData(OrganismDataTemplate data){
		hash.put(data.id(),data);
	}

	public static List<OrganismDataTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
