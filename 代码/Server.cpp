#include <stdio.h>
#include <winsock2.h>
#pragma comment (lib,"ws2_32.lib")

FILE *ioutfileServer;
FILE *ioutfileClient;
void CreateServer_4602();
void CreateServer_4600(char LightStatus[]);
DWORD WINAPI threadproServer(LPVOID pParam);
void SetStatus(char []);
void ExitSystem()
{
	if(ioutfileServer!=NULL)
		fclose(ioutfileServer);
	if(ioutfileClient!=NULL)
		fclose(ioutfileClient);
	WSACleanup();
	exit(0);
}
void SetStatus(char s[])
{
	printf("setstatus:%s\n",s);
	char light1,light2,light3,light4,door,window;
	FILE *Light1=fopen("./Status/Light1State.txt","r+");
	FILE *Light2=fopen("./Status/Light2State.txt","r+");
	FILE *Light3=fopen("./Status/Light3State.txt","r+");
	FILE *Light4=fopen("./Status/Light4State.txt","r+");
	FILE *Door=fopen("./Status/DoorState.txt","r+");
	FILE *Window=fopen("./Status/WindowState.txt","r+");
	light1=s[0];light2=s[1];light3=s[2];
	light4=s[3];door=s[4];window=s[5];
	fprintf(Light1,"%c",light1);
	fprintf(Light2,"%c",light2);
	fprintf(Light3,"%c",light3);
	fprintf(Light4,"%c",light4);
	fprintf(Door,"%c",door);
	fprintf(Window,"%c",window);
	fclose(Light1);fclose(Light2);fclose(Light3);
	fclose(Light4);fclose(Door);fclose(Window);

}
void CreateServer_4602()
{
	//这个socket用来接收客户端传来的灯光控制指令
	SOCKET m_SockServer;
	struct sockaddr_in serveraddr;//本地地址信息,结构体变量，后面的代码将会要填充其成员
	struct sockaddr_in serveraddrfrom;//连接的地址信息

	int iPort=4602;//设定为固定端口
	int iBindResult=-1;//绑定结果
	int iWhileCount=200;
	int num1=0,num2=0;
	struct hostent* localHost;
	char* localIP;

	SOCKET m_Server;
	/*读取状态信息将其发送出去*/
	
	char cWelcomBuffer[100];

	int len=sizeof(struct sockaddr);
	int iWhileListenCount=15;

	DWORD nThreadId = 0;
	int ires;//发送的返回值

	char cSendBuffer[1024];//发送消息缓存
	char cShowBuffer[1024];//接收消息缓存
	char cRecvBuffer[1024];

	ioutfileServer= fopen("MessageServer.txt","a");//打开记录消息的文件
	m_SockServer = socket ( AF_INET,SOCK_STREAM,  0);
	
	localHost = gethostbyname("");
	localIP = inet_ntoa (*(struct in_addr *)*localHost->h_addr_list);
	//设置网络地址信息
	serveraddr.sin_family = AF_INET;//使用ＴＣＰ协议					
	serveraddr.sin_port = htons(iPort);	//端口			
	serveraddr.sin_addr.S_un.S_addr = inet_addr(localIP);//通过IP和端口号绑定socket到本地主机
	//绑定地址信息
	iBindResult=bind(m_SockServer,(struct sockaddr*)&serveraddr,sizeof(struct sockaddr));
	//如果端口不能被绑定，重新设置端口
	while(iBindResult!=0 && iWhileCount > 0)
	{
		printf("绑定失败，重新输入：");
		scanf("%d",iPort);
		//设置网络地址信息
		serveraddr.sin_family = AF_INET;					
		serveraddr.sin_port = htons(iPort);//端口	
		serveraddr.sin_addr.S_un.S_addr = inet_addr(localIP);//IP
		//绑定地址信息
		iBindResult = bind(m_SockServer,(struct sockaddr*)&serveraddr,sizeof(struct sockaddr));
		iWhileCount--;
		if(iWhileCount<=0)
		{
			printf("端口绑定失败,重新运行程序\n");
			exit(0);
		}
	}
	while(iWhileListenCount>0)
	{
		printf("start listen port 4602:\n");
		listen(m_SockServer,0);//返回值判断单个监听是否超时
		m_Server=accept(m_SockServer,(struct sockaddr*)&serveraddrfrom,&len);
			if(m_Server!=INVALID_SOCKET)
			{
				printf("start receive message from client:\n");
			while(1)
			{
				num1 = recv(m_Server,cRecvBuffer,6,0);
				if(num1 >= 0)
				{	
					cRecvBuffer[num1]='\0';
					printf("%s\n",cRecvBuffer);
					
					
					SetStatus(cRecvBuffer);
					num2=send(m_Server,cRecvBuffer,6,0);
					CreateServer_4600(cRecvBuffer);
					printf("%s\n",cRecvBuffer);
					if(strcmp("exit",cRecvBuffer)==0)
					{
						ExitSystem();
					}
				}
			}
				break;
		}
		printf(".");
		iWhileListenCount--;
		if(iWhileListenCount<=0)
		{
			printf("\n建立连接失败\n");
			exit(0);
		}
	}
	
	while(1){}	
}
DWORD WINAPI threadproServer(LPVOID pParam)//服务器接收消息的线程
{
	
	SOCKET hsock=(SOCKET)pParam;
	char cRecvBuffer[1024];
	char cShowBuffer[1024];
	int num1=0,num2=0;
	if(hsock!=INVALID_SOCKET)
		printf("start receive message from client:\n");
	while(1)
	{
		num1 = recv(hsock,cRecvBuffer,1024,0);
		if(num1 >= 0)
		{
			cRecvBuffer[num1]='\0';
			num2=send(hsock,cRecvBuffer,6,0);
			printf("%s\n",cRecvBuffer);
		
			if(strcmp("exit",cRecvBuffer)==0)
			{
				ExitSystem();
			}
		}
	}
	return 0;
}
void CreateServer_4600(char LightState[])
{
	//这个socket用来向开发板发送灯光控制指令
	SOCKET m_SockServer;
	struct sockaddr_in serveraddr;//本地地址信息,结构体变量，后面的代码将会要填充其成员
	struct sockaddr_in serveraddrfrom;//连接的地址信息

	int iPort=4600;//设定为固定端口
	int iBindResult=-1;//绑定结果
	int iWhileCount;

	struct hostent* localHost;
	char* localIP;

	SOCKET m_Server;
	/*读取状态信息将其发送出去*/
	int len=sizeof(struct sockaddr);
	int iWhileListenCount=20;
	DWORD nThreadId = 0;
	int ires;//发送的返回值

	char cSendBuffer[1024];//发送消息缓存
	char cShowBuffer[1024];//接收消息缓存

	ioutfileServer= fopen("MessageServer.txt","a");//打开记录消息的文件
	m_SockServer = socket ( AF_INET,SOCK_STREAM,  0);
	

	//scanf("%d",&iPort);
	iPort=4600;
	localHost = gethostbyname("");
	localIP = inet_ntoa (*(struct in_addr *)*localHost->h_addr_list);
	//设置网络地址信息
	serveraddr.sin_family = AF_INET;//使用ＴＣＰ协议					
	serveraddr.sin_port = htons(iPort);	//端口			
	serveraddr.sin_addr.S_un.S_addr = inet_addr(localIP);//通过IP和端口号绑定socket到本地主机
	//绑定地址信息
	iBindResult=bind(m_SockServer,(struct sockaddr*)&serveraddr,sizeof(struct sockaddr));
	//如果端口不能被绑定，重新设置端口
	while(iBindResult!=0 && iWhileCount > 0)
	{
		printf("绑定失败，重新输入：");
		scanf("%d",iPort);
		//设置网络地址信息
		serveraddr.sin_family = AF_INET;					
		serveraddr.sin_port = htons(iPort);//端口	
		serveraddr.sin_addr.S_un.S_addr = inet_addr(localIP);//IP
		//绑定地址信息
		iBindResult = bind(m_SockServer,(struct sockaddr*)&serveraddr,sizeof(struct sockaddr));
		iWhileCount--;
		if(iWhileCount<=0)
		{
			printf("端口绑定失败,重新运行程序\n");
			exit(0);
		}
	}
	
		printf("从服务端开始监听4600端口\n");
		listen(m_SockServer,0);//返回值判断单个监听是否超时
		m_Server=accept(m_SockServer,(struct sockaddr*)&serveraddrfrom,&len);
			if(m_Server!=INVALID_SOCKET)
			{
				//有连接成功，发送欢迎信息
				printf("连接成功\n");
			
				send(m_Server,LightState,sizeof(LightState),0);
				closesocket(m_Server);
				closesocket(m_SockServer);
			}	
}

