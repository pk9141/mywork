#include<stdio.h>
#include<conio.h>
int n,i,k,c;
struct list
{
int num;
int next;
}a[10];
void create()
{
k=0;
c=0;
printf("\n Enter the element:");
a[0].next=2;
a[1].next=0;
for(i=2;i<=n+1;i++)
{
printf("\nIndex %d:",i);
scanf("%d",&a[i].num);
a[i-1].next=i;
a[i].next=0;
a[0].next=i+1;
k=a[0].next;
printf("\n a[0].next=%d",k);
c++;
}
}
int display()
{
for(i=a[1].next;a[i].next!=0;i=a[i].next)
printf("\nIndex%d:%d\n",i,a[i].num);
printf("\nIndex%d:%d\n",i,a[i].num);
printf("\na[0].next=%d",k);
}
void insert(int ins,int p)
{
int temp,j;
j=1;
for(i=1;i<p;i++)
{
j=a[j].next;
}
temp=a[j].next;
a[j].next=k;
a[k].num=ins;
a[k].next=temp;
for(i=2;a[i].num!=0;i++)
{
}
k=i;
c++;
}
int search(int d)
{
for(i=2;i<=n+1;i++)
{
if(a[i].num==d)
{
return 1;
}
}
return 0;
}
int delete(int d)
{
int j,j1,temp;
for(i=2;i<=n+1;i++)
{
if(a[i].num==d)
{
c--;
a[0].next=i;
k=a[0].next;
temp=a[i].next;
a[i].next=0;
a[i].num=0;
for(j=1;a[j].next!=i;j=a[j].next)
{
}
a[j].next=temp;
break;
}
}
}
main()
{
int ch,ins,p,s,d,b;
clrscr();
while(1)
{
printf("\n Menu:\n1.Create\n2.Insert\n3.Delete\n4.Display\n5.Search\n6.Exit\nEnter your choice:");
scanf("%d",&ch);
switch(ch)
{
case 1:
printf("\n Enter the no of elements:");
scanf("%d",&n);
create();
break;
case 2:
if(c==n)
{
printf("\n The list is full");
break;
}
else
{
printf("\n Enter the element to be inserted and its position:");
scanf("%d%d",&ins,&p);
if((p>n)||(p<1))
{
printf("\n The position does not exist");
break;
}
else
{
insert(ins,p);
display();
}
}
break;
case 3:
if(c<1)
{
printf("\n List is empty");
}
else
{
printf("\n Enter the element to be deleted:");
scanf("%d",&d);
b=search(d);
if(c==1)
{
delete(d);
}
else if(b==1)
{
delete(d);
display();
}
else
{
printf("\n The element not found");
}
}
break;
case 4:
display();
break;
case 5:
printf("\n Enter the element to be searched:");
scanf("%d",&s);
b=search(s);
if(b==1)
{
printf("\n The element is found");
}
else
{
printf("\n The element is not found");
}
break;
case 6:
exit(1);
default:
printf("\n Enter the right option:");
}
}
}

