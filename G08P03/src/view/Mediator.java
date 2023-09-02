package view;

import controller.Controller;

public class Mediator {
	
	Controller ctrl; 
	MainWindow mw; 
	LeftPanel lp; 
	BottomPanel bp;
	
	public Mediator(Controller ctrl, MainWindow mw, LeftPanel lp, BottomPanel bp) {
		this.ctrl = ctrl;
		this.mw = mw;
		this.lp = lp;
		this.bp = bp;
	}
	
	public void changeGraph(String key) {
		mw.changeGraph(key);
	}
	
	public void resetGraph() {
		mw.resetGraph();
	}
}
 