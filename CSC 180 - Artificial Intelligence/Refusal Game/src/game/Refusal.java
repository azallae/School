package game;

import java.util.Scanner;

import moves.CPULegalMoves;
import moves.OPPLegalMoves;

public class Refusal {
	int board[][] = new int[5][8];
	final int maxdepth=6;
	public static final int p = 10;
	public static final int n = 30;
	public static final int br = 41;
	public static final int bl = 40;
	public static final int k = 100;
	final int startState[][] = {
			{-br,0,-n,0,0,n,0,bl},
			{-k,0,-p,0,0,p,0,0},
			{0,0,-p,0,0,p,0,0},
			{0,0,-p,0,0,p,0,k},
			{-bl,0,-n,0,0,n,0,br}};
	
	public static void main(String[] args) { new Refusal();	}
	
	public Refusal() {
		setup();
		System.out.print("Would you like to go 1st or 2nd? (1 for first, 2 for second)  ");
		Scanner kb = new Scanner(System.in);
		int first = kb.nextInt();
		printBoard();
		if (first == 1) {
			for (;;) {  
				getAmove();
			    checkGameOver();
			    miniMax();
			    checkGameOver();
		}	}
		else {
			for (;;) {  
				miniMax();
			    checkGameOver();
			    getAmove();
			    checkGameOver();
	}	}	}

	private void setup() {
		for (int i=0; i<5; i++) {
			for (int j=0; j<8; j++) {
				board[i][j] = startState[i][j];
	}	}	}

	private void printBoard() {
		for (int x=0; x<20; x++) { System.out.println(); }
		char piece = 0;
		int count=8;
		
		System.out.println();
		System.out.println("     A  B  C  D  E");
		System.out.println("     -------------");
		for (int j=7; j>=0; j--) {
			System.out.print("  " + count +"|");
			for (int i=0; i<5; i++) {
				switch (board[i][j]) {
				case 0: piece = '-'; break;
				case bl: piece = 'R'; break;
				case k: piece = 'K'; break;
				case br: piece = 'L'; break;
				case n: piece = 'N'; break;
				case p: piece = 'P'; break;
				case -bl: piece = 'l'; break;
				case -k: piece = 'k'; break;
				case -br: piece = 'r'; break;
				case -n: piece = 'n'; break;
				case -p: piece = 'p'; break;
				}
				System.out.print(" " + piece + " ");
			}
			System.out.println("|" + count);
			count--;
		}
		System.out.println("     -------------");
		System.out.println("     A  B  C  D  E");
		System.out.println();
	}

	private void getAmove() {
		OPPLegalMoves oppMoves = new OPPLegalMoves(board);
		int[][] currMoves = oppMoves.getMoves();
		int count = oppMoves.getCount();
		
		if (count == 0) { System.out.println("You have no legal moves left... You Lose!"); System.exit(0); }
		
		Scanner keyboard = new Scanner(System.in);
		String humanMove;
		System.out.print("Enter your move:  ");
		humanMove = keyboard.nextLine();

		if (humanMove.length()!=4) { System.out.println("Invalid move.... Try again.");   getAmove(); };
		
		int a,b,c,d;
		a = humanMove.charAt(0) - 'a';
		b = humanMove.charAt(1) - '1';
		c = humanMove.charAt(2) - 'a';
		d = humanMove.charAt(3) - '1';
						
		if (a>=0 && a<5 && b>=0 && b<8 && c>=0 && c<5 && d>=0 && d<8 && board[a][b]<0) {
			for (int i=0; i<count; i++) {
				if (a==currMoves[i][0] && b==currMoves[i][1] && c==currMoves[i][2] && d==currMoves[i][3]) {
					if (count==1) { System.out.println("I reject your move, and you have no more legal moves... I Win!"); System.exit(0); }
					
					board[c][d]=board[a][b];   
					board[a][b]=0;				 
					if (d==7 && b!=7) {
						switch(board[c][d]) {
						case -bl: board[c][d] = -br;   break;
						case -br: board[c][d] = -bl;   break;
					}	}
					printBoard();
					System.out.println("I accept your move.");
					return;
		}	}	} 
		System.out.println("Invalid move.... Try again.");   getAmove();
	}

