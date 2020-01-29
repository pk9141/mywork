import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.awt.image.*;
import javax.swing.border.*;


class myframe extends JFrame
{
	JButton j[];
	JPanel drawpanel;
	final JCheckBox fill,fill1;
	mypanel m;
	ImageIcon icon;
	BufferedImage image;
	myframe()
	{
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension screen=kit.getScreenSize();
		setTitle("PAINT");
		setSize(screen.width,screen.height);
		setLocationByPlatform(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setIconImage(kit.getImage("C:/java/images/images.jpg"));
		
		JMenuBar jmb=new JMenuBar();
		setJMenuBar(jmb);

		JMenu file=new JMenu("File");
		JMenu edit=new JMenu("Edit");		
		JMenu view=new JMenu("View");		
		JMenu options=new JMenu("Options");		
		JMenu imag=new JMenu("Image");		
		JMenu help=new JMenu("Help");		

		jmb.add(file);
		jmb.add(edit);
		jmb.add(view);
		jmb.add(imag);
		jmb.add(options);
		jmb.add(help);

		JButton[] c=new JButton[20];
		j=new JButton[8];
		drawpanel=new JPanel();

		GridBagLayout gbl=new GridBagLayout();

		m=new mypanel();
			
		drawpanel.setLayout(gbl);
		drawpanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));


		
		icon=new ImageIcon("C:/java/images/pick_line_32x32.png");
		drawpanel.add(icon(j[0],"l",icon,"Used to Draw Line"),gbc(0,0));
		icon=new ImageIcon("C:/java/images/pencil.png");
		drawpanel.add(icon(j[1],"f",icon,"Free Hand Drawing"),gbc(1,0));
		icon=new ImageIcon("C:/java/images/Rectangle.png");
		drawpanel.add(icon(j[2],"r",icon,"Used to Draw a Rectangle"),gbc(0,1));
		icon=new ImageIcon("C:/java/images/Rounded rectangle.png");
		drawpanel.add(icon(j[3],"rr",icon,"Used to Draw a Rounded Rectangle"),gbc(1,1));
		icon=new ImageIcon("C:/java/images/Ellipse.png");
		drawpanel.add(icon(j[4],"e",icon,"Used to Draw a Ellipse"),gbc(0,2));
		icon=new ImageIcon("C:/java/images/circle.png");
		drawpanel.add(icon(j[5],"c",icon,"Used to Draw a Circle"),gbc(1,2));
		icon=new ImageIcon("C:/java/images/fill.png");
		drawpanel.add(icon(j[6],"bg",icon,"Used to Fill Background Color"),gbc(0,3));
		icon=new ImageIcon("C:/java/images/Rectangle.png");
		drawpanel.add(icon(j[7],"fg",icon,"Used to Draw a Rectangle"),gbc(1,3));

		JPanel fillp=new JPanel();
		fillp.setLayout(new GridBagLayout());	
		
		fill=new JCheckBox("ForeGround Color");
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
		fillp.add(fill,gbc);
		fill1=new JCheckBox("BackGround Color");
		gbc.gridx=0;
		gbc.gridy=1;
		fillp.add(fill1,gbc);
		

		
		JPanel cpanel=new JPanel();
		cpanel.setLayout(new GridBagLayout());

		
		cpanel.add(colorpalette(14,88,3,c[0],"0"),gbc(0,0));
		cpanel.add(colorpalette(21,131,5,c[1],"1"),gbc(1,0));
		cpanel.add(colorpalette(28,171,7,c[2],"2"),gbc(2,0));
		cpanel.add(colorpalette(37,226,10,c[3],"3"),gbc(3,0));
		cpanel.add(colorpalette(116,121,17,c[4],"4"),gbc(4,0));
		cpanel.add(colorpalette(154,160,22,c[5],"5"),gbc(5,0));
		cpanel.add(colorpalette(189,198,28,c[6],"6"),gbc(6,0));
		cpanel.add(colorpalette(221,230,66,c[7],"7"),gbc(7,0));
		cpanel.add(colorpalette(177,23,1,c[8],"8"),gbc(8,0));
		cpanel.add(colorpalette(248,32,1,c[9],"9"),gbc(9,0));
		cpanel.add(colorpalette(254,65,37,c[10],"10"),gbc(0,1));
		cpanel.add(colorpalette(254,111,90,c[11],"11"),gbc(1,1));
		cpanel.add(colorpalette(27,128,133,c[12],"12"),gbc(2,1));
		cpanel.add(colorpalette(37,179,186,c[13],"13"),gbc(3,1));
		cpanel.add(colorpalette(66,209,217,c[14],"14"),gbc(4,1));
		cpanel.add(colorpalette(126,223,228,c[15],"15"),gbc(5,1));
		cpanel.add(colorpalette(165,33,89,c[16],"16"),gbc(6,1));
		cpanel.add(colorpalette(209,41,113,c[17],"17"),gbc(7,1));
		cpanel.add(colorpalette(223,94,149,c[18],"18"),gbc(8,1));
		cpanel.add(colorpalette(228,124,169,c[19],"19"),gbc(9,1));
		
