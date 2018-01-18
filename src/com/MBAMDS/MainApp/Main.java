package com.MBAMDS.MainApp;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ModelInput modelInput = new ModelInput(10, 40, 5, 10, 3, 3, true);
		ModelController mc = new ModelController(modelInput, "null");
		
		mc.testRun();
		
	}

}