	private void miniMax() {
		CPULegalMoves cpuMoves = new CPULegalMoves(board);
		int[][] currMoves = cpuMoves.getMoves();
		int count = cpuMoves.getCount();
		
		if (count == 0) { System.out.println("I am out of moves... You Win!"); System.exit(0); }
		
		int[][] bestMoves = new int[2][5];
		for (int i=0; i<2; i++) { bestMoves[i][0]=9;	bestMoves[i][4] = -99999; }	
		int currScore, depth=1;
		int a,b,c,d, prev;
		
		for (int i=0; i<count; i++) {
			a = currMoves[i][0];	b = currMoves[i][1];	c = currMoves[i][2];	d = currMoves[i][3];	
			prev = board[c][d];
			
			board[c][d] = board[a][b];
			board[a][b] = 0;
			
			currScore = min(depth+1, bestMoves[0][4]);
			if (currScore>bestMoves[0][4]) { 
				bestMoves[1][0] = bestMoves[0][0]; 	bestMoves[1][1] = bestMoves[0][1];	
				bestMoves[1][2] = bestMoves[0][2];	bestMoves[1][3] = bestMoves[0][3];
				bestMoves[0][0] = a; 	bestMoves[0][1] = b;	bestMoves[0][2] = c;	bestMoves[0][3] = d;	
				bestMoves[1][4] = bestMoves[0][4];	bestMoves[0][4]=currScore; 
			} 
			else if (currScore>bestMoves[1][4]) {
				bestMoves[1][0] = a; 	bestMoves[1][1] = b;	bestMoves[1][2] = c;	bestMoves[1][3] = d;	
				bestMoves[1][4]=currScore; 
			}
			
			board[a][b] = board[c][d];
			board[c][d] = prev;
		}
				
		makeMove(bestMoves);
	}

	private int min(int depth, int parentBest) {
		int best=99999, score;
		OPPLegalMoves oppMoves = new OPPLegalMoves(board);
		int[][] currMoves = oppMoves.getMoves();
		int count = oppMoves.getCount();
		int a,b,c,d,prev;
		
		if (check4winner() != 0) return (check4winner());
		if (depth == maxdepth || count == 0) return (evaluate());
				
		for (int i=0; i<count; i++) {
			a = currMoves[i][0];	b = currMoves[i][1];	c = currMoves[i][2];	d = currMoves[i][3];			
			prev = board[c][d];
			
			board[c][d] = board[a][b];
			board[a][b] = 0;
			
			score = max(depth+1, best);
			if (score>parentBest) { return best; }
			if (score<best) { best=score; }
			
			board[a][b] = board[c][d];
			board[c][d] = prev;
		}
		
		return(best);
	}

	private int max(int depth, int parentBest) { 
		int best=-99999, score;
		CPULegalMoves cpuMoves = new CPULegalMoves(board);
		int[][] currMoves = cpuMoves.getMoves();
		int count = cpuMoves.getCount();
		int a,b,c,d,prev;
		
		if (check4winner() != 0) return (check4winner());
		if (depth == maxdepth || count == 0) return (evaluate());
		
		for (int i=0; i<count; i++) {
			a = currMoves[i][0];	b = currMoves[i][1];	c = currMoves[i][2];	d = currMoves[i][3];			
			prev = board[c][d];
			
			board[c][d] = board[a][b];
			board[a][b] = 0;
			
			score = min(depth+1, best);
			if (score<parentBest) { return best; }
			if (score>best) { best=score; }
			
			board[a][b] = board[c][d];
			board[c][d] = prev;
		}
		
		return(best);
	}
	
