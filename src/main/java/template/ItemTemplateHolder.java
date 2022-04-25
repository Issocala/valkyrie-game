/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class ItemTemplateHolder {
	private static final HashMap<Integer, ItemTemplate> hash = new HashMap<>();

	public static ItemTemplate getData(int id){ return hash.get(id); }

	public static void addData(ItemTemplate data){
		hash.put(data.id(),data);
	}

	public static List<ItemTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
