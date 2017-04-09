# CS490 Group2 Final Project
Adrian Ward-Manthey, Jeff Blankenship     
CS 490 Networking, Spring 2017
Prof. Williams

**To do list:**
- [x] Finish the checkpoint documentation
- [x] Ask for destination IP (currently hard coded)
- [ ] Ask for and load a file to send (currently sends a large string)
- [ ] Make the receiver save as a file (currently displays assembled message on console)

### Implementation checkpoint 4/9 **

Video of implementation:
https://github.com/JeffBship/CS490-Group2-Final-Project/blob/Implentation-Checkpoint/Checkpoint.mp4

Instructions to recreate:  
Open 2 instances of an IDE (ie Netbeans), or open the project on 2 computers (must be on the same local network).  
Run the file **RDTack.java**  make note of the local IP.  
RDTack has some built in testing functions, and will only ACK the percent of packets shown.  The rest will be dropped.  
This percentage can be adjusted by altering line 22 of the code.  
There is a built in delay to simulate slow networks.  The delay time and variation percentage can be adjusted by altering lines 23-24 of the code. 

This file is purposefully on an infinite loop.  It will continue to receive and process packets until killed.

In the other instance of the IDE, run the project **P2P_fileshare.java**.  Enter the IP of the target computer running RDTack.java, or just press enter if it's the same computer.


```
Implementation **checkpoint** – P2P client to directory server protocol

Code and comments for implementation of protocol of P2P client to directory server message transfer using UDP
Checkpoint does not actually need to do any sort of directory listing or update, but needs to be able to reliably transfer a multipart message.

Include a detailed description of how to run the implementation;
Contributes 6 points toward the course total;
```
