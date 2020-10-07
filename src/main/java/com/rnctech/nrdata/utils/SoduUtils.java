package com.rnctech.nrdata.utils;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/* 
* @Author Zilin Chen
* @Date 2020/10/06
*/

public class SoduUtils {

	public static final int len = 9;	
	
	//online solution using left shift and XOR (^)
	public static void solveSudokuXOR(char[][] board) {	    
	    int[] rows = new int[9];
	    int[] cols = new int[9];
	    int[][] grid = new int[3][3];
	    
	    for(int i=0;i<board.length;i++){
	        for(int j=0;j<board[0].length;j++){
	            rows[i] |= (1<<(board[i][j]-48));
	            cols[j] |= (1<<board[i][j]-48);
	            grid[i/3][j/3] |= (1<<board[i][j]-48);
	        }
	    }
	    XORSolver(board,0,0,rows,cols,grid);
	    printOut(board);
	}

	public static boolean XORSolver(char[][] board,int i,int j,int[] rows,int[] cols,int[][] grid){    
	    if(i == board.length){
	        return true;
	    }
	    
	    int ni = 0; int nj = 0;	
	    if(j == board[0].length-1){
	        ni = i+1; nj = 0;
	    }else{
	        ni = i; nj = j+1;
	    }
	    
	    if(board[i][j]!='.'){
	        boolean ans = XORSolver(board,ni,nj,rows,cols,grid);
	        if(ans)
	            return true;
	    }else{
	        for(int pos=1;pos<=9;pos++){
	            int mask = 1<<pos;
	            if((rows[i] & mask) == 0 && (cols[j] & mask) == 0 && (grid[i/3][j/3] & mask) == 0){
	                board[i][j] = (char) (pos+48);
	                rows[i]^=mask;
	                cols[j]^=mask;
	                grid[i/3][j/3]^=mask;
	                boolean ans = XORSolver(board,ni,nj,rows,cols,grid);
	                if(ans)
	                    return true;
	                board[i][j] = '.';
	                rows[i]^=mask;
	                cols[j]^=mask;
	                grid[i/3][j/3]^=mask;
	            }
	        }
	    }	        
	    return false;
	}
	
	//recursive from (0,0) to (9,9) 
	public static void solveSudokuChar(char[][] board) {	
	    char[][]temp = new char[board.length][board[0].length];
	    for(int i=0;i<temp.length;i++){
	        for(int j=0;j<temp[0].length;j++)
	            temp[i][j] = board[i][j];
	    }    
	    RecurResolve(board,0,0,temp);
	}

	public static void RecurResolve(char[][] board,int i,int j,char[][] temp){	    
	    if(i == board.length){
	        for(int x=0;x<temp.length;x++){
	            for(int y=0;y<temp[0].length;y++)
	                board[x][y] = temp[x][y];
	        }
	        return;
	    }
	    
	    int ni = 0;   int nj = 0;
	    if(j == board[0].length-1){
	        ni = i+1;     nj = 0;
	    }else{
	        ni = i;   nj = j+1;
	    }
	    
	    if(temp[i][j]!='.'){
	    	RecurResolve(board,ni,nj,temp);
	    }else{
	        for(int pos=1;pos<=9;pos++){
	            if(validBoard(temp,i,j,(char)(48+pos))){
	                temp[i][j] = (char) (pos+48);
	                RecurResolve(board,ni,nj,temp);
	                temp[i][j] = '.';
	            }
	        }
	    }	    
	}
	
	//================solution naturally at Oct.6,2020
	//check any possible fill step by step (size of options with 1)
	static class Point{
		int row;
		int col;
		public char v = '.';
		
		Point(int r, int c){
			this.row = r;
			this.col = c;
		}
		List<Character> options = new ArrayList<>();
	} 
	
	//static Queue<Point> unsigned = new LinkedList<>(); 
	static PriorityQueue<Point> unsigned = new PriorityQueue<>((p1, p2) -> (p1.options.size() - p2.options.size()));
	
