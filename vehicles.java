import Menu.*;
import java.io.*;
import java.util.*;

abstract class Vehicle
{
	String owner_name,registration_no,engine_type,fuel_type,color,model,maker;
	int age,no_of_wheels,no_of_gears;
	Date purchase = new Date();
	double vehicle_price,tank_capacity,mileage,vehicle_expenditure;
	Scanner ip = new Scanner(System.in);
        void mileage_calculation(double end, double start)
        {
                mileage = (end - start) / tank_capacity;
        }
        void age_calculation()
        {
                Date current = new Date();
                int temp = current.getYear();
		System.out.print(temp+" "+purchase.getYear());
                age = temp - purchase.getYear();
        }
        String [] maker_search(String make)
	{
		boolean x=maker.equals(make);
                String [] details={"not"};
		if(x)
		{
			details=new String[3];
			details[0]=this.model;
			details[1]=this.owner_name;
			details[2]=this.registration_no;
		}
                return details;
	}
	String[] vehicle_search(String city)
	{
		int x=registration_no.indexOf(city);
                String [] details={"not"};
		if(x>=0)
		{
			details=new String[3];
			details[0]=this.model;
			details[1]=this.owner_name;
			details[2]=this.registration_no;
		}
                return details;
	}
	void getdata()
	{
                double starting, ending;
                int yrs,mon,date;                
                System.out.println("\nEnter the following details \n");
		System.out.print("\nOwner Name :");
		owner_name = ip.next();
		
		System.out.print("\nRegistration No :");
		registration_no = ip.next();
		
		System.out.print("\nYear of purchase :");
		yrs = ip.nextInt();
		System.out.print("\nMonth of purchase :");
		mon = ip.nextInt();
		System.out.print("\nDate of purchase :");
		date = ip.nextInt();
		purchase.setYear(yrs);
		purchase.setMonth(mon);
		purchase.setDate(date);
		
		System.out.print("\nEngine type :");
		engine_type = ip.next();
		
		System.out.print("\nNo of gears :");
		no_of_gears = ip.nextInt();
		
		System.out.print("\nFuel type :");
		fuel_type = ip.next();
		
		System.out.print("\nVehicle Price :");
		vehicle_price = ip.nextDouble();
		
		System.out.print("\nTank capacity :");
		tank_capacity = ip.nextDouble();
		
		System.out.print("\nColor :");
		color = ip.next();
		
		System.out.print("\nModel :");
		model = ip.next();
		
		System.out.print("\nMaker :");
		maker = ip.next();

                System.out.print("\nFor Mileage calculations \nEnter the starting point :");
                starting = ip.nextDouble();

                System.out.print("\nEnter the ending point : ");
                ending = ip.nextDouble();
                
                while(starting >= ending)
		{
			System.out.print("\nEnding should be > than starting....Enter again \n");
			
			System.out.print("\nEnter the starting point : ");
	                starting = ip.nextDouble();

                	System.out.print("\nEnter the ending point : ");
	                ending = ip.nextDouble();
		}
		
		mileage_calculation(ending,starting);
		age_calculation();
		System.out.println("\nAdded to the array \n");
	}
}
class Bike extends Vehicle
{
	double vehicle_expenditure,fuel_consumption_amount,repair_charges,service_charges;
	int number_of_services;
	int age;
	public static int countBike;
        Bike()
        {
                countBike++;
        }	
        void getdata()
        {
                super.getdata();
                System.out.print("\n Enter the fuel consumption amount :");
		fuel_consumption_amount = ip.nextDouble();
		
		System.out.print("\n Enter the repair charges :");
		repair_charges = ip.nextDouble();
		
		System.out.print("\n Enter the number of services:");
		number_of_services = ip.nextInt();
	
        }
	void expenditure_calculation()
	{
        	
		if(number_of_services <=2)
			service_charges=0;
		else
			service_charges=(number_of_services-2)*1000;

		vehicle_expenditure = fuel_consumption_amount + repair_charges + service_charges;
	        
	}
}
class Car extends Vehicle
{
	double vehicle_expenditure,fuel_consumption_amount,repair_charges,service_charges;
	int number_of_services;
	int age;
	double accessories,battery_charges;
	public static int countCar;
	Car()
	{
	        countCar++;
	}
        void getdata()
        {
                super.getdata();
                System.out.print("\n Enter the fuel consumption amount :");
		fuel_consumption_amount = ip.nextDouble();
		
		System.out.print("\n Enter the repair charges :");
		repair_charges = ip.nextDouble();
		
		System.out.print("\n Enter the number of services:");
		number_of_services = ip.nextInt();
	        
	        System.out.print("\n Enter the battery charges :");
		battery_charges = ip.nextDouble();
        }		
        void expenditure_calculation()
	{
		
		if(number_of_services <=3)
			service_charges=0;
		else
			service_charges=(number_of_services-2)*1000;
			
		vehicle_expenditure = fuel_consumption_amount + repair_charges + service_charges + battery_charges;

	}
}
public class vehicles
{
        public static void main(String args[])
        {
                Vehicle[] reference = new Vehicle[20];
                Scanner ip = new Scanner(System.in);
                int count = 0;
                int op1,op2;
                int i;
                while(true)
                {
                        printMenu  m1 = new  printMenu("Enter a record","Print mileages","Print ages","Maker Search","Vehicle Search","Vehicle Count","Exit");
                        op1 = m1.readMenu(8);
                        switch(op1)
                        {
                                case 1:
                                
                                printMenu  m2 = new  printMenu("Bike","Car","Exit");
                                op2 = m2.readMenu(3);
                                
                                if(op2==3)
                                        System.exit(0);
                                else if(op2==1) /*bike*/
                                {
                                        reference[count] = new Bike();
                                        reference[count].getdata();
                                }
                                else if(op2==2) /*car*/
                                {
                                        reference[count] = new Car();
                                        reference[count].getdata();                   
                                }
                                count++;
                                break;

                                case 2: /*print mileages*/
                                
                                for(i=0;i<count;i++)
                                        System.out.print("\n"+reference[i].owner_name+"\t"+reference[i].registration_no+"\t"+reference[i].mileage+"\n");
                                
                                break;

                                case 3: /*print ages*/
                                for(i=0;i<count;i++)
                                        System.out.print("\n"+reference[i].owner_name+"\t"+reference[i].registration_no+"\t"+(reference[i].age)+"\n");
                                break;

                                case 4:
                           	System.out.print("\n Enter the maker to search for : ");
           	                String maker = ip.next();
                	   	boolean found=false;
           	                for(i=0;i<count;i++)
           	                {
           	                        String [] finding=reference[i].maker_search(maker);
                                	if(finding[0].equals("not"))
           	    	                        continue;
           	                        found=true;	
           	                        System.out.println("\n Model : "+finding[0]+"\n Owner Name : "+finding[1]+"\n Registration Number : "+finding[2]+"\n");
           	                }
           	                if(!found)
           		                System.out.println("\n No vehicles have been made by the maker "+maker);
                                
                                break;

                                case 5:
                             	System.out.print("\n Enter the registration city code to search for : ");
           	                String city = ip.next();
                           	found=false;
           	                for(i=0;i<count;i++)
           	                {
           	                        String [] finding1=reference[i].vehicle_search(city);
           	                        if(finding1[0].equals("not"))
           	                                continue;
                                   	found=true;
           	                        System.out.println("\n Model : "+finding1[0]+"\n Owner name : "+finding1[1]+"\n Registration No : "+finding1[2]+"\n");
           	                }
           	                if(!found)
           		                System.out.println("\n No vehicles with such registration city "+city);
                                break;

                                case 6:
                                System.out.print("\nNo of records for bike: "+Bike.countBike+"\nNo of records for car: "+Car.countCar);
                                break;
                                
                                case 7:
                                
                                for(i=0;i<count;i++)
                                
                                        System.out.print("\n"+reference[i].owner_name+"\t"+reference[i].registration_no+"\t"+reference[i].vehicle_expenditure+"\n");
                                break;
                                
                                case 8:
                                System.exit(0);
                        }
                }                                
        }
}
