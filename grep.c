#include<stdio.h>
#include<stdlib.h>
#include<fcntl.h>
#include<unistd.h>
#include<sys/types.h>
#include<string.h>
int main(int argc, char *argv[])
{
	int i,j,k=0;
	char buf[40];
	i=open(argv[1],O_RDWR,00700);
	if(i==-1)
	{
		printf("grep: %s : No such file or directory\n",argv[1]);
		exit(0);
	}
	j=read(i,buf,10);
	while(j!=0)
	{
		if(strstr(buf,argv[2]))
		{
			write(1,"\n\n",1);			
			printf("%s",buf);
			k++;			
		}
		j=read(i,buf,10);
	}
	printf("\n%s found %d times!\n",argv[2],k);
	return 1;	
}
