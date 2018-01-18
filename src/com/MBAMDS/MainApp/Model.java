package com.MBAMDS.MainApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


/*
 * Model for Bee Allels Migration and Distribution Simulation (MBAMDS)
 */
public class Model {
		
	public static int winterFactor = 20;
	private int spermPoolSize;
	private int[] allelPool;
	private int generationCount;
	private long motherCount;
	
	private ModelInput modelInput;
	private ModelData modelData;
	
	public Model(ModelData modelData, ModelInput modelInput) {
		
		this.spermPoolSize = 15;
		this.motherCount = 1;
		
		this.modelData = modelData;
		this.modelInput = modelInput;
		
		generatAllelPool();
		initialNetFill();
		winterSelection();;
		collectSpermPool();
	}
	

	private void generatAllelPool() {
		allelPool = new int[modelInput.allelNumber];
		for(int i = 0; i < allelPool.length; i++){
			allelPool[i] = i + 1;
		}
	}
	
	private void initialNetFill() {
		int[] randomAllels = new int[2];
		
		for(int i = 0; i < modelInput.netSideSize; i++){
			for(int j = 0; j < modelInput.netSideSize; j++){
				randomAllels = randomAllelPair();
				modelData.motherAllelOneNet[i][j] = randomAllels[0];
				modelData.motherAllelTwoNet[i][j] = randomAllels[1];
				modelData.motherTypeNet[i][j] = 0;
				modelData.motherSelectionFactor[i][j] = winterFactor;
				modelData.motherID[i][j] = motherCount;
				motherCount++;
			}
		}
	}

	
	private int[] randomAllelPair(){
		int[] allels = new int[2];
		do {
			allels[0] = randomAllel(allelPool);
			allels[1] = randomAllel(allelPool);
		} while (allels[0] == allels[1]);
		return allels;
	}

	
	private int randomAllel(int[] pool) {
		return pool[ThreadLocalRandom.current().nextInt(pool.length)];
	}
	
	private int randomAllel(List<Integer> pool) {
		return pool.get(ThreadLocalRandom.current().nextInt(pool.size()));
	}

	
	private void winterSelection(){
		for(int i = 0; i < modelInput.netSideSize; i++){
			for(int j = 0; j < modelInput.netSideSize; j++){
				if ((ThreadLocalRandom.current().nextInt(100) < modelData.motherSelectionFactor[i][j])
						|| (modelData.motherTypeNet[i][j] > 5)) {
					//mother dies
					modelData.motherAllelOneNet[i][j] = 0;
					modelData.motherAllelTwoNet[i][j] = 0;
					modelData.motherTypeNet[i][j] = -1;
					modelData.motherSelectionFactor[i][j] = winterFactor;
					for(int k = 0; k < spermPoolSize ; k++){
						modelData.motherSpermPool[i][j][k] = 0;
					}
					modelData.motherID[i][j] = 0;
				}
			}
		}
	}

	
	private void collectSpermPool() {
		for(int i = 0; i < modelInput.netSideSize; i++){
			for(int j = 0; j < modelInput.netSideSize; j++){
				if (modelData.motherTypeNet[i][j] == 0) {
					neighbouringAllels(i, j);
					modelData.motherTypeNet[i][j] = 1;
					modelData.motherSelectionFactor[i][j] = obtainSelectionFactor(i, j);
				}
			}
		}
	}


