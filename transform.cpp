#include<GL/glut.h>
#include<iostream>
#include<math.h>
using namespace std;
GLfloat rect_in[2][2],rect_out[2][2];
void init()
{
	glClearColor(1.0,1.0,1.0,0.0);
	glClear(GL_COLOR_BUFFER_BIT);
	glColor3f(0.0,0.0,0.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(0.0,640.0,0.0,480.0,-1.0,1.0);

}
void getdata()
{
	GLint i,j;
	cout<<"Enter the Corodinates:\n";
	for(i=0;i<2;i++)
	{
		for(j=0;j<2;j++)
		{
			cin>>rect_in[i][j];
		}	
	}	
}
void display()
{
	glColor3f(1.0,0.0,0.0);
	glRectf(rect_out[0][0],rect_out[1][0],rect_out[0][1],rect_out[1][1]);
}
void translation()
{
	int i,j;
	GLfloat shift_v[2][1];
	glRectf(rect_in[0][0],rect_in[1][0],rect_in[0][1],rect_in[1][1]);
	cout<<"Enter the Shift Vector:";
	for(i=0;i<2;i++)
	{
		cin>>shift_v[i][0];
	}
	for(i=0;i<2;i++)
	{
		for(j=0;j<2;j++)
		{
			rect_out[i][j]=rect_in[i][j]+shift_v[i][0];			
		}
	}
	display();
	glFlush();
}
void rotation()
{
	int i,j,k;GLfloat in[2][1],out[2][1];
	/*in[0][0]=145.0;
	in[1][0]=175.0;
	glPointSize(5.0);
	glBegin(GL_POINTS);
	glVertex2f(in[0][0],in[1][0]);
	glEnd();*/
	GLfloat angle[2][2];
	GLfloat degree,radian;
	cout<<"Enter the rotating angle";
	cin>>degree;
	radian=degree*3.14/180;
	glRectf(rect_in[0][0],rect_in[1][0],rect_in[0][1],rect_in[1][1]);
	angle[0][0]=angle[1][1]=cos(radian);
	angle[0][1]=-sin(radian);
	angle[1][0]=sin(radian);
	cout<<angle[0][0];
	for(i=0;i<2;i++)
	{
		for(j=0;j<2;j++)
		{
			rect_out[i][j]=0.0;
			for(k=0;k<2;k++)
			{
				rect_out[i][j]+=angle[i][k]*rect_in[k][j];
			}
		}
	}
/*	glBegin(GL_POINTS);
	glVertex2f(out[0][0],out[1][0]);
	glEnd();*/
	display();
	glFlush();

}
void scale()
{
	int i,j,k;
	GLfloat sx,sy,scale_f[2][1];
	cout<<"Enter the scaling factor:";
	cin>>sx>>sy;
	scale_f[0][0]=sx;
	scale_f[1][1]=sy;
	scale_f[0][1]=scale_f[1][0]=0.0;
	glRectf(rect_in[0][0],rect_in[1][0],rect_in[0][1],rect_in[1][1]);
	for(i=0;i<2;i++)
	{
		for(j=0;j<2;j++)
		{
			rect_out[i][j]=0.0;
			for(k=0;k<2;k++)
			{
				rect_out[i][j]+=scale_f[i][k]*rect_in[k][j];
			}
		}
	}
	display();
	glFlush();

}
void reflection()
{
	int i,j,k;
	GLfloat reflect[2][1];
	reflect[0][0]=1.0;
	reflect[1][1]=-1.0;
	reflect[0][1]=reflect[1][0]=0.0;
	glRectf(rect_in[0][0],rect_in[1][0],rect_in[0][1],rect_in[1][1]);
	for(i=0;i<2;i++)
	{
		for(j=0;j<2;j++)
		{
			rect_out[i][j]=0.0;
			for(k=0;k<2;k++)
			{
				rect_out[i][j]+=reflect[i][k]*rect_in[k][j];
			}
		}
	}
	display();
	glFlush();


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
				glColor3f(0.0,0.0,0.0);
				translation();
				break;
			case 2:
				glClear(GL_COLOR_BUFFER_BIT);
				glutSwapBuffers();
				glColor3f(0.0,0.0,0.0);
				rotation();
				break;
			case 3:
				glClear(GL_COLOR_BUFFER_BIT);
				glutSwapBuffers();
				glColor3f(0.0,0.0,0.0);
				scale();
				break;		
			case 4:
				glClear(GL_COLOR_BUFFER_BIT);
				glutSwapBuffers();
				glColor3f(0.0,0.0,0.0);
				reflection();
				break;
			case 5:
				break;
			case 6:
				choice=0;
				break;
			default:
				cout<<"Enter the Correct Choice";
		}
	}
}

int main(int argc,char **argv)
{
	glutInit(&argc,argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(640,480);
	glutInitWindowPosition(0,0);
	glutCreateWindow("2D-Transformation");
	init();
	glutDisplayFunc(select);
	glutMainLoop();
}
