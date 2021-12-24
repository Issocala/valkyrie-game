/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;

public class BuffDataTemplateHolder {
	private static HashMap<Integer, BuffDataTemplate> hash = new HashMap<>();

	public static BuffDataTemplate getData(int id){return hash.get(id);}

	public static void addData(BuffDataTemplate data){
		hash.put(data.id(),data);
	}
}

