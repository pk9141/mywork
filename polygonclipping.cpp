#include<GL/glut.h>
#include<iostream>
using namespace std;
GLfloat xwmin,xwmax,ywmin,ywmax;
GLfloat inipts[10][2],finpts[10][2],x1,y1,x2,y2;
int inicount,fincount,i,j;





void init()
{
	glClearColor(1.0,1.0,1.0,0.0);
	glClear(GL_COLOR_BUFFER_BIT);
	glColor3f(0.0,0.0,0.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(0.0,800.0,0.0,600.0,-1,1);
}

void draw_lines(GLfloat a,GLfloat b,GLfloat c,GLfloat d)
{
	glBegin(GL_LINES);
	glVertex2f(a,b);
	glVertex2f(c,d);
	glEnd();
	
}

void maindraw(void)
{
     	glClear(GL_COLOR_BUFFER_BIT);
     	glColor3f(0.0,0.0,0.0);
	draw_lines(xwmin-100,ywmin,xwmax+100,ywmin);
	draw_lines(xwmin-100,ywmax,xwmax+100,ywmax);
	draw_lines(xwmin,ywmin-100,xwmin,ywmax+100);
	draw_lines(xwmax,ywmin-100,xwmax,ywmax+100);
	glColor3f(0.0,0.0,1.0);
   for(i=0;i<inicount;i++)
   {
        glBegin(GL_POLYGON);
        glVertex2f(inipts[i][0],inipts[i][1]);                       
   }
    glEnd(); 
	glFlush();
}

void copy(int count)
{
     for(i=0;i<count;i++)
     {
        inipts[i][0]=finpts[i][0];
        inipts[i][1]=finpts[i][1];    
     }
     inicount=count;
}

void clip_left(void)
{
     float m,x,y;
     int count=0;
     for(i=0;i<inicount;i++)
   {                      
       if(i==inicount-1)
       { 
         x2=inipts[0][0];
         y2=inipts[0][1];                 
       }
       else
       {
          x2=inipts[i+1][0];
         y2=inipts[i+1][1]; 
       }    
       x1=inipts[i][0];y1=inipts[i][1];
       if(x2!=x1)
		{
			m=(y2-y1)/(x2-x1);
		}
       if((x1>xwmin)&&(x2>xwmin))
       {
            finpts[count][0]=x2;
            finpts[count++][1]=y2;
            cout<<"\nCoords: x1="<<finpts[count-1][0]<<" y1="<<finpts[count-1][1];
       }
       else if((x1<xwmin)&&(x2<xwmin))
       {
       }
       else if((x1<xwmin)&&(x2>xwmin))//P out and Q in
       {
            y=y1+((xwmin-x1)*m);
			x=xwmin;
            finpts[count][0]=x;
            finpts[count++][1]=y;
            cout<<"\nCoords: x1="<<finpts[count-1][0]<<" y1="<<finpts[count-1][1];
            finpts[count][0]=x2;
            finpts[count++][1]=y2;
            cout<<"\tCoords: x1="<<finpts[count-1][0]<<" y1="<<finpts[count-1][1];
       }
       else if((x1>xwmin)&&(x2<xwmin))//P in and Q out 
       {
            y=y1+((xwmin-x1)*m);
			x=xwmin;
            finpts[count][0]=x;
            finpts[count++][1]=y;
            cout<<"\n inicount="<<i<<" x1="<<x1<<" y1="<<y1<<" x2="<<x2<<" y2="<<y2;
            cout<<"\nCoords: x1="<<finpts[count-1][0]<<" y1="<<finpts[count-1][1];
       }
		cout<<"\nFinal count = "<<count	;
   }
   copy(count);
}

void clip_top(void)
{
     float m,x,y;
     int count=0;
     for(i=0;i<inicount;i++)
   {                      
       if(i==inicount-1)
       { 
         x2=inipts[0][0];
         y2=inipts[0][1];                 
       }
       else
       {
          x2=inipts[i+1][0];
         y2=inipts[i+1][1]; 
       }    
       x1=inipts[i][0];y1=inipts[i][1];
       if(x2!=x1)
		{
			m=(y2-y1)/(x2-x1);
		}
       if((y1>ywmax)&&(y2>ywmax))
       {
            
       }
       else if((y1<ywmax)&&(y2<ywmax))
       {
           finpts[count][0]=x2;
            finpts[count++][1]=y2;
            cout<<"\nCoords: x1="<<finpts[count-1][0]<<" y1="<<finpts[count-1][1];
       }
       else if((y1>ywmax)&&(y2<ywmax))//P out and Q in
       {
            x=x1+((ywmax-y1)/m);
			y=ywmax;
            finpts[count][0]=x;
            finpts[count++][1]=y;
            cout<<"\nCoords: x1="<<finpts[count-1][0]<<" y1="<<finpts[count-1][1];
            finpts[count][0]=x2;
            finpts[count++][1]=y2;
            cout<<"\tCoords: x1="<<finpts[count-1][0]<<" y1="<<finpts[count-1][1];
       }
       else if((y1<ywmax)&&(y2>ywmax))//P in and Q out 
       {
            x=x1+((ywmax-y1)/m);
			y=ywmax;
            finpts[count][0]=x;
            finpts[count++][1]=y;
            cout<<"\n inicount="<<i<<" x1="<<x1<<" y1="<<y1<<" x2="<<x2<<" y2="<<y2;
            cout<<"\nCoords: x1="<<finpts[count-1][0]<<" y1="<<finpts[count-1][1];
       }
		cout<<"\nFinal count = "<<count	;
   }
   copy(count);
}

void clip_right(void)
{
     float m,x,y;
     int count=0;
     for(i=0;i<inicount;i++)
   {                      
       if(i==inicount-1)
       { 
         x2=inipts[0][0];
         y2=inipts[0][1];                 
       }
       else
       {
          x2=inipts[i+1][0];
         y2=inipts[i+1][1]; 
       }    
       x1=inipts[i][0];y1=inipts[i][1];
       if(x2!=x1)
		{
			m=(y2-y1)/(x2-x1);
		}
       if((x1<xwmax)&&(x2<xwmax))
       {
            finpts[count][0]=x2;
            finpts[count++][1]=y2;
            cout<<"\nCoords: x1="<<finpts[count-1][0]<<" y1="<<finpts[count-1][1];
       }
       else if((x1>xwmax)&&(x2>xwmax))
       {
       }
       else if((x1>xwmax)&&(x2<xwmax))//P out and Q in
       {
            y=y1+((xwmax-x1)*m);
			x=xwmax;
            finpts[count][0]=x;
            finpts[count++][1]=y;
            cout<<"\nCoords: x1="<<finpts[count-1][0]<<" y1="<<finpts[count-1][1];
            finpts[count][0]=x2;
            finpts[count++][1]=y2;
            cout<<"\tCoords: x1="<<finpts[count-1][0]<<" y1="<<finpts[count-1][1];
       }
       else if((x1<xwmax)&&(x2>xwmax))//P in and Q out 
       {
            y=y1+((xwmax-x1)*m);
			x=xwmax;
            finpts[count][0]=x;
            finpts[count++][1]=y;
            cout<<"\n inicount="<<i<<" x1="<<x1<<" y1="<<y1<<" x2="<<x2<<" y2="<<y2;
            cout<<"\nCoords: x1="<<finpts[count-1][0]<<" y1="<<finpts[count-1][1];
       }
		cout<<"\nFinal count = "<<count	;
   }
   copy(count);
}

void clip_bottom(void)
{
     float m,x,y;
     int count=0;
     for(i=0;i<inicount;i++)
   {                      
       if(i==inicount-1)
       { 
         x2=inipts[0][0];
         y2=inipts[0][1];                 
       }
       else
       {
          x2=inipts[i+1][0];
         y2=inipts[i+1][1]; 
       }    
       x1=inipts[i][0];y1=inipts[i][1];
       if(x2!=x1)
		{
			m=(y2-y1)/(x2-x1);
		}
       if((y1<ywmin)&&(y2<ywmin))
       {
            
       }
       else if((y1>ywmin)&&(y2>ywmin))
       {
           finpts[count][0]=x2;
            finpts[count++][1]=y2;
            cout<<"\nCoords: x1="<<finpts[count-1][0]<<" y1="<<finpts[count-1][1];
       }
       else if((y1<ywmin)&&(y2>ywmin))//P out and Q in
       {
            x=x1+((ywmin-y1)/m);
			y=ywmin;
            finpts[count][0]=x;
            finpts[count++][1]=y;
            cout<<"\nCoords: x1="<<finpts[count-1][0]<<" y1="<<finpts[count-1][1];
            finpts[count][0]=x2;
            finpts[count++][1]=y2;
            cout<<"\tCoords: x1="<<finpts[count-1][0]<<" y1="<<finpts[count-1][1];
       }
       else if((y1>ywmax)&&(y2<ywmax))//P in and Q out 
       {
            x=x1+((ywmin-y1)/m);
			y=ywmin;
            finpts[count][0]=x;
            finpts[count++][1]=y;
            cout<<"\n inicount="<<i<<" x1="<<x1<<" y1="<<y1<<" x2="<<x2<<" y2="<<y2;
            cout<<"\nCoords: x1="<<finpts[count-1][0]<<" y1="<<finpts[count-1][1];
       }
		cout<<"\nFinal count = "<<count	;
   }
   copy(count);
}

void draw(void)
{
     int t1,t2;
     maindraw();
     for(t1 = 0; t1 < 2000; t1++)
          for(t2 = 0; t2 < 200000; t2++)
          {}
     clip_left();
     maindraw();
     for(t1 = 0; t1 < 2000; t1++)
           for(t2 = 0; t2 < 200000; t2++)
           {}
     clip_top();
     maindraw();
     for(t1 = 0; t1 < 2000; t1++)
          for(t2 = 0; t2 < 200000; t2++)
          {}
     clip_right();
     maindraw();
     for(t1 = 0; t1 < 2000; t1++)
           for(t2 = 0; t2 < 200000; t2++)
           {}
     clip_bottom();
     maindraw();

}

int main(int argc,char **argv)
{
    cout<<" Enter the world window Boundaries:";
	cout<<"\n XWmin and XWmax:";
	cin>>xwmin>>xwmax; 
	cout<<"\n YWmin and YWmax:";
	cin>>ywmin>>ywmax;
	cout<<"Enter no of coords:";
	cin>>inicount;
	cout<<"\n Enter the vertices:";
	for(i=0;i<inicount;i++)
            cin>>inipts[i][0]>>inipts[i][1];
	
	glutInit(&argc,argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(800,600);
	glutInitWindowPosition(100,100);
	glutCreateWindow("Polygon-Clipping");
	init();
	glutDisplayFunc(draw);
	glutMainLoop();
}
