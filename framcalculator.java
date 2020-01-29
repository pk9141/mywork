import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.lang.*;
import java.lang.Object;
import java.lang.Math.*;
import java.util.*;

class calcframe extends JFrame
{
	boolean start;
	
	public calcframe()
	{
		setSize(600,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		//this.getContentPane().setBackground(Color.GRAY);
		//setBackground(Color.GRAY);
		//repaint();
		setTitle("SCIENTIFIC CALCULATOR");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		setLocation(screenWidth / 4, screenHeight / 4);
		add(new calcpanel());
		pack();
		start=true;

	}



	class calcpanel extends JPanel
	{
		private JPanel panel1,panelmain;
		public Box hbox;
		public JPanel panel2,panel3;
		public JRadioButtonMenuItem mr_basic,mr_scientific;
		public JRadioButton bin_rbutton,dec_rbutton,rad_rbutton,deg_rbutton;
		displaylistener listen = new displaylistener();
		computelistener compute = new computelistener();
		invlistener inv = new invlistener();
		typelistener type = new typelistener();
		combolistener item= new combolistener();
		//formatlistener format=new formatlistener();
		public JTextArea text;
		public JButton[] b=new JButton[32];
		public JCheckBox inv_check;
		public JComboBox combo;
		
		public calcpanel()
		{	
			try
			{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			for(int i=0;i<32;i++)
			{
				b[i]=new JButton();
			}
			
			setLayout(new BorderLayout(5,5));
			
			JMenuBar menubar=new JMenuBar();
			//setJMenuBar(menubar);
			JMenu m_file=new JMenu("File");
			m_file.setMnemonic('F');
			JMenuItem mi_quit=new JMenuItem("Quit",'Q');
			mi_quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
			m_file.add(mi_quit);
			mi_quit.addActionListener(new ActionListener(){
			    public void actionPerformed(ActionEvent e)
			     {
				 System.exit(0);
			     }
			  });

			JMenu m_view=new JMenu("View");
			m_view.setMnemonic('V');
			mr_basic=new JRadioButtonMenuItem("Basic");
			//mr_basic.setAccelerator(KeyStroke.getKeyStroke("ctrl b"));
			mr_basic.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));

			mr_scientific=new JRadioButtonMenuItem("Scientific");
			//mr_scientific.setAccelerator(KeyStroke.getKeyStroke("ctrl s"));
			mr_scientific.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
			mr_basic.addActionListener(new viewlistener());
			mr_scientific.addActionListener(new viewlistener());
			mr_scientific.setSelected(true);
			ButtonGroup group=new ButtonGroup();
			group.add(mr_basic);
			group.add(mr_scientific);
			m_view.add(mr_basic);
			m_view.add(mr_scientific);
			JMenu m_help=new JMenu("Help");
			m_help.setMnemonic('H');
			JMenuItem mi_about=new JMenuItem("About",'A');
			mi_about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{
				JOptionPane.showMessageDialog(null,"JCalc ver 2.34.1\nScientific and Basic modes\n© 2008-2009 The Jcalc \nDeveloped By Dark Knight \n","About !!",JOptionPane.INFORMATION_MESSAGE);
					
			}});
			m_help.add(mi_about);
			menubar.add(m_file);
			menubar.add(m_view);
			menubar.add(m_help);
			//menubar.setBackground(new Color(190,190,190));

			//setBackground(Color.GRAY);
			//setBackground(new Color(132,147,205));

