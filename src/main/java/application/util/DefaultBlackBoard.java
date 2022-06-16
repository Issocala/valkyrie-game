package application.util;

public class DefaultBlackBoard<T> implements BlackBoard<T> {

	private final T data;

	private BoardDataMap dataMap = new BoardDataMap();

	public DefaultBlackBoard(T data) {
		this.data = data;
	}

	@Override
	public T getData() {
		return this.data;
	}

	public BoardDataMap getDataMap() {
		return dataMap;
	}

	public void setDataMap(BoardDataMap dataMap) {
		this.dataMap = dataMap;
	}
}
