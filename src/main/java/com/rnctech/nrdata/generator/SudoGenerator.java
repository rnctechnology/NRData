package com.rnctech.nrdata.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.rnctech.nrdata.utils.SoduUtils;

/* 
* @Author Zilin Chen
* @Date 2020/10/07
*/

public class SudoGenerator {

	public static final int size = 9;
	public static final int begin = 1;
	public static final int end = size;

	public static int[][] generate0() {
		int[][] sudo = new int[size][size];
		int irow = initBoard(sudo);

		try {
			gen(sudo, irow, 0);

		} catch (Exception e) {
			System.out.println("Retry......");
			sudo = new int[size][size];
			try {
				gen(sudo, initBoard(sudo), 0);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return sudo;
	}

	public static int initBoard(int[][] sudo) {
		// setup
		Random gen = new Random();
		ArrayList<Integer> nums = new ArrayList<Integer>();
		for (int i = begin; i <= end; i++) {
			nums.add(i);
		}
		Collections.shuffle(nums);
		int irow = gen.nextInt(size);
		for (int j = 0; j < size; j++) {
			sudo[irow][j] = nums.get(j);
		}
		return irow;
	}

	// fill col by col
	private static void gen(int[][] sudo, int row, int col) throws Exception {
		if (col == sudo[0].length - 1)
			return;

		ArrayList<Integer> nums = new ArrayList<Integer>();
		for (int i = begin; i <= end; i++) {
			if (i == sudo[row][col])
				continue;
			nums.add(i);
		}
		Collections.shuffle(nums);
		for (int j = 0; j < size; j++) {
			if (j == row)
				continue;
			boolean f = false;
			int k = nums.size() - 1;
			while (!f && k >= 0) {
				int v = nums.get(nums.size() - 1);
				if (SoduUtils.validBoard(sudo, j, col, v)) {
					sudo[j][col] = v;
					f = true;
					nums.remove(k);
				} else {
					k--;
				}
			}
			if (!f) {
				throw new Exception("try one more time");
			}
		}

		gen(sudo, row, col + 1);

	}

	public static boolean checker(int[][] bs, int i, ArrayList<Integer> ar) {
		ArrayList<Integer> temp_ar = new ArrayList<Integer>();
		boolean check1 = true; // For returning true for good Sudoku
		for (int e = 0; e < 9; e++) {
			bs[i][e] = ar.get(e);
		}
		for (int t = 0; t < 9; t++) {
			for (int e = 0; e <= i; e++) {

				temp_ar.add(e, bs[e][t]);
			}
			Set<Integer> temp_set = new HashSet<Integer>(temp_ar);
			if (temp_set.size() < temp_ar.size()) {
				check1 = false;
				break;
			} else {
				temp_ar.clear();
				temp_set.clear();
			}
		}
		return check1;
	}

	public static boolean checkPath(int[][] bs, int i) {
		// boolean check_cP = false;
		ArrayList<Integer> temp_cP = new ArrayList<Integer>();
		Set<Integer> temp_setcP = new HashSet<Integer>();
		boolean denoter = true;
		while (i == 3) {
			for (int k = 0; k < i; k++) {
				for (int e = 0; e < 3; e++) {
					temp_cP.add(e, bs[k][e]);
				}
			}
			temp_setcP = new HashSet<Integer>(temp_cP);
			if (temp_cP.size() > temp_setcP.size()) {
				denoter = false;
				break;

			} else {
				temp_cP.clear();
				temp_setcP.clear();

				for (int k = 0; k < i; k++) {
					for (int e = 3; e < 6; e++) {
						temp_cP.add(bs[k][e]);
					}
				}
				temp_setcP = new HashSet<Integer>(temp_cP);
				if (temp_cP.size() > temp_setcP.size()) {
					denoter = false;
					break;

				} else {
					break;
				}
			}
		}
		while (i == 6) {

			for (int k = 3; k < i; k++) {
				for (int e = 0; e < 3; e++) {
					temp_cP.add(e, bs[k][e]);
				}
			}
			temp_setcP = new HashSet<Integer>(temp_cP);
			if (temp_cP.size() > temp_setcP.size()) {
				denoter = false;
				break;

			} else {
				temp_cP.clear();
				temp_setcP.clear();

				for (int k = 3; k < i; k++) {
					for (int e = 3; e < 6; e++) {
						temp_cP.add(bs[k][e]);
					}
				}
				temp_setcP = new HashSet<Integer>(temp_cP);
				if (temp_cP.size() > temp_setcP.size()) {
					denoter = false;
					break;

				} else {
					break;
				}
			}

		}
		return denoter;
	}

	public static int[][] generate() {
		ArrayList<Integer> ar = new ArrayList<Integer>();
		int[][] bs = new int[9][9];
		for (int i = 0; i < 9; i++) {
			ar.add(i + 1);
		}
		Collections.shuffle(ar);

		// fill 1st row i=0
		for (int j = 0; j < 9; j++) {
			bs[0][j] = ar.get(j);
		}

		boolean check = true;
		//fill row 1-9
		for (int i = 1; i < 9; i++) {
			if (i % 3 == 0) {
				check = checkPath(bs, i);
				if (check == false)
					i = i - 2;
			}

			for (int j = 0; j < 9; j++) {
				check = checker(bs, i, ar);
				if (check == false) {
					i--;
					break;
				} else {
					bs[i][j] = ar.get(j);
				}
			}
			Collections.shuffle(ar);
		}

		return bs;
	}

	public static int[][] markDown(int[][] sudo, int count){
		ArrayList<Integer> nums = new ArrayList<Integer>();
		for (int i = begin; i <= end*end; i++) {
			nums.add(i);
		}
		Collections.shuffle(nums);
		
		while(count > 0) {
			int n = nums.get(count);
			int row = (n-1) / size;
			int col = (n-1) % size;
			sudo[row][col] = 0;
			count --;
		}
		
		return sudo;
	}
	
	public static void main(String[] args) {

		try {
			SudoGenerator g = new SudoGenerator();
			int[][] a =markDown(g.generate(),48);

			SoduUtils.printOut(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}