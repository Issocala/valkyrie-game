/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;

public class BaseFormulaTemplateHolder {
	private static HashMap<Integer, BaseFormulaTemplate> hash = new HashMap<>();

	public static BaseFormulaTemplate getData(int id){return hash.get(id);}

	public static void addData(BaseFormulaTemplate data){
		hash.put(data.id(),data);
	}
}

