#include<stdio.h>
#include<stdlib.h>

struct node
{
       int coeff;
       int pow;
       struct node *next;
};

struct node *poly1=NULL,*poly2=NULL,*polysum=NULL,*polydiff=NULL,*polyprod=NULL; /*inital*/

void create(struct node *node) /*module to create a list*/
{
	int ch;
 	do
 	{
  		printf("\nEnter the coefficient :");
  		scanf("%d",&node->coeff); /*read coefficient*/
  		printf("\nEnter the power:");
  		scanf("%d",&node->pow); /*read power*/
  		node->next=(struct node*)malloc(sizeof(struct node)); /*create a new node an give link*/
  		node=node->next;
  		node->next=NULL;
  		printf("\nAdd further terms(1/2):");
  		scanf("%d",&ch);
  		
		while(ch<1 || ch>2) /*data validation*/
		{
			printf("\nWrong choice enter again \n");
			scanf("%d",&ch);
		}
		
 	}while(ch==1);
}
void show(struct node *node) /*module to display the polynomial*/
{
	while(node!=NULL)
 {
if(node->coeff==0)
{
   node=node->next;
   continue;
}
else if(node->coeff==0&&node->pow==0)
{
 node=node->next; 
 continue;
}
else if(node->pow==0)
{ 
   
  printf("%d",node->coeff);
  node=node->next;
  continue;
}
  printf("%dx^%d",node->coeff,node->pow);
  node=node->next;
  
   printf("+");
 }
 	printf("\n");
}
void polyadd(struct node *poly1,struct node *poly2,struct node *poly) /*module to add 2 polynomials*/
{
	while(poly1->next &&  poly2->next)
      {
     	      if(poly1->pow>poly2->pow)
      	{
       		poly->pow=poly1->pow;
       		poly->coeff=poly1->coeff;
       		poly1=poly1->next;
       	}
      	else if(poly1->pow<poly2->pow)
      	{
       		poly->pow=poly2->pow;
       		poly->coeff=poly2->coeff;
       		poly2=poly2->next;
       	}
      	else
      	{
       		poly->pow=poly1->pow;
       		poly->coeff=poly1->coeff+poly2->coeff;
       		poly1=poly1->next;
       		poly2=poly2->next;
       	}
      	poly->next=(struct node *)malloc(sizeof(struct node));
      	poly=poly->next;
      	poly->next=NULL;
     }
     while(poly1->next || poly2->next)
     {
     	if(poly1->next)
      	{
       		poly->pow=poly1->pow;
       		poly->coeff=poly1->coeff;
       		poly1=poly1->next;
       	}
      	if(poly2->next)
      	{
       		poly->pow=poly2->pow;
       		poly->coeff=poly2->coeff;
       		poly2=poly2->next;
       	}
       	poly->next=(struct node *)malloc(sizeof(struct node));
       	poly=poly->next;
       	poly->next=NULL;
     }
}
void polysub(struct node *poly1,struct node *poly2,struct node *poly) /*module to subtract 2 polynomials*/
{
	while(poly1->next &&  poly2->next)
      {
     	     if(poly1->pow>poly2->pow)
	     {
       		poly->pow=poly1->pow;
  	            poly->coeff=poly1->coeff;
       		poly1=poly1->next;
       	}
      	else if(poly1->pow<poly2->pow)
      	{
       		poly->pow=poly2->pow;
       		poly->coeff=-poly2->coeff;
	            poly2=poly2->next;
          }
      	else
      	{
       		poly->pow=poly1->pow;
       		poly->coeff=poly1->coeff-poly2->coeff;
       		poly1=poly1->next;
       		poly2=poly2->next;
       	}
      	poly->next=(struct node *)malloc(sizeof(struct node));
      	poly=poly->next;
      	poly->next=NULL;
     }
     while(poly1->next || poly2->next)
     {
     	      if(poly1->next)
     	      {
       		poly->pow=poly1->pow;
       		poly->coeff=poly1->coeff;
       		poly1=poly1->next;
     	      }
      	if(poly2->next)
      	{
       		poly->pow=poly2->pow;
       		poly->coeff=poly2->coeff;
       		poly2=poly2->next;
       	}
       	poly->next=(struct node *)malloc(sizeof(struct node));
       	poly=poly->next;
       	poly->next=NULL;
     }
}

