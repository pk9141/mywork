#include<GL/glut.h>
#include<iostream>
#include<math.h>
using namespace std;
GLfloat rect_in[4][4],rect_out[4][4];
GLfloat imatrix[3][3];
GLfloat shift_v[2][1];
void init()
{
	glClearColor(1.0,1.0,1.0,0.0);
	glClear(GL_COLOR_BUFFER_BIT);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(-1000.0,1000.0,-800.0,800.0,-1.0,1.0);
}
void getdata()
{
	GLint i,j;
	cout<<"Enter the Corodinates:\n";
	for(i=0;i<4;i++)
	{
		for(j=0;j<2;j++)
		{
			cin>>rect_in[i][j];
		}	
	}	
}
void set_identity(GLfloat matrix[3][3])
{
	for(int i=0;i<3;i++)
	{
		for(int j=0;j<3;j++)
			matrix[i][j]=(i==j);
	}
}
void transform()
{
	for(int i=0;i<4;i++)
	{
		rect_out[i][0]=imatrix[0][0]*rect_in[i][0]+imatrix[0][1]*rect_in[i][1]+imatrix[0][2];
		rect_out[i][1]=imatrix[1][0]*rect_in[i][0]+imatrix[1][1]*rect_in[i][1]+imatrix[1][2];
	}	
}
void init_display()
{
	glColor3f(0.0,0.0,0.0);
	glBegin(GL_LINES);
	glVertex2i(-1000,0);
	glVertex2i(1000,0);
	glEnd();
	glBegin(GL_LINES);
	glVertex2i(0,-800);
	glVertex2i(0,800);
	glEnd();
	glColor3f(1.0,0.0,0.0);
	glBegin(GL_QUADS);
	for(int i=0;i<4;i++)
		glVertex2f(rect_in[i][0],rect_in[i][1]);
	glEnd();
	glFlush();
}
void final_display()
{
	glColor3f(0.0,1.0,0.0);
	glBegin(GL_QUADS);
	for(int i=0;i<4;i++)
		glVertex2f(rect_out[i][0],rect_out[i][1]);
	glEnd();
	glFlush();
}
void translation(int tx,int ty)
{
	int i,j;
	set_identity(imatrix);
	imatrix[0][2]=tx;
	imatrix[1][2]=ty;	
}
void rotation(GLfloat degree,GLfloat x,GLfloat y)
{
	GLfloat radian;
	radian=degree*3.14/180;
	cout<<cos(radian);
	set_identity(imatrix);
	imatrix[0][0]=imatrix[1][1]=cos(radian);
	imatrix[0][1]=-sin(radian);
	imatrix[1][0]=sin(radian);
	imatrix[0][2]=x*(1-cos(radian))+y*sin(radian);
	imatrix[1][2]=y*(1-cos(radian))-x*sin(radian);
}
void scale(GLfloat sx,GLfloat sy,GLfloat x,GLfloat y)
{
	int i,j,k;
	set_identity(imatrix);
	imatrix[0][0]=sx;
	imatrix[1][1]=sy;
	imatrix[0][2]=x*(1-sx);
	imatrix[1][2]=y*(1-sy);	
}
void reflection(int choice)
{
	set_identity(imatrix);
	if(choice==1)
	{
		imatrix[0][0]=-1;
		imatrix[1][1]=-1;
	}
	else if(choice==2)
	{
		imatrix[1][1]=-1;
	}
	else if(choice==3)
	{
		imatrix[0][0]=-1;
	}
	else
		cout<<"Enter the Correct Choice";	
}
void shear(int choice,GLfloat s)
{
	set_identity(imatrix);
	if(choice==1)
		imatrix[0][1]=s;
	else if(choice==2)
		imatrix[1][0]=s;
	else
		cout<<"Enter the correct choice";
}
void select()
{
	getdata();
	GLint choice;
	while(choice!=0)
	{
		cout<<"\n 1.Translation \n 2.Rotation \n 3.Scaling \n 4.Reflection \n 5.Shear \n 6.Exit \n Enter your choice :";
		cin>>choice;
		switch(choice)
		{
			case 1:
				glClear(GL_COLOR_BUFFER_BIT);
				glutSwapBuffers();	
				init_display();
				cout<<"Enter the Shift Vector:";
				for(int i=0;i<2;i++)
				{
					cin>>shift_v[i][0];
				}	
				translation(shift_v[0][0],shift_v[1][0]);
				break;
			case 2:
				GLfloat degree,x,y;
				glClear(GL_COLOR_BUFFER_BIT);
				glutSwapBuffers();
				init_display();
				cout<<"Enter the rotating angle:";
				cin>>degree;
				cout<<"Enter the rotating Point:";
				cin>>x>>y;
				rotation(degree,x,y);
				break;
			case 3:
				glClear(GL_COLOR_BUFFER_BIT);
				glutSwapBuffers();
				init_display();
				GLfloat sx,sy;
				cout<<"Enter the Scaling Factor:";
				cin>>sx>>sy;
				cout<<"Enter the pivot point:";
				cin>>x>>y;
				scale(sx,sy,x,y);
				break;		
			case 4:
				int choice1;
				glClear(GL_COLOR_BUFFER_BIT);
				glutSwapBuffers();
				init_display();
				cout<<"\n 1.About Origin \n 2.About X-Axis \n 3.About Y-Axis \n 4.About the line y=mx+c \n Enter your choice:";
				cin>>choice1;
				reflection(choice1);
				break;
			case 5:
				GLfloat s;
				glClear(GL_COLOR_BUFFER_BIT);
				glutSwapBuffers();
				init_display();
				cout<<"\n 1.About X-axis \n 2.About Y-axis \n Enter your choice";
				cin>>choice1;
				cout<<"Enter the shear Value:";
				cin>>s;
				shear(choice1,s);
				break;
			case 6:
				choice=0;
				break;
			default:
				cout<<"Enter the Correct Choice";
		}
		transform();
		final_display();
	}
}

int main(int argc,char **argv)
{
	glutInit(&argc,argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(1000,800);
	glutInitWindowPosition(200,200);
	glutCreateWindow("2D-Transformation");
	init();
	glutDisplayFunc(select);
	glutMainLoop();
}
