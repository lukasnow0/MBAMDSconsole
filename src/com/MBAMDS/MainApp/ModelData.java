package com.MBAMDS.MainApp;

/*
 * ModelData holds the data that simulation works on.
 * It's constructor takes netSideSize and spermPoolSize to create data arrays of correct size.
 */

public class ModelData {
	
	protected int[][] motherAllelOneNet;
	protected int[][] motherAllelTwoNet;
	protected int[][] motherTypeNet;
	protected int[][] motherSelectionFactor;
	protected int[][][] motherSpermPool;
	protected long[][] motherID;
	
	protected int[][] eggAllelOneNet;
	protected int[][] eggAllelTwoNet;
	protected int[][] eggTypeNet;
	protected int[][] eggSelectionFactor;
	protected int[][][] eggSpermPool;
	protected long[][] eggID;
	
	public ModelData(int netSideSize, int spermPoolSize) {
		motherAllelOneNet = new int[netSideSize][netSideSize];
		motherAllelTwoNet = new int[netSideSize][netSideSize];
		motherTypeNet = new int[netSideSize][netSideSize];
		motherSelectionFactor = new int[netSideSize][netSideSize];
		motherSpermPool = new int[netSideSize][netSideSize][spermPoolSize];
		motherID = new long[netSideSize][netSideSize];
		
		eggAllelOneNet = new int[netSideSize][netSideSize];
		eggAllelTwoNet = new int[netSideSize][netSideSize];
		eggTypeNet = new int[netSideSize][netSideSize];
		eggSelectionFactor = new int[netSideSize][netSideSize];
		eggSpermPool = new int[netSideSize][netSideSize][spermPoolSize];
		eggID = new long[netSideSize][netSideSize];
	}

//	public synchronized int[][] getMotherAllelOneNet() {
//		return motherAllelOneNet;
//	}
//
//	public synchronized void setMotherAllelOneNet(int[][] motherAllelOneNet) {
//		this.motherAllelOneNet = motherAllelOneNet;
//	}
//
//	public synchronized int[][] getMotherAllelTwoNet() {
//		return motherAllelTwoNet;
//	}
//
//	public synchronized void setMotherAllelTwoNet(int[][] motherAllelTwoNet) {
//		this.motherAllelTwoNet = motherAllelTwoNet;
//	}
//
//	public synchronized int[][] getMotherTypeNet() {
//		return motherTypeNet;
//	}
//
//	public synchronized void setMotherTypeNet(int[][] motherTypeNet) {
//		this.motherTypeNet = motherTypeNet;
//	}
//
//	public synchronized int[][] getMotherSelectionFactor() {
//		return motherSelectionFactor;
//	}
//
//	public synchronized void setMotherSelectionFactor(int[][] motherSelectionFactor) {
//		this.motherSelectionFactor = motherSelectionFactor;
//	}
//
//	public synchronized int[][][] getMotherSpermPool() {
//		return motherSpermPool;
//	}
//
//	public synchronized void setMotherSpermPool(int[][][] motherSpermPool) {
//		this.motherSpermPool = motherSpermPool;
//	}
//
//	public synchronized long[][] getMotherID() {
//		return motherID;
//	}
//
//	public synchronized void setMotherID(long[][] motherID) {
//		this.motherID = motherID;
//	}
//
//	public synchronized int[][] getEggAllelOneNet() {
//		return eggAllelOneNet;
//	}
//
//	public synchronized void setEggAllelOneNet(int[][] eggAllelOneNet) {
//		this.eggAllelOneNet = eggAllelOneNet;
//	}
//
//	public synchronized int[][] getEggAllelTwoNet() {
//		return eggAllelTwoNet;
//	}
//
//	public synchronized void setEggAllelTwoNet(int[][] eggAllelTwoNet) {
//		this.eggAllelTwoNet = eggAllelTwoNet;
//	}
//
//	public synchronized int[][] getEggTypeNet() {
//		return eggTypeNet;
//	}
//
//	public synchronized void setEggTypeNet(int[][] eggTypeNet) {
//		this.eggTypeNet = eggTypeNet;
//	}
//
//	public synchronized int[][] getEggSelectionFactor() {
//		return eggSelectionFactor;
//	}
//
//	public synchronized void setEggSelectionFactor(int[][] eggSelectionFactor) {
//		this.eggSelectionFactor = eggSelectionFactor;
//	}
//
//	public synchronized int[][][] getEggSpermPool() {
//		return eggSpermPool;
//	}
//
//	public synchronized void setEggSpermPool(int[][][] eggSpermPool) {
//		this.eggSpermPool = eggSpermPool;
//	}
//
//	public synchronized long[][] getEggID() {
//		return eggID;
//	}
//
//	public synchronized void setEggID(long[][] eggID) {
//		this.eggID = eggID;
//	}
	
}
