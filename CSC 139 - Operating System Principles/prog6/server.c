/************************************************
Filename: server.c
Description: message queue server
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
int result;

int main(int argc, char *argv[]) {
	//create and access the message queue
	key = ftok(".", 'z'); 
	mid = msgget(key, IPC_CREAT|0660);
	
	//loop that runs until empty message received
	for(;;) {
		//receive a message
		result = msgrcv(mid, &msg, sizeof(msg), SERVER, 0);
		
		//if message received errored, display the error, close the message queue, and exit
		if(result == -1) {
			printf("message receive failed at server... %s\n", strerror(errno));
			msgctl(mid, IPC_RMID, (struct msqid_ds *) 0);
			exit(1);
		}

		//if empty message received, close the message queue and exit
		if(result == 0) { 
			msgctl(mid, IPC_RMID, (struct msqid_ds *) 0);
			printf("Empty message received. Exiting...\n");
			exit(1);
		}
		
		//valid message received, loop through and change all lowercase characters to uppercase
		int i;
		for(i=0; i<sizeof(msg); i++) {
			msg.buffer[i] = toupper(msg.buffer[i]);
		}
		
		//set headers and send uppercase version back to client that sent the lowercase message
		msg.msg_to=msg.msg_fm;
		msg.msg_fm=SERVER;
		if(msgsnd(mid, &msg, sizeof(msg.buffer), 0)==-1) {
			//if error, display the error, close the message queue, and exit
			printf("message send failed at server... %s\n", strerror(errno));
			msgctl(mid, IPC_RMID, (struct msqid_ds *) 0);
			exit(1);
		}
	}
	
	return(0);
}



