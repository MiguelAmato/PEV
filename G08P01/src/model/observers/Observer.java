package model.observers;

import model.AlgoritmoGenetico;

public interface Observer  {
	
	public void onEnd(AlgoritmoGenetico algoritmo);
	public void onReset();
	public void onError(String exception);
	
}