	private void neighbouringAllels(int x, int y) {
		List<Integer> collectedAllels = new ArrayList<>();
		int left = x - modelInput.droneDist;
		int right = x + modelInput.droneDist;
		int top = y - modelInput.droneDist;
		int bottom = y + modelInput.droneDist;
		
		if(modelInput.torus){
			for(int i = left; i < right; i++){
				for(int j = top; j < bottom; j++){
					if(i == x && j == y){
						continue;
					}
					if (i < 0 || i >= modelInput.netSideSize || j < 0 || j >= modelInput.netSideSize) {
						if(modelData.motherTypeNet[getTorusValue(i)][getTorusValue(j)] != -1) {
							collectedAllels.add(modelData.motherAllelOneNet[getTorusValue(i)][getTorusValue(j)]);
							collectedAllels.add(modelData.motherAllelTwoNet[getTorusValue(i)][getTorusValue(j)]);
						}
					} else {
						if(modelData.motherTypeNet[i][j] != -1) {
							collectedAllels.add(modelData.motherAllelOneNet[i][j]);
							collectedAllels.add(modelData.motherAllelTwoNet[i][j]);
						}
					}		
				}
			}		
		} else {
			while(left < 0){
				left++;
			}
			while(right > modelInput.netSideSize){
				right--;
			}
			while(top < 0){
				top++;
			}
			while(bottom > modelInput.netSideSize){
				bottom--;
			}

			for(int i = left; i < right; i++){
				for(int j = top; j < bottom; j++){
					if(i == x && j == y){
						continue;
					}
					if(modelData.motherTypeNet[i][j] != -1) {
						collectedAllels.add(modelData.motherAllelOneNet[i][j]);
						collectedAllels.add(modelData.motherAllelTwoNet[i][j]);
					}
				}
			}
		}
		
		for(int k = 0; k < spermPoolSize; k++){
			modelData.motherSpermPool[x][y][k] = randomAllel(collectedAllels);
		}
	}

	
	private int getTorusValue(int i) {
		int nss = modelInput.netSideSize;
		return (i % nss + nss) % nss;
	}
	
	
	private int obtainSelectionFactor(int x, int y) {
		int temp = 0;//double?
		for(int k = 0; k < spermPoolSize; k++) {
			if(modelData.motherAllelOneNet[x][y] == modelData.motherSpermPool[x][y][k]) {
				temp++;
			}
			if(modelData.motherAllelTwoNet[x][y] == modelData.motherSpermPool[x][y][k]) {
				temp++;
			}
		}	
		temp *= modelInput.dip;
		temp += winterFactor;
		return temp;
	}

	
	private void createEggs() {
		for(int i = 0; i < modelInput.netSideSize; i++){
			for(int j = 0; j < modelInput.netSideSize; j++){
				if(modelData.motherTypeNet[i][j] != -1) {
					int[] selectedAllels = allelSelector(i, j);
					modelData.eggAllelOneNet[i][j] = selectedAllels[0];
					modelData.eggAllelTwoNet[i][j] = selectedAllels[1];
					modelData.eggTypeNet[i][j] = 0;
					modelData.eggSelectionFactor[i][j] = winterFactor;
					modelData.eggID[i][j] = motherCount;
					motherCount++;
				} else {
					modelData.eggAllelOneNet[i][j] = modelData.motherAllelOneNet[i][j];
					modelData.eggAllelTwoNet[i][j] = modelData.motherAllelTwoNet[i][j];
					modelData.eggTypeNet[i][j] = modelData.motherTypeNet[i][j];
					modelData.eggID[i][j] = modelData.motherID[i][j];
				}
			}
		}
	}


	private int[] allelSelector(int i, int j) {
		int[] tempSpermPool = modelData.motherSpermPool[i][j];
		int[] motherAllels = new int[2];
		motherAllels[0] = modelData.motherAllelOneNet[i][j];
		motherAllels[1] = modelData.motherAllelTwoNet[i][j];
		int[] selectedAllels = new int[2];
		do {
			selectedAllels[0] = randomAllel(tempSpermPool);
			selectedAllels[1] = randomAllel(motherAllels);
		} while (selectedAllels[0] == selectedAllels[1]);
		return selectedAllels;
	}
	
	private void oldMothersSwarming() {
		Map<Integer, int[]> oldMothers = oldMothersCollector();
		List<Integer> keys = new ArrayList<>(oldMothers.keySet());		
		A: while(!oldMothers.isEmpty()) {
			int key = keys.remove(ThreadLocalRandom.current().nextInt(oldMothers.size()));
			int [] coordinates = oldMothers.get(key);			
			int left = coordinates[0] - modelInput.swarmingDist;
			int right = coordinates[0] + modelInput.swarmingDist;
			int top = coordinates[1] - modelInput.swarmingDist;
			int bottom = coordinates[1] + modelInput.swarmingDist;
			
			int tempX;
			int tempY;
			if(modelInput.torus) {
				for(int i = 0; i < 5; i++) {
					tempX = ThreadLocalRandom.current().nextInt(left, right + 1);//not sure if +1 is necessary
					tempY = ThreadLocalRandom.current().nextInt(top, bottom + 1);//same here
					if(tempX < 0 || tempX >= modelInput.netSideSize || tempY < 0 || tempY >= modelInput.netSideSize) {
						tempX = getTorusValue(tempX);
						tempY = getTorusValue(tempY);	
					}
					if(modelData.eggTypeNet[tempX][tempY] == -1) {
						modelData.eggAllelOneNet[tempX][tempY] = modelData.motherAllelOneNet[coordinates[0]][coordinates[1]];
						modelData.eggAllelTwoNet[tempX][tempY] = modelData.motherAllelTwoNet[coordinates[0]][coordinates[1]];
						modelData.eggID[tempX][tempY] = modelData.motherID[coordinates[0]][coordinates[1]];
						modelData.eggSelectionFactor[tempX][tempY] = modelData.motherSelectionFactor[coordinates[0]][coordinates[1]];
						modelData.eggSpermPool[tempX][tempY] = modelData.motherSpermPool[coordinates[0]][coordinates[1]];
						modelData.eggTypeNet[tempX][tempY] = modelData.motherTypeNet[coordinates[0]][coordinates[1]] + 1;
						oldMothers.remove(key);
						continue A;
					}
					
				}
			} else {
				while (left < 0) {
					left++;
				}
				while (right > modelInput.netSideSize) {
					right--;
				}
				while (top < 0) {
					top++;
				}
				while (bottom > modelInput.netSideSize) {
					bottom--;
				}
				
				for(int i = 0; i < 5; i++) {
					tempX = ThreadLocalRandom.current().nextInt(left, right + 1);
					tempY = ThreadLocalRandom.current().nextInt(top, bottom + 1);
					if(modelData.eggTypeNet[tempX][tempY] == -1) {
						modelData.eggAllelOneNet[tempX][tempY] = modelData.motherAllelOneNet[coordinates[0]][coordinates[1]];
						modelData.eggAllelTwoNet[tempX][tempY] = modelData.motherAllelTwoNet[coordinates[0]][coordinates[1]];
						modelData.eggID[tempX][tempY] = modelData.motherID[coordinates[0]][coordinates[1]];
						modelData.eggSelectionFactor[tempX][tempY] = modelData.motherSelectionFactor[coordinates[0]][coordinates[1]];
						modelData.eggSpermPool[tempX][tempY] = modelData.motherSpermPool[coordinates[0]][coordinates[1]];
						modelData.eggTypeNet[tempX][tempY] = modelData.motherTypeNet[coordinates[0]][coordinates[1]] + 1;
						oldMothers.remove(key);
						continue A;
					}
				}
			}
			oldMothers.remove(key);
		}
	}