		JPanel main=new JPanel();
		main.setLayout(new GridBagLayout());
		main.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		gbc.gridx=0;
		gbc.gridy=0;
		main.add(fillp,gbc);
		gbc.gridx=2;
		gbc.gridy=0;
		main.add(cpanel,gbc);
		

		add(drawpanel,BorderLayout.WEST);
		add(main,BorderLayout.SOUTH);
		add(m);

		image=new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_RGB); 			
		Graphics2D imageG = image.createGraphics(); 
		imageG.fillRect(0, 0, this.getWidth(),this.getHeight()); 
		imageG.dispose(); 


	}
	public JButton icon(JButton j,String s,ImageIcon icon,String s1)
	{
		j=new JButton(" ");
		j.setActionCommand(s);
		j.addActionListener(m);
		j.setPreferredSize(new Dimension(42,42));
		j.setToolTipText(s1);
		j.setIcon(icon);
		return j;
	}
	public JButton colorpalette(int r,int g,int b,JButton j,String s)
	{
		final Color c=new Color(r,g,b);
		j=new JButton(" ");
		j.setActionCommand(s);
		j.setBackground(c);
		j.setForeground(c);
		j.setMaximumSize(new Dimension(3,3));
		j.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent a)
			{
				m.c=c;
			}
		});
		return j;
	}
	public GridBagConstraints gbc(int x,int y)
	{
		
		GridBagConstraints g=new GridBagConstraints();
		g.gridx=x;
		g.gridy=y;
		return g;
	}
	

class mypanel extends JPanel implements ActionListener
{
	public Color c;
	public int shape;
	double oldx,oldy,newx,newy,x,y,x1,y1;
	ArrayList al;
	
	mypanel()
	{
		al=new ArrayList(100);
				
	}		

