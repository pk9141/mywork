/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package central;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import java.sql.*;

/**
 *
 * @author Premkumar
 */
public class Central1 extends Agent{
    
    private AID[] cloud;
    private MessageTemplate mt; 
    Connection connect;
    Statement st,st1;
    ResultSet rs,rs1;
    
    protected void setup()
    {
                  
        
        
                  addBehaviour(new TickerBehaviour(this,6000) {

            @Override
            protected void onTick() {
             
                               
                  DFAgentDescription template = new DFAgentDescription();
		  ServiceDescription sd = new ServiceDescription();
		  sd.setType("cloud");
		  template.addServices(sd);
		  try 
                  {
			DFAgentDescription[] result = DFService.search(myAgent, template); 
			System.out.println("Forwarding Request To Cloud:");
			cloud = new AID[result.length];
			for (int i = 0; i < result.length; ++i) 
                        {
				cloud[i] = result[i].getName();
				System.out.print(cloud[i].getName());
			}
		  }
		  catch (FIPAException fe) 
                  {
			fe.printStackTrace();
		  }                  
                  
            
            
                            ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
				for (int i = 0; i < cloud.length; ++i) {
					cfp.addReceiver(cloud[i]);
				} 
				cfp.setConversationId("Resource-Search");
				cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
				myAgent.send(cfp);
				// Prepare the template to get proposals
				mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Resource-Search"),
						MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
			
                                
                                ACLMessage reply;
                                do
                                {    
                                    reply = myAgent.receive(mt);
                                }while(reply==null);
                                
                               try
                               {
                                   Class.forName("com.mysql.jdbc.Driver");
                                    connect=DriverManager.getConnection("jdbc:mysql://localhost/cloud","root","darkknightrises");           
                                }
                                catch(Exception e)
                                {
                                    System.out.println(e.toString());
                                }
                              
                               if(reply.getPerformative() == ACLMessage.PROPOSE) 
                               {
				
                                      String[] str;int i;
                                      String content=reply.getContent();
                                      str=content.split("/");
                                      for(i=1;!(str[i].equals("software"));i++)
                                      {
                                       
                                          String[] con=str[i].split(" ");
                                            try
                                            {
                                                PreparedStatement p=connect.prepareStatement("insert ignore into hard values (?,?,?,?,?)");
                                                p.setString(1,cloud[0].getName());
                                                p.setInt(2,Integer.parseInt(con[0]));
                                                p.setString(3,con[1]);
                                                p.setString(4,con[2]);
                                                p.setString(5,con[3]);
                                                p.executeUpdate();
                                            }
                                        catch(Exception e)
                                        {
                                                System.out.println("Insertion Error:"+e.toString());
                                        }
                                    }
                                    
                                      ++i;
                                      for(;i<str.length;i++)
                                      {
                                       
                                           String[] con=str[i].split(" ");
                                            try
                                            {
                                                PreparedStatement p=connect.prepareStatement("insert ignore into soft values (?,?,?,?,?)");
                                                p.setString(1,cloud[0].getName());
                                                p.setInt(2,Integer.parseInt(con[0]));
                                                p.setString(3,con[1]);
                                                p.setInt(4,Integer.parseInt(con[2]));
                                                p.setInt(5,Integer.parseInt(con[3]));
                                                p.executeUpdate();
                                            }
                                        catch(Exception e)
                                        {
                                                System.out.println("Insertion Error:"+e.toString());
                                        }
                                      }
                                      
                                      try
        {
            System.out.println("\n Cloud Database:");
            st=connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            st1=connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery("select  * from soft");
            rs1=st1.executeQuery("select * from hard");
            String format="%1$-50s %2$-20s %3$-25s %4$-20S %5$-20s %n";
            System.out.println("\n Hardware:");
            System.out.format(format,"CLOUD","HID","DISK NAME","TOTAL MEMORY" ,"FREE MEMORY");
            while(rs1.next())
            {
                System.out.format(format,rs1.getString(1),rs1.getInt(2),rs1.getString(3),rs1.getString(4),rs1.getString(5));
            }
            System.out.println("\n Software:");
            System.out.format(format,"CLOUD","SID","SOFTWARE NAME","MAXIMUM USER" ,"CURRENT USER");
            while(rs.next())
            {
                System.out.format(format,rs.getString(1),rs.getInt(2),rs.getString(3),rs.getInt(4),rs.getInt(5));
            }
        }
        catch(Exception e)
        {
            System.out.println("Display Error:"+e.toString());
        }
                               }         
            }
            
                  });
    }
    
}
