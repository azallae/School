package moves;

import game.Refusal;

public class CPULegalMoves {
	int cpuMoves[][] = new int[43][4];
	int board[][];
	int count = 0;
	
	public CPULegalMoves(int[][] b) {
		board = b;
		
		for (int j=7; j>=0; j--) {
			for (int i=0; i<5; i++) {
				switch (board[i][j]) {
				case Refusal.bl: cpuRightOnlyBishopMoves(i, j); break;
				case Refusal.br: cpuLeftOnlyBishopMoves(i, j); break;
				case Refusal.n: cpuKnightMoves(i, j); break;
				case Refusal.p: cpuPawnMoves(i, j); break;
				case Refusal.k: cpuKingMoves(i, j); break;
				}
			}
		}
	}
	
	public int getCount() { return count; }
	public int[][] getMoves() { return cpuMoves; }
	
	private void cpuLeftOnlyBishopMoves(int c, int r) {
		int origC=c, origR=r;
		
		while (r>0) {
			r--;
			if (board[origC][r] == 0) {
				cpuMoves[count][0] = origC;   
				cpuMoves[count][1] = origR; 
				cpuMoves[count][2] = origC;   
				cpuMoves[count][3] = r;    	
				count++;	
			}
			else if (board[origC][r] < 0) {
				cpuMoves[count][0] = origC;	 
				cpuMoves[count][1] = origR;
				cpuMoves[count][2] = origC;	 
				cpuMoves[count][3] = r;
				count++;	
				break;
			}
			else {	break;	}
		}
		while (c>0) {
			c--;
			if (board[c][origR] == 0) {
				cpuMoves[count][0] = origC;	 
				cpuMoves[count][1] = origR;
				cpuMoves[count][2] = c;	 
				cpuMoves[count][3] = origR;	
				count++;
			}
			else if (board[c][origR] < 0) {
				cpuMoves[count][0] = origC;		 
				cpuMoves[count][1] = origR;
				cpuMoves[count][2] = c;		 
				cpuMoves[count][3] = origR;	
				count++;	
				break;
			}
			else {	break;	}
		}
	}

	private void cpuRightOnlyBishopMoves(int c, int r) {
		int origC=c, origR=r;
		
		while (r>0) {
			r--;
			if (board[origC][r] == 0) {
				cpuMoves[count][0] = origC;   
				cpuMoves[count][1] = origR; 
				cpuMoves[count][2] = origC;   
				cpuMoves[count][3] = r;	
				count++;	
			}
			else if (board[origC][r] < 0) {
				cpuMoves[count][0] = origC;   
				cpuMoves[count][1] = origR; 
				cpuMoves[count][2] = origC;   
				cpuMoves[count][3] = r;	
				count++;	
				break;
			}
			else {	break;	}
		}
		while (c<4) {
			c++;
			if (board[c][origR] == 0) {
				cpuMoves[count][0] = origC;		 
				cpuMoves[count][1] = origR;
				cpuMoves[count][2] = c;		 
				cpuMoves[count][3] = origR;	
				count++;	
			}
			else if (board[c][origR] < 0) {
				cpuMoves[count][0] = origC;		 
				cpuMoves[count][1] = origR;
				cpuMoves[count][2] = c;		 
				cpuMoves[count][3] = origR;	
				count++;
				break;
			}
			else {	break;	}
		}
	}

	private void cpuKnightMoves(int c, int r) {
		if (c>0 && r>1) {
			if (board[c-1][r-2] <= 0) {
				cpuMoves[count][0] = c; 
				cpuMoves[count][1] = r;
				cpuMoves[count][2] = c-1; 
				cpuMoves[count][3] = r-2; 
				count++;	
			}
		}
		if (c<4 && r>1) {
			if (board[c+1][r-2] <= 0) {
				cpuMoves[count][0] = c;   
				cpuMoves[count][1] = r;  
				cpuMoves[count][2] = c+1;   
				cpuMoves[count][3] = r-2; 
				count++;
			}
		}
		if (c>1 && r>0) {
			if (board[c-2][r-1] <= 0) {
				cpuMoves[count][0] = c;  
				cpuMoves[count][1] = r;  
				cpuMoves[count][2] = c-2;  
				cpuMoves[count][3] = r-1;
				count++;
			}
		}
		if (c<3 && r>0) {
			if (board[c+2][r-1] <= 0) {
				cpuMoves[count][0] = c;  
				cpuMoves[count][1] = r;  
				cpuMoves[count][2] = c+2;  
				cpuMoves[count][3] = r-1; 
				count++;	
			}
		}
	}

	private void cpuPawnMoves(int c, int r) {
		if (r>0) {
			if (board[c][r-1] == 0) {
				cpuMoves[count][0]=c;
				cpuMoves[count][1]=r;
				cpuMoves[count][2]=c;
				cpuMoves[count][3]=r-1;
				count++;
			}
			if (c>0) {
				if (board[c-1][r-1] < 0) {
					cpuMoves[count][0]=c;
					cpuMoves[count][1]=r;
					cpuMoves[count][2]=c-1;
					cpuMoves[count][3]=r-1;
					count++;
				}
			}
			if (c<4) {
				if (board[c+1][r-1] < 0) {
					cpuMoves[count][0]=c;
					cpuMoves[count][1]=r;
					cpuMoves[count][2]=c+1;
					cpuMoves[count][3]=r-1;
					count++;
				}
			}
		}
	}

	private void cpuKingMoves(int c, int r) {
		int origC=c;
		
		while (c>0) {
			c--;
			if (board[c][r] < 0) {
				cpuMoves[count][0]=origC;
				cpuMoves[count][1]=r;
				cpuMoves[count][2] = c;		
				cpuMoves[count][3] = r;	
				count++;	
				break;
			}
			else if (board[c][r] > 0) {	break; }
		}
		c=origC;
		while (c<4) {
			c++;
			if (board[c][r] < 0) {
				cpuMoves[count][0]=origC;
				cpuMoves[count][1]=r;
				cpuMoves[count][2] = c;		
				cpuMoves[count][3] = r;	
				count++;	
				break;
			}
			else if (board[c][r] > 0) {	break; }
		}		
	}
}
