/*Hashing - Open Addressing*/
#include<stdio.h>
#include<stdlib.h>
struct hash
{
	int *arr;
};

int size; /*global declaration of size of the table*/

int is_empty(struct hash *H) /*module to check if the hash table is emprt*/
{
	int i;
	for(i=0;i<size;i++)
	{
		if(H->arr[i]!=0)
		{
			return 0;
		}
	}
	return 1;
}

int is_full(struct hash *H) /*module to check if the hash table is emprt*/
{
	int i;
	for(i=0;i<size;i++)
	{
		if(H->arr[i]==0)
		{
			return 0;
		}
	}
	return 1;
}

struct hash *rehash(struct hash *H)
{
	struct hash *newhash;
	newhash=malloc(sizeof(struct hash));
	if(newhash==NULL)
	{
        printf("\nNo memory space available!\n");
    }
    else
    {
        printf("\nNew memory for Hash Table allocated!\n");
 	    size*=2; /*doubling the size of the table*/	
	    newhash->arr=calloc(size,sizeof(int));  
	    if(newhash->arr==NULL)
	    {
              printf("\nNo memory space available!\n");
        }
        else
        {
            int i,temp,pos;
	        for(i=0;i<size/2;i++)
	        {
             temp=H->arr[i];
		     pos=temp%size; 
		     newhash->arr[pos]=temp;
            }
	        free(H);
       }
    }
    
	return newhash;
}

int retrieve(struct hash *H,int pos) /*module to perform retrieve operation*/
{
	if(pos>size-1)
	{
		return -1;	/*for invalid positions*/
	}	
	else
	{
		return (H->arr[pos]);
	}
}

int search(struct hash *H,int no) /*module to perform search operation*/
{
	int i;
	for(i=0;i<size;i++)
	{
		if(H->arr[i]==no)
		{
			return i;
		}
	}
	return 0;
}

struct hash *insert(struct hash *H,int no,int pos) /*module to perform insert operation*/
{
	if(H->arr[pos]!=0) /*if space not empty*/
	{
		if(pos==size-1)
		{
			H=insert(H,no,0);	/*if number found at last position, move to 0th position*/
		}
		else
		{
			H=insert(H,no,pos+1);
		}
	}
	else /*if space available for insertion*/
	{
		H->arr[pos]=no;
		printf("\nElement %d inserted into the Hash Table!\n",no);
	}
	
	return H;
}

struct hash *deletee(struct hash *H,int no) /*module to perform delete operation*/
{
	int i;
	i=search(H,no);	
	if(i==0)
	{
		printf("\nElement %d not found in the Hash Table!\n",no);
	}
	else
	{
		H->arr[i]=0;
		printf("\nElement %d deleted from the Hash Table!\n");	
	}
	return H;
}

int display(struct hash *H) /*module to display the hash table*/
{
	int i;
	printf("\nIndex\tData\n\n");
	for(i=0;i<size;i++)
	{
		if(H->arr[i]==0)
		{
				printf("%d\t-\n\n",i);
		}
		else
		{
			printf("%d\t%d\n\n",i,H->arr[i]); /*print the elements with its index*/
		}
	}
}

int menu() /*module to print the menu and read choice*/
{
	int op;
	printf("\nWhat operation do you want to perform on the Hash Table?\n");
	printf("\n1.Display\n\n2.Insert\n\n3.Delete\n\n4.Locate\n\n5.Retrieve\n\n6.Exit\n\nEnter your choice: ");
	scanf("%d",&op);
	
	while(op<1||op>6) /*data validation*/
	{
		printf("\nInvalid Option! Enter a correct choice: ");
		scanf("%d",&op);
	}
	return op;
		
}
	
int main()
{
	struct hash *H;
	H=malloc(sizeof(struct hash));
	if(H==NULL)
	{
               printf("\nNo memory space available!\n");
    } 
	int i,no,op,pos;
	printf("OPEN ADDRESSING HASHING WITH LINEAR PROBING AND REHASHING\n");
	printf("\nEnter the size of the hashing table required: ");	
	scanf("%d",&size);
	H->arr=calloc(size,sizeof(int)); /*dynamically allocate memory for the hash table and initialise elements to 0*/
	
	if(H->arr==NULL) /*if no space available*/
	{
		printf("\nNo memory space avaiable!\n");	
	}
	else
	{
		printf("\nHash table created with sufficient memory!\n");	
	}

	while(1)
	{
		op=menu();
		switch(op)
		{
			case 1:
			{
				/*display*/
				display(H);
				if(is_empty(H))
				{
					printf("\nThe Hash Table is empty!\n");
				}
				break;
			}
			case 2:
			{
				/*insert*/
				printf("\nEnter the number to be inserted: ");
				scanf("%d",&no);
				if(is_full(H))
				{
					printf("\nMemory full for the Hash Table! Performing Rehashing.....\n");
					H=rehash(H);	
				}
				pos=no%size;
				H=insert(H,no,pos);
				break;
			}	
			case 3:
			{
				/*delete*/
				if(is_empty(H))
				{
					printf("\nThe Hash Table is empty! Cannot perform deletion operation !\n");
				}
				else
				{
					printf("\nEnter the number to be inserted: ");
					scanf("%d",&no);
					H=deletee(H,no);
				}
				break;
			}
			case 4:
			{
				/*locate*/
				if(is_empty(H))
				{
					printf("\nThe Hash Table is empty! Cannot perform search operation !\n");
				}
				else
				{
					printf("\nEnter the element to be located in the Hash table: ");
					scanf("%d",&no);
					i=search(H,no);
					if(i==0)
					{
						printf("\nElement %d not found in the Hash Table!\n",no);
					}
					else
					{
						printf("\nElement %d found at index %d!\n",no,i); 
					}
				}
				break;
			}
			case 5:
			{
				/*retrieve*/
				if(is_empty(H))
				{
					printf("\nThe Hash Table is empty! Cannot perform retrieve operation !\n");
				}
				else
				{
					printf("\nEnter the Index to be retrieved: ");
					scanf("%d",&no);
					i=retrieve(H,no);
					if(i==0)
					{
						printf("\nNo element found at position %d!\n",no);
					}
					else
					if(i==-1)
					{
						printf("\nInvalid Index!\n");	
					}
					else
					{
						printf("\nElement %d retrieved at position %d!\n",i,no);	
					}
				}
				break;

			} 
			case 6:
			{
				exit(0);
			}

		}
	}
}