void padd(int c,int e,struct node **s)
{
	struct node *r,*temp=*s;
	if(*s==NULL||e>(*s)->pow)
	{
		*s=r=malloc(sizeof(struct node));
		(*s)->coeff=c;
		(*s)->pow=e;
		(*s)->next=temp;
	}
	else
	{
		while(temp!=NULL)
		{
			if(temp->pow==e)
			{
				temp->coeff+=c;
				return;
			}
			if(temp->pow>e&&(temp->next->pow<e||temp->next==NULL))
			{
				r=malloc(sizeof(struct node));
				r->coeff=c;
				r->pow=e;
				r->next=temp->next;
				temp->next=r;
				return;
			}
			temp=temp->next;
		}
		r->next=NULL;
		temp->next=r;
	}
}
void polymul(struct node *x,struct node *y,struct node **m) /*module to multiply 2 polynomials*/
{
	struct node *y1;
	int coeff1,exp1;
	y1=y;
	if(x==NULL&&y==NULL)
	{
			return;
	}
	if(x==NULL)
	{
		*m=y;
	}
	else
	{
		if(y==NULL)
		*m=x;
		else
		{
			while(x!=NULL)
			{
				while(y!=NULL)
				{
					coeff1=x->coeff*y->coeff;
					exp1=x->pow+y->pow;
					y=y->next;
					padd(coeff1,exp1,m);
				}
				y=y1;
				x=x->next;
			}
		}
	}
}

int main()
{
      int choice;
	while(1)
	{
		printf("\nPolynomial Manipulation\n");
		printf("\nWhat do you want to do?\n");
		printf("\n\n1. Polynomial Addition \n\n2. Polynomial Subtraction \n\n3. Polynomial Multiplication \n\n4. Exit \n");
		printf("\nEnter your choice: ");
		scanf("%d",&choice);
		while(choice<1||choice>4) /*data validation*/
		{
			printf("\n Wrong choice enter again \n");
			scanf("%d",&choice);
		}
		if(choice==4)
		{
			exit(0);
		}
		switch(choice)
		{
			case 1: 
			{
				poly1=(struct node *)malloc(sizeof(struct node));
       			poly2=(struct node *)malloc(sizeof(struct node));
		            polysum=(struct node *)malloc(sizeof(struct node));
  				printf("\nPolynomial 1:\n");
      			create(poly1);
      			printf("\nPolynomial 2:\n");
      			create(poly2);
      			printf("\nPolynomial 1:");
      			show(poly1);
      			printf("\nPolynomial 2:");
      			show(poly2);
      			polyadd(poly1,poly2,polysum);
				printf("\nAdded polynomial:\n");
      			show(polysum);break;
      	      }
			case 2: 
			{
				poly1=(struct node *)malloc(sizeof(struct node));
      			poly2=(struct node *)malloc(sizeof(struct node));
      			polydiff=(struct node *)malloc(sizeof(struct node));
				printf("\nPolynomial 1:\n");
      			create(poly1);
      			printf("\nPolynomial 2:\n");
      			create(poly2);
      			printf("\nPolynomial 1:");
      			show(poly1);
      			printf("\nPolynomial 2:");
      			show(poly2);
      			polysub(poly1,poly2,polydiff);
				printf("\n Subtracted polynomial :\n");	
				show(polydiff);
        			break;
        		}
			case 3:
			{
				poly1=(struct node *)malloc(sizeof(struct node));
				poly2=(struct node *)malloc(sizeof(struct node));
				polyprod=(struct node *)malloc(sizeof(struct node));
      			create(poly1);
      			printf("\nPolynomial 2:\n");
      			create(poly2);
      			printf("\nPolynomial 1:");
      			show(poly1);
      			printf("\nPolynomial 2:");
      			show(poly2);
				polymul(poly1,poly2,&polyprod);
				printf("\nMultiplied polynomial :\n");
		      	show(polyprod);
				break;
			}
		}
	}
	return 0;
}
