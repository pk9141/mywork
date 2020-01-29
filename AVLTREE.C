#include<stdio.h>
#include<malloc.h>
#include<process.h>
#include<conio.h>
struct avl
{
	int data,height;
	struct avl *left,*right;
}*root,*temp;
typedef struct avl *avltree;
avltree makeempty(avltree node)
{
	if(node!=NULL)
	{
		makeempty(node->left);
		makeempty(node->right);
		free(node);
	}
	return NULL;
}
avltree findmin(avltree node)
{
	if(node==NULL)
		printf("\n Tree is empty");
	else if(node->left==NULL)
		return node;
	else
		return findmin(node->left);
}
avltree findmax(avltree node)
{
	if(node==NULL)
		printf("\n Tree is empty");
	else if(node->right==NULL)
		return node;
	else
		return findmax(node->right);
}
avltree find(int no,avltree node)
{
	if(node==NULL)
		printf("\n Element is not found");
	else if(no<node->data)
		find(no,node->left);
	else if(no>node->data)
		find(no,node->right);
	else
		printf("\n The element is found");
}
int max(int a,int b)
{	if((a>b)||(a==b))
		return a;
	else
		return b;
}
int fheight(avltree node)
{
	if(node==NULL)
		return -1;
	else
		return node->height;
}

avltree rightrotation(avltree node)
{
       avltree nroot;
       nroot=node->left;
       node->left=nroot->right;
       nroot->right=node;
       nroot->height=max(fheight(nroot->left),node->height)+1;
       node->height=max(fheight(node->left),fheight(node->right))+1;
       return nroot;
}
avltree leftrotation(avltree node)
{
	avltree nroot;
	nroot=node->right;
	node->right=nroot->left;
	nroot->left=node;
	nroot->height=max(fheight(nroot->right),node->height)+1;
	node->height=max(fheight(node->left),fheight(node->right))+1;
	return nroot;
}
avltree leftrightrotation(avltree node)
{
	avltree nroot;
	node->left=leftrotation(node->left);
	node=rightrotation(node);
	return node;
}
avltree rightleftrotation(avltree node)
{
	avltree nroot;
	node->right=rightrotation(node->right);
	node=leftrotation(node);
	return node;
}
avltree insert(int no,avltree node)
{
	if(node==NULL)
	{
		node=(struct avl*)malloc(sizeof(struct avl));
		if(node==NULL)
		{
			printf("\n No Memory Space");
		}
		else
		{
			node->data=no;
			node->left=node->right=NULL;
		}
	}
	else if(no<node->data)
	{
		node->left=insert(no,node->left);
		if(fheight(node->left)-fheight(node->right)==2)
		{
			if(no<node->left->data)
				node=rightrotation(node);
			else
				node=leftrightrotation(node);
		}
	}
	else if(no>node->data)
	{
		node->right=insert(no,node->right);
		if(fheight(node->right)-fheight(node->left)==2)
		{
			if(no>node->right->data)
				node=leftrotation(node);
			else
				node=rightleftrotation(node);
		}
	}
	else
		printf("\n The element is alreay present in the tree");
       	node->height=max(fheight(node->left),fheight(node->right))+1;
	return node;
}

avltree delete(int no,avltree node)
{
	if(node==NULL)
	printf("\n The element is not found");
	else if(no<node->data)
	node->left=delete(no,node->left);
	else if(no>node->data)
	node->right=delete(no,node->right);
	else
	{
		if(node->right && node->left)
		{
			temp=findmin(node->right);
			node->data=temp->data;
			node->right=delete(temp->data,node->right);
		}
		else
		{
			temp=node;
			if(node->left==NULL)
			node=node->right;
			else if(node->right==NULL)
			node=node->left;
			free(temp);
		}
	}
	return node;
}

void display(avltree node,int level)
{
	int i;
	if(node!=NULL)
	{
		display(node->right,level+1);
		printf("\n");
		for(i=0;i<level;i++)
			printf("\t");
		printf("%d",node->data);
		display(node->left,level+1);
	}
}
main()
{
	int ch,no;
	clrscr();
	while(1)
	{
	printf("\n\n AVLTREE:\n \n 1.Insert \n 2.Delete \n 3.Findmin \n 4.Findmax \n 5.Find \n 6.Makeempty \n 7.Display \n 8.Exit \n Enter your option:");
	scanf("%d",&ch);
	switch(ch)
	{
		case 1:
			if(root==NULL)
			{
				printf("\n Enter the root:");
				scanf("%d",&no);
				root=insert(no,root);
			}
			else
			{
				printf("\n Enter the element to be inserted:");
				scanf("%d",&no);
				root=insert(no,root);
			}
			display(root,1);
			break;
		case 2:
			printf("\n Enter the element to be deleted:");
			scanf("%d",&no);
			root=delete(no,root);
			display(root,1);
			break;
		case 3:
			temp=findmin(root);
			if(temp!=NULL)
				printf("\n The Minimum element is %d",temp->data);
			break;
		case 4:
			temp=findmax(root);
			if(temp!=NULL)
				printf("\n The Maximum element is %d",temp->data);
			break;
		case 5:
			printf("\n Enter the element to be found:");
			scanf("%d",&no);
			find(no,root);
			break;
		case 6:
			root=makeempty(root);
			printf("\n Tree has been emptied");
			break;
		case 7:
			display(root,1);
			break;
		case 8:
			exit(0);
		default:
			printf("\n Enter the correct option:");
	}
	}
}




