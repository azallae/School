/************************************************
Filename: prog1.c
Description: multiprocessor
Author: Cody Lanier
***************************************/

#include <stdio.h> 
#include <sys/types.h> 
#include <sys/wait.h> 
#include <unistd.h> 
#include <sys/uio.h> 
#include <string.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
	//check for correct number of args and first arg
	if ( (argc != 9) || (strcmp(argv[0], "prog1") != 0) ) {
		printf("arguments invalid. exiting...\n");
		return 0;
	}
	
	int i, j, k; //loop variables
	//loop through each argument and verify that it is an integer
	for (i=1; i<argc; i++) {
		//first char of arg can be +, -, or a digit
		if ( (argv[i][0]=='+') || (argv[i][0]=='-') || (isdigit(argv[i][0])) ) {
			k = strlen(argv[i]);
			//loop through the current args chars and verify that each is a digit
			for (j=1; j<k; j++) {
				if ( isdigit(argv[i][j]) ) {
					//do nothing
				}
				else { //if something other than a digit is found
					printf("arguments invalid. exiting...\n");
					return 0;
				}
			}
		}
		else { //if first char of arg is not +, -, or digit
			printf("arguments invalid. exiting...\n");
			return 0;
		}
	}
	//arguments pass validity tests
	printf("arguments valid. proceeding...\n");

	//create pipes and pids
	int p1[2], p2[2], p3[2], p4[2];
	int status;
	pid_t MC0, MC1, MC2, MC3, display;
	pipe(p1);
	pipe(p2);
	pipe(p3);
	pipe(p4);

	//first fork, first child
	if ((MC0 = fork()) == 0) {
		//display pids and close unused pipe ends
		printf("ppid: %d min pid: %d\n", getppid(), getpid());
		close(p1[0]);
		close(p2[0]);
		close(p3[0]);
		close(p4[0]);
		close(p2[1]);
		close(p3[1]);
		close(p4[1]);

		//convert to integer
		j = atoi(argv[1]);
		//find the min of the integers
		for (i=2; i<argc; i++) {
			k = atoi(argv[i]);
			if (k < j) { j = k; }
		}
		
		//write to the pipe, close it and exit child process
		write(p1[1], (void *) &j, (sizeof(j)));
		close(p1[1]);
		exit(0);
	}

	if ((MC1 = fork()) == 0) {
		//display pids and close unused pipe ends
		printf("ppid: %d max pid: %d\n", getppid(), getpid());
		close(p1[0]);
		close(p2[0]);
		close(p3[0]);
		close(p4[0]);
		close(p1[1]);
		close(p3[1]);
		close(p4[1]);

		//convert to integer
		j = atoi(argv[1]);
		//find the max of the integers
		for (i=2; i<argc; i++) {
			k = atoi(argv[i]);
			if (k > j) { j = k; }
		}

		//write to the pipe, close it and exit child process
		write(p2[1], (void *) &j, (sizeof(j)));
		close(p2[1]);
		exit(0);
	}

	if ((MC2 = fork()) == 0) {
		//display pids and close unused pipe ends
		printf("ppid: %d sum pid: %d\n", getppid(), getpid());
		close(p1[0]);
		close(p2[0]);
		close(p3[0]);
		close(p4[0]);
		close(p1[1]);
		close(p2[1]);
		close(p4[1]);

		//convert to integer
		j = atoi(argv[1]);
		//compute the sum of the integers
		for (i=2; i<argc; i++) {
			k = atoi(argv[i]);
			j = j + k;
		}

		//write to the pipe, close it and exit child process
		write(p3[1], (void *) &j, (sizeof(j)));
		close(p3[1]);
		exit(0);
	}

	if ((MC3 = fork()) == 0) {
		//display pids and close unused pipe ends
		printf("ppid: %d product pid: %d\n", getppid(), getpid());
		close(p1[0]);
		close(p2[0]);
		close(p3[0]);
		close(p4[0]);
		close(p1[1]);
		close(p2[1]);
		close(p3[1]);

		//convert to integer
		j = atoi(argv[1]);
		//computer the product from the integers
		for (i=2; i<argc; i++) {
			k = atoi(argv[i]);
			j = j * k;
		}

		//write to the pipe, close it and exit child process
		write(p4[1], (void *) &j, (sizeof(j)));
		close(p4[1]);
		exit(0);
	}

	if ((display = fork()) == 0) {
		//display pids and close unused pipe ends
		printf("ppid: %d display pid: %d\n", getppid(), getpid());
		close(p1[1]);
		close(p2[1]);
		close(p3[1]);
		close(p4[1]);

		//create variables to store results
		int min, max, sum, prod;
		
		//read and store results from each pipe
		read(p1[0], (void *) &min, sizeof(min));
		read(p2[0], (void *) &max, sizeof(max));
		read(p3[0], (void *) &sum, sizeof(sum));
		read(p4[0], (void *) &prod, sizeof(prod));
		
		//print out each result
		printf("The min is: %d\n", min);
		printf("The max is: %d\n", max);
		printf("The sum is: %d\n", sum);
		printf("The product is: %d\n", prod);
		
		//close pipes and exit the child process
		close(p1[0]);
		close(p2[0]);
		close(p3[0]);
		close(p4[0]);
		exit(0);
	}

	//wait for the display child process to finish before ending the parent process/exiting the program
	waitpid(display, &status, 0);
	return 0;
}
