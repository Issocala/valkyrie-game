/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TestTemplateHolder {
	private static final HashMap<Integer, TestTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static TestTemplate getData(int id){ return hash.get(id); }

	public static TestTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "TestTemplate not found id=" + id); }

	public static void addData(TestTemplate data){
		hash.put(data.id(),data);
	}

	public static List<TestTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