	public static void solveSudoku(char[][] board) throws Exception {			
		printOut(board);
		nResolver(board);
	}	
	
	private static void nResolver(char[][] uboard) throws Exception {
		unsigned.clear();
		int imrpve =0;
		for (int row=0; row < len; row++) {
			for(int col =0;  col < len; col++) {
				char c = uboard[row][col];
				if(c =='.') {					
					Point p = checkUnassigned(row, col, uboard);
					if(1 == p.options.size()) {				
						uboard[p.row][p.col] = p.options.iterator().next();
						imrpve++;
					}else {
						unsigned.offer(p);
					}
				}
			}
		}
		
		if(unsigned.isEmpty()) { 
			printOut(uboard);
			return;
		}
		//printOut(uboard);
		if(imrpve > 0) {
			nResolver(uboard);
		}else{
			Point p = unsigned.poll();
			tryResolve(uboard, p);	
			nResolver(uboard);
		}
	}

	//for multiple options
	private static void tryResolve(char[][] xboard, Point p) throws Exception {		
		List<Character> alist = p.options;
		boolean valid = false;
		for(int i=alist.size()-1; i>=0; i--) {
			char c = alist.get(i);
			valid = validBoard(xboard, p.row, p.col, c);
			if(valid) {		
                xboard[p.row][p.col] = c;
                break;
			}
        }
		
		throw new Exception("No solution");
	}

	//utilities 
	public static boolean validBoard(char[][] uboard, int row, int col, char c) {
        for(int j=0; j<len;j++){
            char rc = uboard[row][j];
            if(rc == c) return false;
            char cc = uboard[j][col];
            if(cc == c) return false;
        }
        
        int ci = 3*(row/3);
        int cj = 3*(col/3);
        for(int i=ci; i<ci+3; i++){
            for(int j=cj; j<cj+3;j++){
                char bc = uboard[i][j];
                if(bc == c) return false;
            }
        }
        
		return true;
	}
	
	public static boolean validBoard(int[][] uboard, int row, int col, int c) {
        for(int j=0; j<len;j++){
            int rc = uboard[row][j];
            if(rc == c) return false;
            int cc = uboard[j][col];
            if(cc == c) return false;
        }
        
        int ci = 3*(row/3);
        int cj = 3*(col/3);
        for(int i=ci; i<ci+3; i++){
            for(int j=cj; j<cj+3;j++){
                int bc = uboard[i][j];
                if(bc == c) return false;
            }
        }
        
		return true;
	}