	private Map<Integer, int[]> oldMothersCollector() {
		Map<Integer, int[]> oldMothers = new HashMap<>();
		for(int i = 0; i < modelInput.netSideSize; i++) {
			for(int j = 0; j < modelInput.netSideSize; j++) {
				if(modelData.motherTypeNet[i][j] != -1) {
					oldMothers.put((int) modelData.motherID[i][j], new int [] {i, j});
				}
			}
		}
		return oldMothers;
	}
	
	private void arraysDoubleBufferAssignment() {
		modelData.motherAllelOneNet = modelData.eggAllelOneNet;
		modelData.motherAllelTwoNet = modelData.eggAllelTwoNet;
		modelData.motherTypeNet = modelData.eggTypeNet;
		modelData.motherSelectionFactor = modelData.eggSelectionFactor;
		modelData.motherSpermPool = modelData.eggSpermPool;
		modelData.motherID = modelData.eggID;
		
		modelData.eggAllelOneNet = new int[modelInput.netSideSize][modelInput.netSideSize];
		modelData.eggAllelTwoNet = new int[modelInput.netSideSize][modelInput.netSideSize];
		modelData.eggTypeNet = new int[modelInput.netSideSize][modelInput.netSideSize];
		modelData.eggSelectionFactor = new int[modelInput.netSideSize][modelInput.netSideSize];
		modelData.eggSpermPool = new int[modelInput.netSideSize][modelInput.netSideSize][spermPoolSize];
		modelData.eggID = new long[modelInput.netSideSize][modelInput.netSideSize];
	}
	
	public void generationCycle() {
		/*
		 * 1. new egg creation
		 * 2. old mothers's migration
		 * 3. collection of neighboring allels into a spermpool in an young mother
		 * 4. winter selection 
		 */
		createEggs();
		generationCount++;
		oldMothersSwarming();
		arraysDoubleBufferAssignment();
		collectSpermPool();
		winterSelection();
	}
	
	public int getGenerationCount() {
		return generationCount;
	}


	public void testPrint() {
		System.out.println("==============================================\nMother:");
		System.out.println(Arrays.deepToString(modelData.motherAllelOneNet));
		System.out.println(Arrays.deepToString(modelData.motherAllelTwoNet));
		System.out.println(Arrays.deepToString(modelData.motherTypeNet));
		System.out.println(Arrays.deepToString(modelData.motherSelectionFactor));
		System.out.println(Arrays.deepToString(modelData.motherSpermPool));
		System.out.println(Arrays.deepToString(modelData.motherID));
		System.out.println("==============================================\nEgg:");
		System.out.println(Arrays.deepToString(modelData.eggAllelOneNet));
		System.out.println(Arrays.deepToString(modelData.eggAllelTwoNet));
		System.out.println(Arrays.deepToString(modelData.eggTypeNet));
		System.out.println(Arrays.deepToString(modelData.eggSelectionFactor));
		System.out.println(Arrays.deepToString(modelData.eggSpermPool));
		System.out.println(Arrays.deepToString(modelData.eggID));
		System.out.println("==============================================");
	}
	
	
}