			panel1=new JPanel();
			panel1.setLayout(new GridLayout(4,4,5,5));
			text= new JTextArea("0.0",2,8);
			panelmain=new JPanel();
			//panelmain.setBackground(new Color(190,190,190));
			panelmain.setLayout(new BorderLayout(5,5));
			//text.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			panelmain.add(menubar,BorderLayout.NORTH);
			panelmain.add(text,BorderLayout.CENTER);
			text.setEditable(false);
			add(panelmain,BorderLayout.NORTH);
			//text.setHorizontalAlignment(JTextField.RIGHT);
			text.setFont(new Font("SansSerif",Font.PLAIN,16));
			Box vbox=Box.createVerticalBox();
			hbox=Box.createHorizontalBox();
			inv_check=new JCheckBox("INV");
			inv_check.addActionListener(inv);
			deg_rbutton=new JRadioButton("Degrees");
			//deg_rbutton.addActionListener(format);
			deg_rbutton.setSelected(true);
			deg_rbutton.setFocusPainted(false);
			rad_rbutton=new JRadioButton("Radians");
			//rad_rbutton.addActionListener(format);
			rad_rbutton.setFocusPainted(false);
			ButtonGroup groupformat=new ButtonGroup();
			groupformat.add(deg_rbutton);
			groupformat.add(rad_rbutton);
			//inv_check.setBackground(new Color(190,190,190));
			ButtonGroup grouptype=new ButtonGroup();
			bin_rbutton=new JRadioButton("Binary");
			bin_rbutton.setFocusPainted(false);
			//bin_rbutton.setBackground(new Color(190,190,190));
			bin_rbutton.addActionListener(type);
			grouptype.add(bin_rbutton);
			dec_rbutton=new JRadioButton("Decimal");
			dec_rbutton.setSelected(true);
			//dec_rbutton.setBackground(new Color(190,190,190));
			dec_rbutton.addActionListener(type);
			dec_rbutton.setFocusPainted(false);
			grouptype.add(dec_rbutton);
			
			vbox.add(Box.createVerticalStrut(10));
			hbox.add(Box.createHorizontalStrut(10));
			hbox.add(inv_check);
			hbox.add(deg_rbutton);
			hbox.add(rad_rbutton);
			hbox.add(Box.createGlue());
			hbox.add(Box.createHorizontalStrut(10));
			hbox.add(dec_rbutton);
			hbox.add(Box.createHorizontalStrut(5));
			hbox.add(bin_rbutton);
			hbox.add(Box.createHorizontalStrut(10));
			vbox.add(hbox);
			vbox.add(Box.createVerticalStrut(10));
			panel3=new JPanel();
			panel3.setLayout(new GridLayout(2,3,5,5));
			addButton(b[0],"Sin",panel3,"sin(",listen);
			addButton(b[1],"Cos",panel3,"cos(",listen);
			addButton(b[2],"Tan",panel3,"tan(",listen);
			addButton(b[3],"Log",panel3,"log(",listen);
			b[3].setToolTipText("Log value to the base 10");
			addButton(b[4],"Ln",panel3,"ln(",listen);
			b[4].setToolTipText("Log value to the base e");
			String consts[]={"e","pi"};
			combo=new JComboBox(consts);
			combo.addActionListener(item);
		    	
		    	panel3.add(combo);
			JButton b_clr=new JButton("Clr");
			b_clr.setToolTipText("Clear ");
			b_clr.addActionListener(compute);
			b_clr.setFocusPainted(false);
			JButton b_bksp=new JButton("BKSP");
			b_bksp.setToolTipText("Backspace");
			b_bksp.addActionListener(compute);
			b_bksp.setFocusPainted(false);
			Box hbox1=Box.createHorizontalBox();
			hbox1.add(Box.createHorizontalStrut(10));
			hbox1.add(panel3);
			hbox1.add(Box.createGlue());
			hbox1.add(b_clr);
			hbox1.add(Box.createHorizontalStrut(10));
			hbox1.add(b_bksp);
			hbox1.add(Box.createHorizontalStrut(10));
			vbox.add(hbox1);
			vbox.add(Box.createVerticalStrut(20));

			Box hbox2=Box.createHorizontalBox();
			hbox2.add(Box.createHorizontalStrut(10));
			addButton(b[5],"7",panel1,"7",listen);
			addButton(b[6],"8",panel1,"8",listen);
			addButton(b[7],"9",panel1,"9",listen);
			addButton(b[13],"/",panel1,"/",listen);

