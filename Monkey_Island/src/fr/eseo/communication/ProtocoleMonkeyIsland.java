package fr.eseo.communication;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import fr.eseo.model.Case;
import fr.eseo.model.CaseType;
import fr.eseo.model.CrazyMonkey;
import fr.eseo.model.HunterMonkey;
import fr.eseo.model.Island;
import fr.eseo.model.Monkey;
import fr.eseo.model.Pirate;
import fr.eseo.model.Rhum;
import fr.eseo.model.Treasure;

/**
 * Classe du protocole de communication de MonkeyIsland avec un client Guybrush.
 * 
 * @version 1.0
 * @author Matthias Brun, Camille Constant
 * 
 */
public final class ProtocoleMonkeyIsland{
	/**
	 * Taille d'une commande.
	 */
	private static final int TAILLE_COMMANDE = 2;

	/**
	 * Caractere prefixant une commande.
	 */
	static final char CARACTERE_COMMANDE = '/';

	/**
	 * Commande de demande d'inscription du pirate.
	 */
	static final char INSCRIPTION_PIRATE = 'I';
	
	/**
	 * Commande d'indication de l'identifiant du pirate.
	 */
	static final char IDENTIFIANT_PIRATE = 'i';

	/**
	 * Commande de demande de deplacement d'un pirate.
	 */
	static final char DEPLACEMENT_PIRATE = 'D';
	
	/**
	 * Commande de refus de deplacement d'un pirate.
	 */
	static final char REFUS_DEPLACEMENT_PIRATE = 'R';
	
	/**
	 * Commande d'acceptation de deplacement d'un pirate.
	 */
	static final char ACCEPTATION_DEPLACEMENT_PIRATE = 'A';

	/**
	 * Commande d'indication de tous les pirates.
	 */
	static final char TOUS_PIRATES = 'P';
	
	/**
	 * Commande d'indication d'un nouveau pirate.
	 */
	static final char NOUVEAU_PIRATE = 'n';
	
	/**
	 * Commande d'indication de suppression d'un pirate.
	 */
	static final char SUPPRESSION_PIRATE = 's';
	
	/**
	 * Commande d'indication d'un pirate deplace.
	 */
	static final char PIRATE_DEPLACE = 'p';
	
	/**
	 * Commande d'indication de la carte.
	 */
	static final char CARTE = 'C';
	
	/**
	 * Commande d'indication des positions des singes erratiques.
	 */
	static final char SINGES_ERRATIQUES = 'e';
	
	/**
	 * Commande d'indication des positions des singes chasseurs.
	 */
	static final char SINGES_CHASSEURS = 'c';
	
	/**
	 * Commande d'indication des positions et de la visibilite des bananes.
	 */
	static final char BOUTEILLES = 'B';
	
	/**
	 * Commande d'indication de visibilite d'une banane.
	 */
	static final char VISIBILITE_BOUTEILLE = 'b';
	
	/**
	 * Commande d'indication du tresor.
	 */
	static final char TRESOR = 'T';
	
	/**
	 * Commande d'indication d'une nouvelle partie.
	 */
	static final char NOUVELLE_PARTIE = 'N';
	
	/**
	 * Separateur de champs d'information differents.
	 */
	static final String SEPARATEUR_CHAMPS = " ";
	
	/**
	 * Separateur de champs d'information pour un objet.
	 */
	static final String SEPARATEUR_CHAMPS_OBJET = "-";

	/**
	 * Separateur d'objets dans un ensemble d'objets.
	 */
	static final String SEPARATEUR_OBJETS = "___";

	/**
	 * Message erreur.
	 */
	static final String MESSAGE_ERREUR = "Erreur MonkeyIsland : ";
	
	/**
	 * Message erreur.
	 */
	public static final String MESSAGE_INSCRIPTION = " Welcome To MonkeyIsland : ";

	/**
	 * Constructeur privé d'un protocole de communication avec un client.
	 * 
	 * Ce constructeur privé assure la non-instanciation d'un
	 * protocole de communication dans un programme
	 * (le protocole n'offre que des attributs et des méthodes statiques).
	 */
	private ProtocoleMonkeyIsland() {
		// Constructeur privé pour assurer la non-instanciation d'un protocole de communication.
	}

	/**
	 * Formate une commande sans paramètre à partir de son nom.
	 * 
	 * <p>La construction d'une commande se fait en préfixant son nom 
	 * par le caractère CARACTERE_COMMANDE et en le suffixant par un espace.</p>
	 *
	 * @param commande la commande à formater.
	 * @return la commande formatée.
	 *
	 */
	public static final String formateCommande(char commande){
		return Character.toString(ProtocoleMonkeyIsland.CARACTERE_COMMANDE) + commande + " "; 
	}
	
