import lisplist.*;
import static java.lang.System.*;
import java.lang.*;
import java.util.*;

public class lisp
{
	public static void main(String args[])
	{
		Scanner read=new Scanner(System.in);
		int n,ele,ch;
		out.print("\n Enter the no of elements:");
		n=read.nextInt();
		lisplist a=new lisplist(n);
		do
		{	
			out.print("\n\n Menu \n\n 1.Create \n 2.Display \n 3.Car \n 4.Cdr \n 5.Cons \n 6.Length \n 7.nthCdr \n 8.Setcar \n 9.Setcdr \n 10.nthlist \n 11.First_Last \n 12.Cadr \n 13.Exit \n\n Enter your option:");
			ch=read.nextInt();
			switch(ch)
			{
				case 1:
					for(int i=0;i<n;i++)
					{
						out.print("\n Enter the element:");
						ele=read.nextInt();
						Integer I=new Integer(ele);
						a.create(I,i);
						out.println("\n Integer "+ele+" inserted in the list");						
					}
					break;
				case 2:
					a.display();
					break;	
				case 3:
					out.println("\n First Element in the list: "+a.get(0));
					break;
				case 4:
					out.print("\n Element in the list except the first one: ");
					for(int i=1;i<n;i++)
						out.print(a.get(i)+"\t");
					break;
				case 5:
					out.print("\n Enter the element:");
					ele=read.nextInt();
					Integer I=new Integer(ele);
					a.create(I,0);
					out.println("\n Integer "+ele+" inserted as the first element in the list");
					a.display();
					n++;
					break;
				case 6:
					out.print("\n Length of the list: "+a.length());
					break;
				case 7:
					out.print("\n Enter the value of n:");
					ele=read.nextInt();
					a.nthcdr(ele);
					break;
				case 8:
					out.print("\n Enter the element:");
					ele=read.nextInt();
					I=new Integer(ele);
					a.replace(I,0);
					a.display();
					break;
				case 9:
					out.print("\n Enter the no of elements of second list:");
					ele=read.nextInt();
					lisplist a1=new lisplist(ele);
					for(int i=0;i<ele;i++)
					{
						out.print("\n Enter the element: ");
						I=new Integer(read.nextInt());
						a1.create(I,i);
					}
					a.addlist(a1);
					n=a.length();
					a.display();
					break;
				case 10:
					out.print("\n Enter the element position:");
					ele=read.nextInt();
					if(ele<n)
						out.println("\n Element at the position "+ele+" is: "+a.get(ele));
					else
						out.println("\n Null");
					break;
				case 11:
					out.println("\n First Element in the list is: "+a.get(0));
					out.println("\n Last Element in the list is: "+a.get(n-1));
					break;
				case 12:
					if(n>1)
						out.println("\n Element: "+a.get(1));
					else
						out.println("\n Null");						
					break;
				case 13:
					ch=0;
					break;
				default:
					out.print("\n Enter the right option:");
			}
		}while(ch!=0);
	}
}
		
		
