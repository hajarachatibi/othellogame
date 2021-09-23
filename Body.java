package othellogame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;

//Le body du jeu contenant le board 
public class Body extends JPanel implements MouseListener{
    ArrayList<Rectangle> grid;
    private OthelloPosition tp;
    ArrayList<Integer> ai=new ArrayList<Integer>();
    int picked=-1;
    JButton btnNewButton_2;
    public Body()
    {
        super(null);
        this.setSize(600, 400);
         grid = new ArrayList<Rectangle>();
	      for( int y=0; y < 8; ++y ) {
	            for( int x=3; x <11; ++x ) {
	                Rectangle rect = new Rectangle( x * 40, y * 40, 40, 40 );
	                grid.add( rect );
	            }
	        }
                btnNewButton_2 = new JButton("Nouvelle Partie");
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setBackground(new Color(111, 34, 50));
		btnNewButton_2.setBounds(230, 350, 112, 28);
		add(btnNewButton_2);
        this.setVisible(true);
        this.repaint();
    }
    public void setai(ArrayList<Integer> a)
    {
    ai=a;
    }
    public void setposition(OthelloPosition p)
    {
   this.tp=p;
    }
     public OthelloPosition getposition()
    {
   return tp;
    }
     @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent( g );
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,    RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(65, 179, 163));
        for( Rectangle r : grid ) {
            g2d.fillRect(r.x, r.y, r.width, r.height);
        }
    if(tp!=null)
        {
        for(int i=0;i<64;i++ ) {
            Rectangle r=grid.get(i);
            int a=this.tp.board[i];
            if(a==1) {
                g2d.setColor(Color.BLACK);
                g2d.fillOval(r.x+3, r.y+3, r.width-3, r.height-3);
            }
            else if(a==-1) {
                g2d.setColor(Color.WHITE);
                g2d.fillOval(r.x+3, r.y+3, r.width-6, r.height-6);
            }
            else if(ai.contains(i)) {
                g2d.setColor(Color.GRAY);
                g2d.drawOval(r.x+3, r.y+3, r.width-6, r.height-6);
            }
        }
    }
    g2d.setColor(Color.BLACK);
    for( Rectangle r : grid ) {
        g2d.drawRect(r.x, r.y, r.width, r.height);
    }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(int i=0;i<64;i++)
        {
            if(e.getX()>=grid.get(i).getX() && e.getX()<=grid.get(i).getX()+40 && e.getY()-160>=grid.get(i).getY() && e.getY()-160<=grid.get(i).getY()+40 )
            {
                picked=i;
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

   
}