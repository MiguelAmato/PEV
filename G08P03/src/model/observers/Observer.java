package model.observers;

import model.AlgoritmoGenetico;

public interface Observer  {
	
	public void onEnd(AlgoritmoGenetico algoritmo, String key);
	public void onReset();
	public void onError(String exception);
	
}