	public static char[][] getSection(int xPos, int yPos, char[][] board) {
		char[][] section = new char[3][3];
		int xIndex = 3 * (xPos / 3);
		int yIndex = 3 * (yPos / 3);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				section[i][j] = board[xIndex + i][yIndex + j];
			}
		}
		return section;

	}
	
	public static Point checkUnassigned(int row, int col, char[][] board){            
			boolean[] v = new boolean[len];
            //Arrays.fill(v, false);
            int ci = 3*(row/3);
            int cj = 3*(col/3);
            for(int i=ci; i<ci+3; i++){
                for(int j=cj; j<cj+3;j++){
                    char bc = board[i][j];
                    if(bc !='.'){
                        v[bc - '1'] = true;
                    }
                }
            }
            
            for(int j=0; j<len;j++){
                char rc = board[row][j];
                if(rc !='.'){
                    v[rc - '1'] = true;
                }
                char cc = board[j][col];
                if(cc != '.'){
                    v[cc - '1'] = true;
                }
            }
            
            Point p = new Point(row,col);
            for(int k=0; k<len; k++){
                if(!v[k]) { 
                    char oc = (char)(k+(int)'1'); 
                    p.options.add(oc);                    
                }
            }
            return p;
        }

	public static boolean emptyCheck(int[][] sudo) {
		for (int i = 0; i < sudo.length; i++) {
			for (int j = 0; j < sudo[i].length; j++) {
				if (sudo[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean emptyCheck(char[][] sudo) {
		for (int i = 0; i < sudo.length; i++) {
			for (int j = 0; j < sudo[i].length; j++) {
				if (sudo[i][j] == '.') {
					return false;
				}
			}
		}
		return true;
	}
	
	public static void printOut(char[][] a) {
		System.out.println();
		for (char[] row : a) {
			System.out.println(Arrays.toString(row));
		}
		System.out.println();
	}
	
	public static void printOut(int[][] a) {
		printOut(a, System.out);
	}
	
	public static void printOut(int[][] a, PrintStream out) {
		out.print("\n");
		for (int[] row : a) {
			out.println(Arrays.toString(row));
		}
		out.print("\n");
	}
	
	public static void writerOut(int[][] a, PrintWriter out) {
		out.write("\n");
		for (int[] row : a) {
			out.write(Arrays.toString(row)+"\n");
		}
		out.write("\n");
	}

	public static void main(String[] args) {
		char[][] board = new char[][] { { '5', '3', '.', '.', '7', '.', '.', '.', '.' },
				{ '6', '.', '.', '1', '9', '5', '.', '.', '.' }, { '.', '9', '8', '.', '.', '.', '.', '6', '.' },
				{ '8', '.', '.', '.', '6', '.', '.', '.', '3' }, { '4', '.', '.', '8', '.', '3', '.', '.', '1' },
				{ '7', '.', '.', '.', '2', '.', '.', '.', '6' }, { '.', '6', '.', '.', '.', '.', '2', '8', '.' },
				{ '.', '.', '.', '4', '1', '9', '.', '.', '5' }, { '.', '.', '.', '.', '8', '.', '.', '7', '9' } };
		printOut(board);
		try {
			solveSudoku(board);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("\nResolved:");
		printOut(board);
		
		System.out.println("\nSecond:");
		char[][] board2 = new char[][] {{'.','.','9','7','4','8','.','.','.'},{'7','.','.','.','.','.','.','.','.'},
			{'.','2','.','1','.','9','.','.','.'},{'.','.','7','.','.','.','2','4','.'},{'.','6','4','.','1','.','5','9','.'},
			{'.','9','8','.','.','.','3','.','.'},{'.','.','.','8','.','3','.','2','.'},{'.','.','.','.','.','.','.','.','6'},
			{'.','.','.','2','7','5','9','.','.'}};
		printOut(board2);
		solveSudokuXOR(board2);
		//solveSudoku(board2);
		
		System.out.println("\nResolved:");
		printOut(board2);
		/*
		 * [["5","1","9","7","4","8","6","3","2"],
		 * ["7","8","3","6","5","2","4","1","9"],
		 * ["4","2","6","1","3","9","8","7","5"],
		 * ["3","5","7","9","8","6","2","4","1"],
		 * ["2","6","4","3","1","7","5","9","8"],
		 * ["1","9","8","5","2","4","3","6","7"],
		 * ["9","7","5","8","6","3","1","2","4"],
		 * ["8","3","2","4","9","1","7","5","6"],
		 * ["6","4","1","2","7","5","9","8","3"]]
		 */
		
		//char[][] resolved = { { '5', '3', '4', '6', '7', '8', '9', '1', '2' },
		//		{ '6', '7', '2', '1', '9', '5', '3', '4', '8' }, { '1', '9', '8', '3', '4', '2', '5', '6', '7' },
		//		{ '8', '5', '9', '7', '6', '1', '4', '2', '3' }, { '4', '2', '6', '8', '5', '3', '7', '9', '1' },
		//		{ '7', '1', '3', '9', '2', '4', '8', '5', '6' }, { '9', '6', '1', '5', '3', '7', '2', '8', '4' },
		//		{ '2', '8', '7', '4', '1', '9', '6', '3', '5' }, { '3', '4', '5', '2', '8', '6', '1', '7', '9' } };
		//printOut(resolved);
	}

}
