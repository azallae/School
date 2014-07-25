/************************************************
Filename: prog5.c
Description: memory-mapping
Author: Cody Lanier
***************************************/

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <signal.h>
#include <string.h>

int fd;
struct stat buf;
char *mm_file;

int main(int argc, char *argv[]) {
	//check for correct number of args
	if (argc != 2) {
		printf("Correct usage: prog5 filename\n");
		return 0;
	}
	
	//create a new file and write the lowercase string to it
	FILE* theFile = fopen(argv[1], "wb");
	char* lowercaseStr = "a disk file can be memory mapped into virtual memory, through the mmap\ncall, thus enabling file i/o to be done via simple memory accesses.\nin this assignment, this disk file gets memory mapped first. then two\nchild processes are created, each making some changes to the file.\nwhen both child processes are done, changes are synch'ed to disk file.\n";
	fwrite(lowercaseStr, sizeof(char), strlen(lowercaseStr), theFile);
	//close the newly created file and open with a file descriptor
	fclose(theFile);
	fd = open(argv[1], O_RDWR);
	
	//memory-map the file
	fstat(fd, &buf); //used to determine the size of the file
	if ((mm_file = mmap(0, (size_t) buf.st_size, PROT_READ|PROT_WRITE,MAP_SHARED, fd, 0)) == (caddr_t) - 1) {
		fprintf(stderr, "mmap call fails\n");
	}
	
	//create children
	int status;
	pid_t child1, child2;

	//first fork, first child
	if ((child1 = fork()) == 0) {
		printf("Child 1 %d reads: \n %s\n", getpid(), mm_file);
		int count=0;
		//convert file content to uppercase string;
		while(count != (size_t) buf.st_size) {
			mm_file[count] = toupper(mm_file[count]);
			count++;
		}
		msync(0, (size_t) buf.st_size, MS_SYNC);
		printf("Child 1 %d reads again: \n %s\n", getpid(), mm_file);
		
		exit(0); //exit the process
	}

	if ((child2 = fork()) == 0) {
		sleep(1); //so that child 2 will perform its task after child 1 finishes
		printf("Child 2 %d reads: \n %s\n", getpid(), mm_file);
		//use a hyphen to replace the space between “MEMORY” and “MAPPED”
		char* ptr;
		while ((ptr = strstr(mm_file, "MEMORY MAPPED")) != NULL) {
			strncpy (ptr,"MEMORY-MAPPED",13);
		}
		//replace “CHANGES” with “UPDATES”;
		while ((ptr = strstr(mm_file, "CHANGES")) != NULL) {
			strncpy (ptr,"UPDATES",7);
		}
		
		printf("Child 2 %d reads again: \n %s\n", getpid(), mm_file);

		exit(0); //exit the process
	}

	//wait for the display child process to finish before ending the parent process/exiting the program
	waitpid(child2, &status, 0);
	close(fd);
	return 0;
}
