/************************************************
Filename: prog3.c
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
#include <semaphore.h>

//define variables
#define SLOTSIZE 20
#define SLOTCNT 12
char buffer[SLOTCNT][SLOTSIZE];
int bytes[SLOTCNT];
int pIndex = 0, cIndex=0;
char* infile;
char* outfile;

//define methods
void produce();
void consume();

//create and initialize semaphores
sem_t buf_lock, slot_avail, item_avail;

int main(int argc, char *argv[]) {
	//check for correct number of args
	if ((argc != 3)/* || (argc != 4)*/) {
		printf("Correct usage: prog3 infile outfile\n");
		return 0;
	}

	sem_init(&buf_lock, 0, 1);
	sem_init(&slot_avail, 0, 12);
	sem_init(&item_avail, 0, 0);
	
	infile = argv[1];
	outfile = argv[2];

	//create threads
	pthread_t producer, consumer;
	printf("creating producer thread...\n");
	pthread_create(&producer, NULL, (void *)produce, NULL);
	printf("creating consumer thread...\n");
	pthread_create(&consumer, NULL, (void *)consume, NULL);

	//wait for threads to finish
	printf("joining producer thread...\n");
	pthread_join(producer, NULL);
	printf("joining consumer thread...\n");
	pthread_join(consumer, NULL);

	return 0;
}
//find open the infile
void produce() {
	FILE* myFile = fopen(infile, "rb");
	int count;

	if (myFile == 0)
	{
		printf("Could not open file\n");
		printf("Correct usage: prog3 infile outfile\n");
		exit(0);
	}
	else
	{
		do {
			sem_wait(&slot_avail);
			sem_wait(&buf_lock);
			count = fread(buffer[pIndex], sizeof(char), sizeof(buffer[pIndex]), myFile);
			bytes[pIndex]=count;
			printf("put %d chars in buffer spot %d.\n", count, pIndex);
			sem_post(&buf_lock);
			sem_post(&item_avail);
			pIndex = (pIndex+1) % 12;
		} while (!feof(myFile));
		
	}
	
	fclose(myFile);
	pthread_exit(NULL);
}

void consume() {
	FILE* myFile = fopen(outfile, "wb");
	int count;

	do {
		sem_wait(&item_avail);
		sem_wait(&buf_lock);
		count = fwrite(buffer[cIndex], sizeof(char), bytes[cIndex], myFile);
		printf("got %d chars from buffer spot %d.\n", count, cIndex);
		sem_post(&buf_lock);
		sem_post(&slot_avail);
		cIndex = (cIndex + 1) % 12;
	} while (count==20);
	
	fclose (myFile);
	pthread_exit(NULL);
}