void GetStatus(char welcome[])
{
	FILE *Light1=fopen("./Status/Light1State.txt","r+");
	FILE *Light2=fopen("./Status/Light2State.txt","r+");
	FILE *Light3=fopen("./Status/Light3State.txt","r+");
	FILE *Light4=fopen("./Status/Light4State.txt","r+");
	FILE *Door=fopen("./Status/DoorState.txt","r+");
	FILE *Window=fopen("./Status/WindowState.txt","r+");
	FILE *Temp1=fopen("./Status/Temperature1.txt","r+");
	FILE *Temp2=fopen("./Status/Temperature2.txt","r+");
	FILE *Wet=fopen("./Status/Wet.txt","r+");
	FILE *PM25=fopen("./Status/PM25.txt","r+");
	FILE *Employee1=fopen("./Status/Employee1.txt","r+");
	FILE *Employee2=fopen("./Status/Employee2.txt","r+");
	FILE *Employee3=fopen("./Status/Employee3.txt","r+");
	

	char light1[5],light2[5],light3[5],light4[5],door[5],window[5],temp1[20],temp2[10],wet[10],pm25[10],emp1[5],emp2[5],emp3[5];

	char cWelcomBuffer[25]="A\0";
	fscanf(Light1,"%s",light1);
	fscanf(Light2,"%s",light2);
	fscanf(Light3,"%s",light3);
	fscanf(Light4,"%s",light4);
	fscanf(Door,"%s",door);
	fscanf(Window,"%s",window);
	fscanf(Temp1,"%s",temp1);
	fscanf(Temp2,"%s",temp2);
	fscanf(Wet,"%s",wet);
	fscanf(PM25,"%s",pm25);
	fscanf(Employee1,"%s",emp1);
	fscanf(Employee2,"%s",emp2);
	fscanf(Employee3,"%s",emp3);

	/*每个字符串末尾加上A*/
	strcat(window,"A");
	strcat(temp1,"A");
	strcat(temp2,"A");
	strcat(wet,"A");
	strcat(pm25,"A");
	strcat(emp1,"A");
	strcat(emp2,"A");
	/*拼接字符串到cWelcomBuffer*/
	strcat(cWelcomBuffer,light1);
	strcat(cWelcomBuffer,light2);
	strcat(cWelcomBuffer,light3);
	strcat(cWelcomBuffer,light4);
	strcat(cWelcomBuffer,door);
	strcat(cWelcomBuffer,window);
	strcat(cWelcomBuffer,temp1);
	strcat(cWelcomBuffer,temp2);
	strcat(cWelcomBuffer,wet);
	strcat(cWelcomBuffer,pm25);
	strcat(cWelcomBuffer,emp1);
	strcat(cWelcomBuffer,emp2);
	strcat(cWelcomBuffer,emp3);

	//printf("%s%s%s%s%s%s%s%s%s%s\n",light1,light2,light3,light4,door,window,temp1,temp2,wet,pm25);
	//printf("%d %d %d %d %d %d %d %d %d %d\n",strlen(light1),strlen(light2),strlen(light3),strlen(light4),strlen(door),strlen(window),strlen(temp1),strlen(temp2),strlen(wet),strlen(pm25));
	printf("%s %d\n",cWelcomBuffer,strlen(cWelcomBuffer));
	/*关闭所有打开的文件*/
	fclose(Light1);fclose(Light2);fclose(Light3);fclose(Light4);
	fclose(Door);fclose(Window);fclose(Temp1);
	fclose(Temp2);fclose(Wet);fclose(PM25);
	fclose(Employee1);fclose(Employee2);fclose(Employee3);
	strcpy(welcome,cWelcomBuffer);
}
void CreateServer()
{
	//这个是主调服务端
	int num1=0,num2=0;
	char cRecvBuffer[1024];
	char cWelcomBuffer[28];
	cWelcomBuffer[28]='\0';

	SOCKET m_SockServer;
	struct sockaddr_in serveraddr;//本地地址信息,结构体变量，后面的代码将会要填充其成员
	struct sockaddr_in serveraddrfrom;//连接的地址信息

	int iPort=4601;//设定为固定端口
	int iBindResult=-1;//绑定结果
	int iWhileCount=20;

	struct hostent* localHost;
	char* localIP;
	
	char test[]="A111111A28A26A300A222A111\r\n";

	GetStatus(cWelcomBuffer);
	//strcat(cWelcomBuffer,"\0");
	printf("%s\n",cWelcomBuffer);
	printf("%d\n",strlen(cWelcomBuffer));
	SOCKET m_Server;

	
	int len=sizeof(struct sockaddr);
	int iWhileListenCount=20;

	DWORD nThreadId = 0;
	int ires;//发送的返回值

	char cSendBuffer[1024];//发送消息缓存
	char cShowBuffer[1024];//接收消息缓存

	ioutfileServer= fopen("MessageServer.txt","a");//打开记录消息的文件
	m_SockServer = socket ( AF_INET,SOCK_STREAM,  0);
	
	//scanf("%d",&iPort);

	localHost = gethostbyname("");
	localIP = inet_ntoa (*(struct in_addr *)*localHost->h_addr_list);
	//设置网络地址信息
	serveraddr.sin_family = AF_INET;//使用ＴＣＰ协议					
	serveraddr.sin_port = htons(iPort);	//端口			
	serveraddr.sin_addr.S_un.S_addr = inet_addr(localIP);//通过IP和端口号绑定socket到本地主机
	//绑定地址信息
	iBindResult=bind(m_SockServer,(struct sockaddr*)&serveraddr,sizeof(struct sockaddr));
	
	//如果端口不能被绑定，重新设置端口
	while(iBindResult!=0 && iWhileCount > 0)
	{
		printf("绑定失败，重新输入：");
		scanf("%d",iPort);
		//设置网络地址信息
		serveraddr.sin_family = AF_INET;					
		serveraddr.sin_port = htons(iPort);//端口	
		serveraddr.sin_addr.S_un.S_addr = inet_addr(localIP);//IP
		//绑定地址信息
		iBindResult = bind(m_SockServer,(struct sockaddr*)&serveraddr,sizeof(struct sockaddr));
		iWhileCount--;
		if(iWhileCount<=0)
		{
			printf("端口绑定失败,重新运行程序\n");
			exit(0);
		}
	}
	while(iWhileListenCount>0)
	{
		printf("start listen port 4601:\n");
		listen(m_SockServer,0);//返回值判断单个监听是否超时
		m_Server=accept(m_SockServer,(struct sockaddr*)&serveraddrfrom,&len);
			if(m_Server!=INVALID_SOCKET)
			{
				//有连接成功，发送欢迎信息
				num2=send(m_Server,cWelcomBuffer,28,0);
				if(num2>0)
				{
					printf("发送成功\n");
					//启动接收服务端
					CreateServer_4602();
				}
				else
				{
					num2=send(m_Server,cWelcomBuffer,strlen(cWelcomBuffer),0);
				}
				
				
				
				//UserCreateServer();
				break;
			}
		printf(".");
		iWhileListenCount--;
		if(iWhileListenCount<=0)
		{
			printf("\n建立连接失败\n");
			exit(0);
		}
	}
	while(1)
	{}

}
int CheckIP(char *cIP)
{
	char IPAddress[128];//IP地址字符串
	char IPNumber[4];//IP地址中每组的数值
	int iSubIP=0;//IP地址中4段之一
	int iDot=0;//IP地址中'.'的个数
	int iResult=0;
	int iIPResult=1;
	int i;//循环控制变量
	memset(IPNumber,0,4);
	strncpy(IPAddress,cIP,128);
	for(i=0;i<128;i++)
	{
		if(IPAddress[i]=='.')
		{
			iDot++;
			iSubIP=0;
			if(atoi(IPNumber)>255)
				iIPResult = 0;
			memset(IPNumber,0,4);
		}
		else
		{
			IPNumber[iSubIP++]=IPAddress[i];
		}
		if(iDot==3 && iIPResult!=0)
			iResult= 1;
	}
	return iResult;
}
int main(void)
{
	int iSel=0;
	WSADATA wsd;									
	WSAStartup(MAKEWORD(2,2),&wsd);
	CreateServer();
	return 0;
}

