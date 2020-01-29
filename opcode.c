#include<stdio.h>
#include<stdlib.h>
#include<string.h>
int ncount=0,acount=0,wcount=0;
struct normal
{
	char name[100];
}normal[100];
struct assembler
{
	char name[100];
}assem[100];
struct wrong
{
	char name[100];
}opc[100];

void search(char name[])
{
	FILE *f;int flag=0;
	char buf[100],*buf1;
	f=fopen("optab.txt","r");
	while(fgets(buf,100,f)!=NULL)
	{
		buf1=strtok(buf," \n");
		while(buf1!=NULL)
		{
			if(!strncmp(buf1,name,strlen(name)))
			{
				flag=1;
				strcpy(normal[ncount++].name,buf1);
			}
			buf1=strtok(NULL," \n");
						
		}
	}
	if(flag==0)
	{
		if(!strncmp(name,"RESB",strlen(name))||!strncmp(name,"RESW",strlen(name))||!strncmp(name,"WORD",strlen(name))||!strncmp(name,"BYTE",strlen(name))||!strncmp(name,"START",strlen(name))||!strncmp(name,"END",strlen(name)))
			{
				strcpy(assem[acount++].name,name);
			}
		else
			strcpy(opc[wcount++].name,name);
	}
	fclose(f);
}
			
	
int main()
{
	int count=0;
	FILE *f1;
	char buf[100],*buf1,temp[10][10];
	f1=fopen("pass1.txt","r");
	while(fgets(buf,100,f1)!=NULL)
	{
		buf1=strtok(buf," \n");
		while(buf1!=NULL)
		{
			strcpy(temp[count++],buf1);
			buf1=strtok(NULL," \n");
		}
		if(count==3)
		{
			search(temp[1]);
		}
		else
		{
			search(temp[0]);
		}
		count=0;
	}
	fclose(f1);
	int ch,i;
	printf("\n 1.NORMAL \n 2.ASSEMBLER \n 3.WRONG \n ENTER YOUR CHOICE:");
	scanf("%d",&ch);
	switch(ch)
	{
		case 1:
			for(i=0;i<ncount;i++)
				printf("\n %s",normal[i].name);
			break;
		case 2:
			for(i=0;i<acount;i++)
				printf("\n %s",assem[i].name);
			break;
		case 3:
			for(i=0;i<wcount;i++)
				printf("\n %s",opc[i].name);
			break;
	}
}	
