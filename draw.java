import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

class mouse extends JFrame
{
	public mouse()
	{
		setTitle("Free Hand Drawing");
		setVisible(true);
		setSize(400,400);
		setResizable(true);
		setLocation(250,250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawing d=new drawing();
		add(d);
	}

class drawing extends JPanel
{
	int oldx,oldy,x,y;
	public drawing()
	{
		addMouseListener(new event());
		addMouseMotionListener(new event());
	}
	
	public void paintComponent(Graphics g)
	{				
		g.drawLine(oldx,oldy,x,y);
	}

	class event extends MouseAdapter implements MouseMotionListener
	{
		public void mousePressed(MouseEvent e)
		{
			x=e.getX();
			y=e.getY();
		}

		public void mouseEntered(MouseEvent E1)
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	
		public void mouseDragged(MouseEvent E)
		{			
				oldx=x;
				oldy=y;
				x=E.getX();
				y=E.getY();
				repaint();
		}
	
		public void mouseMoved(){}
	}
}
}

public class draw
{
	public static void main(String args[])
	{
		mouse m=new mouse();
	}
}
