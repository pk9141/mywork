#include<iostream.h>
#include<math.h>
class shape
{
      protected:
                float a,b;
      public:
             void getdata(float a1,float b1)
             {
                  a=a1;
                  b=b1;
             }
             virtual void display()=0;
};
class rectangle:public shape
{
      void display()
      {
           cout<<"\nArea of rectangle is "<<a*b;
      }
};
class square:public shape
{
      void display()
      {
           cout<<"\nArea of square is"<<a*a;
      }
};
class circle:public shape
{
      void display()
      {
           cout<<"\nArea of circle is"<<3.14*a*a;
      }
};
class triangle:public shape
{
      void display()
      {
           cout<<"\nArea of triangle is"<<(a*b)/2;
      }
};
class ellipse:public shape
{
      void display()
      {
           cout<<"\n Area of ellipse is"<<3.14*a*b; 
      }
};
class polygon:public shape
{
      void display()
      {
           cout<<"\n Area of polygon is"<<((a*b*b)/(4*tan(3.14/a)));
      }
};
int main()
{
    shape *ptr;
    float a,b;
    while(1)
    {
    cout<<"\n\n Menu \n 1.Rectangle \n 2.Square \n 3.Triangle \n 4.circle \n 5.Ellipse \n 6.Polygon \n 7.Exit \n Enter your option:";
    int ch;
    cin>>ch;
    switch(ch)
    {
              case 1:
                   {
                   rectangle r;
                   cout<<"\n Enter the length:";
                   cin>>a;
                   cout<<"\n Enter the breadth:";
                   cin>>b;
		   ptr=&r;
		   ptr->getdata(a,b);
		   ptr->display();
		   break;
		   }
	      case 2:
		   {
		   square s;
		   cout<<"\n Enter the side:";
		   cin>>a;
		   ptr=&s;
		   ptr->getdata(a,0);
		   ptr->display();
		   break;
		   }
	      case 3:
		   {
		   triangle t;
		   cout<<"\n Enter the base:";
		   cin>>a;
		   cout<<"\n Enter the height:";
		   cin>>b;
		   ptr=&t;
		   ptr->getdata(a,b);
		   ptr->display();
		   break;
		   }
	      case 4:
		   {
		   circle c;
		   cout<<"\n Enter the radius:";
		   cin>>a;
		   ptr=&c;
		   ptr->getdata(a,0);
		   ptr->display();
		   break;
		   }
	      case 5:
		   {
		   ellipse e;
		   cout<<"\n Enter the length of major-axis:";
		   cin>>a;
		   cout<<"\n Enter the length of minor-axis:";
		   cin>>b;
		   ptr=&e;
		   ptr->getdata(a,b);
		   ptr->display();
		   break;
		   }
	      case 6:
		   {
		   polygon p;
		   cout<<"\n Enter the sides:";
		   cin>>a;
		   cout<<"\n Enter the length:";
		   cin>>b;
		   ptr=&p;
		   ptr->getdata(a,b);
		   ptr->display();
		   break;
		   }
	      case 7:
		   exit(0);
   }
   }
}

                   
    
