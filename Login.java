package othellogame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

//Panel pour le login des utilisateurs
public class Login extends JPanel{
	private JTextField textField;
        private JButton btnNewButton;
        public JButton getb()
        {
            return btnNewButton;
        }
        public JTextField gett()
        {
            return textField;
        }
	public Login() {
            setBackground(new Color(12, 0, 50));
            setLayout(null);

            //textField pour la saisi du username
            textField = new JTextField();
            textField.setBounds(150, 124, 122, 27);
            add(textField);
            textField.setColumns(10);
            //Bouton Entrer
            btnNewButton = new JButton("Entrer");
            btnNewButton.setForeground(Color.WHITE);
            btnNewButton.setBackground(new Color(53, 0, 211));
            btnNewButton.setBounds(167, 189, 85, 21);
            add(btnNewButton);

            JLabel lblNewLabel = new JLabel("Username");
            lblNewLabel.setForeground(Color.WHITE);
            lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
            lblNewLabel.setBounds(61, 128, 79, 13);
            add(lblNewLabel);
	}
}