	/**
	 * Retourne les paramètres d'une commande dans un tableau de chaînes de caractères.
	 * 
	 * @param commande la commande d'où extraire les paramètres.
	 * @param separateur le séparateur permettant de distinguer les paramètres dans la commande.
	 * @return le tableau des paramètres de la commande.
	 */
	public static final String[] parametresCommande(String commande, String separateur){
		String chaineParametres = "";
		String[] parametres = null;

		if (commande.length() > ProtocoleMonkeyIsland.TAILLE_COMMANDE + 1) {
			chaineParametres = commande.substring(ProtocoleMonkeyIsland.TAILLE_COMMANDE + 1, commande.length());
			parametres = chaineParametres.split(separateur);
		}
		return parametres;		
	}
	
	/**
	 * Valide qu'un message respecte bien le format d'une commande.
	 *
	 * @param message le message à analyser.
	 * 
	 * @return vrai (<i>true</i>) si le message est une commande, faux (<i>false</i>) sinon.
	 */
	public static final Boolean estUneCommande(String message){
		return (message.length() > 1) && (message.charAt(0) == ProtocoleMonkeyIsland.CARACTERE_COMMANDE);
	}
	
	/**
	 * Donne la commande d'un message.
	 * 
	 * <p>
	 * La commande est identifiée par le second caractère d'un message
	 * (le premier caractère étant ProtocoleMonkeyIsland.CARACTERE_COMMANDE).
	 * </p>
	 * 
	 * @param message le message d'où extraire la commande.
	 * 
	 * @return la commande du message.
	 */
	public static final char commandeDuMessage(String message){
		return message.charAt(1);
	}
	
	/**
	 * 	Formate le message renvoyé pour le déplacement du pirate.
	 * @param message the message sent
	 * @return p a point
	 */
	public static final Point commandeDuDeplacement(String message){
		final Point p = new Point();
		if(message.charAt(3) == '-'){
			p.x = -1;
			p.y = 0;	
		}else if(message.charAt(5) == '-'){
			p.x = 0;
			p.y = -1;	
		}else if(message.charAt(3) == '1'){
			p.x = 1;
			p.y = 0;
		}else{
			p.x = 0;
			p.y = 1;
		}
		return p;
	}
	
	/**
	 * Formate l'identification du port du client
	 * @param client the client
	 * @return port the port of the client
	 */
	public static int formaterIdentificationPort(Client client){
		final String[] strArray=client.donneId().split(",");
		final String[] strArray1 = strArray[1].split("=");
		final int port = Integer.parseInt(strArray1[1]);
		return port;
	}

	/**
	 * Formate le message d'indication de la carte avec sa largeur, sa hauteur et le type des cases.
	 * Les champs de la commande sont respectivement :
	 * - la largeur de l'île
	 * - la hauteur de l'île
	 * - le type des cases de la carte, 0 pour une case mer, 1 pour une case terre
	 * 
	 * @param cases Les cases données
	 * @return le message formaté de ces informations.
	 */
	public static final String formatteMessageIndicationCarte(Case[][] cases){
		String message = "";

		message = message.concat("/C " +
				Island.getInstance().getnbRows() + " " +
				Island.getInstance().getnbLines() + " "
		);
		
		// Pour chaque case.
		for (int x = 0; x < Island.getInstance().getnbRows(); x++) {
			for (int y = 0; y < Island.getInstance().getnbLines(); y++) {
				int typeCase = 0;
				if (cases[y][x].getCaseType() == CaseType.sea){
					typeCase = 0;
				}else if (cases[y][x].getCaseType() == CaseType.earth){
					typeCase = 1;
				}
				if(!(x == Island.getInstance().getnbRows() - 1 && y == Island.getInstance().getnbLines() - 1 )){
					message = message.concat(typeCase + ProtocoleMonkeyIsland.SEPARATEUR_CHAMPS_OBJET);
				}else{
					message = message.concat(typeCase + "");
				}
			}
		}
		System.out.println(message);
		
		return message;
	}
	
	/**
	 * Formate le message de position d'un singe erratique
	 * @param monkeys the list of monkeys of the island
	 * @return message a message for the position of the monkey
	 */
	public static final String formaterPositionSingeCrazy(ArrayList<Monkey> monkeys){
		String message = "";
		message = message.concat("/e ");
		final ArrayList<Monkey> crazyMonkeys = new ArrayList<Monkey>();
		if(monkeys.isEmpty() == false){
			for(Monkey monkey : monkeys){
				if(monkey instanceof CrazyMonkey){
					crazyMonkeys.add(monkey);
				}
			}
			for(Monkey monk : crazyMonkeys){
				if(crazyMonkeys.indexOf(monk) != crazyMonkeys.size()-1){
					message = message.concat(
							+monk.getCoordinateX()+ProtocoleMonkeyIsland.SEPARATEUR_CHAMPS_OBJET
							+monk.getCoordinateY()+"___");
				}else{
					message = message.concat(
							+monk.getCoordinateX()+ProtocoleMonkeyIsland.SEPARATEUR_CHAMPS_OBJET
							+monk.getCoordinateY()+"");
				}
			}
		}	
		return message;
	}
	
