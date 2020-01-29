package lisplist;
import static java.lang.System.*;
import java.lang.*;
import java.util.*;
public class lisplist
{
	ArrayList l;
	Scanner read=new Scanner(System.in);
	public int size;
	public lisplist()
	{
		size=0;
	}
	public lisplist(int size)
	{
		this.size=size;
		l=new ArrayList(size);
	}
	public void cdr(ArrayList Ar)
	{
		Ar.remove(0);
	}
	public void create(Integer I,int pos)
	{
		l.add(pos,I);
	}
	public void display()
	{
		Integer[] list=new Integer[l.size()];
		l.toArray(list);
		out.println("\n Elements in the list:\n");
		for(int i=0;i<l.size();i++)
			out.println(list[i]);
	}
	public int length()
	{
		return l.size();
	}
	public Integer get(int pos)
	{
		return (Integer)l.get(pos);
	}
	public void replace(Integer I,int pos)
	{
		l.set(pos,I);
	}
	public void addlist(lisplist a)
	{
		size=l.size();
		for(int i=1;i<size;i++)
			l.remove(1);
		for(int i=0;i<a.l.size();i++)
			l.add(i+1,a.l.get(i));
	}
	public void nthcdr(int pos)
	{
		int temp=0;
		ArrayList l1=(ArrayList)l.clone();
		while(temp<pos)
		{
			cdr(l1);
			Integer[] list=new Integer[l1.size()];
			l1.toArray(list);
			out.println("\n Elements in the list:\n");
			for(int i=0;i<l1.size();i++)
				out.println(list[i]);	
			temp++;
		}
	}

}


