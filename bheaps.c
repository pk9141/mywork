#include<stdio.h>
#include<stdlib.h>
#define mindata -32768
struct heap
{
      int *arr;      
      int capacity;
      int size;
};

int is_null(struct heap *H)
{
	if(H->size==0)	
	{
		return 1;
	}
	else
	{
		return 0;	
	}
}
int is_full(struct heap *H)
{
	if(H->size==H->capacity)
	{
		return 1;
	}
	else
	{
		return 0;
	}
}
struct heap *h_init(int size)
{
	struct heap *H=malloc(sizeof(struct heap));
	if(H==NULL)
	{
		printf("\nNo memory space available!\n");

	}
	H->arr=malloc((size+1)*sizeof(size));
	if(H->arr==NULL)
	{
		printf("\nNo memory space available!\n");
	}
	else
	{
		printf("\nMemory for Binary Heap allocated!\n");
	}
	H->size=0;
	H->capacity=size;
	H->arr[0]=mindata;
	return H;
}
struct heap *h_insert(struct heap *H, int ele)
{
	int i;
	for(i=++H->size;H->arr[i/2]>ele;i/=2)
	{
		H->arr[i]=H->arr[i/2];
	}
	H->arr[i]=ele;
	printf("\nElement %d inserted into the Binary Heap!\n",ele);
	return H;
}
int h_sort()
{
	struct heap *H;
	int i,size,no;
	printf("\nEnter the number of elements to be sorted: ");
	scanf("%d",&size);
	H=h_init(size);
	for(i=0;i<size;i++)
	{
		printf("\nEnter the element %d: ",i+1);
		scanf("%d",&no);
		H=h_insert(H,no);
	}
	int *p;
	p=malloc(size*sizeof(int));
	for(i=0;i<size;i++)
	{
		*(p+i)=h_delete_min(H);
	}
	printf("\nSorted Array: \n\n");
	for(i=0;i<size;i++)
	{
		printf("%d ",*(p+i));
	}
	printf("\n");
}

int check(int no)
{
	int i=2;
	while(i<=no)
	{
		if(no==i)	
		{
			return 1;
		}
		i*=2;
	}
	return 0;
}
int h_display(struct heap *H)
{
	if(is_null(H))
	{
		printf("\nBinary Heap is empty! Cannot perform display operation!\n");
	}
	else
	{
		int i,k,level;
		level=(H->size/2)+1;
		printf("\nBinary Heap: \n\n");
		for(i=1;i<=H->size;i++)
		{
			printf("[ %d ]     ",H->arr[i]);
			if(check(i+1))
			printf("\n\n");
		}
		printf("\n"); 
		/*print the parent nodes*/
		printf("\nRoot --> [ %d ]\n",H->arr[1]);
		printf("\nParent    Node\n");
		for(i=2;i<=H->size;i++)
		{
			printf("\n[ %d ] --> [ %d ]\n",H->arr[i/2],H->arr[i]);
		}
	}
}

int h_delete_min(struct heap *H)
{
	int i,child;
	int min,last;
	min=H->arr[1];
	last=H->arr[H->size--];
	for(i=1;i*2<=H->size;i=child)
	{
		child=i*2;
		if(child!=H->size&&H->arr[child+1]<H->arr[child])
		{
			child++;
		}
		if(last>H->arr[child])
		{
			H->arr[i]=H->arr[child];
		}
		else
		{
			break;
		}
	}
	H->arr[i]=last;
	return min;
}
                
int h_search(struct heap *H,int ele)
{
	if(is_null(H))
	{
		printf("\nBinary Heap is empty! Cannot perform Search operation!\n");
	}             
	else
	{
		int i;
		for(i=1;i<=H->size;i++)
		{
			if(H->arr[i]==ele)
			{
				printf("\nElement %d found in the Binary Heap!\n",ele);
				break;
			}
		}	
		if(i>H->size)
		{
			printf("\nElement %d not found in the Binary Heap!\n",ele);
		}
	}
}
             
int m_menu()
{
      int op;
      printf("\nWhat do you want to perform?\n");
      printf("\n1.Operation on Binary Heaps\n\n2.Heap Sorting\n\n3.Exit\n\nEnter your choice: ");      
      scanf("%d",&op);
      while(op<1||op>3)
      {
            
            printf("\nInvalid Option! Enter a correct choice: ");
            scanf("%d",&op);
      }
      return op;
}

int h_menu()
{
      int op,size,ele,no;
      struct heap *H;
      printf("\nEnter the size of the Binary Heap: ");
      scanf("%d",&size);	
      H=h_init(size);  
      while(1)
      {
            printf("\nWhat operation do you want to perform on the Binary Heap?\n");
            printf("\n1.Display\n\n2.Insert\n\n3.Delete Root\n\n4.Search\n\n5.Back to main menu\n\n6.Exit\n\nEnter your choice: ");      
            scanf("%d",&op);
            while(op<1||op>6)
            {
                  
                  printf("\nInvalid Option! Enter a correct choice: ");
                  scanf("%d",&op);
            }
            switch(op)                         
            {
                  case 1:
                  {
                        h_display(H);
                        break;      
                  }
                  case 2:
                  {
				if(is_full(H))
			      {
					printf("\nBinary Heap is full! Cannot perform insert operation!\n");	
				}            		
		      	else
		      	{
					printf("\nEnter the element to be inserted: ");
					scanf("%d",&ele);
					H=h_insert(H,ele);
				}
                        break;      
                  }
                  case 3:
                  {
				if(is_null(H))
				{
					printf("\nBinary Heap is empty! Cannot perform deletion operation!\n");
				}
				else
				{
                        	no=h_delete_min(H);
					printf("\nElement %d deleted from the Binary Heap!\n",no);
				}
				break;      
                  }
                  case 4:
                  {
				if(is_null(H))
				{
					printf("\nBinary Heap is empty! Cannot perform deletion operation!\n");
				}
				else
				{
					printf("\nEnter the element to be searched: ");
					scanf("%d",&ele);
	                        h_search(H,ele);
				}
				break;      
                  }
                  case 5:
                  {
                        return;
                  }
                  case 6:
                  {
                        exit(0);      
                  }
            }
      }
}
int main()
{
      printf("\nMINIMUM BINARY HEAPS AND HEAP SORT\n");      
      int op1,op2;
      while(1)
      {
            op1=m_menu();
            if(op1==1)
            {
			printf("\nBINARY HEAPS\n");
                  h_menu();                        
            }
            else
            if(op1==2)
            {
			printf("\nHEAPS SORTING\n");
                  h_sort();
            }
            else
            {
                  exit(0);
            }
      }
      return 1;      
}
