#include<iostream>
#include<GL/glut.h>

using namespace std;
float xwmin,xwmax,ywmin,ywmax,xvmin,xvmax,yvmin,yvmax,inipt[10][2],finpt[10][2],sx,sy;
int i,j,vcount;


void init()
{
     glClearColor(1.0,1.0,1.0,0.0);
     glClear(GL_COLOR_BUFFER_BIT);
     glColor3f(0.0f,0.0f,0.0f);
     glMatrixMode(GL_PROJECTION);
     glLoadIdentity();
     gluOrtho2D(0.0,1000.0,0.0,500.0);
}

void window()
{
     xwmin=50;xwmax=400;ywmin=50;ywmax=400;
     xvmin=50+500;xvmax=400+500;yvmin=50;yvmax=250;
     /*cout<<"Enter the window min and max for x and y ";
     cin>>xwmin>>xwmax>>ywmin>>ywmax;
     cout<<"Enter the viewport min and max for x and y ";
     cin>>xvmin>>xvmax>>yvmin>>yvmax;*/
     sx=(xvmax-xvmin)/(xwmax-xwmin);
     sy=(yvmax-yvmin)/(ywmax-ywmin);
         
}

void calccoords()
{
     for(i=0;i<vcount;i++)
     {
            finpt[i][0]=xvmin+(inipt[i][0]-xwmin)*sx;                                   
            finpt[i][1]=yvmin+(inipt[i][1]-ywmin)*sy;                                   
     }    
}

void display()
{
     glColor3f(0.5f,0.5f,0.5f);    
     glRectf(xwmin,ywmin,xwmax,ywmax);
     glRectf(xvmin,yvmin,xvmax,yvmax);
     glEnd();
     glColor3f(0.0f,0.0f,1.0f);
     glBegin(GL_POLYGON);
     for(i=0;i<vcount;i++)
     glVertex2f(inipt[i][0],inipt[i][1]);
     glEnd();
     glBegin(GL_POLYGON);
     for(i=0;i<vcount;i++)
     glVertex2f(finpt[i][0],finpt[i][1]);
     glEnd();
     glFlush();
     
}

int main(int argc,char** argv)
{
     cout<<"Enter the no of vertices: "  ;
     cin>>vcount;
     for(i=0;i<vcount;i++)
     cin>>inipt[i][0]>>inipt[i][1];
     window();
     calccoords();
     glutInit(&argc,argv);
     glutInitDisplayMode(GLUT_SINGLE|GLUT_RGB);
     glutInitWindowSize(1000,500);
     glutInitWindowPosition(100,100);
     glutCreateWindow("Windowing");
     init();
     glutDisplayFunc(display);
     glutMainLoop();
}