			addButton(b[8],"4",panel1,"4",listen);
			addButton(b[9],"5",panel1,"5",listen);
			addButton(b[10],"6",panel1,"6",listen);
			addButton(b[14],"*",panel1,"*",listen);

			addButton(b[15],"1",panel1,"1",listen);
			addButton(b[11],"2",panel1,"2",listen);
			addButton(b[12],"3",panel1,"3",listen);
			addButton(b[16],"-",panel1,"-",listen);

			addButton(b[17],"0",panel1,"0",listen);
			addButton(b[18],".",panel1,".",listen);
			addButton(b[19],"=",panel1,"=",compute);
			addButton(b[20],"+",panel1,"+",listen);

			//panelmain.add(panel1,BorderLayout.WEST);
			hbox2.add(panel1);
			hbox2.add(Box.createHorizontalStrut(10));
			panel2=new JPanel();
			panel2.setLayout(new GridLayout(4,3,5,5));
			addButton(b[21],"(",panel2,"(",listen);
			addButton(b[22],"e^x",panel2,"e^",listen);
			addButton(b[23],"%",panel2,"%",listen);
			b[23].setToolTipText("Percentage ");
			addButton(b[24],")",panel2,")",listen);
			addButton(b[25],"10^x",panel2,"10^",listen);
			addButton(b[26],"1/x",panel2,"1/(",listen);
			b[26].setToolTipText("Inverse");
			addButton(b[27],"x^2",panel2,"^2",listen);
			addButton(b[28],"x^y",panel2,"^",listen);
			addButton(b[29],"Sqrt",panel2,"sqrt(",listen);
			b[29].setToolTipText("Square root ");
			addButton(b[30],"Abs",panel2,"abs(",listen);
			b[30].setToolTipText("Absolute value");
			hbox2.add(panel2);
			hbox2.add(Box.createHorizontalStrut(10));

			vbox.add(hbox2);
			vbox.add(Box.createVerticalStrut(10));
			//add(vbox,BorderLayout.CENTER);
			JPanel panel4=new JPanel();
			panel4.add(vbox);
			add(panel4,BorderLayout.CENTER);
			//vbox.setBackground(new Color(200,190,190));
			//add(hbox2,BorderLayout.SOUTH);
			//panelmain.add(panel2,BorderLayout.EAST);
			//add(panelmain,BorderLayout.SOUTH);
			//panel1.setBackground(Color.GRAY);
			//panel1.setBackground(new Color(190,190,190));
			//panel2.setBackground(Color.GRAY);
			//panel2.setBackground(new Color(190,190,190));
			//panel3.setBackground(Color.GRAY);
			//panel3.setBackground(new Color(190,190,190));
			//panelmain.setBackground(new Color(190,190,190));
			//panelmain.setBackground(Color.GRAY);
			