	/**
	 * Formate le message de position d'un singe chasseur
	 * @param monkeys the list of monkeys of the island
	 * @return message a message for the position of the monkey
	 */
	public static final String formaterPositionSingeHunter(ArrayList<Monkey> monkeys){
		String message = "";
		message = message.concat("/c ");
		final ArrayList<Monkey> hunterMonkeys = new ArrayList<Monkey>();
		if(monkeys.isEmpty() == false){
			for(Monkey monkey : monkeys){
				if(monkey instanceof HunterMonkey){
					hunterMonkeys.add(monkey);
				}
			}
			for(Monkey monk : hunterMonkeys){
				if(hunterMonkeys.indexOf(monk) != hunterMonkeys.size()-1){
					message = message.concat(
							+monk.getCoordinateX()+ProtocoleMonkeyIsland.SEPARATEUR_CHAMPS_OBJET
							+monk.getCoordinateY()+"___");
				}else{
					message = message.concat(
							+monk.getCoordinateX()+ProtocoleMonkeyIsland.SEPARATEUR_CHAMPS_OBJET
							+monk.getCoordinateY()+"");
				}
			}
		}	
		return message;
	}

	/**
	 * Formate le message de déplacement du pirate.
	 * @param pirate the pirate
	 * @return a message
	 */
	public static String formaterDeplacementPirate(Pirate pirate){
		return "/p "+pirate.getId()
				+ ProtocoleMonkeyIsland.SEPARATEUR_CHAMPS_OBJET + pirate.getCoordinateX() 
				+ ProtocoleMonkeyIsland.SEPARATEUR_CHAMPS_OBJET +pirate.getCoordinateY();
	}

	/**
	 * Formate le message d'acceptation de déplacement du pirate. 
	 * @param pirate the pirate
	 * @return a message
	 */
	public static String formaterAcceptationDeplacementPirate(Pirate pirate){
		return ProtocoleMonkeyIsland.formateCommande(ProtocoleMonkeyIsland.ACCEPTATION_DEPLACEMENT_PIRATE) 
				+ pirate.getCoordinateX() 
				+ ProtocoleMonkeyIsland.SEPARATEUR_CHAMPS_OBJET + pirate.getCoordinateY()
				+ ProtocoleMonkeyIsland.SEPARATEUR_CHAMPS_OBJET + pirate.getEnergy();	
	}

	/**
	 * Formate le message de refus de déplacement du pirate.
	 * @return a message
	 */
	public static final String formaterRefusDeplacementPirate(){
		return ProtocoleMonkeyIsland.formateCommande(ProtocoleMonkeyIsland.REFUS_DEPLACEMENT_PIRATE);
	}

	/**
	 * 
	 * @param pirate pour l pirate.
	 * @return pirate.
	 */
	public static String formaterIdentificationPirate(Pirate pirate){
		
		final int x = pirate.getCoordinateX();
		final int y = pirate.getCoordinateY();
		final int nrj = pirate.getEnergy();
		final int id = pirate.getId();
		System.out.println(id+'-'+ x + '-'+ y );
		return "/i " + id +'-'+ x + '-'+ y+'-'+ nrj +"";
		}
	
	/**
	 * Formate le message d'identification du rhum.
	 * @param rhum the selected rhum
	 * @param index the index
	 * @return a message
	 */
	public static String formaterIdentificationRhum(Rhum rhum, int index){
		String message = "/b ";
				message = message.concat(
						+index+ProtocoleMonkeyIsland.SEPARATEUR_CHAMPS_OBJET);
				if(rhum.getVisibility() == false){
					message = message.concat("0");
				}else {
						message = message.concat("1");
				}
		return message;
	}
	
	/**
	 * 
	 * @return a message
	 */
	public static String formaterIdentificationTreasure(){
		String message = "/T ";
		message = message.concat(
				+Treasure.getTreasure().getCoordinateX()+ProtocoleMonkeyIsland.SEPARATEUR_CHAMPS_OBJET
				+Treasure.getTreasure().getCoordinateY());
		return message;
	}
	
	/**
	 * 
	 * @param rhums list of rhums of the island.
	 * @return message.
	 */
	public static String formaterPositionRhum(List<Rhum> rhums){
		String message = "";
		message = message.concat("/B ");
		if(rhums.isEmpty() == false){
			for(Rhum rhum : rhums){
				if(rhums.indexOf(rhum) != rhums.size()-1){
					message = message.concat(
							+rhum.getCoordinateX()+ProtocoleMonkeyIsland.SEPARATEUR_CHAMPS_OBJET
							+rhum.getCoordinateY()+"-1___");
				}else{
					message = message.concat(
							+rhum.getCoordinateX()+ProtocoleMonkeyIsland.SEPARATEUR_CHAMPS_OBJET
							+rhum.getCoordinateY()+"-1");
				}
			}
		}
		
		return message;		
	}
	

}

