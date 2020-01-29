#include<GLUT/glut.h>
#include<iostream>
#define ROUND(a) ((int)(a+0.5))
using namespace std;
int flag;
void init()
{
	glClearColor(1.0,1.0,1.0,0.0);
	glClear(GL_COLOR_BUFFER_BIT);
	glColor3f(0.0,0.0,1.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(0.0,1000.0,0.0,800.0,1.0,-1.0);
}

void setPixel(GLint x,GLint y)
{
	glBegin(GL_POINTS);
	glVertex2i(x,y);
	glEnd();
	glFlush();
}

void ellipsePloteye(GLint x,GLint y,GLint xcenter,GLint ycenter)
{
	setPixel(xcenter+x,ycenter+y);
	setPixel(xcenter-x,ycenter+y);
}
void ellipsePlotMouth(GLint x,GLint y,GLint xcenter,GLint ycenter)
{
	setPixel(xcenter+x,ycenter-y);
	setPixel(xcenter-x,ycenter-y);	
}

void draw_nose()
{
	glBegin(GL_LINES);
	glVertex2i(300,320);
	glVertex2i(300,270);
	glEnd();
	glFlush();
}

void draw_face(GLint rx,GLint ry,GLint xcenter,GLint ycenter)
{
	GLint rx2=rx*rx;
	GLint ry2=ry*ry;
	GLint tworx2=2*rx2;
	GLint twory2=2*ry2;
	GLint p;
	GLint x=0,y=ry;
	GLint px=0,py=tworx2*y;

	if(flag==0)
		ellipsePlotMouth(x,y,xcenter,ycenter);
    else
		ellipsePloteye(x,y,xcenter,ycenter);

	p=ROUND(ry2-(rx2*ry)+(.25*rx2));
	while(px<py)
	{
		x++;
		px+=twory2;
		if(p<0)
			p+=ry2+px;
		else
		{
			y--;
			py-=tworx2;	
			p+=ry2+px-py;
		}
	
		if(flag==0)
		ellipsePlotMouth(x,y,xcenter,ycenter);
    else
		ellipsePloteye(x,y,xcenter,ycenter);	
	}


}


void draw_eyebrows(GLint rx,GLint ry,GLint xcenter,GLint ycenter)
{
	flag=1;
	draw_face(rx,ry,xcenter,ycenter);
}
void draw_mouth(GLint rx,GLint ry,GLint xcenter,GLint ycenter)
{
	flag=0;
	draw_face(rx,ry,xcenter,ycenter);
}
void ellipsePlotPoints(GLint x,GLint y,GLint xcenter,GLint ycenter)
{
	setPixel(xcenter+x,ycenter+y);
	setPixel(xcenter-x,ycenter+y);
	setPixel(xcenter+x,ycenter-y);
	setPixel(xcenter-x,ycenter-y);	
}

void ellipseMidPoint(GLint rx,GLint ry,GLint xcenter,GLint ycenter )
{
	GLint rx2=rx*rx;
	GLint ry2=ry*ry;
	GLint tworx2=2*rx2;
	GLint twory2=2*ry2;
	GLint p;
	GLint x=0,y=ry;
	GLint px=0,py=tworx2*y;

	ellipsePlotPoints(x,y,xcenter,ycenter);

	p=ROUND(ry2-(rx2*ry)+(.25*rx2));
	while(px<py)
	{
		x++;
		px+=twory2;
		if(p<0)
			p+=ry2+px;
		else
		{
			y--;
			py-=tworx2;	
			p+=ry2+px-py;
		}
		ellipsePlotPoints(x,y,xcenter,ycenter);
	}

	p=ROUND(ry2*(x+0.5)*(x+0.5)+rx2*(y-1)*(y-1)-rx2*ry2);
	while(y>0)
	{
		y--;
		py-=tworx2;
		if(p>0)
			p+=rx2-py;
		else
		{
			x++;
			px+=twory2;
			p+=rx2-py+px;
		}
		ellipsePlotPoints(x,y,xcenter,ycenter);
	}
		
}

void smiley()
{
	ellipseMidPoint(100,150,300,300);
	ellipseMidPoint(20,10,260,350);
	ellipseMidPoint(20,10,340,350);	
	ellipseMidPoint(5,5,260,350);
	ellipseMidPoint(5,5,340,350);
	draw_eyebrows(20,10,260,365);
	draw_eyebrows(20,10,340,365);
	draw_nose();
	draw_mouth(30,10,300,250);

}
	
int main(int argc,char **argv)
{
	/*cout<<"Enter the radius:";
	cin>>rx>>ry;
	cout<<"Enter the Origin:";
	cin>>xcenter>>ycenter;*/
	glutInit(&argc,argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(1000,800);
	glutInitWindowPosition(100,100);
	glutCreateWindow("MidPoint Ellipse");
	init();
	glutDisplayFunc(smiley);
	glutMainLoop();
}
