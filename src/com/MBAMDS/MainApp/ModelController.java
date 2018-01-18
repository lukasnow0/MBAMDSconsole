package com.MBAMDS.MainApp;

public class ModelController {
	
	private ModelInput modelInput;
	private ModelData modelData;
	private Model model;
	

	private int generationNum;
	private String dir;
	
	public static int winterFactor = 20;
	private int spermPoolSize;
	private int generationCount;
	
	
	
	public ModelController (ModelInput modelInput, String dir) {

		this.generationNum = modelInput.generationNum;
		this.dir = dir;
		this.spermPoolSize = 15;
		
		this.modelInput = modelInput;
		this.modelData = new ModelData(modelInput.netSideSize, spermPoolSize);
		this.model = new Model(modelData, modelInput);
	}
	
	public void testRun() {
		model.testPrint();
		for(int i = 0; i < modelInput.generationNum; i++) {
			model.generationCycle();
			model.testPrint();
		}
	}
}
