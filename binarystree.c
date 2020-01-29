#include<stdio.h>
#include<malloc.h>
#include<process.h>
#include<conio.h>
struct bsearch
{
	int data,height;
	struct bsearch *left,*right;
}*root,*temp;
typedef struct bsearch *bstree;
bstree makeempty(bstree node)
{
	if(node!=NULL)
	{
		makeempty(node->left);
		makeempty(node->right);
		free(node);
	}
	return NULL;
}
bstree findmin(bstree node)
{
	if(node==NULL)
		printf("\n Tree is empty");
	else if(node->left==NULL)
		return node;
	else
		return findmin(node->left);
}
bstree findmax(bstree node)
{
	if(node==NULL)
		printf("\n Tree is empty");
	else if(node->right==NULL)
		return node;
	else
		return findmax(node->right);
}
bstree find(int no,bstree node)
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

bstree insert(int no,bstree node)
{
	if(node==NULL)
	{
		node=(struct bsearch*)malloc(sizeof(struct bsearch));
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
			node->left=insert(no,node->left);
	else if(no>node->data)
			node->right=insert(no,node->right);
	else
		printf("\n The element is alreay present in the tree");

	return node;
}

bstree delete(int no,bstree node)
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

void display(bstree node,int level)
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
	printf("\n\n BINARY SEARCH TREE:\n \n 1.Insert \n 2.Delete \n 3.Findmin \n 4.Findmax \n 5.Find \n 6.Makeempty \n 7.Display \n 8.Exit \n Enter your option:");
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