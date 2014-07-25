/************************************************
Filename: prog2.c
Description: threads
Author: Cody Lanier
***************************************/

#include <stdio.h> 
#include <sys/types.h> 
#include <sys/wait.h> 
#include <unistd.h>
#include <sys/uio.h> 
#include <string.h>
#include <stdlib.h>
#include <pthread.h>

//define methods
void findMin();
void findMax();
void findSum();
void findProd();
void display();

//create shared data
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;	
int count, min, max, sum, prod;
char *ints[];

int main(int argc, char *argv[]) {
	//check for correct number of args and first arg
	if ( (argc != 9) || (strcmp(argv[0], "prog2") != 0) ) {
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

	//assign inputs to shared variables
	count = argc - 1;
	for (i=1; i<argc; i++) {
		ints[i-1] = (char*)malloc(sizeof(char) * strlen(argv[i])+1);
		strcpy(ints[i-1], argv[i]);
	}
	
	//create threads
	pthread_t mi, ma, s, p, d;
	printf("creating thread findMin...\n");
	pthread_create(&mi, NULL, (void *) findMin, NULL);
	printf("creating thread findMax...\n");
	pthread_create(&ma, NULL, (void *) findMax, NULL);
	printf("creating thread findSum...\n");
	pthread_create(&s, NULL, (void *) findSum, NULL);
	printf("creating thread findProd...\n");
	pthread_create(&p, NULL, (void *) findProd, NULL);
	printf("creating thread display...\n");
	pthread_create(&d, NULL, (void *) display, NULL);	
	
	//wait for threads to finish
	printf("joining thread findMin...\n");
	pthread_join(mi, NULL);
	printf("joining thread findMax...\n");
	pthread_join(ma, NULL);
	printf("joining thread findSum...\n");
	pthread_join(s, NULL);
	printf("joining thread findProd...\n");
	pthread_join(p, NULL);
	printf("joining thread display...\n");
	pthread_join(d, NULL);
	printf("all threads finished... exiting...\n");
	return 0;
}
//find minimum
void findMin() {	
	printf("thread %d, aka findMin, has started...\n", pthread_self());
	int i, curr, next;
	
	do {
		pthread_mutex_lock(&mutex);
		
			//convert to int and assign to curr
			curr = atoi(ints[0]);
			
			//find the min of the integers
			for (i=1; i<count; i++) {
				next = atoi(ints[i]);
				if (next < curr) { curr = next; }
			}
			//assign min
			min = curr;
		pthread_mutex_unlock(&mutex);
		printf("findMin result is %d... thread exiting...\n", min);
		pthread_exit(NULL);
	} while (1);
}

void findMax() {
	printf("thread %d, aka findMax, has started...\n", pthread_self());
	int i, curr, next;
	
	do {
		pthread_mutex_lock(&mutex);
			//convert to int and assign to curr
			curr = atoi(ints[0]);
			
			//find the max of the integers
			for (i=1; i<count; i++) {
				next = atoi(ints[i]);
				if (next > curr) { curr = next; }
			}
			
			//assign max
			max = curr;
		pthread_mutex_unlock(&mutex);
		printf("findMax result is %d... thread exiting...\n", max);
		pthread_exit(NULL);
	} while (1);	
}

void findSum() {
	printf("thread %d, aka findSum, has started...\n", pthread_self());
	int i, curr, next;
	
	do {
		pthread_mutex_lock(&mutex);
			//convert to int and assign to curr
			curr = atoi(ints[0]);
			
			//compute the sum of the integers
			for (i=1; i<count; i++) {
				next = atoi(ints[i]);
				curr = curr + next;
			}
			
			//assign sum
			sum = curr;
		pthread_mutex_unlock(&mutex);
		printf("findSum result is %d... thread exiting...\n", sum);
		pthread_exit(NULL);
	} while (1);
}

void findProd() {
	printf("thread %d, aka findProd, has started...\n", pthread_self());
	int i, curr, next;

	do {
		pthread_mutex_lock(&mutex);
			//convert to integer
			curr = atoi(ints[0]);
			//computer the product from the integers
			for (i=1; i<count; i++) {
				next = atoi(ints[i]);
				curr = curr * next;
			}
			
			//assign product
			prod = curr;
		pthread_mutex_unlock(&mutex);
		printf("findProd result is %d... thread exiting...\n", prod);
		pthread_exit(NULL);
	} while(1);
}

void display() {
	printf("thread %d, aka display, has started...\n", pthread_self());
	
	do {
		pthread_mutex_lock(&mutex);
	
			//print out each result
			printf("The min is: %d\n", min);
			printf("The max is: %d\n", max);
			printf("The sum is: %d\n", sum);
			printf("The product is: %d\n", prod);
		pthread_mutex_unlock(&mutex);
		printf("display thread is finished... thread exiting...\n");
		pthread_exit(NULL);
	} while(1);
}
