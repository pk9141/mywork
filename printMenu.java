package Menu;
import java.util.Scanner;
public class printMenu
{
        Scanner ip = new Scanner(System.in);
        public printMenu(String... option)
        {
                System.out.print("Menu: \n");
                int count=1;
                for(count=0;count<option.length;count++)
                        System.out.print((count+1)+". "+option[count]+"\n");
        }
        public int readMenu(int limit)
        {
                int op;
                System.out.print("\nEnter an option: ");
                op = ip.nextInt();
                while(op<0 || op>limit)
                {
                        System.out.print("\nEnter a correct option: ");
                        op = ip.nextInt();
                }
                return op;
        }
}
