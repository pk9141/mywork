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
public class Central extends Agent
{
    
     private AID[] cloud;
    Connection connect;
    Statement st,st1;
    ResultSet rs,rs1;
   String content=null;
     protected void setup() {
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("central");
                sd.setName("Central Server");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
                    System.out.println("\n Hello");
			fe.printStackTrace();
		}	
                
                addBehaviour(new OfferRequestsServer());
	}

        private class OfferRequestsServer extends CyclicBehaviour{
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				// CFP Message received. Process it
				String[] title;
                                title=msg.getContent().split("/");
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
                                System.out.println(title[0]);
                                try
                                {
          
                                    st=connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                                    if(title[0].contains("Infrastructure"))
                                    {
                                         
                                        int memory=Integer.parseInt(title[1]);
                                        rs=st.executeQuery("select * from hard where free_mem >="+memory);
                                        
                                        rs.first();
                                        if(rs.getRow()>0)
                                            content=rs.getString(1);
                                        
                                    }
                                    else
                                    {
                                         
                                            rs=st.executeQuery("select * from soft where sname like '"+title[1]+"' and Maximum <> current");
                                            rs.first();
                                            if(rs.getRow()>0)
                                                content=rs.getString(1);
                                              
                                        }
                                }
                                catch(Exception e)
                                {
                                        System.out.println("Search Error:"+e.toString());
                                }

                                
				reply.setContent(content);
                                myAgent.send(reply);	     
                               
                        }
			else 
                        {
				block();
			}
		}
                
	}
    
}
