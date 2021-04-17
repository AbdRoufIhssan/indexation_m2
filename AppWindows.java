
import org.apache.commons.io.FileUtils;
import traitement.index.Indexation;
import traitement.index.Info_document;
import traitement.index.Traitement_indexation;
import traitement.swing.FocusTraversalOnArray;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


public class AppWindows implements ActionListener{

	private JFrame frmRechercheindexe;
	private JButton btnNewButton_1;
	private JTextPane collectionArea;
	private Indexation index =new Indexation();
	Info_document document;
	private String path ="src/main/resources/assets/files/";
	private List<String> fileList=index.getListOfFiles(path);
	private Map<String, Map<String,Integer>> inverted=index.invertedMap(fileList,path);
	int size = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppWindows window = new AppWindows();
					window.frmRechercheindexe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppWindows() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		
		frmRechercheindexe = new JFrame();
		frmRechercheindexe.setResizable(true);
		frmRechercheindexe.setLocationByPlatform(true);
		frmRechercheindexe.setMinimumSize(new Dimension(600, 300));
		frmRechercheindexe.setMaximumSize(new Dimension(600, 300));
		frmRechercheindexe.setResizable(false);
		frmRechercheindexe.setPreferredSize(new Dimension(500, 250));
		frmRechercheindexe.setVisible(true);
		frmRechercheindexe.setTitle("Recherche-Index\u00E9e");
		frmRechercheindexe.setSize(new Dimension(500, 250));
		frmRechercheindexe.getContentPane().setMaximumSize(new Dimension(600, 300));
		frmRechercheindexe.getContentPane().setMinimumSize(new Dimension(600, 300));
		frmRechercheindexe.getContentPane().setPreferredSize(new Dimension(600, 300));
		frmRechercheindexe.getContentPane().setSize(new Dimension(600, 300));
		frmRechercheindexe.setBounds(100, 0, 787, 721);
		frmRechercheindexe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRechercheindexe.getContentPane().setLayout(null);
		JLabel lblNewLabel_3 = new JLabel("");
		
		JList list = new JList();
		list.setBounds(10, 155, 413, 462);
		frmRechercheindexe.getContentPane().add(list);
		frmRechercheindexe.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnNewButton_1}));
		
		/*****/
		/*****/
		//Lister tout les document.txt existants dans un dossier donnï¿½:
		index.affichage(list,"src/main/resources/assets/files");
		lblNewLabel_3.setText(String.valueOf(list.getModel().getSize()));

	    /******/
	    /******/
	        
	    // Ajout de fichier dans le dossier textes   
		btnNewButton_1 = new JButton("Ajouter un document");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_1.setToolTipText("");
		btnNewButton_1.setBounds(595, 54, 153, 35);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Enregistrement de documents");
				int result = fileChooser.showSaveDialog(null);
				if(result == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					String filePath = file.getAbsolutePath();
					String fileExtenstion=file.getName().substring(file.getName().lastIndexOf(".")+1);
					if(fileExtenstion.equalsIgnoreCase("txt")){
						File dest = new File("src/main/resources/assets/files",file.getName());
						try {
							FileUtils.copyFile(file,dest);
							inverted=index.invertedMap(fileList,path);
							index.affichage(list,"src/main/resources/assets/files");
							lblNewLabel_3.setText(String.valueOf(list.getModel().getSize()));
							collectionArea.setText("taille de la collection : "+ inverted.size()+"\n"
									+"nombre de documents : "+list.getModel().getSize()+"\n"
							);
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}else{
						JOptionPane.showMessageDialog(frmRechercheindexe, "Votrer fichier doit avoir l'extention .txt");
					}

				}
				
			}});
		frmRechercheindexe.getContentPane().add(btnNewButton_1);
		
		JLabel collectionLabel = new JLabel("Collection de documents");
		collectionLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
		collectionLabel.setBounds(10, 122, 233, 27);
		frmRechercheindexe.getContentPane().add(collectionLabel);

		collectionArea = new JTextPane();
		collectionArea.setBounds(447,155,315,153);
		collectionArea.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		collectionArea.setEnabled(false);
		collectionArea.setText("taille de la collection : "+ inverted.size()+"\n"
				+"nombre de documents : "+list.getModel().getSize()+"\n"
		);
		
		frmRechercheindexe.getContentPane().add(collectionArea);

		JLabel lblIdtailsSurLe = new JLabel("D\u00E9tails sur le document");
		lblIdtailsSurLe.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		lblIdtailsSurLe.setBounds(447, 343, 233, 27);

		frmRechercheindexe.getContentPane().add(lblIdtailsSurLe);
		
		
		JLabel lblDtailsSurLa = new JLabel("D\u00E9tails sur la collection");
		lblDtailsSurLa.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		lblDtailsSurLa.setBounds(447, 122, 233, 27);
		frmRechercheindexe.getContentPane().add(lblDtailsSurLa);
		
		JButton btnNewButton_1_1 = new JButton("Lancer le Traitement");
		btnNewButton_1_1.setToolTipText("");
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_1_1.setBounds(235, 632, 171, 35);
		btnNewButton_1_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Traitement_indexation frame= new Traitement_indexation();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
	
		frmRechercheindexe.getContentPane().add(btnNewButton_1_1);
		
		
		JLabel lblNewLabel_1 = new JLabel("Recherche et indexation");
		lblNewLabel_1.setFont(new Font("Arial Narrow", Font.BOLD, 22));
		lblNewLabel_1.setBounds(286, 10, 221, 50);
		frmRechercheindexe.getContentPane().add(lblNewLabel_1);
		
		/*
		 * Récuperation du document 
		 * Instancier Info_document(file)
		 * */
		// Item list listener :
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.getSelectionModel().addListSelectionListener(e->{
			String fileName;
			File file;
			Path path;
			String paths = null;
			try {
				list.getSelectedValue();
				for(int i : list.getSelectedIndices()) {
					fileName = fileList.get(i);
					paths = "src/main/resources/assets/files/"+Paths.get(fileName);
				}
				path = (Path)Paths.get(paths);
				file = path.toFile();
				Info_document document = new Info_document(file);
			}catch (Exception e1) {
	            e1.printStackTrace();
	        }
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
