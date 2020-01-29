
package cloud;

import jade.core.AID;
import java.net.*;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Premkumar
 */
public class Cloud extends Agent{

    Connection connect;
    Statement st,st1;
    ResultSet rs,rs1;
    
    protected void setup() 
    {
		// Create the catalogue

		// Register the book-selling service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("cloud");
                sd.setName("Cloud Server");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
                    System.out.println("\n Hello");
			fe.printStackTrace();
		}	
                
                addBehaviour(new TickerBehaviour(this,6000)
                {
	
        public void onTick() {
			
			// CFP Message received. Process it
                                String content="";
                               MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);    
                               ACLMessage msg = myAgent.receive(mt);
                               if(msg!=null)
                               {    
                                ACLMessage reply = msg.createReply();
                                reply.setPerformative(ACLMessage.PROPOSE);
                                                            
 
                            try
                            {
                                Class.forName("com.mysql.jdbc.Driver");
                                connect=DriverManager.getConnection("jdbc:mysql://localhost/cloud","root","darkknightrises");
                            }
                            catch(Exception e)
                            {
                                System.out.println(e.toString());
                            }
                            try
                            {
                                System.out.println("\n Cloud Database:");
                                st=connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                                st1=connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                                rs=st.executeQuery("select  * from software1");
                                rs1=st1.executeQuery("select * from hardware1");
                                String format="%1$-20s %2$-20s %3$-25s %4$-20S %n";
                                System.out.println("\n Hardware:");
                                content+="Hardware"+"/";
                                System.out.format(format,"HID","DISK NAME","TOTAL MEMORY" ,"FREE MEMORY");
                                while(rs1.next())
                                {
                                    System.out.format(format,rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4));
                                    content+=rs1.getString(1)+" "+rs1.getString(2)+" "+rs1.getString(3)+" "+rs1.getString(4)+"/";
                                }
                                System.out.println("\n Software:");
                                content+="software"+"/";
                                System.out.format(format,"SID","SOFTWARE NAME","MAXIMUM USER" ,"CURRENT USER");
                                while(rs.next())
                                {
                                    System.out.format(format,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
                                    content+=rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+"/";
                                }
                                
                                reply.setContent(content);
                                myAgent.send(reply);
                            }
                            catch(Exception e)
                            {
                                System.out.println("Display Error:"+e.toString());
                            }                                         
                               
                         }
                               else
                               {
                                   block();
                               }
		}
                
	});

        
    }
}
