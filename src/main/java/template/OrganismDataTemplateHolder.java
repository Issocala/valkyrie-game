/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class OrganismDataTemplateHolder {
	private static final HashMap<Integer, OrganismDataTemplate> hash = new HashMap<>();

	public static OrganismDataTemplate getData(int id){ return hash.get(id); }

	public static void addData(OrganismDataTemplate data){
		hash.put(data.id(),data);
	}

	public static List<OrganismDataTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
