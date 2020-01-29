import java.io.*;
import java.util.Scanner;
class read
{
  Object obj;
  FileInputStream f1=null;
  ObjectInputStream o1=null;
  FileOutputStream k1=null;
  public read()
  {
  try
  {
    f1=new FileInputStream("currency_store");
	o1=new ObjectInputStream(f1);
  }
  catch(IOException ex)
  {
    ex.printStackTrace();
  }
  while(true)
  {
	try
	{
	  obj=o1.readObject();
	}
	catch(IOException S)
	{
	  System.exit(0);
	}
	catch(ClassNotFoundException cl)
	{
	  cl.printStackTrace();
	}
	if(obj instanceof dollar)
	{
	  dollar d=(dollar)obj;
	  System.out.println("\nRead object is of type dollar & the dollar value is: "+d.values+"\n");
	  d.convert_rupee();
	  System.out.println("converted rupee value is: " + d.rup+"\n");
	}
	else if(obj instanceof rupee)
	{
	  rupee r=(rupee)obj;
	  System.out.println("\nRead object is of type rupee & the rupee value is: "+r.values+"\n");
	  r.convert_doll();
	  System.out.println("converted Dollar value is: " + r.dol+"\n");
	}
  }
  }
  public static void main(String args[])
  {
    read r=new read();
  }
}
  
	  
