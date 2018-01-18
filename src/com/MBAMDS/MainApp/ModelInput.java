package com.MBAMDS.MainApp;

/*
 * ModelInput class holds all the inputs needed by Model, ModelData and ModelController to setup and run
 */

public class ModelInput {
	protected int netSideSize;
	protected int allelNumber;
	protected int generationNum;
	protected int dip;
	protected int droneDist;
	protected int swarmingDist;
	protected boolean torus;
	
	public ModelInput(int netSideSize, int allelNumber, int generationNum, int dip, int droneDist, int swarmingDist,
			boolean torus) {
		this.netSideSize = netSideSize;
		this.allelNumber = allelNumber;
		this.generationNum = generationNum;
		this.dip = dip;
		this.droneDist = droneDist;
		this.swarmingDist = swarmingDist;
		this.torus = torus;
	}
	
//	public int getNetSideSize() {
//		return netSideSize;
//	}
//	public int getAllelNumber() {
//		return allelNumber;
//	}
//	public int getGenerationNum() {
//		return generationNum;
//	}
//	public int getDip() {
//		return dip;
//	}
//	public int getDroneDist() {
//		return droneDist;
//	}
//	public int getSwarmingDist() {
//		return swarmingDist;
//	}
//	public boolean isTorus() {
//		return torus;
//	}
	

}