			/*panel2.setVisible(false);
			panel3.setVisible(false);
			hbox.setVisible(false);*/

		}
		catch(Exception unused)
		{
		
		}
		}


		public void addButton(JButton button,String label,JPanel panel,String cmd,ActionListener listen1)
		{
			//button=new JButton(label);
			button.setText(label);
			button.setFont(new Font("SansSerif",Font.PLAIN,14));
			button.setActionCommand(cmd);
			button.setPreferredSize(new Dimension(50,40));
			button.setFocusPainted(false);
			//button.setBackground(new Color(238,238,224)); 
			button.addActionListener(listen1);
			panel.add(button);

		}
		
		class combolistener implements ActionListener
		{
		      	public void actionPerformed(ActionEvent ie)
		      	{
				String str = (String)combo.getSelectedItem();
				if(start)
				{
					text.setText("");
					start=false;
				}
				if(str.equals("e"))
				text.setText(text.getText()+"2.72");
				else
				text.setText(text.getText()+"3.14");
		      	}
		 }
		    

		class viewlistener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				if(mr_basic.isSelected())
				{
					panel2.setVisible(false);
					panel3.setVisible(false);
					hbox.setVisible(false);
					setTitle("JCALC");
					pack();
				}
				else 
				{
					panel2.setVisible(true);
					panel3.setVisible(true);
					hbox.setVisible(true);
					setTitle("JCALC - Scientific");
					pack();
				}
			}
	
		}
		
		/*class formatlistener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				String str=text.getText();
				if(deg_rbutton.isSelected())
				{
					double val=Double.parseDouble(str);
					val=Math.toDegrees(val);
					text.setText(Double.toString(val));
				}
				else
				{
					double val=Double.parseDouble(str);
					val=Math.toRadians(val);
					text.setText(Double.toString(val));
				}
			}
		}*/
		
		class displaylistener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				String disp=event.getActionCommand();
				if(start)
				{
					text.setText("");
					start=false;
				}
				if(disp.equals("1/("))
					text.setText("1/("+text.getText()+")");
				else 
					text.setText(text.getText()+disp);
			}
		
		}
		
		class invlistener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				if(inv_check.isSelected())
				{
					b[0].setText("ASin");
					b[1].setText("ACos");	
					b[2].setText("ATan");		
					b[0].setActionCommand("asin(");
					b[1].setActionCommand("acos(");
					b[2].setActionCommand("atan(");
				}
				else
				{
					b[0].setText("Sin");
					b[1].setText("Cos");	
					b[2].setText("Tan");		
					b[0].setActionCommand("sin(");
					b[1].setActionCommand("cos(");
					b[2].setActionCommand("tan(");
				}
			
			}
		}
		
		class typelistener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				if(bin_rbutton.isSelected())
				{
					inv_check.setEnabled(false);
					combo.setEnabled(false);
					deg_rbutton.setEnabled(false);
					rad_rbutton.setEnabled(false);	
					for(int i=0;i<=12;i++)
					{
						b[i].setEnabled(false);
					}
					b[22].setEnabled(false);
					b[23].setEnabled(false);
					b[25].setEnabled(false);
					b[26].setEnabled(false);
					b[27].setEnabled(false);
					b[30].setEnabled(false);
					b[18].setEnabled(false);
					b[24].setEnabled(false);
					b[21].setEnabled(false);
					double d1=Double.parseDouble(text.getText());
					long dec=(long)d1;
					String bin=Long.toBinaryString(dec);
					
					text.setText(bin);
					
				}
				else
				{
					for(int i=0;i<=12;i++)
					{
						b[i].setEnabled(true);
					}
					b[22].setEnabled(true);
					b[23].setEnabled(true);
					b[25].setEnabled(true);
					b[26].setEnabled(true);
					b[27].setEnabled(true);
					b[30].setEnabled(true);
					b[18].setEnabled(true);
					b[24].setEnabled(true);
					b[21].setEnabled(true);
					double d2=Double.parseDouble(text.getText());
					long i1=(long)d2;
					long dec= Long.parseLong(Long.toString(i1),2);
					
					//long dec= Long.parseLong(text.getText(),2);
					//System.out.println(dec);
					text.setText(Long.toString(dec));
					combo.setEnabled(true);
					inv_check.setEnabled(true);
					deg_rbutton.setEnabled(true);
					rad_rbutton.setEnabled(true);	

				}
			}
		}
		
		class computelistener implements ActionListener
		{
		
			String[] op=new String[10];
			String[] val=new String[10];
			
			public void actionPerformed(ActionEvent event)
			{
				String disp=event.getActionCommand();
				if(disp.equals("Clr"))
				{
					text.setText("0.0");
					start=true;
				}
				else if(disp.equals("BKSP"))
				{
					String ctext=text.getText();
					text.setText(ctext.substring(0,ctext.length()-1));
					if(text.getText().length()==0)
					{
					start=true;
					text.setText("0.0");
					
					}
					
				}
				else if(disp.equals("="))
				{
					//expressionevaluate(text.getText());
					this.evaluate(text.getText());
				}
			}
			
			public void evaluate(String exp)
			{
				char[] a=new char[40];
				a=exp.toCharArray();
				//double val3,val4;
				int priority,flag=0,unary=0;
				int valcount=0;
				int opcount=0,k=0,temp=0;
				try
				{
				for(int i=0;i<10;i++)
				{
					//if(i<6)
					op[i]=new String();
					val[i]=new String();
				}
				for(int i=0;i<exp.length();i++)
				{
					//System.out.println(a[i]);
					if(opcheck(a[i])==5)
					{	
						flag=1;
						val[valcount++]=String.copyValueOf(a,temp,k);
						k=0;
						temp=i+1;
						for(int l=0;l<valcount;l++)
						System.out.println(val[l]);
						for(int m=0;m<opcount;m++)
						System.out.println(op[m]);
						int tempcount=opcount-1;
						int l=tempcount;
						int l2=valcount-1;
						System.out.println("valcount "+valcount+" opcount "+opcount);
						while((op[l].compareTo("(")!=0))
						{	
							/*if(op[l].equals("("))
							{
							opcount--;
							break;
							
							}*/
							System.out.println("val"+val[l2]+" op "+op[l]);
							double val1=Double.parseDouble(val[l2]);
							double val2=Double.parseDouble(val[l2-1]);
						
							double result=calculate(val1,val2,op[l]);
							System.out.println("val1 "+val[l2]+" val2 "+val[l2-1]+" result "+result);
							val[l2-1]=Double.toString(result);
							valcount--;
							opcount--;
							l--;
							//System.out.println(l);
					
						}
						opcount--;
						System.out.println("valcount "+valcount+" opcount "+opcount);
						for(l=0;l<valcount;l++)
						System.out.println(val[l]);
						for(int m=0;m<opcount;m++)
						System.out.println(op[m]);
					
					}
					else if(a[i]=='(')
					{
					String charop=String.copyValueOf(a,temp,k);
					System.out.println("operation "+charop);
					if(charop.length()>0)
					op[opcount++]=charop;
					op[opcount++]=Character.toString(a[i]);
					System.out.println(opcount+" hELLO "+op[opcount-1]);
					temp=i+1;
					k=0;
					}
					else if(a[i]=='%')
					{
						unary=1;
						val[valcount++]=String.copyValueOf(a,temp,k);	
						k=0;
						temp=i+1;
						double val1=Double.parseDouble(val[valcount-1]);
						val1=val1/100;
						val[valcount-1]=Double.toString(val1);
						//valcount--;
					}
					else if(opcheck(a[i])==6)
					{
						k++;
						//valcount++;
					}
					else
					{
						if(flag==1)
							flag=0;
						else
						{
						val[valcount++]=String.copyValueOf(a,temp,k);
						}
						k=0;
						temp=i+1;
						
						priority=opcheck(a[i]);
						if(opcount>1)
						{
							if(priority>=opcheck(op[opcount-1].charAt(0)))
							{
								op[opcount++]=Character.toString(a[i]);
							}
							else
							{
								double val1=Double.parseDouble(val[valcount-1]);
								double val2=Double.parseDouble(val[valcount-2]);
								double result=calculate(val1,val2,op[opcount-1]);
								System.out.println("val1 "+val1+" val2 "+val2+" result "+result+a[i]);
								val[valcount-2]=Double.toString(result);
								valcount--;
								op[opcount-1]=Character.toString(a[i]);
								
							}
						}
						else
						{
						op[opcount++]=Character.toString(a[i]);
						}
						//System.out.println(Character.toString(a[i]));
						//val[0].split(Character.toString(a[i]));
						
					}
					
					
		
				}
				if(flag==0&&unary==0)
				val[valcount++]=String.copyValueOf(a,temp,k);
				for(int l=0;l<valcount;l++)
				System.out.println("sa"+val[l]);
				for(int m=0;m<opcount;m++)
				System.out.println("das"+op[m]);
				System.out.println("valcount : "+valcount+" opcount : "+opcount);
				//if(((valcount-opcount)==1)||unary==1)
				//{
				if(bin_rbutton.isSelected())
				{
					for(int l=0;l<valcount;l++)
					{
						double d2=Double.parseDouble(val[l]);
						long i1=(long)d2;
						long dec= Long.parseLong(Long.toString(i1),2);
						val[l]=Long.toString(dec);
						
					}
				}
				int l1=opcount-1;
				int l2=valcount-1;
				double val1,val2;
				while(l1>=0)
				{	if(val[l2].equals("e"))	
					val1=Math.E;
					else
					val1=Double.parseDouble(val[l2]);
					if(op[l1].length()>1)
					{
					
					
					double result=calculate(val1,90,op[l1]);
					System.out.println("val1 "+val[l2]+"op "+op[l1]+" result "+result);
					val[l2]=Double.toString(result);
					
					}
					else
					{
					if(val[l2-1].equals("e"))	
					val2=Math.E;
					else
					val2=Double.parseDouble(val[l2-1]);
					double result=calculate(val1,val2,op[l1]);
					//System.out.println("val1 "+val[l2]+" val2 "+val[l2-1]+" result "+result);
					val[l2-1]=Double.toString(result);
					//valcount--;
					
					l2--;
					}
					l1--;
						//System.out.println(l);
					
				}
				if(bin_rbutton.isSelected())
				{
					double d1=Double.parseDouble(val[0]);
					long dec=(long)d1;
					String bin=Long.toBinaryString(dec);
					text.setText(bin);
				}
				else
				{	
					
					if(val[0].equals("Infinity"))
						JOptionPane.showMessageDialog(null,"Enter a VALID Expression !!!","ERROR !!!",JOptionPane.ERROR_MESSAGE);
					else
						text.setText(val[0]);
				}
				/*}
				else
				{
					System.out.println("ERROR");
				}*/
				
				//System.out.println(val[valcount-1]);
				}
				catch(Exception generealexc)
				{
				System.out.println("\nERROR\n");
				JOptionPane.showMessageDialog(null,"Enter a VALID Expression !!!","ERROR !!!",JOptionPane.ERROR_MESSAGE);
				}
		
			}
			
			public int opcheck(char op)
			{
						if((op=='+')||(op=='-'))
						return 0;
						else if((op=='*')||(op=='/'))
						return 1;
						else if((op=='+')||(op=='+'))
						return 2;
						else if((op=='^'))
						return 3;
						else if(op=='(')
						return -1;
						else if(op==')')
						return 5;
						else if(op=='%')
						return -2;
						else
						return 6;
			
			}
			
			public Double calculate(double val1,double val2,String op1)
			{
				if(op1.equals("+"))
				return val1+val2;
				else if(op1.equals("-"))
				return val2-val1;
				else if(op1.equals("*"))
				return val1*val2;
				else if(op1.equals("/"))
				return val2/val1;
				else if(op1.equals("^"))
				return Math.pow(val2,val1);
				else if(op1.equals("cos"))
				{
					if(deg_rbutton.isSelected())	
					return Math.cos(Math.toRadians(val1));
					else
					return Math.cos(val1);
				}
				else if(op1.equals("tan"))
				{
					if(deg_rbutton.isSelected())	
					return Math.tan(Math.toRadians(val1));
					else
					return Math.tan(val1);
				}
				else if(op1.equals("sin"))
				{
					if(deg_rbutton.isSelected())	
					return Math.sin(Math.toRadians(val1));
					else
					return Math.sin(val1);
				}
				else if(op1.equals("log"))
				return (Math.log(val1)/Math.log(10));
				else if(op1.equals("ln"))
				return Math.log(val1);
				else if(op1.equals("sqrt"))
				return Math.sqrt(val1);
				else if(op1.equals("abs"))
				return Math.abs(val1);
				else if(op1.equals("asin"))
				return Math.toDegrees(Math.asin(val1));
				else if(op1.equals("acos"))
				return Math.toDegrees(Math.acos(val1));
				else if(op1.equals("atan"))
				return Math.toDegrees(Math.atan(val1));
				return 1.2;
			}
			
			
			
			
		}
		
		
	
	}
}



public class framcalculator
{

	public static void main(String arg[])
	{
		calcframe c1=new calcframe();
	}

}