	public void paintComponent(Graphics G)
	{
	
		super.paintComponent(G);
		Graphics2D g=(Graphics2D)G;
		draw();	
		g.drawImage(image,0,0,null);
	}	
	public void draw()
	{
		Graphics2D g=image.createGraphics();
		
		if(fill.isSelected()||fill1.isSelected())
			g.setColor(c);		
		else 
			g.setColor(Color.BLACK);
		
		if(shape==1)
		{
			Line2D.Double line=new Line2D.Double(oldx,oldy,newx,newy);
			g.draw(line);
			al.add(line);

		}
		else if(shape==2)
		{
			Line2D.Double point=new Line2D.Double(x,y,newx,newy);
			g.draw(point);
			al.add(point);
		}
		else if(shape==3)
		{
			Rectangle2D.Double rec=new Rectangle2D.Double();
			rec.setFrameFromDiagonal(oldx,oldy,newx,newy);
			g.draw(rec);
			al.add(rec);
			if(fill1.isSelected())
				g.fill(rec);	
		}
		else if(shape==4)
		{
			RoundRectangle2D.Double rrec=new RoundRectangle2D.Double();
			rrec.arcwidth=15.0;
			rrec.archeight=15.0;	
			rrec.setFrameFromDiagonal(oldx,oldy,newx,newy);
			g.draw(rrec);
			al.add(rrec);
			if(fill1.isSelected())
				g.fill(rrec);

		}
		else if(shape==5)
		{
			Rectangle2D.Double rec=new Rectangle2D.Double();
			Ellipse2D.Double ell=new Ellipse2D.Double();
			rec.setFrameFromDiagonal(oldx,oldy,newx,newy);
			ell.setFrame(rec);
			g.draw(ell);			
			al.add(ell);
			if(fill1.isSelected())
				g.fill(ell);	
		}
		else if(shape==6)
		{			
			double centerx,centery;
			Rectangle2D.Double rec=new Rectangle2D.Double();
			Ellipse2D.Double circle=new Ellipse2D.Double();
			rec.setFrameFromDiagonal(oldx,oldy,newx,newy);			
			circle.setFrame(rec);
			g.draw(circle);	
			al.add(circle);
			if(fill1.isSelected())
				g.fill(circle);	
		}
		else if(shape==7)
		{
			
			Rectangle2D.Double rec=new Rectangle2D.Double();
			Ellipse2D.Double ell=new Ellipse2D.Double();
			rec.setFrameFromDiagonal(oldx,oldy,newx,newy);
			ell.setFrame(rec);
			g.draw(ell);
		}
		else if(shape==8)
		{
			if(fill1.isSelected())
			{
				Object shape;
				for(int i=0;i<al.size();i++)
				{
					shape=al.get(i);
					if(shape instanceof Rectangle2D.Double)
					{
						Rectangle2D.Double rec=(Rectangle2D.Double)shape;
						if(rec.contains(oldx,oldy))
						{
							g.setColor(c);
							g.fill(rec);
						}
					}
					else if(shape instanceof RoundRectangle2D.Double)
					{
						RoundRectangle2D.Double rrec=(RoundRectangle2D.Double)shape;
						if(rrec.contains(oldx,oldy))
						{
							g.setColor(c);
							g.fill(rrec);							
						}

					}
					else if(shape instanceof Ellipse2D.Double)
					{
						Ellipse2D.Double ell=(Ellipse2D.Double)shape;
						if(ell.contains(oldx,oldy))
						{
							g.setColor(c);
							g.fill(ell);							
						}
					}
				}
			}

		}
		g.dispose();
	
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("l"))
		{
			shape=1;
			addMouseListener(new event());
		}
		else if(e.getActionCommand().equals("f"))
		{
			shape=2;
			addMouseListener(new event());
			addMouseMotionListener(new event());
		}
		else if(e.getActionCommand().equals("r"))
		{
			shape=3;
			addMouseListener(new event());
		}
		else if(e.getActionCommand().equals("rr"))
		{
			shape=4;
			addMouseListener(new event());
		}
		else if(e.getActionCommand().equals("e"))
		{
			shape=5;
			addMouseListener(new event());
		}
		else if(e.getActionCommand().equals("c"))
		{
			shape=6;
			addMouseListener(new event());
		}
		else if(e.getActionCommand().equals("fg"))
		{
			shape=7;
			addMouseListener(new event());
		}
		else if(e.getActionCommand().equals("bg"))
		{
			shape=8;
			addMouseListener(new event());
		}
		else if(e.getActionCommand().equals("rr"))
		{
			shape=4;
			addMouseListener(new event());
		}

	}
	class event extends MouseAdapter implements MouseMotionListener		
	{
		public void mousePressed(MouseEvent E)
		{
			oldx=newx=E.getX();
			oldy=newy=E.getY();
			
		}
		public void mouseReleased(MouseEvent e)
		{					
			newx=e.getX();
			newy=e.getY();
			repaint();			
		}		
		public void mouseDragged(MouseEvent e1)
		{
			x=newx;
			y=newy;
			newx=e1.getX();
			newy=e1.getY();
			repaint();	
		}
		public void mouseMoved(MouseEvent e2){ }
	}

	
}
}

public class paint
{
	public static void main(String args[])
	{
		myframe m=new myframe();		
	}
}