#include<stdlib.h>
#include<stdio.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<sys/un.h>
#include<string.h>
#include<netinet/in.h>
#include<arpa/inet.h>
void lighton()
{
	system("/home/pi/driver/Light 1 0");
	system("sleep 2s");
	system("/home/pi/driver/Light 2 0");
	system("sleep 2s");
	system("/home/pi/driver/Light 3 0");
	system("sleep 2s");
	system("/home/pi/driver/Light 3 1");
	system("sleep 2s");
	system("/home/pi/driver/Light 2 1");
	system("sleep 2s");
	system("/home/pi/driver/Light 1 1");
	system("sleep 2s");

}
int main()
{
	int recv_fd;
	int res;
	struct sockaddr_in myaddr;
	myaddr.sin_family=AF_INET;
	myaddr.sin_addr.s_addr=inet_addr("115.28.26.84");

	myaddr.sin_port = htons(4600);
	char recv_buf[4];
	char send_buf[100];

	//1,socket
	recv_fd=socket(AF_INET,SOCK_STREAM,0);
	if(recv_fd == -1)
	{
		perror("socket error");
		exit(1);
	} 
	//2,bind
	if(connect(recv_fd,(struct sockaddr *)&myaddr,sizeof(myaddr))==-1)
	{
		perror("connect error ");
		exit(1);
	}
	
	recv(recv_fd,recv_buf,4,0);
	printf("get message : %s\n",recv_buf);
		if(recv_buf[0]=='1')
			system("/home/pi/driver/Light 1 0");
		if(recv_buf[0]=='0')
			system("/home/pi/driver/Light 1 1");
			
		if(recv_buf[1]=='1')
			system("/home/pi/driver/Light 2 0");
		if(recv_buf[0]=='0')
			system("/home/pi/driver/Light 2 1");
			
		if(recv_buf[2]=='1')
			system("/home/pi/driver/Light 3 0");
		if(recv_buf[0]=='0')
			system("/home/pi/driver/Light 3 1");
			
		if(recv_buf[3]=='1')
			system("/home/pi/driver/Light 4 0");
		if(recv_buf[0]=='0')
			system("/home/pi/driver/Light 4 1");
	
			
	close(recv_fd);
}
