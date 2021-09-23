package othellogame;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//L'historique des parties deja sauvegardees par un utilisateur
public class Historique extends JPanel {
    JTable table;
    public Historique(Object[][] data) {
		super();
		String[] columnNames = {"id", "Mode", "Niveau","Date",""};// Mode c-a-d: partie contre la machine ou humain 
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table = new JTable( model );
	        // adding it to JScrollPane 
                JScrollPane sp = new JScrollPane(table); 
                this.setBackground(new Color(12, 0, 50));
                setVisible(true);
	   	this.add(sp);
	   	this.setLayout(new GridLayout(1,1));
	   	table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	   	sp.getViewport().setBackground(new Color(12, 0, 50));
	}
}
