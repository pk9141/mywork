/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.sql.*;
import jade.core.Agent;
import java.util.*;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
/**
 *
 * @author Premkumar
 */
public class Client extends Agent{
    
    protected void setup()
    {
        int count=0;
        
        Database d=new Database();
        Scanner read=new Scanner(System.in);
        thread[] t=new thread[100];
        int ch=0;
        
            System.out.println("\n 1.Request Resource \n 2.Deallocate Resource \n 3.Exit \n Choice:");
            ch=read.nextInt();
            if(ch==1)
            {
                try
                {
                     t[++count]=new thread(count);
                     addBehaviour(t[count]);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }                
            }
            else if(ch==2)
            {
               int id,row;
               row=d.print_details();
               if(row<1)
               {
                    System.out.println("\n No Resource Exist to Deallocate");
               }
               else
               {
                        System.out.println("\n Enter the Id of the Resource:");
                        id=read.nextInt();
                        t[id].deallocate();
               }
            }            
          
    }
    
    class thread extends Behaviour{
    
       int count;
       Database d1;
       private AID[] Central;
       Timer t;
       String content;
       long time_interval;
        public thread(int count)
        {
            this.count=count;
        }
        public void action()
        {
            Scanner read=new Scanner(System.in);
            String resource=new String();
            String service=null;
            
            int choice;
            
            
            try
            {
                System.out.println("Request Type:\n 1.Infrastructure As a Service \n 2.Software As a Service \n Enter your Choice:");
                choice=read.nextInt();    
                
                if(choice==1)
                {
                    
                        System.out.println("Memory Required:");
                        service="Infrastructure As a Service";
                        resource=read.next(); // read the request from user
                }
                else if(choice==2)
                {
                        System.out.println("Software Name:");
                        service="Software As a Service";
                        resource=read.next(); // read the request from user
                }
                else
                {
                        System.out.println("Enter the right choice");                       
                }
                
                 content=service+"/"+resource;
                 d1=new Database();
                 d1.insert(count, service, resource);
                 t=new Timer();
                
                              
                  t.start();  // start the timer
                  DFAgentDescription template = new DFAgentDescription();
		  ServiceDescription sd = new ServiceDescription();
		  sd.setType("central");
		  template.addServices(sd);
		  try 
                  {
			DFAgentDescription[] result = DFService.search(myAgent, template); 
			System.out.println("Forwarding Request To Central Server:");
			Central = new AID[result.length];
			for (int i = 0; i < result.length; ++i) 
                        {
				Central[i] = result[i].getName();
				System.out.print(Central[i].getName());
			}
		  }
		  catch (FIPAException fe) 
                  {
			fe.printStackTrace();
		  }
                  myAgent.addBehaviour(new RequestPerformer());
                                  
            }
             catch(Exception e)
            {
                System.out.println(e.toString());
            }
        }
        public boolean done()
        {
            return true;
        }
        public void deallocate()
        {
            try
            {
                System.out.println("Sending Request to Central Server to deallocate..... ");
                d1.remove(this.count);
                System.out.println("\n Resource Deallocated");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
         }
        private class RequestPerformer extends Behaviour {
		
		private MessageTemplate mt; 
		private int step = 0;
                              
                         
		public void action()
                {
				// Send the cfp to central
				ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
				for (int i = 0; i < Central.length; ++i) {
					cfp.addReceiver(Central[i]);
				} 
				cfp.setContent(content);
				cfp.setConversationId("Resource-Request");
				cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
				myAgent.send(cfp);
				// Prepare the template to get proposals
				mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Resource-Request"),
						MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
			
                                
                                ACLMessage reply;
                                do
                                {    
                                    reply = myAgent.receive(mt);
                                }while(reply==null);
                                
					// Reply received
				 if (reply.getPerformative() == ACLMessage.PROPOSE) 
                                 {
				
                                      content=reply.getContent();
                                      System.out.println(reply.getSender()+" "+content);
                                 }         
				
                                 t.stop();
                                 System.out.println("\n Response Received From Server");
                
                                 time_interval=t.interval();
                
                                System.out.format("%n Elapsed time interval:%5d%n",time_interval);
                                System.out.format("%n Cloud IP:%s%n",content);
		}

		public boolean done()
                {
		/*	if (step == 2 && bestSeller == null) {
				System.out.println("Attempt failed: "+" not available for sale");
			}
			return ((step == 2 && bestSeller == null) || step == 4);*/
                  return true;
		}
	}
     
}

}    
    class Database {
    Connection connect;
    Statement st;
    ResultSet rs;
    
    public Database()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connect=DriverManager.getConnection("jdbc:mysql://localhost/cloud","root","darkknightrises");           
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    public void insert(int id,String service,String resource)
    {
       
        try
        {
              PreparedStatement p=connect.prepareStatement("insert ignore into client values (?,?,?)");
              p.setInt(1,id);
              p.setString(2,service);
              p.setString(3,resource);
              p.executeUpdate();               
        }
        catch(Exception e)
        {
            System.out.println("Insertion Error:"+e.toString());
        }
    }
    public int print_details()
    {
        System.out.println("\n Client Request Details:");
        try
        {
            int c=0;
            st=connect.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery("select * from client");
            while(rs.next())
            {
                System.out.println("\n"+rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3));
                c++;
            }
            return c;
        }
        catch(Exception e)
        {
            System.out.println("Display Error:"+e.toString());
            return 0;
        }
    }
    public void remove(int id)
    {
        try
        {
            PreparedStatement p=connect.prepareStatement("delete from client where id="+id);
            p.executeUpdate();
            
        }
        catch(Exception e)
        {
            System.out.println("Deallocation Error:"+e.toString());
        }
    }
    
}
    
    class Timer
    {
    
         long start_time,end_time;
    
          public Timer()
          {
            start_time=end_time=0;
          }
          public void start()
          {
               start_time=System.currentTimeMillis();
         }
         public void stop()
         {
               end_time=System.currentTimeMillis();
         }
         public long interval()
         {
              return (end_time-start_time);
          }
    }


