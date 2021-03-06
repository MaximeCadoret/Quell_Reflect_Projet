import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;

import org.w3c.dom.Document;

import java.io.IOException;

import org.xml.sax.SAXException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Plateau {

	// / Attributs de la classe Plateau

	public Case[][] plateau; // C'est le plateau du jeu sous forme de tableau

	public Case[][] plateauDeDepart; // Plateau initial du jeu

	private int nombre_Coup_Niveau = 0;

	private Ecran ecran;

	public objet_Mouvant[] objetMouvant;

	public int niveau; // Donne le num�ro du niveau associ� avec le plateau

	private int nombreBulle; // donne le nombre de bulle du plateau

	public int nombrePiece; // donne le nombre de pi�ce

	 private BufferedReader entree;

	// Constructeur du plateau (non-termin�e)

	public Plateau(int num) {

		// Enregistrement des images correspondant aux objets

		// Pour le moment j'ai consid�r� 2 paires d'anneaux et un type
		// d'interrupteur.
		// ATTENTION : Je n'ai pas mis les images pour les pics et les
		// interrupteurs.

		ImageIcon mur = new ImageIcon(getClass().getResource(
				"Image_des_objets/mur.jpg"));
		ImageIcon fissure1 = new ImageIcon(getClass().getResource(
				"Image_des_objets/fissure1.jpg"));
		ImageIcon fissure2 = new ImageIcon(getClass().getResource(
				"Image_des_objets/fissure2.jpg"));
		ImageIcon joyau = new ImageIcon(getClass().getResource(
				"Image_des_objets/joyau.jpg"));
		ImageIcon anneau1 = new ImageIcon(getClass().getResource(
				"Image_des_objets/anneau bleu.jpg"));
		ImageIcon anneau2 = new ImageIcon(getClass().getResource(
				"Image_des_objets/anneau rouge.jpg"));
		ImageIcon blocMale = new ImageIcon(getClass().getResource(
				"Image_des_objets/male.png"));
		ImageIcon blocFeminin = new ImageIcon(getClass().getResource(
				"Image_des_objets/feminin.jpg"));
		ImageIcon bulle = new ImageIcon(getClass().getResource(
				"Image_des_objets/bulle.jpg"));
		ImageIcon caseVerte1 = new ImageIcon(getClass().getResource(
				"Image_des_objets/pointVert.jpg"));
		ImageIcon caseVerte2 = new ImageIcon(getClass().getResource(
				"Image_des_objets/murVert.jpg"));
		ImageIcon vide = new ImageIcon(getClass().getResource(
				"Image_des_objets/vide.jpg"));
		ImageIcon piece = new ImageIcon(getClass().getResource(
				"Image_des_objets/piece.jpg"));
		ImageIcon interrupteur = new ImageIcon(getClass().getResource(
				"Image_des_objets/vide.jpg"));
		ImageIcon pic1gauche = new ImageIcon(getClass().getResource(
				"Image_des_objets/vide.jpg"));
		ImageIcon pic1gaucheHaut = new ImageIcon(getClass().getResource(
				"Image_des_objets/vide.jpg"));
		ImageIcon pic1gaucheDroite = new ImageIcon(getClass().getResource(
				"Image_des_objets/vide.jpg"));
		ImageIcon pic1gaucheHautDroite = new ImageIcon(getClass().getResource(
				"Image_des_objets/vide.jpg"));
		ImageIcon pic1total = new ImageIcon(getClass().getResource(
				"Image_des_objets/vide.jpg"));

		// Changement de la taille des images pour les faire correspondre au
		// plateau
		// ATTENTION NON FAIT

		// Attribution des images correspondantes � chaque objets.
		// Le probl�me c'est qu'il faut g�rer les aspects statiques et pas
		// statiques...

		Anneau.image1 = anneau1;
		bloc_Sexue.image1 = blocMale;
		bloc_Sexue.image2 = blocFeminin;
		Bulle.image = bulle;
		case_Verte.image1 = caseVerte1;
		case_Verte.image2 = caseVerte2;
		case_Vide.image1 = vide;
		case_Vide.image2 = piece;
		Mur.image1 = mur;
		Mur.image2 = fissure1;
		Mur.image3 = fissure2;
		Mur.image4 = joyau;
		Pic.image1 = pic1gauche;
		Pic.image2 = pic1gaucheHaut;
		Pic.image3 = pic1gaucheDroite;
		Pic.image4 = pic1gaucheHautDroite;
		Pic.image5 = pic1total;
		Switcher.image = interrupteur;

		// //////////////////////////////////////////////////////////////////////////////////////////////

		// Creation du plateau de jeu
		 // Initialisation
		
		niveau = num;
		 URL url= getClass().getResource("cartes_De_Jeu/niveau"+num+".txt");
		 File file =new File(url.getPath());
		 entree = new BufferedReader (new FileReader(file));
		
		
		 // Cadrage du plateau
		
		 int hauteurPlateau = 0;
		 int largeurPlateau = 0;
		 String ligneLue;
		
		 do{
		 ligneLue = entree.readLine();
		 if (ligneLue != null){
		 hauteurPlateau ++;
		 largeurPlateau = ligneLue.split(" ").length;
		 }
		 }
		 while ( ligneLue != null) ;
		
		
		 // G�n�ration du plateau
		
		 Case[][] t = new Case[hauteurPlateau][largeurPlateau];
		 this.entree = new BufferedReader (new FileReader(file));
		 String[] cases;
		 objet_Mouvant[] listeObjet_mouvant= new objet_Mouvant[10];
		 int nombreObjetMouvant=0; // Nombre d'objet mouvant sur le plateau
		 int nombreBulle=1; // Num�ro de la bulle
		 int[] coordA1 = null;
		 int[] coordA2 = null;
		 int ligne = 0; // C'est la ligne qui va �tre lue. Initialiser � z�ro car
		// les prei�res cases des tableaux Java sont � 0
		
		 do{
		
		 ligneLue = entree.readLine();
		 if (ligneLue != null) {
		 cases=ligneLue.split(" ");
		
		 for (int colonne = 0; colonne <= cases.length - 1; colonne++) {
		
		 if (cases[colonne].equals("a1")){
		 if(coordA1 == null){
		 coordA1= new int[] {ligne, colonne};
		 }
		 else
		 t[coordA1[0]][coordA1[1]] = new Anneau (1, null, coordA1[0], coordA1[1]);
		 t[ligne][colonne] = new Anneau (1, (Anneau) t[coordA1[0]][coordA1[1]],
		 ligne, colonne);
		 t[coordA1[0]][coordA1[1]].setPartenaire((Anneau) t[ligne][colonne]) ;
		 }
		
		 if (cases[colonne].equals("a2")){
		 if(coordA2 == null){
		 coordA2= new int[] {ligne, colonne};
		 }
		 else
		 t[coordA2[0]][coordA2[1]] = new Anneau (2, null, coordA2[0], coordA2[1]);
		 t[ligne][colonne] = new Anneau (2, (Anneau) t[coordA2[0]][coordA2[1]],
		 ligne, colonne);
		 t[coordA2[0]][coordA2[1]].setPartenaire(t[ligne][colonne]);
		 }
		
		
		
		 if (cases[colonne].equals("bsm")){
		 t[ligne][colonne]=new case_Vide(false, ligne, colonne);
		 listeObjet_mouvant[nombreObjetMouvant] = new bloc_Sexue(true, ligne,
		 colonne, this);
		 nombreObjetMouvant++;
		 }
		
		 if (cases[colonne].equals("bsf")){
		 t[ligne][colonne]=new case_Vide(false, ligne, colonne);
		 listeObjet_mouvant[nombreObjetMouvant] = new bloc_Sexue(false, ligne,
		 colonne, this);
		 nombreObjetMouvant++;
		 }
		
		 if (cases[colonne].equals("bu")){
		 t[ligne][colonne]=new case_Vide(false, ligne, colonne);
		 listeObjet_mouvant[nombreObjetMouvant] = new Bulle(false, nombreBulle,
		 ligne, colonne, this);
		 nombreObjetMouvant++;
		 nombreBulle++;
		 }
		
		 if (cases[colonne].equals("cve")){
		 t[ligne][colonne]=new case_Verte(ligne, colonne);
		 }
		
		 if (cases[colonne].equals("c")){
		 t[ligne][colonne]=new case_Vide(false, ligne, colonne);
		 }
		
		 if (cases[colonne].equals("p")){
		 t[ligne][colonne]=new case_Verte(ligne, colonne);
		 }
		
		 if (cases[colonne].equals("m")){
		 t[ligne][colonne]=new Mur(false, ligne, colonne);
		 }
		
		 if (cases[colonne].equals("j")){
		 t[ligne][colonne]=new case_Verte(ligne, colonne);
		 }
		 // il faut rajouter les pics et les switchers. Mais je pense qu'il faut
		 //voir les probl�mes auxquels nous sommes confront�s pour savoir exactement
		 // comment les g�rer au niveau du plateau
		 }
		 ligne ++;
		 }
		
		 while ( ligneLue != null) ;{
		
		 this.objetMouvant=new objet_Mouvant[nombreObjetMouvant];
		 for (int i = 0; i < this.objetMouvant.length; i++) {
		 this.objetMouvant[i]=listeObjet_mouvant[i];
		 }
		
		 this.setPlateau(t);
		 this.setPlateauDeDepart(t);
		

		 }
		 }
	}

		
	
	// / Lecture du fichier XML

	public static void main(String[] args) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.parse(new File("Tableau.xml"));

			System.out.println(document.getXmlVersion());
			System.out.println(document.getXmlEncoding());
			System.out.println(document.getXmlStandalone());

			// R�cup�rer la racine de l'arbre

			Element data = document.getDocumentElement();
			System.out.println(data.getNodeName());

			// R�cup�rer les noeuds de la racine : level

			NodeList dataNoeuds = data.getChildNodes();

			int nbDataNoeuds = dataNoeuds.getLength();
			for (int i = 0; i < nbDataNoeuds; i++) {
				if (dataNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element level = (Element) dataNoeuds.item(i);
					System.out.println(level.getNodeName());
				}
			}

			// R�cup�rer les diff�rents �l�ments d'un niveau

			Element level = document.getDocumentElement();
			NodeList levelNoeuds = level.getChildNodes();

			int nbLevelNoeuds = levelNoeuds.getLength();
			for (int i = 0; i < nbLevelNoeuds; i++) {

				Element length = (Element) levelNoeuds.item(0);
				Element width = (Element) levelNoeuds.item(1);
				Element nombrePiece = (Element) levelNoeuds.item(2);

				if (levelNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE
						&& levelNoeuds.item(i).getNodeName() == "Anneau") {
					Element Anneau = (Element) levelNoeuds.item(i);
					Element coordinateX = (Element) Anneau
							.getElementsByTagName("coordinateX").item(i);
					Element coordinateY = (Element) Anneau
							.getElementsByTagName("coordinateY").item(i);
					Element color = (Element) Anneau.getElementsByTagName(
							"color").item(i);
					Element partner = (Element) Anneau
							.getElementsByTagName("partner");
					NodeList partnerNoeuds = partner.getChildNodes();
					int nbPartnerNoeuds = partnerNoeuds.getLength();
					for (int j = 0; j < nbPartnerNoeuds; j++) {
						Element coordinateXPartner = (Element) partner
								.getElementsByTagName("coordinateX");
						Element coordinateYPartner = (Element) partner
								.getElementsByTagName("coordinateY");
						Element colorPartner = (Element) partner
								.getElementsByTagName("color");
					}

					plateauDeDepart[coordinateX][coordinateY] = new Anneau(
							color, partner, coordinateX, coordinateY);
					plateauDeDepart[coordinateX][coordinateY]
							.setPartenaire(new Anneau(colorPartner,
									plateauDeDepart[coordinateX][coordinateY],
									coordinateXPartner, coordinateYPartner));
				}
			}

			NodeList Anneau = level.getElementsByTagName("Anneau");

		} catch (final ParserConfigurationException e) {
			e.printStackTrace();

		} catch (final SAXException e) {
			e.printStackTrace();

		} catch (final IOException e) {
			e.printStackTrace();
		}

	

	
	

	// M�thodes

	public objet_Mouvant[] getObjet_mouvant() {
		// begin-user-code
		return this.objetMouvant;
		// end-user-code
	}

	public void setObjet_mouvant(objet_Mouvant[] objet_mouvant) {
		// begin-user-code
		this.objetMouvant = objet_mouvant;
		// end-user-code
	}

	public int getNombre_Coup_Niveau() {
		// begin-user-code
		return this.nombre_Coup_Niveau;
		// end-user-code
	}

	public void setNombre_Coup_Niveau(int nombre_Coup_Niveau) {
		// begin-user-code
		this.nombre_Coup_Niveau = nombre_Coup_Niveau;
		// end-user-code
	}

	public Ecran getEcran() {
		// begin-user-code
		return this.ecran;
		// end-user-code
	}

	public void setEcran(Ecran ecran) {
		// begin-user-code
		this.ecran = ecran;
		// end-user-code
	}

	public void perdant(String Reponse) {

	}

	public String gagnant() {
		// Il manque � �crire dans quelles conditions ce message s'affiche
		if (this.nombrePiece == 0) {
			String gagnant = "Vous avez gagn�";
			return gagnant;
		} else
			return "";
	}

	public void calcul_Coup_Optimal(int Parameter1) {

	}

	public int actualiser_Piece() {
		this.nombrePiece = this.nombrePiece - 1;
		return this.nombrePiece;
	}

	public void reinitialiser_Niveau() {

	}

	public void actualiser_Plateau() {

	}

	public int getNombreBulle() {

		return this.nombreBulle;
	}

	public void setNombreBulle(int nombreBulle) {
		this.nombreBulle = nombreBulle;
	}

	public void setPlateau(Case[][] t) {
		this.plateau = new Case[(t[0].length)][(t.length)];
		for (int colonne = 0; colonne < t.length; colonne++) {
			for (int ligne = 0; ligne < t[0].length; ligne++) {
				this.plateau[ligne][colonne] = t[ligne][colonne];
			}
		}
	}

	public void setPlateauDeDepart(Case[][] t) {
		this.plateau = new Case[(t[0].length)][(t.length)];
		for (int colonne = 0; colonne < t.length; colonne++) {
			for (int ligne = 0; ligne < t[0].length; ligne++) {
				this.plateauDeDepart[ligne][colonne] = t[ligne][colonne];

			}
		}
	}

	// Reinitialisation du plateau

	public void restartPlateau() {
		// G�rer la r�initialisation des pi�ces, en fonction de leur
		// initialisation de base
		for (int i = 0; i < this.objetMouvant.length; i++) {
			this.objetMouvant[i].x = this.objetMouvant[i].xi;
			this.objetMouvant[i].y = this.objetMouvant[i].yi;

		}
		setPlateau(this.plateauDeDepart);

	}

}