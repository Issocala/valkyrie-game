/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BaseFormulaTemplateHolder {
	private static final HashMap<Integer, BaseFormulaTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static BaseFormulaTemplate getData(int id){ return hash.get(id); }

	public static BaseFormulaTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "BaseFormulaTemplate not found id=" + id); }

	public static void addData(BaseFormulaTemplate data){
		hash.put(data.id(),data);
	}

	public static List<BaseFormulaTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
