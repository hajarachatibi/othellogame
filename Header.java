package othellogame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*Header du JFrame du jeu contenant le nombre de coups de chaque joueur et le timer
  Le bouton d'enregistrement d'une partie
  Le bouton pour annuler un coup
  Le bouton pour la deconnexion
*/
public class Header extends JPanel {
    int a=0;
    int i = 0;
    JLabel jb1;
    JLabel jb2;
    JLabel jb3;
    JLabel jb4;
    JLabel bwon;
    JLabel wwon;
    JButton btnNewButton;
    JButton btnNewButton_1;
    JButton btnNewButton_2 ;
    boolean askingforhelp=false; // pour l'assistance
    public Header()
    {
        super();
        this.setLayout(null);
        jb1=new JLabel();
        jb2=new JLabel();
        jb3=new JLabel("00 : ");
        jb4=new JLabel(" 10");
        this.setSize(600,240);
        btnNewButton = new JButton("Annuler le coup");
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setBackground(new Color(111, 34, 50));
        btnNewButton.setBounds(120, 10, 119, 28);
        add(btnNewButton);
        btnNewButton_1 = new JButton("Enregistrer");
        btnNewButton_1.setForeground(Color.WHITE);
        btnNewButton_1.setBackground(new Color(111, 34, 50));
        btnNewButton_1.setBounds(250, 10, 96, 28);
        add(btnNewButton_1);
        btnNewButton_2 = new JButton("Se déconnecter");
        btnNewButton_2.setForeground(Color.WHITE);
        btnNewButton_2.setBackground(new Color(111, 34, 50));
        btnNewButton_2.setBounds(460, 10, 112, 28);
        add(btnNewButton_2);
        jb1.setBounds(250,110, 18, 16);
        this.add(jb1);
        jb1.setForeground(Color.white);
        jb2.setForeground(Color.white);
        jb2.setBounds(350,110, 18, 16);
        jb3.setBounds(265,140, 50, 16);
        jb4.setBounds(283, 140, 18, 16);
        bwon=new JLabel("Black won, Congrats !!");
        wwon=new JLabel("White won, Congrats !!");
        bwon.setForeground(Color.WHITE);
        wwon.setForeground(Color.WHITE);
        bwon.setBounds(210, 50, 300, 28);
        bwon.setVisible(false);
        add(bwon);
        wwon.setBounds(210, 50, 300, 28);
        wwon.setVisible(false);
        add(wwon);
        this.add(jb3);
        this.add(jb4);
        this.add(jb2);
        // bouton pour l'assistance
        JButton btnNewButton_4 = new JButton("Assistance");
        btnNewButton_4.setForeground(Color.WHITE);
        btnNewButton_4.setBackground(new Color(111, 34, 50));
        btnNewButton_4.setBounds(20, 10, 90, 28);
        add(btnNewButton_4);
        btnNewButton_4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    askingforhelp=true;
                }
        });
        this.repaint();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent( g );
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,    RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(54, 69, 79));
        g2d.fillRect(120, 90,320, 90);
        g2d.setColor(Color.WHITE);
        g2d.fillOval(200, 100, 40,40);
        if(i==1)
        {
            g2d.setColor(Color.green);
            g2d.drawOval(200, 100, 40,40);
        }
        g2d.setColor(Color.BLACK);
        g2d.fillOval(300, 100, 40, 40);
        if(i==2)
        {
            g2d.setColor(Color.green);
            g2d.drawOval(300, 100, 40, 40);
        }
    }
}