import java.sql.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.WindowListener;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JComponent.*;
class tabbedpane extends JFrame 
{
	Connection con;
	ResultSet rs;
	PreparedStatement pre;
	Statement st;
	tabbedpane()
	{
		setTitle("OPAC");
		Toolkit kit=Toolkit.getDefaultToolkit();
		setSize(650,500);
		setLocationByPlatform(true);
		setLocation(250,250);		
		setResizable(true);		
		setLayout(new BorderLayout(20,20));

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception look)
		{
			look.printStackTrace();
		}

		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con=DriverManager.getConnection("jdbc:mysql://localhost/library","root","dark");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		final entry e=new entry();
		final search s=new search();
		final issue i=new issue();
		final return_book r=new return_book();
		final list l=new list();
		
		
		final JTabbedPane jtp=new JTabbedPane();
		jtp.addTab("Entry",e);
		jtp.addTab("Search",s);
		jtp.addTab("Issue",i);
		jtp.addTab("Return",r);
		jtp.addTab("List Issued Books",l);	

		jtp.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent ce)
			{
				JTabbedPane jtab=(JTabbedPane)ce.getSource();
				String title=jtab.getTitleAt(jtab.getSelectedIndex());
				if(title.equals("Entry"))
					e.disable_panel();	
				else if(title.equals("Search"))
					s.disable_comp();
				else if(title.equals("Issue"))
					i.clear_fields();
				else if(title.equals("Return"))
					r.clear_fields();
				else if(title.equals("List Issued Books"))
					l.clear_fields();
			}
		});
			
		tabbedpane.this.addWindowListener(new WindowAdapter()
		{
			public void windowOpened(WindowEvent W)
			{
				String message="Welcome to OPAC system for Library"+"\n\n\n"+"Developed by M.Prem Kumar";				
				JOptionPane.showMessageDialog(tabbedpane.this,message);
			}
			public void windowClosing(WindowEvent W)
			{
				String message="Thank You For using MY OPAC System for library "+"\n\n\n"+"With Regards From M.Prem Kumar";				
				JOptionPane.showMessageDialog(tabbedpane.this,message);
			}
		});	
		add(jtp);				
	}

	
	class entry extends JPanel
	{
		final JPanel panel,mpanel;
		final JLabel bname,acc_no,author,cat,sta,mname,mid,mdept,icount,msta;
		final JTextField bname_text,accno_text,author_text,cat_text,mname_text,mid_text,mdept_text,icount_text,usertext;
		final JRadioButton book,mem;
		final JButton insert,bclear;
		final JComboBox status,mstatus;
		final JPasswordField passfield;
		entry()
		{		
			panel=new JPanel();
			panel.setLayout(new GridLayout(3,2,5,5));

			JLabel username=new JLabel("User Name:",JLabel.CENTER);
			JLabel pass=new JLabel("Password:",JLabel.CENTER);

			usertext=new JTextField(10);
			passfield=new JPasswordField(10);

			JButton ok=new JButton("OK");
			JButton clear=new JButton("CLEAR");	

			mpanel=new JPanel();					
			mpanel.setLayout(new GridLayout(12,2,5,5));
			ButtonGroup group=new ButtonGroup();
			book=new JRadioButton("Book",false);
			group.add(book);
			mem=new JRadioButton("Member",false);
			group.add(mem);
			mpanel.add(book);
			mpanel.add(mem);	
					
			bname=new JLabel("Book Name :");
			acc_no=new JLabel("Access No :");			
			author=new JLabel("Author:");
			cat=new JLabel("Category:");
			sta=new JLabel("Status:");
			mname=new JLabel("Member Name:");
			mid=new JLabel("Memeber ID:");
			mdept=new JLabel("Department:");
			msta=new JLabel("Status:");
			icount=new JLabel("Issued Count:");
			bname_text=new JTextField(10);
			accno_text=new JTextField(10);
			author_text=new JTextField(10);
			cat_text=new JTextField(10);
			mname_text=new JTextField(10);
			mid_text=new JTextField(10);
			mdept_text=new JTextField(10);
			icount_text=new JTextField(10);

			insert=new JButton("INSERT");
			bclear=new JButton("CLEAR");
		
			status=new JComboBox();
			status.addItem("");
			status.addItem("Available");
			status.addItem("Issued");	
			status.addItem("Transferred");
			status.setSelectedIndex(0);			
			mstatus=new JComboBox();
			mstatus.addItem("");
			mstatus.addItem("Student");
			mstatus.addItem("Staff");				
			mstatus.setSelectedIndex(0);
			

			panel.add(username);
			panel.add(usertext);
			panel.add(pass);			
			panel.add(passfield);
			panel.add(ok);
			panel.add(clear);			

			add(panel);

			mpanel.add(bname);
			mpanel.add(bname_text);
			mpanel.add(mname);
			mpanel.add(mname_text);
			mpanel.add(acc_no);
			mpanel.add(accno_text);
			mpanel.add(mid);
			mpanel.add(mid_text);
			mpanel.add(author);
			mpanel.add(author_text);
			mpanel.add(mdept);
			mpanel.add(mdept_text);
			mpanel.add(cat);
			mpanel.add(cat_text);
			mpanel.add(icount);
			mpanel.add(icount_text);
			mpanel.add(sta);
			mpanel.add(status);	
			mpanel.add(msta);
			mpanel.add(mstatus);			
			mpanel.add(insert);
			mpanel.add(bclear);
					
			disable_panel();

			add(mpanel);

			clear.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					usertext.setText("");
					passfield.setText("");
				}
			});

			ok.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(usertext.getText().equals("premkumar"))
					{
						if(String.valueOf(passfield.getPassword()).equals("darkknight"))
						{
							JOptionPane.showMessageDialog(tabbedpane.this,"Admin Logged Successfully");
							panel.setVisible(false);					
							mpanel.setVisible(true);	
							mem.doClick();
							book.doClick();
						}
						else
						{
							JOptionPane.showMessageDialog(tabbedpane.this,"Wrong Password");
						}
					}
					else
					{
							JOptionPane.showMessageDialog(tabbedpane.this,"Wrong UserName");
					}
					usertext.setText("");
					passfield.setText("");
				}		
			});
	
			book.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
				
					clear_fields();
					bname.setVisible(true);
					bname_text.setVisible(true);
					acc_no.setVisible(true);
					accno_text.setVisible(true);
					author.setVisible(true);
					author_text.setVisible(true);
					cat.setVisible(true);
					cat_text.setVisible(true);
					sta.setVisible(true);
					status.setVisible(true);
					status.setSelectedIndex(0);
					mname.setVisible(false);
					mname_text.setVisible(false);			
					mid.setVisible(false);
					mid_text.setVisible(false);
					msta.setVisible(false);
					mstatus.setVisible(false);
					mdept.setVisible(false);
					mdept_text.setVisible(false);
					icount.setVisible(false);
					icount_text.setVisible(false);
					insert.setVisible(true);
					bclear.setVisible(true);
				
				}
			});

			mem.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)			
				{
					clear_fields();
					bname.setVisible(false);
					bname_text.setVisible(false);
					acc_no.setVisible(false);
					accno_text.setVisible(false);
					author.setVisible(false);
					author_text.setVisible(false);
					cat.setVisible(false);
					cat_text.setVisible(false);
					sta.setVisible(false);
					status.setVisible(false);											
					mname.setVisible(true);
					mname_text.setVisible(true);			
					mid.setVisible(true);
					mid_text.setVisible(true);
					msta.setVisible(true);
					mstatus.setVisible(true);
					mstatus.setSelectedIndex(0);
					mdept.setVisible(true);
					mdept_text.setVisible(true);
					icount.setVisible(true);
					icount_text.setVisible(true);
					insert.setVisible(true);
					bclear.setVisible(true);
				}
			});			
			
			insert.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(book.isSelected())
					{
						try
						{
							String query=("insert into book_details values(?,?,?,?,?)");					
							pre=con.prepareStatement(query);
							pre.setString(1,bname_text.getText());
							pre.setInt(2,Integer.parseInt(accno_text.getText()));
							pre.setString(3,author_text.getText());
							pre.setString(4,cat_text.getText());
							pre.setString(5,status.getSelectedItem().toString());
							try
							{
								if(bname_text.getText().equals("")||accno_text.getText().equals("")||author_text.getText().equals("")||cat_text.getText().equals("")||status.getSelectedItem().toString().equals(""))
									JOptionPane.showMessageDialog(tabbedpane.this,"Fill in the fields");
								else
								{
									pre.executeUpdate();					
									JOptionPane.showMessageDialog(tabbedpane.this,"Entry for Book Done Successfully");
								}
							}
							catch(Exception Ex)
							{
								JOptionPane.showMessageDialog(tabbedpane.this,Ex.toString());
							}						
						}					
						catch(Exception E)
						{
								JOptionPane.showMessageDialog(tabbedpane.this,E.toString());
						}
					}
					else if(mem.isSelected())
					{
						try
						{
							String query=("insert into member_details values(?,?,?,?,?)");					
							pre=con.prepareStatement(query);
							pre.setString(1,mname_text.getText());
							pre.setInt(2,Integer.parseInt(mid_text.getText()));
							pre.setString(3,mstatus.getSelectedItem().toString());
							pre.setString(4,mdept_text.getText());
							pre.setInt(5,Integer.parseInt(icount_text.getText()));
							
							try
							{
								if(mname_text.getText().equals("")||mid_text.getText().equals("")||mdept_text.getText().equals("")||icount_text.getText().equals("")||mstatus.getSelectedItem().toString().equals(""))
									JOptionPane.showMessageDialog(tabbedpane.this,"Fill in the fields");
								else
								{
									pre.executeUpdate();					
									JOptionPane.showMessageDialog(tabbedpane.this,"Entry for Member Done Successfully");
								}
							}
							catch(Exception Ex)
							{
								JOptionPane.showMessageDialog(tabbedpane.this,Ex.toString());
							}						
						}					
						catch(Exception E)
						{
								JOptionPane.showMessageDialog(tabbedpane.this,E.toString());
						}
					}
					clear_fields();
				}
			});

			bclear.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					clear_fields();
				}
			});			
		}
		void disable_panel()
		{
			bname.setVisible(false);
			bname_text.setVisible(false);	
			acc_no.setVisible(false);
			accno_text.setVisible(false);
			author.setVisible(false);
			author_text.setVisible(false);
			cat.setVisible(false);
			cat_text.setVisible(false);
			sta.setVisible(false);
			status.setVisible(false);
			mname.setVisible(false);
			mname_text.setVisible(false);			
			mid.setVisible(false);
			mid_text.setVisible(false);
			msta.setVisible(false);
			mstatus.setVisible(false);
			mdept.setVisible(false);
			mdept_text.setVisible(false);
			icount.setVisible(false);
			icount_text.setVisible(false);
			insert.setVisible(false);
			bclear.setVisible(false);
			panel.setVisible(true);	
			mpanel.setVisible(false);	
			clear_fields();				
		}
		void clear_fields()
		{	
			usertext.setText("");
			passfield.setText("");
			bname_text.setText("");	
			accno_text.setText("");
			author_text.setText("");
			cat_text.setText("");
			status.setSelectedIndex(0);
			mname_text.setText("");						
			mid_text.setText("");			
			mstatus.setSelectedIndex(0);			
			mdept_text.setText("");			
			icount_text.setText("");			
		}	
	}
	class search extends JPanel
	{
		
		final JPanel panel;	
		final JLabel lsearch,ltitle,titleset,lauthor,authorset,laccno,accset,lcat,catset,lstatus,staset,lmemid,memset,laauthor,latitle;
		final JTextField ttitle,tauthor;
		final JButton bsearch,bclear,next,back;
		final JComboBox search;

		search()
		{
			panel=new JPanel();				  	
				  
			panel.setLayout(new GridLayout(11,2,5,5));
				
			lsearch=new JLabel("Search:");
			ltitle=new JLabel("Book Title:");
			latitle=new JLabel("Book Title:");												
			titleset=new JLabel();
			lauthor=new JLabel("Author Name:");
			laauthor=new JLabel("Author Name:");
			authorset=new JLabel();
			laccno=new JLabel("Access No:");
			accset=new JLabel();
			lcat=new JLabel("Category:");
			catset=new JLabel();
			lstatus=new JLabel("Current status:");
			staset=new JLabel();
			lmemid=new JLabel("Member ID:");
			memset=new JLabel();

			ttitle=new JTextField(10);
			tauthor=new JTextField(10);

			bsearch=new JButton("SEARCH");
			bclear=new JButton("CLEAR");
			next=new JButton("NEXT");
			back=new JButton("PREVIOUS");			
	
			search=new JComboBox();
			search.addItem("");
			search.addItem("By Title");
			search.addItem("By Author");		

			panel.add(lsearch);
			panel.add(search);	

			panel.add(ltitle);
			panel.add(ttitle);			
			panel.add(lauthor);
			panel.add(tauthor);
			panel.add(laauthor);
			panel.add(authorset);			
			panel.add(latitle);
			panel.add(titleset);			
			panel.add(laccno);
			panel.add(accset);
			panel.add(lcat);
			panel.add(catset);
			panel.add(lstatus);
			panel.add(staset);
			panel.add(lmemid);			
			panel.add(memset);
			panel.add(next);
			panel.add(back);
			panel.add(bsearch);
			panel.add(bclear);			

			disable_comp();			
		
			search.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(("By Title".equals((String)search.getSelectedItem())))
					{
						disable_comp();
						clear_fields();
						search.setSelectedIndex(1);
						ttitle.setText("");
						ttitle.setVisible(true);
						ltitle.setVisible(true);
						bsearch.setVisible(true);
						bclear.setVisible(true);
					}		
					else if(("By Author".equals((String)search.getSelectedItem())))
					{
						disable_comp();
						clear_fields();
						tauthor.setText("");
						search.setSelectedIndex(2);
						lauthor.setVisible(true);
						tauthor.setVisible(true);
						bsearch.setVisible(true);
						bclear.setVisible(true);											
					}	
					else
					{
						disable_comp();							
					}	
				
				}
			});

			bsearch.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(("By Title".equals((String)search.getSelectedItem())))
					{
						laauthor.setVisible(true);
						authorset.setVisible(true);
						laccno.setVisible(true);
						accset.setVisible(true);
						lcat.setVisible(true);
						catset.setVisible(true);
						lstatus.setVisible(true);
						staset.setVisible(true);
						lmemid.setVisible(false);
						memset.setVisible(false);
						try
						{
							Statement st1;
							ResultSet rs1;
							st1=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
							st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
							rs=st.executeQuery("select * from book_details where title like '%"+ttitle.getText()+"%'");
							rs.first();
							if(rs.getString("status").equals("Issued"))
							{
								rs1=st1.executeQuery("select * from issue_details where access_no="+rs.getInt("access_no"));
								rs1.first();
								lmemid.setVisible(true);
								memset.setVisible(true);
								memset.setText(rs1.getString("memid"));
							}
							authorset.setText(rs.getString("author"));
							accset.setText(rs.getString("access_no"));
							catset.setText(rs.getString("category"));
							staset.setText(rs.getString("status"));
							next.setVisible(true);
							back.setVisible(true);
						}
						catch(Exception Ex)
						{
							disable_comp();					
							Ex.printStackTrace();			
							ttitle.setText("");
							ttitle.setVisible(true);
							ltitle.setVisible(true);
							bsearch.setVisible(true);
							bclear.setVisible(true);
							search.setSelectedIndex(1);
							JOptionPane.showMessageDialog(tabbedpane.this,"No Match Found For The Given Book Title");				
						}						
				
					}
					else if(("By Author".equals((String)search.getSelectedItem())))
					{
						latitle.setVisible(true);
						titleset.setVisible(true);
						lauthor.setVisible(true);
						tauthor.setVisible(true);
						authorset.setVisible(false);
						laccno.setVisible(true);
						accset.setVisible(true);
						lcat.setVisible(true);
						catset.setVisible(true);
						lstatus.setVisible(true);
						staset.setVisible(true);
						lmemid.setVisible(false);
						memset.setVisible(true);												
						try
						{
							st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
							rs=st.executeQuery("select * from book_details where author like '%"+tauthor.getText()+"%'");
							rs.first();
							titleset.setText(rs.getString("title"));
							accset.setText(rs.getString("access_no"));
							catset.setText(rs.getString("category"));
							staset.setText(rs.getString("status"));
							next.setVisible(true);
							back.setVisible(true);
						}
						catch(Exception Ex)
						{
							disable_comp();
							tauthor.setText("");
							search.setSelectedIndex(2);
							lauthor.setVisible(true);
							tauthor.setVisible(true);
							bsearch.setVisible(true);
							bclear.setVisible(true);								
							JOptionPane.showMessageDialog(tabbedpane.this,"No Match Found For The Given Author Name");				
						}										
					}	
				}
			});

			bclear.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					disable_comp();
					search.setSelectedIndex(0);
				}
			});

			next.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent A)
				{
					try
					{
						rs.next();
						if(search.getSelectedItem().toString().equals("By Title"))
						{
							authorset.setText(rs.getString("author"));
							accset.setText(rs.getString("access_no"));
							catset.setText(rs.getString("category"));
							staset.setText(rs.getString("status"));
						}	
						else if(search.getSelectedItem().toString().equals("By Author"))
						{
							titleset.setText(rs.getString("title"));
							accset.setText(rs.getString("access_no"));
							catset.setText(rs.getString("category"));
							staset.setText(rs.getString("status"));
						}
					}
					catch(Exception E)
					{
						JOptionPane.showMessageDialog(tabbedpane.this,"No More Search Results Found");
					}
				}
			});

			back.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent A)
				{
					
					try
					{
						rs.previous();
						if(search.getSelectedItem().toString().equals("By Title"))
						{
							authorset.setText(rs.getString("author"));
							accset.setText(rs.getString("access_no"));
							catset.setText(rs.getString("category"));
							staset.setText(rs.getString("status"));
						}	
						else if(search.getSelectedItem().toString().equals("By Author"))
						{
							titleset.setText(rs.getString("title"));
							accset.setText(rs.getString("access_no"));
							catset.setText(rs.getString("category"));
							staset.setText(rs.getString("status"));
						}
					}
					catch(Exception E)
					{
						JOptionPane.showMessageDialog(tabbedpane.this,"No More Search Results Found");
					}
				}
			});
			add(panel);	
			
		}
		
		void disable_comp()
		{
			ttitle.setVisible(false);
			ltitle.setVisible(false);
			latitle.setVisible(false);
			lauthor.setVisible(false);
			laauthor.setVisible(false);
			tauthor.setVisible(false);
			titleset.setVisible(false);
			authorset.setVisible(false);
			laccno.setVisible(false);
			accset.setVisible(false);
			lcat.setVisible(false);
			catset.setVisible(false);
			lstatus.setVisible(false);
			staset.setVisible(false);
			lmemid.setVisible(false);
			memset.setVisible(false);
			bsearch.setVisible(false);
			bclear.setVisible(false);
			next.setVisible(false);
			back.setVisible(false);
			search.setSelectedIndex(0);	

		}
		void clear_fields()
		{
			titleset.setText("");
			authorset.setText("");
			accset.setText("");
			catset.setText("");
			staset.setText("");
			memset.setText("");			
		}
	}
	class issue extends JPanel
	{
		final JPanel panel;
		final JLabel lmemid,laccno,ldate,temp,temp1,temp2,temp3;
		final JTextField memid,accno,date;	
		final JButton issue,clear;
		issue()
		{
			panel=new JPanel();
			panel.setLayout(new GridLayout(6,2,5,5));
						
			lmemid=new JLabel("Memeber ID:");
			laccno=new JLabel("Book Access No:");
			ldate=new JLabel("Issued Date:");
			temp=new JLabel();
			temp1=new JLabel();
			temp2=new JLabel();
			temp3=new JLabel();

			memid=new JTextField(10);
			accno=new JTextField(10);		
			date=new JTextField(5);	

			issue=new JButton("ISSUE");
			clear=new JButton("CLEAR");

			panel.add(lmemid);
			panel.add(memid);
			panel.add(laccno);
			panel.add(accno);
			panel.add(ldate);
			panel.add(date);
			panel.add(temp);
			panel.add(temp1);
			panel.add(temp2);
			panel.add(temp3);
			panel.add(issue);
			panel.add(clear);


			issue.addActionListener(new ActionListener()
			{
				int count,max;String mstatus,bstatus,expire;
				public void actionPerformed(ActionEvent A)
				{
					try
					{
						Statement st1;
						st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						st1=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						try
						{
							rs=st1.executeQuery("select * from member_details where mid="+Integer.parseInt(memid.getText()));
							rs.first();
							mstatus=rs.getString("category");
							count=rs.getInt("icount");
							if(rs.getRow()>0)
							{
								ResultSet rs1;
								try
								{
									rs1=st.executeQuery("select * from book_details where access_no="+Integer.parseInt(accno.getText()));
									rs1.first();
									bstatus=rs1.getString("status");
									if(rs1.getRow()>0)
									{
										pre=con.prepareStatement("insert into issue_details values(?,?,?,'0',0)");
										pre.setString(1,accno.getText());
										pre.setString(2,memid.getText());
										pre.setString(3,date.getText());							
										try
										{
											if(date.getText().equals(""))
												throw new Exception();
											else
											{	
												int year1,mon1,day1;
												String[] date1=new String[4];
												date1=date.getText().split("/");																
												year1=Integer.parseInt(date1[0]);
												mon1=Integer.parseInt(date1[1]);
												day1=Integer.parseInt(date1[2]);
												if((mon1>12)||(day1>31))
													throw new Exception();
												else
												{
													if(mstatus.equals("Student"))
														mon1+=3;
													else if(mstatus.equals("Staff"))														
														mon1+=6;
													if(mon1>12)
													{
														mon1-=12;
														year1+=1;
													}
													expire=date1[0].valueOf(year1)+"/"+date1[1].valueOf(mon1)+"/"+date1[2];
												}									
												try
												{									
													if(bstatus.equals("Available"))
													{			
														if(mstatus.equals("Student"))
															max=3;
														else if(mstatus.equals("Staff"))														
															max=6;
														try
														{
															if(count<max)
															{
																pre.executeUpdate();
																rs1.updateString("status","Issued");
																rs1.updateRow();
																rs.updateInt("icount",count+1);
																rs.updateRow();
																JOptionPane.showMessageDialog(tabbedpane.this,"Book Issued Successfully"+"\n "+"Returned Date on or before : "+expire);
															}
															else
																throw new Exception();		
														}
														catch(Exception Ex)
														{ Ex.printStackTrace();
															JOptionPane.showMessageDialog(tabbedpane.this,"Member Reached the Maximum Count"+"\n"+"Return One Book And try again Later");
														}
													}
													else	
														throw new Exception();
												}
												catch(Exception s)
												{
													JOptionPane.showMessageDialog(tabbedpane.this,"Required Book is Not Available Now.Sorry for the inconvenience try again later !!!!!!!!"); 
												}													
											}	
										}
										catch(Exception e)
										{
											JOptionPane.showMessageDialog(tabbedpane.this,"Fill in the Date Field Properly");
										}
									}
									else
										throw new Exception();
								}
								catch(Exception ex)
								{
									JOptionPane.showMessageDialog(tabbedpane.this,"Book does not exist");									
								}

							}
							else
								throw new Exception();
						}
						catch(Exception e)
						{
							JOptionPane.showMessageDialog(tabbedpane.this,"Member does not exist");
						}
					}
					catch(Exception E){}
					clear_fields();
				}
			});	

			clear.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent A)
				{
					clear_fields();
				}
			});
	
			add(panel);
		}

		void clear_fields()
		{
			memid.setText("");
			accno.setText("");
			date.setText("");
		}		
	}
	class return_book extends JPanel
	{
		final JPanel panel;
		final JLabel lmemid,laccno,ldate,temp,temp1,temp2,temp3;
		final JTextField memid,accno,date;	
		final JButton returnb,clear;
		return_book()
		{
			panel=new JPanel();
			panel.setLayout(new GridLayout(6,2,5,5));
						
			lmemid=new JLabel("Memeber ID:");
			laccno=new JLabel("Book Access No:");
			ldate=new JLabel("Returned Date:");
			temp=new JLabel();
			temp1=new JLabel();
			temp2=new JLabel();
			temp3=new JLabel();

			memid=new JTextField(10);
			accno=new JTextField(10);		
			date=new JTextField(5);	

			returnb=new JButton("RETURN");
			clear=new JButton("CLEAR");

			panel.add(lmemid);
			panel.add(memid);
			panel.add(laccno);
			panel.add(accno);
			panel.add(ldate);
			panel.add(date);
			panel.add(temp);
			panel.add(temp1);
			panel.add(temp2);
			panel.add(temp3);
			panel.add(returnb);
			panel.add(clear);

			returnb.addActionListener(new ActionListener()
			{
				int count,max,fine=0;String bstatus,mstatus;
				public void actionPerformed(ActionEvent A)
				{
					try
					{
						Statement st1,st2;
						st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						st1=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						st2=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						try
						{
							rs=st1.executeQuery("select * from member_details where mid="+Integer.parseInt(memid.getText()));
							rs.first();
							count=rs.getInt("icount");
							mstatus=rs.getString("category");
							if(rs.getRow()>0)
							{
								ResultSet rs1,rs2;
								try
								{
									rs1=st.executeQuery("select * from book_details where access_no="+Integer.parseInt(accno.getText()));
									rs1.first();
									bstatus=rs1.getString("status");
									try
									{
										String query=null;
										int issue_accno,issue_memid;
										rs2=st2.executeQuery("select * from issue_details where access_no="+Integer.parseInt(accno.getText())+" and rdate='"+"0'");
										rs2.first();
										issue_accno=Integer.parseInt(accno.getText());
										issue_memid=Integer.parseInt(rs2.getString("memid"));
										try
										{
											if(date.getText().equals(""))
												throw new Exception();
											else
											{	
												int year1,year2,mon1,mon2,day1,day2;
												String[] date1=new String[4];
												String[] date2=new String[4];
												date1=date.getText().split("/");																
												date2=rs2.getString("idate").split("/");
												year1=Integer.parseInt(date1[0]);
												mon1=Integer.parseInt(date1[1]);
												day1=Integer.parseInt(date1[2]);
												year2=Integer.parseInt(date2[0]);
												mon2=Integer.parseInt(date2[1]);												
												day2=Integer.parseInt(date2[2]);
												if((mon1>12)||(mon2>12)||(day1>31)||(day2>31)||(year1<year2))
													throw new Exception();
												else
												{
													int year,mon,date;
													if(mon1>mon2)
														mon=mon1-mon2;
													else
														mon=mon1+12-mon2;
													if(mstatus.equals("Student")&& mon>3)
														fine=(mon-3)*30*2;
													else if(mstatus.equals("Staff")&& mon>6)
														fine=(mon-6)*30*2;
												}												
												try
												{									
													if(count>0)
													{
														try
														{
															if(issue_memid==Integer.parseInt(memid.getText()))
															{
																String ret;
																rs2.updateString("rdate",date.getText());
																rs2.updateInt("fine",fine);
																rs2.updateRow();
																rs1.updateString("status","Available");
																rs1.updateRow();
																rs.updateInt("icount",count-1);
																rs.updateRow();
																ret="Book Returned Successfully"+"\n"+" Fine= "+String.valueOf(fine);
																JOptionPane.showMessageDialog(tabbedpane.this,ret);
															}
															else
																throw new Exception();
														}
														catch(Exception Ec)
														{Ec.printStackTrace();
															JOptionPane.showMessageDialog(tabbedpane.this,"Wrong Entry of Membership ID!!!!Book Issued To Different Member");
														}														
													}
													else
														throw new Exception();		
												}																							
												catch(Exception s)
												{
													JOptionPane.showMessageDialog(tabbedpane.this,"Memeber ID does not Exist in the Issued Table"); 
												}													
											}	
										}
										catch(Exception e)
										{e.printStackTrace();
											JOptionPane.showMessageDialog(tabbedpane.this,"Fill in the Date Field Properly");
										}
									}
									catch(Exception t)
									{t.printStackTrace();
										JOptionPane.showMessageDialog(tabbedpane.this,"Book Returned Already!!!");	
									}
								}
								catch(Exception ex)
								{
									JOptionPane.showMessageDialog(tabbedpane.this,"Book does not exist");									
								}

							}
							else
								throw new Exception();
						}
						catch(Exception e)
						{
							JOptionPane.showMessageDialog(tabbedpane.this,"Member does not exist");
						}
					}
					catch(Exception E){}
					clear_fields();
				}
			});	

			clear.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent A)
				{
					clear_fields();
				}
			});				
			add(panel);
		}
		
		void clear_fields()
		{
			memid.setText("");
			accno.setText("");
			date.setText("");
		}
	}
	class list extends JPanel
	{
		final JPanel panel,ipanel;
		final JLabel memid,imemid,temp,temp1,bname,baccno,bauthor,idate,rdate,mname,fine,lbname,lbaccno,lbauthor,lidate,lrdate,lmname,lfine,lmemid,temp2,temp3,temp4,temp5;
		final JTextField tmemid;
		final JButton ok,clear,next,back,lclear;
		ResultSet rs1,rs2;
		Statement st1,st2;
		list()
		{

			panel=new JPanel();
			ipanel=new JPanel();
			panel.setLayout(new GridLayout(3,2));
			ipanel.setLayout(new GridLayout(12,2));

			memid=new JLabel("Member ID:");
			mname=new JLabel("Member Name:");
			bname=new JLabel("Book Name:");
			baccno=new JLabel("Book Access No:");
			bauthor=new JLabel("Book Author:");
			idate=new JLabel("Issued Date:");
			rdate=new JLabel("Returned Date");
			fine=new JLabel("Fine Amount:");
			imemid=new JLabel();
			lmemid=new JLabel();
			lmname=new JLabel();
			lbname=new JLabel();
			lbaccno=new JLabel();
			lbauthor=new JLabel();
			lidate=new JLabel();
			lrdate=new JLabel();
			lfine=new JLabel();			
			temp=new JLabel();
			temp1=new JLabel();
			temp2=new JLabel();
			temp3=new JLabel();
			temp4=new JLabel();
			temp5=new JLabel();
			

			tmemid=new JTextField(10);

			ok=new JButton("OK");
			clear=new JButton("CLEAR");
			lclear=new JButton("CLEAR");
			next=new JButton("NEXT");
			back=new JButton("PREVIOUS");

			temp.setVisible(false);
			temp1.setVisible(false);

			panel.add(memid);
			panel.add(tmemid);
			panel.add(temp);
			panel.add(temp1);
			panel.add(ok);
			panel.add(clear);

			ipanel.add(imemid);
			ipanel.add(lmemid);
			ipanel.add(mname);
			ipanel.add(lmname);
			ipanel.add(bname);
			ipanel.add(lbname);
			ipanel.add(baccno);
			ipanel.add(lbaccno);
			ipanel.add(bauthor);
			ipanel.add(lbauthor);
			ipanel.add(idate);
			ipanel.add(lidate);
			ipanel.add(rdate);
			ipanel.add(lrdate);
			ipanel.add(fine);
			ipanel.add(lfine);
			ipanel.add(temp2);
			ipanel.add(temp3);
			ipanel.add(next);
			ipanel.add(back);			
			ipanel.add(temp4);
			ipanel.add(temp5);
			ipanel.add(lclear);
			
			ipanel.setVisible(false);

			ok.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent E)
				{
					ipanel.setVisible(true);
					panel.setVisible(false);
					try
					{
						st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
						st1=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
						st2=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
						rs=st.executeQuery("select * from member_details where mid="+Integer.parseInt(tmemid.getText()));
						rs.first();
						try
						{
							rs1=st1.executeQuery("select * from issue_details where memid="+Integer.parseInt(tmemid.getText()));
							rs1.first();
							rs2=st2.executeQuery("select * from book_details where access_no="+rs1.getInt("access_no"));
							rs2.first();
							lmname.setText(rs.getString("mname"));
							lbname.setText(rs2.getString("title"));
							lbaccno.setText(rs2.getString("access_no"));
							lbauthor.setText(rs2.getString("author"));
							lidate.setText(rs1.getString("idate"));
							lrdate.setText(rs1.getString("rdate"));
							lfine.setText(rs1.getString("fine"));							
						}
						catch(Exception E1)
						{
							JOptionPane.showMessageDialog(tabbedpane.this,"Member not yet taken any books");

						}
						
					}
					catch(Exception E2)
					{
						JOptionPane.showMessageDialog(tabbedpane.this,"Member Does Not Exist!!!!!!!");
					}	
					
				}

			});
		
			next.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent A)
				{
					try
					{
						rs1.next();
						rs2=st2.executeQuery("select * from book_details where access_no="+rs1.getInt("access_no"));
						rs2.first();
						lmname.setText(rs.getString("mname"));
						lbname.setText(rs2.getString("title"));
						lbaccno.setText(rs2.getString("access_no"));
						lbauthor.setText(rs2.getString("author"));
						lidate.setText(rs1.getString("idate"));
						lrdate.setText(rs1.getString("rdate"));
						lfine.setText(rs1.getString("fine"));								
					}
					catch(Exception E)
					{
						JOptionPane.showMessageDialog(tabbedpane.this,"Currently in Last Record No more Next Record For the given Member");		
					}
				}
			});

			back.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent A)
				{
					try
					{
						rs1.previous();
						rs2=st2.executeQuery("select * from book_details where access_no="+rs1.getInt("access_no"));
						rs2.first();
						lmname.setText(rs.getString("mname"));
						lbname.setText(rs2.getString("title"));
						lbaccno.setText(rs2.getString("access_no"));
						lbauthor.setText(rs2.getString("author"));
						lidate.setText(rs1.getString("idate"));
						lrdate.setText(rs1.getString("rdate"));
						lfine.setText(rs1.getString("fine"));								
					}
					catch(Exception E)
					{
						JOptionPane.showMessageDialog(tabbedpane.this,"Currently in First Record No more Previous Record For the given Member");		
					}
				}
			});

			clear.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent E)
				{
					clear_fields();
				}
			});

			lclear.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent E)
				{
					clear_fields();
				}
			});

			add(panel);			
			add(ipanel);
			
		}
		
		void clear_fields()
		{
			ipanel.setVisible(false);
			panel.setVisible(true);
			tmemid.setText("");
		}
	}	

}


public class opac
{
	public static void main(String args[])
	{
		tabbedpane tp=new tabbedpane();
		tp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tp.setVisible(true);		
	}
}

		
		
