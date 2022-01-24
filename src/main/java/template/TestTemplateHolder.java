/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;

public class TestTemplateHolder {
	private static final HashMap<Integer, TestTemplate> hash = new HashMap<>();

	public static TestTemplate getData(int id){ return hash.get(id); }

	public static void addData(TestTemplate data){
		hash.put(data.id(),data);
	}

	public static List<TestTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
