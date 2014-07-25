/************************************************
Filename: prog4.c
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

//define variables
#define SLOTSIZE 20
#define SLOTCNT 12
char buffer[SLOTCNT][SLOTSIZE];
int bytes[SLOTCNT];
int pIndex = 0, cIndex=0, total=0;
char* infile;
char* outfile;

//define methods
void produce();
void consume();

//create and mutex and condition variables
pthread_mutex_t buf_lock = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t empty_slot = PTHREAD_COND_INITIALIZER;
pthread_cond_t avail_item = PTHREAD_COND_INITIALIZER;

int main(int argc, char *argv[]) {
	//check for correct number of args
	if ((argc != 3)/* || (argc != 4)*/) {
		printf("Correct usage: prog3 infile outfile\n");
		return 0;
	}
	
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

void produce() {
	//open the infile
	FILE* myFile = fopen(infile, "rb");
	int count;

	//if the file doesn't exist, output error message and exit
	if (myFile == 0) {
		printf("Could not open file\n");
		printf("Correct usage: prog3 infile outfile\n");
		exit(0);
	}
	else {	//if file does exist, continue	
		do {
			//take mutex
			pthread_mutex_lock(&buf_lock);
			//wait for a slot, if the buffer is full
			while (total==12) { 
				pthread_cond_wait(&empty_slot, &buf_lock); 
			}
			//when slot available, read data into a buffer slot
			count = fread(buffer[pIndex], sizeof(char), sizeof(buffer[pIndex]), myFile);
			//save how many bytes were read into that slot
			bytes[pIndex]=count;
			//output message
			printf("put %d chars in buffer spot %d.\n", count, pIndex);
			//update total number of buffer slots in use
			total++;
			//signal that an item is available and release the mutex
			pthread_cond_signal(&avail_item); 
			pthread_mutex_unlock(&buf_lock);
			//update the index number for the producer to use next
			pIndex = (pIndex+1) % 12;
		} while (!feof(myFile)); //stop if the end of file is reached		
	}
	//close the file and exit the thread
	fclose(myFile);
	pthread_exit(NULL);
}

void consume() {
	//open or create file for writing
	FILE* myFile = fopen(outfile, "wb");
	int count;

	do {
		//take the mutex
		pthread_mutex_lock(&buf_lock); 
		//wait for an item, if the buffer is empty
		while (total==0) { 
			pthread_cond_wait(&avail_item, &buf_lock); 
		}
		//when item available, write from the buffer to the file
		count = fwrite(buffer[cIndex], sizeof(char), bytes[cIndex], myFile);
		//output message
		printf("got %d chars from buffer spot %d.\n", count, cIndex);
		//update total number of buffer slots in use
		total--;
		//signal that a slot is available and release the mutex
		pthread_cond_signal(&empty_slot); 
		pthread_mutex_unlock(&buf_lock);
		//update the index number for the consumer to use next
		cIndex = (cIndex + 1) % 12;
	} while (count==20); //stop if the number of bytes written is less than the number that fits in a slot
	
	//close the file and exit the thread
	fclose (myFile);
	pthread_exit(NULL);
}
