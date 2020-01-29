#include <math.h>
#include <GL/glut.h>
#include <stdio.h>
#include <stdlib.h>
#include<iostream.h>

typedef float Matrix3x3 [3][3];
Matrix3x3 theMatrix;
int NEdges;
float ptsIni[20][2];
float ptsFin[20][2];
float refpt[2];
float RotAngle;
float TransDistX,TransDistY;
float ScaleX,ScaleY;
int choice,choiceRef,choiceShear;
float slope,yIntercept;
float shearValue;
void matrixSetIdentity(Matrix3x3 m)
{
 int i, j;
 for (i=0; i<3; i++)
 for (j=0; j<3; j++)
 m[i][j] = (i == j);
}                                  
                                                     
void matrixPreMultiply(Matrix3x3 a , Matrix3x3 b)
{
 int i,j;
 Matrix3x3 tmp;
 for (i = 0; i < 3; i++)
 for (j = 0; j < 3; j++)
 tmp[i][j] = a[i][0]*b[0][j] + a[i][1]*b[1][j] + a[i][2]*b[2][j];
 for (i = 0; i < 3; i++)
 for (j = 0; j < 3; j++)
 theMatrix[i][j] = tmp[i][j];
}

void Translate(int tx, int ty)
{
 Matrix3x3 m;
 matrixSetIdentity(m);
 m[0][2] = tx;
 m[1][2] = ty;
 matrixPreMultiply(m, theMatrix);
}

void Scale(float sx , float sy)
{
 Matrix3x3 m;
 matrixSetIdentity(m);
 m[0][0] = sx;
 m[0][2] = (1 - sx)*refpt[0];
 m[1][1] = sy;
 m[1][2] = (1 - sy)*refpt[1];
 matrixPreMultiply(m , theMatrix);
}

void Rotate(float a)
{
 Matrix3x3 m;
 matrixSetIdentity(m);
 a = a*22/1260;
 m[0][0] = cos(a);
 m[0][1] = -sin(a) ;
 m[0][2] = refpt[0]*(1 - cos(a)) + refpt[1]*sin(a);
 m[1][0] = sin(a);
 m[1][1] = cos(a);
 m[1][2] = refpt[1]*(1 - cos(a)) - refpt[0]*sin(a);
 matrixPreMultiply(m , theMatrix);
}

void Reflect(int xy)
{
 Matrix3x3 m;
 matrixSetIdentity(m);
 if(xy == 2)
 m[1][1] = -1;
 if(xy == 3)
 m[0][0] = -1;
 matrixPreMultiply(m , theMatrix);
}

void Shear(int xy)
{
 Matrix3x3 m;
 matrixSetIdentity(m);
 if(xy == 1)
 m[0][1] = shearValue;
 if(xy == 2)
 m[1][0] = shearValue;
 matrixPreMultiply(m , theMatrix);
}

void TransformPoints(void)
{
 int k;
 for (k = 0 ; k<NEdges && k<20 ; k++)
 {
 ptsFin[k][0] = theMatrix[0][0]*ptsIni[k][0]
              + theMatrix[0][1]*ptsIni[k][1] + theMatrix[0][2];
 ptsFin[k][1] = theMatrix[1][0]*ptsIni[k][0]
              + theMatrix[1][1]*ptsIni[k][1] + theMatrix[1][2];
 }
}

