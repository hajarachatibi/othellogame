
package othellogame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

public class app extends javax.swing.JFrame {
static Body b;
static Header h;
private static Login log;
static Mn mn;
private String username; 
static Connection conn;
boolean f;
boolean mode;
int nv;
static JFrame jfp=new JFrame();
static JFrame jf=new JFrame();
String x;
OthelloPosition p;
Othello ttt;
Thread Gamethread;
Thread secthread;
Thread cancelthread;
Thread restorethread;
boolean annuler= false;
    /**
     * Creates new form app
     */
    public app() {
        initComponents();
        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        sp.setResizeWeight(0.3);
	sp.setDividerSize(0);
        jf.setLocationRelativeTo(null);
        b=new Body();
        h=new Header();
        //Bouton du Login
        log=new Login();
        log.getb().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str=log.gett().getText();
                if(str.equals(""))//si l'user n'a pas saisi user name
                {
                    JOptionPane.showMessageDialog(jf, "Il faut saisir un nom d'utilisateur");
                }
                else{
                    try{
                        //ouvrir la connexion avec la bdd
                        Class.forName("com.mysql.jdbc.Driver");
                        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Objects", "root", "");
                        /*
                        Si le nom d'utilisateur existe dans la bdd on ouvre une session
                        Sinon on ajoute l'utilisateur a la bdd
                        */
                        PreparedStatement statement = conn.prepareStatement("select username from users where username = ?");  
                        statement.setString(1,str);    
                        ResultSet rs = statement.executeQuery();
                        if (!rs.isBeforeFirst() ) {    
                            PreparedStatement pstmt = conn
                            .prepareStatement("INSERT INTO users(username) VALUES(?)");
                            pstmt.setString(1,str);
                            pstmt.execute();
                            pstmt.close();
                            username=str;
                            jf.dispose();
                            jf.setLocationRelativeTo(null);
                            st();
                        }
                        else {
                            username=str;
                            jf.dispose();
                            jf.setLocationRelativeTo(null);
                            st();
                        }
                    }catch(Exception exc)
                    {}
                }
            }
            });
        b.setBackground(new Color(26, 26, 29));
        h.setBackground(new Color(26, 26, 29));
        sp.add(h);
        sp.add(b);
        sp.addMouseListener(b);
        jfp.addMouseListener(b);
        jfp.setContentPane(sp);
        jfp.setSize(600, 600);
        jfp.setResizable(true);
        jfp.setLocationRelativeTo(null);
    }
    public void st()
    {
        Mn mn=new Mn();
        jfp.setVisible(false);
        JFrame frame = new JFrame();
            frame.setBounds(800, 100, 450, 450);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(mn);
            frame.setLocationRelativeTo(null);
            //bouton d'annulation des coups
            h.btnNewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    OthelloPosition np;
                    np = (OthelloPosition)ttt.annulation();
                    if (np == null)// si le board est deja en etat initial
                    {
                        String st = "Impossible d'annuler un autre coup !";
                        JOptionPane.showMessageDialog(null, st);
                    }
                    else //sinon on recupere le dernier board et on ouvre un nv thread
                    {
                       if(cancelthread!=null) cancelthread.stop();
                       if(secthread!=null) secthread.stop();
                       if(Gamethread!=null) Gamethread.stop();
                       if(restorethread!=null) restorethread.stop();
                        cancelthread=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ttt.playGame(np, false);
                            }
                        });
                        cancelthread.start();
                    }
                }
            });
            
            //bouton de nouvelle partie
            b.btnNewButton_2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                       if(cancelthread!=null) cancelthread.stop();
                       if(secthread!=null) secthread.stop();
                       if(Gamethread!=null) Gamethread.stop();
                       if(restorethread!=null) restorethread.stop();
                       h.wwon.setVisible(false);
                       h.bwon.setVisible(false);
                       secthread =new Thread(new Runnable() {
                        @Override
                        public void run() {
                            st();
                            ttt.setmode(false);
                            jfp.dispose();
                            jfp.setLocationRelativeTo(null);
                        }
                    });
                    secthread.start();
                }
            });
            frame.setVisible(true);
                  //le bouton commencer
                  mn.btnNewButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
                               x = String.valueOf(mn.comboBox.getSelectedItem());//le niveau de difficulte choisi
	        	       frame.dispose();
                               jfp.setVisible(true); 
                               jfp.setLocationRelativeTo(null);
                               if(cancelthread!=null) cancelthread.stop();
                               if(secthread!=null) secthread.stop();
                               if(Gamethread!=null) Gamethread.stop();
                               if(restorethread!=null) restorethread.stop();
                               Gamethread=new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //si l'utilisateur a choisi une partie H vs M
                                        if(mn.playwithwho==false){
                                            h.btnNewButton.setVisible(true);
                                            h.jb3.setVisible(true);
                                            h.jb4.setVisible(true);
                                            p = new OthelloPosition();
                                            ttt = new Othello(p,b,h);   
                                            switch(x) 
                                                        { 
                                                            case "Facile": 
                                                            ttt.setNiveau(2);
                                                            break; 
                                                            case "Moyen": 
                                                            ttt.setNiveau(4);
                                                            break; 
                                                            case "Difficile": 
                                                            ttt.setNiveau(6);
                                                            break; 
                                                            default: 
                                                            ttt.setNiveau(2);
                                                         }
                                             ttt.playGame(p, true);
                                        }
                                        else { // si l'user a choisi une partie H vs H
                                            OthelloPosition p = new OthelloPosition();
                                            h.btnNewButton.setVisible(false);
                                            h.jb3.setVisible(false);
                                            h.jb4.setVisible(false);
                                            ttt = new Othello(p,b,h);
                                            ttt.playGamewithAHuman(p, true);
                                        }
                                    }
                                });
                                Gamethread.start();
                        }
                    });
                    //Bouton enregistrer une partie
                    h.btnNewButton_1.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
                            try{
                                Class.forName("com.mysql.jdbc.Driver");
                                conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Objects", "root", "");					
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                ObjectOutputStream oos = new ObjectOutputStream(baos);
                                oos.writeObject(b.getposition());
                                byte[] employeeAsBytes = baos.toByteArray();
                                PreparedStatement pstmt = conn
                                .prepareStatement("INSERT INTO game(username,oposition,oplayer,mode,level,date) VALUES(?,?,?,?,?,?)");
                                ByteArrayInputStream bais = new ByteArrayInputStream(employeeAsBytes);
                                pstmt.setString(1,username);
                                pstmt.setBinaryStream(2, bais, employeeAsBytes.length);
                                pstmt.setBoolean(3, ttt.actualplayer);
                                pstmt.setBoolean(4, mn.playwithwho);
                                pstmt.setInt(5, ttt.getNiveau());
                                java.util.Date utilDate = new java.util.Date();
                                pstmt.setDate(6, new java.sql.Date(utilDate.getTime()));
                                pstmt.execute();
                                pstmt.close();
                            }
                            catch(Exception exc){}
                        }
                    });
                    //Bouton Restaurer qui affiche l'historique de tt les parties deja enregistrees
                    mn.btnNewButton_1.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
                            try{
                                Class.forName("com.mysql.jdbc.Driver");
                                conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Objects", "root", "");					
                                PreparedStatement statement = conn.prepareStatement("select * from game where username = ?");  
                                statement.setString(1,username);    
                                ResultSet rs = statement.executeQuery();
                                ArrayList<ArrayList<Object>> ao=new ArrayList<ArrayList<Object>>();
                                int sz=0;
                                if (!rs.isBeforeFirst() ) {
                                    JOptionPane.showMessageDialog(frame, "Vous n'avez aucun enregistrement");
                                }
                                else {
                                    while (rs.next()) {
                                        ArrayList<Object> temp=new ArrayList<Object>();
                                        int id=rs.getInt(1);
                                        temp.add(id);
                                        String user=rs.getString(2);
                                        byte[] po = (byte[]) rs.getObject(3);
                                        boolean f=rs.getBoolean(4);
                                        mode=rs.getBoolean(5);
                                        nv=rs.getInt(6);
                                        if(mode) 
                                        {
                                            temp.add("Humain vs Humain");
                                            temp.add("   -   ");
                                        }
                                        else 
                                            {
                                             temp.add("Humain vs Machine");
                                             if(nv==2) temp.add("facile");
                                             else if(nv==4) temp.add("Moyen");
                                             else  temp.add("difficile");
                                            }
                                        nv=rs.getInt(6);
                                        ByteArrayInputStream baip = new ByteArrayInputStream(po);
                                        temp.add(rs.getDate(7));
                                        temp.add("Restaurer");
                                        ObjectInputStream ois = new ObjectInputStream(baip);
                                        p = (OthelloPosition) ois.readObject();
                                        temp.add(p);
                                        ao.add(temp);
                                        sz++;
                                    }
                                    Object[][] data=new Object[sz][5];
                                    for(int i=0;i<sz;i++)
                                    {
                                        for(int k=0;k<5;k++)
                                        data[i][k]=ao.get(i).get(k);
                                    }
                                    rs.close();
                                    conn.close(); 
                                    JFrame tframe = new JFrame();
                                    tframe.setBounds(100, 100, 700, 339);
                                    Historique his=new Historique(data);
                                    Action delete = new AbstractAction()
                                    {
                                        public void actionPerformed(ActionEvent e)
                                        {
                                            JTable table = (JTable)e.getSource();
                                            int modelRow = Integer.valueOf( e.getActionCommand() );
                                            if(cancelthread!=null) cancelthread.stop();
                                            if(secthread!=null) secthread.stop();
                                            if(Gamethread!=null) Gamethread.stop();
                                            if(restorethread!=null) restorethread.stop();
                                            restorethread =new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Othello ttt = new Othello((OthelloPosition) ao.get(modelRow).get(5),b,h);
                                                    frame.dispose();
                                                    tframe.dispose();
                                                    jfp.setVisible(true); 
                                                    jfp.setLocationRelativeTo(null);
                                                    if(mode)
                                                    {
                                                        h.btnNewButton.setVisible(false);
                                                        h.jb3.setVisible(false);
                                                        h.jb4.setVisible(false);
                                                        ttt.playGamewithAHuman((OthelloPosition) ao.get(modelRow).get(5), true);
                                                    }
                                                    else {
                                                    ttt.setNiveau(nv);
                                                        h.btnNewButton.setVisible(true);
                                                        h.jb3.setVisible(true);
                                                        h.jb4.setVisible(true);
                                                        ttt.playGame((OthelloPosition) ao.get(modelRow).get(5), f);
                                                    }
                                                }
                                            });
                                            restorethread.start();
                                        }
                                    };
                                    ButtonColumn bc = new ButtonColumn(his.table, delete, 4);
                                    tframe.getContentPane().add(his);
                                    tframe.setVisible(true);
                                    tframe.setLocationRelativeTo(null);
                                    tframe.setBackground(new Color(12, 0, 50));
                                }
                            }
                            catch(Exception excp)
                            {
                                System.out.println(excp.toString());
                            }  
                        }
                    });
                    //Bouton pour la deconnexion
                    h.btnNewButton_2.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
                            username="";
                            if(cancelthread!=null) cancelthread.stop();
                            if(secthread!=null) secthread.stop();
                            if(Gamethread!=null) Gamethread.stop();
                            if(restorethread!=null) restorethread.stop();
                            jf.dispose();
                            jfp.dispose();
                            jf=new JFrame();
                            jf.setBounds(100, 100, 477, 339);
                            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            jf.setLocationRelativeTo(null);
                            jf.setVisible(true);
                            jf.getContentPane().add(log);
                        }   
                    });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(app.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(app.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(app.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(app.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {         
		app ap= new app();
		jf.setBounds(100, 100, 477, 339);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jf.setVisible(true);
                jf.setLocationRelativeTo(null);
		jf.getContentPane().add(log);
            } 
        });                           
    }                  
}
