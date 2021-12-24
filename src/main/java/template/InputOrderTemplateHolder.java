/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;

public class InputOrderTemplateHolder {
	private static HashMap<Integer, InputOrderTemplate> hash = new HashMap<>();

	public static InputOrderTemplate getData(int id){return hash.get(id);}

	public static void addData(InputOrderTemplate data){
		hash.put(data.id(),data);
	}
}

