/************************************************
Filename: client1.c
Description: message queue client1
Author: Cody Lanier
***************************************/

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <string.h>
#include <ctype.h>
#include <fcntl.h> 
#include <errno.h>
 
#define SERVER 1L

typedef struct { 
	long msg_to; 
	long msg_fm; 
	char buffer[BUFSIZ] 
} MESSAGE;

int mid; 
key_t key; 
struct msqid_ds buf; 
MESSAGE msg;
int fd1, fd2; 

int main(int argc, char *argv[]) {
	//verify correct arguments given
	if (argc != 3) {
		printf("Correct usage: client1 infile1 infile2\n");
		return 0;
	}

	//access the message queue
	key = ftok(".", 'z'); 
	mid = msgget(key, 0);
	
	//open infile1
	if((fd1 = open(argv[1], O_RDWR)) == -1) {
		//if error, display the error, close the message queue, and exit
		printf("failed to open infile1... %s\n", strerror(errno));
		msgctl(mid, IPC_RMID, (struct msqid_ds *) 0);
		exit(1);
	}
	//read infile1 into the message buffer
	if(read(fd1, msg.buffer, BUFSIZ) == -1) {
		//if error, display the error, close the message queue, and exit
		printf("failed to open infile1... %s\n", strerror(errno));
		msgctl(mid, IPC_RMID, (struct msqid_ds *) 0);
		exit(1);
	}	
	
	//print out the message text from infile1
	printf("infile1 before: %s\n", msg.buffer);
	
	//set the header then send the message to server
	msg.msg_to=SERVER;
	msg.msg_fm=getpid();
	if(msgsnd(mid, &msg, sizeof(msg.buffer), 0) == -1) {
		//if error, display the error, close the message queue, and exit
		printf("message send failed at client1... %s\n", strerror(errno));
		msgctl(mid, IPC_RMID, (struct msqid_ds *) 0);
		exit(1);
	}
	
	//receive the message from server
	if(msgrcv(mid, &msg, sizeof(msg), getpid(), 0) == -1) {
		//if error, display the error, close the message queue, and exit
		printf("message send failed at client1... %s\n", strerror(errno));
		msgctl(mid, IPC_RMID, (struct msqid_ds *) 0);
		exit(1);
	}
	
	//print out the new message text from server and close the fd
	printf("infile1 after: %s\n", msg.buffer);
	close(fd1);
	
	//open infile2
	if((fd2 = open(argv[2], O_RDWR)) == -1) {
		//if error, display the error, close the message queue, and exit
		printf("failed to open infile1... %s\n", strerror(errno));
		msgctl(mid, IPC_RMID, (struct msqid_ds *) 0);
		exit(1);
	}
	//read infile2 into the message buffer
	if(read(fd2, msg.buffer, BUFSIZ) == -1) {
		//if error, display the error, close the message queue, and exit
		printf("failed to open infile1... %s\n", strerror(errno));
		msgctl(mid, IPC_RMID, (struct msqid_ds *) 0);
		exit(1);
	}	
	
	//print out the message text from infile2
	printf("infile2 before: %s\n", msg.buffer);
	
	//set the header then send the message to server
	msg.msg_to=SERVER;
	msg.msg_fm=getpid();
	if(msgsnd(mid, &msg, sizeof(msg.buffer), 0) == -1) {
		//if error, display the error, close the message queue, and exit
		printf("message send failed at client1... %s\n", strerror(errno));
		msgctl(mid, IPC_RMID, (struct msqid_ds *) 0);
		exit(1);
	}
	
	//receive the message from server
	if(msgrcv(mid, &msg, sizeof(msg), getpid(), 0) == -1) {
		//if error, display the error, close the message queue, and exit
		printf("message send failed at client1... %s\n", strerror(errno));
		msgctl(mid, IPC_RMID, (struct msqid_ds *) 0);
		exit(1);
	}
	
	//print out the new message text from server andd close the fd
	printf("infile2 after: %s\n", msg.buffer);
	close(fd2);
	
	return(0);
}