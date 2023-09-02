package utils;

public class Cuarteto<T1, T2, T3, T4> {
	private T1 _first;
	private T2 _second;
	private T3 _third;
	private T4 _fourth;

	public Cuarteto(T1 first, T2 second, T3 third, T4 fourth) {
		_first = first;
		_second = second;
		_third = third;
		_fourth = fourth;
	}

	public T1 getFirst() {
		return _first;
	}

	public T2 getSecond() {
		return _second;
	}
	
	public T3 getThird() {
		return _third;
	}
	
	public T4 getFourth() {
		return _fourth;
	}
	
	public void setFirst(T1 cambio) {
		_first = cambio;
	}
	
	public void setSecond(T2 cambio) {
		_second = cambio;
	}
	
	public void setThird(T3 cambio) {
		_third = cambio;
	}
	
	public void setFourth(T4 cambio) {
		_fourth = cambio;
	}
}
