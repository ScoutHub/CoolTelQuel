package fr.eseo.communication;

import java.io.IOException;
import java.net.Socket;

import fr.eseo.command.Manager;
import fr.eseo.command.CommandControl;
import fr.eseo.command.CommandDeplacement;
import fr.eseo.command.CommandInscription;
/**
 * Classe client du serveur MonkeyIsland.
 * 
 * @version 2.0
 * @author Matthias Brun
 * 
 */
public class ClientMonkeyIsland extends Thread implements Client{
	/**
	 * Identifiant du client.
	 */
	private String id;
	
	/**
	 * La communication avec l'entité MonkeyIsland du client.
	 */
	private CommunicationMonkeyIsland communication;

	/**
	 * Serveur MonkeyIsland du client.
	 */
	private ServiceMonkeyIsland monkeyIsland;
	
	private CommandControl commandControl;
	
	private Manager action; 
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see morix.serveur.Client
	 */
	@Override
	public String donneId(){
		return this.id;
	}
	
	@Override
	public ServiceMonkeyIsland getMonkeyIsland(){
		return this.monkeyIsland;
	}


	/**
	 * Constructeur d'un client de MonkeyIsland.
	 *
	 * @param monkeyIsland service MonkeyIsland du client.
	 * @param socket la socket de connexion du client.
	 *
	 * @throws IOException exception d'entrée/sortie sur le buffer de transmission.
	 *
	 */
	public ClientMonkeyIsland(ServiceMonkeyIsland monkeyIsland, Socket socket) throws IOException{
		super();
		
		this.monkeyIsland = monkeyIsland;
		
		try {
			// Création d'une communication pour le client.
			this.communication = new CommunicationMonkeyIsland(socket);
		}catch (IOException ex) {
			System.err.println("Problème de mise en place d'une gestion de client MonkeyIsland.");
			throw ex;
		}
		
		// Utilisation de la socket de connexion du client comme identifiant.
		this.id = socket.toString();
	}

	/**
	 * Lancement du service à un client.
	 */
	public void lanceService(){
		this.start();
	}
	
	/**
	 * Point d'entrée du thread de service au client 
	 * (atteint via start() dans le lancement du service au client).
	 * 
	 * <p>
	 * Lecture de messages sur la socket de communication avec le client puis traitement du message.
	 * S'arrête quand le message est <tt>null</tt>. 
	 * </p>
	 */
	public void run(){
		try {
			while (true) {
				final String message = this.communication.recoitMessage();
				if (message == null) {
					// Fermeture de la connexion par le client.
					break;
				}
				this.commandControl = new CommandControl();
				this.action = new Manager( this, message);
				this.traiteMessage(message);
			}
		}catch (IOException ex) {
			System.err.println("Problème de gestion d'un client (id : " + this.id + ")");
			System.err.println(ex.getMessage());
		}finally {
			this.monkeyIsland.fermeConnexion(this);
		}
	}
	
	/**
	 * Traitement d'un message envoyé par le client.
	 * 
	 * @param message le message à traiter.
	 * @throws IOException exception
	 */
	private void traiteMessage(String message) throws IOException{
		if (ProtocoleMonkeyIsland.estUneCommande(message)) {
			switch (ProtocoleMonkeyIsland.commandeDuMessage(message)){
			
				case ProtocoleMonkeyIsland.INSCRIPTION_PIRATE :
					this.monkeyIsland.inscriptionCanal(this);
					this.commandControl.setCommand(new CommandInscription(action));
					this.commandControl.commandAction();
					break;
					
				case ProtocoleMonkeyIsland.DEPLACEMENT_PIRATE: 
					this.commandControl.setCommand(new CommandDeplacement(action));
					this.commandControl.commandAction();
					break;
					
				default : 
					this.envoieMessageErreur("Commande inconnue.");
					break;
			}
		} else {
			// Si le message n'est pas une commande.
			this.envoieMessageErreur("Format de commande erroné.");
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see Client
	 */
	@Override
	public void envoieMessage(String message){
		try {
			this.communication.envoieMessage(message);
		}catch (IOException ex) { 
			System.err.println("Problème d'envoie d'un message à un client (id : " + this.id + ")"); 
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see Client
	 */
	@Override
	public void envoieMessageErreur(String message){
		try {
			this.communication.envoieMessageErreur(message);
		}catch (IOException ex) { 
			System.err.println("Problème d'envoie d'un message d'erreur à un client (id : " + this.id + ")"); 
			System.err.println(ex.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see Client
	 */
	@Override
	public void termineCommunication() {
		this.communication.termine();
	}

}
