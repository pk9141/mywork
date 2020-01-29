#include<GL/glut.h>
#include<iostream>
using namespace std;
#define ROUND(a) ((int)(a+0.5))
#define LEFT_EDGE 0x1
#define RIGHT_EDGE 0x2
#define TOP_EDGE 0x8
#define BOTTOM_EDGE 0x4
#define INSIDE(a) (!a)
#define REJECT(a,b) (a&b)
#define ACCEPT(a,b) (!(a|b))
GLfloat xwmin,xwmax,ywmin,ywmax;

char encode(GLint x,GLint y)
{
		char code=0x00;
		
		if(x<xwmin)
			code=code|LEFT_EDGE;
		if(x>xwmax)
			code=code|RIGHT_EDGE;
		if(y<ywmin)
			code=code|BOTTOM_EDGE;
		if(y>ywmax)
			code=code|TOP_EDGE;
			
		return code;
}

void swappts(GLint *x1,GLint *y1,GLint *x2,GLint *y2)
{
	GLint temp1,temp2;
	temp1=*x1;temp2=*y1;
	*x1=*x2;
	*y1=*y2;
	*x2=temp1;
	*y2=temp2;	
}

void swapcodes(char *c1,char *c2)
{
	char temp;
	temp=*c1;
	*c1=*c2;
	*c2=temp;
}

void init()
{
	glClearColor(1.0,1.0,1.0,0.0);
	glClear(GL_COLOR_BUFFER_BIT);
	glColor3f(0.0,0.0,0.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(0.0,1000.0,0.0,800.0,-1,1);
}

void draw_lines(GLint a,GLint b,GLint c,GLint d)
{
	glBegin(GL_LINES);
	glVertex2i(a,b);
	glVertex2i(c,d);
	glEnd();
	glFlush();
}
void draw(void)
{
	GLint x1,y1,x2,y2;
	char code1,code2;
	int done=0,draw=0;
	float m;
	cout<<" Enter the world window Boundaries:";
	cout<<"\n XWmin and XWmax:";
	cin>>xwmin>>xwmax; 
	cout<<"\n YWmin and YWmax:";
	cin>>ywmin>>ywmax;
	draw_lines(xwmin-50,ywmin,xwmax+50,ywmin);
	draw_lines(xwmin-50,ywmax,xwmax+50,ywmax);
	draw_lines(xwmin,ywmin-50,xwmin,ywmax+50);
	draw_lines(xwmax,ywmin-50,xwmax,ywmax+50);
	cout<<"\n Enter the Endpoints:";
	cin>>x1>>y1>>x2>>y2;
	glColor3f(1.0,0.0,0.0);

	draw_lines(x1,y1,x2,y2);
	
	glColor3f(0.0,1.0,0.0);
	while(!done)
	{
		cout<<"x1:"<<x1<<"y1:"<<y1<<"x2:"<<x2<<"y2:"<<y2<<"\n";
		code1=encode(x1,y1);
		code2=encode(x2,y2);
		if(ACCEPT(code1,code2))
		{
			done=1;
			draw=1;
		}
		else if(REJECT(code1,code2))
		{
			done=1;
		}
		else
		{
			if(INSIDE(code1))
			{
				swappts(&x1,&y1,&x2,&y2);
				swapcodes(&code1,&code2);
				cout<<"swap:";
						cout<<"x1:"<<x1<<"y1:"<<y1<<"x2:"<<x2<<"y2:"<<y2<<"\n";
			}
			if(x2!=x1)
			{
				m=(y2-y1)/(x2-x1);
			}
			if(code1&LEFT_EDGE)
			{
				y1+=(xwmin-x1)*m;
				x1=xwmin;				
			}
			else if(code1&RIGHT_EDGE)
			{
				y1+=(xwmax-x1)*m;
				x1=xwmax;					
			}
			else if(code1&BOTTOM_EDGE)
			{
				if(x2!=x1)
					x1+=(ywmin-y1)/m;
				y1=ywmin;						
			}				
			else if(code1&TOP_EDGE)
			{	
				if(x2!=x1)
					x1+=(ywmax-y1)/m;
				y1=ywmax;
			}
					cout<<"x1:"<<x1<<"y1:"<<y1<<"x2:"<<x2<<"y2:"<<y2<<"\n";
		}					
	}
	if(draw)
		draw_lines(ROUND(x1),ROUND(y1),ROUND(x2),ROUND(y2));
}

int main(int argc,char **argv)
{
	glutInit(&argc,argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(1000,800);
	glutInitWindowPosition(300,200);
	glutCreateWindow("Line-Clipping");
	init();
	glutDisplayFunc(draw);
	glutMainLoop();
}
