/* 由程序自动生成，请勿修改。*/
package template;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TaskListTemplateHolder {
	private static final HashMap<Integer, TaskListTemplate> hash = new HashMap<>();

	public static boolean containsKey(int id) { return hash.containsKey(id); }

	public static TaskListTemplate getData(int id){ return hash.get(id); }

	public static TaskListTemplate getOrThrowData(int id){ return Objects.requireNonNull(hash.get(id), "TaskListTemplate not found id=" + id); }

	public static void addData(TaskListTemplate data){
		hash.put(data.id(),data);
	}

	public static List<TaskListTemplate> getValues() {
		return List.copyOf(hash.values());
	}
}
