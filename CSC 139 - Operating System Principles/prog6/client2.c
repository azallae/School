/************************************************
Filename: client2.c
Description: message queue client2
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
int fd3;

int main(int argc, char *argv[]) {
	//verify correct arguments given
	if (argc != 2) {
		printf("Correct usage: client2 infile3\n");
		return 0;
	}
	
	//access the message queue
	key = ftok(".", 'z'); 
	mid = msgget(key, 0);
		
	//open infile3
	if((fd3 = open(argv[1], O_RDWR)) == -1) {
		//if error, display the error, close the message queue, and exit
		printf("failed to open infile1... %s\n", strerror(errno));
		msgctl(mid, IPC_RMID, (struct msqid_ds *) 0);
		exit(1);
	}
	//read infile3 into the message buffer
	if(read(fd3, msg.buffer, BUFSIZ) == -1) {
		//if error, display the error, close the message queue, and exit
		printf("failed to open infile1... %s\n", strerror(errno));
		msgctl(mid, IPC_RMID, (struct msqid_ds *) 0);
		exit(1);
	}	
	
	//print out the message text from infile3
	printf("infile3 before: %s\n", msg.buffer);
	
	//set the header then send the message to server
	msg.msg_to=SERVER;
	msg.msg_fm=getpid();
	if(msgsnd(mid, &msg, sizeof(msg.buffer), 0) == -1) {
		//if error, display the error, close the message queue, and exit
		printf("message send failed at client2... %s\n", strerror(errno));
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
	printf("infile3 after: %s\n", msg.buffer);
	close(fd3);
	
	//set header and send empty message to server
	msg.msg_to=SERVER;
	msg.msg_fm=getpid();
	if(msgsnd(mid, &msg, 0, 0) == -1) {
		//if error, display the error, close the message queue, and exit
		printf("message send failed at client2... %s\n", strerror(errno));
		msgctl(mid, IPC_RMID, (struct msqid_ds *) 0);
		exit(1);
	}
	
	return(0);
}



