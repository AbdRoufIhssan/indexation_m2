package traitement.index;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;
import javax.swing.JTextPane;
import java.awt.SystemColor;

public class Info_document extends JFrame {
    private JPanel contentPan=new JPanel();
    private JFrame frame = new JFrame();
	Indexation index = new Indexation();

	/**
	 * Create the frame.
	 */

	
	public Info_document(File file) {
		
		
		//Créer une Frame pour 'affichage des termes;
	    frame.setVisible(true);
	    frame.setResizable(true);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setBounds(10,10,900,650);
	    //frame.pack();
	    frame.setContentPane(contentPan);
	    contentPan.setPreferredSize(new Dimension(400, 400)); 
        contentPan.setLayout(null);
        
        
        // Terms Table
        /*
         * Affichage de la table des termes d'un document sélectionné
         * */
        JTable table = new JTable();
        table.setBounds(10,10,800,400);
        DefaultTableModel dtm = new DefaultTableModel();
        table.setModel(dtm);
        dtm.addColumn("Term");
        dtm.addColumn("TF-Terme");
        dtm.addColumn("....");
        Map<String, Long> inverted = inverted(file);
		inverted.forEach((term,doc)->{
            AtomicReference<Integer> tfCol= new AtomicReference<>(0);
            dtm.addRow(new String [] {term,doc.toString()});
        });
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(500);

        
        // scroll bar.
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(36, 139, 823, 350);
        contentPan.add(pane);
       

      //back button down
        JButton backBtn_1 = new JButton("Retour");
        backBtn_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        backBtn_1.setBounds(618, 517, 241, 40);
        contentPan.add(backBtn_1);
        this.frameClose(backBtn_1);
        
        JTextPane txtpnNomDuFichier = new JTextPane();
        txtpnNomDuFichier.setBackground(SystemColor.inactiveCaptionBorder);
        txtpnNomDuFichier.setFont(new Font("Tahoma", Font.BOLD, 14));
        txtpnNomDuFichier.setBounds(36, 55, 553, 73);
        txtpnNomDuFichier.setText("1- Nom du Fichier : "+file.getName()+"\n\n"+"2-Lien du fichier : "+file.getPath());
        contentPan.add(txtpnNomDuFichier);
        
	}
	
	
	// Traitement de document
	/*
	 * effacer les vides et les ponctuations
	 * Ordonnoncer les terme par ordre alphabétique
	 * @return Map
	 * */
	public Map<String,Long>  inverted(File file){
        Map<String,Long> inverted=new HashMap<>();
        
        List<String> tokens=index.tokenization(file.getPath());
        tokens=index.deleteStopWords(tokens);
        tokens=index.troncationAndSort(tokens);
        inverted = tokens.stream().collect(Collectors.groupingBy(s->s, Collectors.counting()));
        
        return inverted;
    }
	public void frameClose(JButton btn) {
		btn.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            frame.dispose();
	        }
        });
	}
}