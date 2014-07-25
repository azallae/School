#Jack Tam Huang and Cody Lanier
#CSC 138-01
#Assignment 1

from socket import *
import ssl
import base64
import multiprocessing

mailServer = 'smtp.gmail.com'
serverPort = 587

#creating the socket
clientSocket = socket(AF_INET, SOCK_STREAM)

#establish connection with server
clientSocket.connect((mailServer, serverPort))
print 'Connected to ' + mailServer + ' Port: 587'
replyOne = clientSocket.recv(1024)
print replyOne
if replyOne[:3] != '220':
        print '220 reply not received from server.'
#greeting
heloCommand = 'helo gmail.com\r\n'
clientSocket.send(heloCommand)
replyTwo = clientSocket.recv(1024)
print replyTwo
if replyTwo[:3] != '250':
        print '250 reply not received from server.'

#starts data transfer
clientSocket.send('starttls\r\n')
replyThree = clientSocket.recv(1024)
print replyThree

#request for login
secureConnect = ssl.wrap_socket(clientSocket, ssl_version=ssl.PROTOCOL_SSLv23)
secureConnect.send('auth login\r\n')
replyFour = secureConnect.recv(1024)
print replyFour

userID = '@gmail.com'
pw = ''

#send login info
secureConnect.send(base64.b64encode(userID)+'\r\n')
secureConnect.send(base64.b64encode(pw)+'\r\n')
print secureConnect.recv(1024)

print 'login response:'
print secureConnect.recv(1024)


#send emails
def sendEmail(fromAddr, toAddr, toName, subj, ccAddr=""):
        #sending from 
        print 'from'
        secureConnect.send("mail from: " + fromAddr + "\r\n")
        print secureConnect.recv(1024)

        #sending to
        print 'to'
        secureConnect.send("rcpt to: " + toAddr + "\r\n")
        print secureConnect.recv(1024)

        if ccAddr:
                print 'cc'
                secureConnect.send("rcpt to: " + ccAddr + "\r\n")
                print secureConnect.recv(1024)

        #email body
        print 'data coming...'
        secureConnect.send('data\r\n')
        print secureConnect.recv(1024)

        print 'message'
        secureConnect.send('To: ' + toName + '\r\nSubject: ' + subj + '\r\nThis is a test message sent from Jack and Cody using Python.\r\n.\r\n')
        print secureConnect.recv(1024)

        #connection.close()

if __name__ == '__main__':
        p1 = multiprocessing.Process(target=sendEmail, args=('<bcdauto2013@gmail.com>','<cody.lanier9@gmail.com>', 'Cody\r\nCC: Jack', 'Test Email #1', '<jtlpe156@gmail.com>',))
        p2 = multiprocessing.Process(target=sendEmail, args=('<bcdauto2013@gmail.com>','<jtlpe156@gmail.com>', 'JackTamHuang', 'Test Email #2'))
        p3 = multiprocessing.Process(target=sendEmail, args=('<bcdauto2013@gmail.com>','<cody.lanier9@gmail.com>', 'CodyLanier', 'Test Email #3'))
        p1.start()
        p2.start()
        p3.start()
        p1.join()
        p2.join()
        p3.join()

        #close connections
        secureConnect.close()
        clientSocket.close()
