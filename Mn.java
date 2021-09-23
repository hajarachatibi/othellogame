
package othellogame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*Panel du menu du jeu
  contenant le choix du mode du jeu (Humain vs Machine ou Humain vs Humain)
  et le niveau de difficulte si il s'agit d'une partie Humain vs Machine
*/
public class Mn extends JPanel {
    JToggleButton toggleButton;
    JButton btnNewButton ;
    JButton btnNewButton_1;
    JComboBox comboBox;
    boolean playwithwho=false; 
	public Mn() {
		this.setBackground(new Color(101, 157, 189));
	        setLayout(null);
                //ToggleButton pour une partie Humain vs Machine
	        JToggleButton j2 = new javax.swing.JToggleButton();
                //comboBox pour le choix du niveau de difficulte
	        comboBox = new JComboBox();
	        comboBox.setForeground(new Color(237, 245, 225));
	        comboBox.setBackground(new Color(5, 56, 107));
	        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Facile", "Moyen", "Difficile"}));
	        comboBox.setBounds(188, 226, 84, 21);
	        comboBox.setVisible(false);
	        add(comboBox);
	        JLabel lblNewLabel = new JLabel("Niveau : ");
	        lblNewLabel.setForeground(new Color(237, 245, 225));
	        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
	        lblNewLabel.setBounds(120, 230, 69, 13);
	        lblNewLabel.setVisible(false);
	        add(lblNewLabel);
	        j2.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        if(j2.isSelected()) 
                        {
                            if(toggleButton.isSelected())
                                toggleButton.setSelected(false);
                            comboBox.setVisible(true);
                            lblNewLabel.setVisible(true);
                            j2.setBackground(new Color(111, 34, 50));   
                            playwithwho=false;//false pour Humain vs Machine 
                        }

                        else if(!j2.isSelected())
                        {
                            comboBox.setVisible(false);
                            lblNewLabel.setVisible(false);
                            j2.setBackground(Color.white);   
                        }
                    }
	        });
	        j2.setBounds(124, 92, 152, 36);
	        j2.setForeground(new Color(5, 56, 107));
	        j2.setBackground(Color.white);
	        this.add(j2);
	        j2.setText("Humain vs Machine");
	        //toggleButton pour une partie Humain vs Humain
	        toggleButton = new JToggleButton();
	        toggleButton.setBounds(124, 151, 152, 36);
	        toggleButton.setForeground(new Color(5, 56, 107));
	        toggleButton.setText("Humain vs Humain");
	        toggleButton.setBackground(Color.white);
	        add(toggleButton);
	        toggleButton.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        if(toggleButton.isSelected()) 
                        {
                            if(j2.isSelected())
                            j2.setSelected(false);
                            toggleButton.setBackground(new Color(111, 34, 50));
                            playwithwho=true; // true pour Humain vs Humain
                        }
                        else if(!toggleButton.isSelected())
                            toggleButton.setBackground(Color.white);  
                    }
	        });
                //Bouton pour commencer le jeu
	        btnNewButton = new JButton("Commencer");
	        btnNewButton.setBackground(new Color(188, 152, 106));
	        btnNewButton.setForeground(new Color(237, 245, 225));
	        btnNewButton.setBounds(91, 301, 119, 28);
	        add(btnNewButton);
                //bouton pour restaurer une partie sauvegardee
                btnNewButton_1 = new JButton("Restaurer ");
	        btnNewButton_1.setBackground(new Color(141, 135, 65));
	        btnNewButton_1.setForeground(Color.WHITE);
	        btnNewButton_1.setBounds(250, 301, 119, 28);
	        add(btnNewButton_1);
                  
	}
}