	private void makeMove(int[][] moves) {
		int a,b,c,d;
		String[] stringMoves;
		
		a = moves[0][0];	b = moves[0][1];	c = moves[0][2];	d = moves[0][3];
		stringMoves = convertMove(a, b, c, d);			
		System.out.print("I want to make the move " + stringMoves[0] + stringMoves[1] + ". Do you accept or reject? (a or r)  ");
		Scanner s = new Scanner(System.in); 
		String refuse = s.nextLine();
		while (refuse.charAt(0) != 'a' && refuse.charAt(0) != 'r') {
			System.out.print("Please input only 'a' or 'r':  ");
			refuse = s.nextLine();
		}
		if (refuse.charAt(0) == 'r') {
			a = moves[1][0];	b = moves[1][1];	c = moves[1][2];	d = moves[1][3];
			if (a==9) { System.out.println("I have no more legal moves. You got me!"); System.exit(0); }
			stringMoves = convertMove(a, b, c, d);
		}	
			
		if (d==0 && b!=0) {
			switch(board[c][d]) {
			case bl: board[c][d] = br;   break;
			case br: board[c][d] = bl;   break;
			}
		}
			
		board[c][d]=board[a][b];   
		board[a][b]=0;
		printBoard();
		if (refuse.charAt(0) == 'r') { System.out.println("My move was " + stringMoves[0] + stringMoves[1]); }
	}

	private int evaluate() { 
		int sum = 0;
		for (int i=0; i<5; i++) {
			for (int j=0; j<8; j++) {
				sum += board[i][j];
			}
		}
		return sum;
	}

	private int check4winner()
	{
		boolean oppKing=false, cpuKing=false;
		
		for (int i=0; i<5; i++) {
			if (board[i][7] == k) {	cpuKing = true; }
			if (board[i][0] == -k) { oppKing = true; }
		}	
		
		if (oppKing && !cpuKing) { return -9999; }
		else if (!oppKing && cpuKing) {	return 9999; }	
		
		return 0;
	}
	
	private void checkGameOver() {
		if (check4winner() == 9999) { System.out.println("I Won! Wow, I'm not even real!"); System.exit(0); }
		else if (check4winner() == -9999) { System.out.println("You beat me!"); System.exit(0); }
	}
	
	private String[] convertMove(int a, int b, int c, int d) {
		char c1,r1,c2,r2, c3=0, r3=0, c4=0, r4=0;
		c1 = (char) (a + 'a');
		r1 = (char) (b + '1');
		c2 = (char) (c + 'a');
		r2 = (char) (d + '1');	
		
		switch (c1) {
		case 'a': c3='e'; break;
		case 'b': c3='d'; break;
		case 'c': c3='c'; break;
		case 'd': c3='b'; break;
		case 'e': c3='a'; break;
		}
		switch (r1) {
		case '1': r3='8'; break;
		case '2': r3='7'; break;
		case '3': r3='6'; break;
		case '4': r3='5'; break;
		case '5': r3='4'; break;
		case '6': r3='3'; break;
		case '7': r3='2'; break;
		case '8': r3='1'; break;
		}
		switch (c2) {
		case 'a': c4='e'; break;
		case 'b': c4='d'; break;
		case 'c': c4='c'; break;
		case 'd': c4='b'; break;
		case 'e': c4='a'; break;
		}
		switch (r2) {
		case '1': r4='8'; break;
		case '2': r4='7'; break;
		case '3': r4='6'; break;
		case '4': r4='5'; break;
		case '5': r4='4'; break;
		case '6': r4='3'; break;
		case '7': r4='2'; break;
		case '8': r4='1'; break;
		}
		String[] moves = new String[2];
		moves[0] = "" + c1+r1+c2+r2;
		moves[1] = " (" + c3+r3+c4+r4 + ")";
		
		return moves;
	}
}