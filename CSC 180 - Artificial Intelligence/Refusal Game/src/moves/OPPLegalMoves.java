package moves;

import game.Refusal;

public class OPPLegalMoves {
	int oppMoves[][] = new int[43][4];
	int board[][];
	int count = 0;
	
	public OPPLegalMoves(int[][] b) {
		board = b;
		
		for (int j=7; j>=0; j--) {
			for (int i=0; i<5; i++) {
				switch (board[i][j]) {
				case -Refusal.br: oppRightOnlyBishopMoves(i, j); break;
				case -Refusal.bl: oppLeftOnlyBishopMoves(i, j); break;
				case -Refusal.n: oppKnightMoves(i, j); break;
				case -Refusal.p: oppPawnMoves(i, j); break;
				case -Refusal.k: oppKingMoves(i, j); break;
				}
			}
		}
	}
	
	public int getCount() { return count; }
	public int[][] getMoves() { return oppMoves; }
	
	private void oppKnightMoves(int c, int r) {				
		if (c<4 && r<6) {
			if (board[c+1][r+2] >= 0) {
				oppMoves[count][0] = c; 
				oppMoves[count][1] = r; 
				oppMoves[count][2] = c+1; 
				oppMoves[count][3] = r+2; 
				count++;	
			}
		}
		if (c>0 && r<6) {
			if (board[c-1][r+2] >= 0) {
				oppMoves[count][0] = c; 
				oppMoves[count][1] = r;
				oppMoves[count][2] = c-1;   
				oppMoves[count][3] = r+2;  
				count++;
			}
		}
		if (c<3 && r<7) {
			if (board[c+2][r+1] >= 0) {
				oppMoves[count][0] = c; 
				oppMoves[count][1] = r;
				oppMoves[count][2] = c+2;  
				oppMoves[count][3] = r+1;  
				count++;
			}
		}
		if (c>1 && r<7) {
			if (board[c-2][r+1] >= 0) {
				oppMoves[count][0] = c; 
				oppMoves[count][1] = r;
				oppMoves[count][2] = c-2;  
				oppMoves[count][3] = r+1;  
				count++;	
			}
		}
	}

	private void oppRightOnlyBishopMoves(int c, int r) {
		int origC=c, origR=r;
		
		while (r<7) {
			r++;
			if (board[origC][r] == 0) {
				oppMoves[count][0] = origC; 
				oppMoves[count][1] = origR;
				oppMoves[count][2] = origC;   
				oppMoves[count][3] = r;    	
				count++;	
			}
			else if (board[origC][r] > 0) {
				oppMoves[count][0] = origC; 
				oppMoves[count][1] = origR;
				oppMoves[count][2] = origC;	 
				oppMoves[count][3] = r;
				count++;	
				break;
			}
			else {	break;	}
		}
		while (c<4) {
			c++;
			if (board[c][origR] == 0) {
				oppMoves[count][0] = origC; 
				oppMoves[count][1] = origR;
				oppMoves[count][2] = c;	 
				oppMoves[count][3] = origR;	
				count++;
			}
			else if (board[c][origR] > 0) {
				oppMoves[count][0] = origC; 
				oppMoves[count][1] = origR;
				oppMoves[count][2] = c;		 
				oppMoves[count][3] = origR;	
				count++;	
				break;
			}
			else {	break;	}
		}
	}

	private void oppLeftOnlyBishopMoves(int c, int r) {
		int origC=c, origR=r;
		
		while (r<7) {
			r++;
			if (board[origC][r] == 0) {
				oppMoves[count][0] = origC; 
				oppMoves[count][1] = origR;
				oppMoves[count][2] = origC;	
				oppMoves[count][3] = r;	
				count++;	
			}
			else if (board[origC][r] > 0) {
				oppMoves[count][0] = origC; 
				oppMoves[count][1] = origR;
				oppMoves[count][2] = origC;	 
				oppMoves[count][3] = r;	
				count++;	
				break;
			}
			else {	break;	}
		}
		while (c>0) {
			c--;
			if (board[c][origR] == 0) {
				oppMoves[count][0] = origC; 
				oppMoves[count][1] = origR;
				oppMoves[count][2] = c;		
				oppMoves[count][3] = origR;	
				count++;	
			}
			else if (board[c][origR] > 0) {
				oppMoves[count][0] = origC; 
				oppMoves[count][1] = origR;
				oppMoves[count][2] = c;		 
				oppMoves[count][3] = origR;	
				count++;
				break;
			}
			else {	break;	}
		}
	}
	
	private void oppPawnMoves(int c, int r) {		
		if(r<7) {
			if (board[c][r+1] == 0) {
				oppMoves[count][0]=c;
				oppMoves[count][1]=r;
				oppMoves[count][2]=c;
				oppMoves[count][3]=r+1;
				count++;
			}
			if (c>0) {
				if (board[c-1][r+1] > 0) {
					oppMoves[count][0]=c;
					oppMoves[count][1]=r;
					oppMoves[count][2]=c-1;
					oppMoves[count][3]=r+1;
					count++;
				}
			}
			if (c<4) {	
				if (board[c+1][r+1] > 0) {
					oppMoves[count][0]=c;
					oppMoves[count][1]=r;
					oppMoves[count][2]=c+1;
					oppMoves[count][3]=r+1;
					count++;
				}
			}
		}
	}

	private void oppKingMoves(int c, int r) {
		int origC=c;
		
		while (c>0) {
			c--;
			if (board[c][r] > 0) {
				oppMoves[count][0]=origC;
				oppMoves[count][1]=r;
				oppMoves[count][2] = c;		
				oppMoves[count][3] = r;	
				count++;	
				break;
			}
			else if (board[c][r] < 0) {	break; }
		}
		c=origC;
		while (c<4) {
			c++;
			if (board[c][r] > 0) {
				oppMoves[count][0]=origC;
				oppMoves[count][1]=r;
				oppMoves[count][2] = c;		
				oppMoves[count][3] = r;	
				count++;
				break;
			}
			else if (board[c][r] < 0) {	break; }
		}
	}
}
