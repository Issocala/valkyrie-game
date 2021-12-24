/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;

public class UnitSelectorTemplateHolder {
	private static HashMap<Integer, UnitSelectorTemplate> hash = new HashMap<>();

	public static UnitSelectorTemplate getData(int id){return hash.get(id);}

	public static void addData(UnitSelectorTemplate data){
		hash.put(data.id(),data);
	}
}