void display(void)
{
 glClear (GL_COLOR_BUFFER_BIT);
 glColor3f (0.0, 0.0, 0.0);                 
 glBegin(GL_LINES);                         
 glVertex2s(-1000 ,0);
 glVertex2s( 1000 ,0);
 glEnd();
 glBegin(GL_LINES);                         
 glVertex2s(0 ,-1000);
 glVertex2s(0 , 1000);
 glEnd();
 glColor3f (0.0, 0.0, 0.0);                 
 int PlotCt;
 glBegin(GL_POLYGON);                       
 for(PlotCt=0 ; PlotCt<NEdges ; PlotCt++)
 {
 glVertex2s(ptsIni[PlotCt][0],ptsIni[PlotCt][1]);
 }
 glEnd();
 matrixSetIdentity(theMatrix);
 switch(choice)
 {
 
 case 1:   Translate(TransDistX , TransDistY);
 break;
 case 2:   Rotate(RotAngle);
 break;
 case 3:   Scale(ScaleX, ScaleY);
 break;
 case 4:   switch(choiceRef)
 {
 case 1 :  refpt[0]=0.0; refpt[1]=0.0;
 Rotate(180);
 break;
 case 2 :  Reflect(2);
 break;
 case 3 :  Reflect(3);
 break;
 case 4 :  Translate(slope/yIntercept , -yIntercept);
 Rotate(-1260*atan(slope)/22);
 Reflect(2);
 Rotate(1260*atan(slope)/22);
 Translate(-slope/yIntercept , yIntercept);
 glBegin(GL_LINES);
 glVertex2s(-1000 ,-slope*1000 + yIntercept);
 glVertex2s( 1000 , slope*1000 + yIntercept);
 glEnd();
 break;
 }
 case 5:   Shear(choiceShear);
 break;    
 }  
 TransformPoints();   
 glColor3f (0.0, 1.0, 1.0);                
 glBegin(GL_POLYGON);
 for(PlotCt=0 ; PlotCt<NEdges ; PlotCt++)  
 {
 glVertex2s(ptsFin[PlotCt][0],ptsFin[PlotCt][1]);
 }
 glEnd();
 glFlush();
}
void init(void)
 {
 glClearColor (1.0, 1.0, 1.0, 1.0);
 glOrtho(-200.0, 200.0, -200.0, 200.0, -1.0, 1.0);
 }
 
int main (int argc, char *argv)
{
 glutInit(&argc, &argv);
 glutInitDisplayMode (GLUT_SINGLE | GLUT_RGB);
 glutInitWindowSize (750, 750);
 glutInitWindowPosition (0, 0);
 glutCreateWindow (" Basic Transformations ");
 init ();
 cout<<"Enter the no. of Vertices:\n ";
 cin>>NEdges;
 printf("Enter %d Co-ordinate pairs \n",NEdges);
 int PlotCt;
 for(PlotCt=0 ; PlotCt<NEdges ; PlotCt++)
 cin>>ptsIni[PlotCt][0]>>ptsIni[PlotCt][1];
 
 cout<<"Enter your choice number:\n 1.Translation\n2.Rotation\n3.Scaling\n4.Reflection\n5.Shearing\n=>";
 cin>>choice;
 switch(choice)
 {
  case 1:     printf("Enter translation along X & Y\n=>");
 scanf("%f%f",&TransDistX , &TransDistY);
 break;
 case 2:     printf("Enter Angle of Rotation\n=>");
 scanf("%f",&RotAngle);
 printf("Enter reference point Co-ordinates:\n=>");
 scanf("%f %f",&refpt[0],&refpt[1]);
 break;
 case 3:     printf("Enter Scaling ratios along X & Y\n=>");
 scanf("%f%f",&ScaleX , &ScaleY);
 break;
 case 4:     printf("Enter your choice number for Reflection about:\n 1.Origin\n 2.X-axis\n 3.Y-axis\n 4.y = mx+c\n=>");
 scanf("%d",&choiceRef);
 if(choiceRef == 4)
 {
 cout<<"Enter m & c: \n=>";
 cin>>slope>>yIntercept;
 }
 break;      
 case 5:     cout<<"Enter your choice number to shear about:\n 1.x-axis\n 2.y-axis\n=>";
 cin>>choiceShear;
cout<<"Enter shear value:\n=>";
cin>>shearValue;
 break;
 default:   cout<<"enter correct option!!!\n";
 return 0;
 }
 glutDisplayFunc(display);
 glutMainLoop();
 return 0;
}
