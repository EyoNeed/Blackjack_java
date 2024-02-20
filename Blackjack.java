package cartes;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Blackjack {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		//préparation du jeu
		String jeu[] = {"As", "Deux", "Trois", "Quatre", "Cinq", "Six", "Sept", "Huit", "Neuf", "Dix", "Valet", "Dame", "Roi"}; 
		int liste_valeur[] = {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
		String signes[] = {"Carreaux", "Coeur", "Pique", "Trefle"};
		int Crédit = 2000;
		Boolean continuer = true;
		int counter = 0;
		int valeur_joueur = 0;
		int valeur_banque = 0;
		int mise;
		Boolean eliminated = false;
		Boolean blackjack = false;
		List<String> usedCards = new ArrayList<>();

	  while (continuer) {
	  	//Clear();
	  	System.out.println();
	  	do {
	  		System.out.println("Vous avez " + Crédit + " jetons");
	  		System.out.println("Combien voulez vous miser ? (entre 5 et 1000)");
	  		mise = scan.nextInt();	  			
	  	} while (mise < 5 || mise > 1000);
	  	Crédit -= mise;
	  	if (counter%5 == 0) {
	  		System.out.println("\n\nCartes mélangées...");
	  		usedCards = new ArrayList<>();
	  	}
	    Resultat card_1;
	    String card_1_str;
	    do {
	    	card_1 = pickUniqueCard(jeu, signes, usedCards, valeur_joueur, liste_valeur);
	      card_1_str = card_1.getStr();
	      valeur_joueur = card_1.getEntier();
	    } while (usedCards.contains(card_1_str));
	    usedCards.add(card_1_str);
	       
	    Resultat card_2;
	    String card_2_str;
	    do {
	    	card_2 = pickUniqueCard(jeu, signes, usedCards, valeur_joueur, liste_valeur);
	      card_2_str = card_2.getStr();
	      valeur_joueur = card_2.getEntier();
	    } while (usedCards.contains(card_2_str));
	    usedCards.add(card_2_str);
	       
	    System.out.println("Vos cartes sont :\n• " + card_1_str + "\n• " + card_2_str);
	    if (valeur_joueur == 21) {
	    	System.out.println("BLACKJACK\ngains x1.5 : +" + mise/2);
	    	mise *= 1.5;
	    	blackjack = true;
	    } else {
	    	System.out.println("Soit une valeur de " + valeur_joueur);
	    	Resultat card_1_banque;
		    String card_1_banque_str;
		    do {
		    	card_1_banque = pickUniqueCard(jeu, signes, usedCards, valeur_banque, liste_valeur);
		    	card_1_banque_str = card_1_banque.getStr();
		    	valeur_banque = card_1_banque.getEntier();
		    } while (usedCards.contains(card_1_banque_str));
		    usedCards.add(card_1_banque_str);
		     
	    	System.out.println("\nLa premiere carte du croupier est : \n• " + card_1_banque_str);
	    	System.out.println("Soit une valeur de " + valeur_banque);

	    	System.out.println("Vous souhaitez tirer une nouvelle carte ?(o/n)");
	    	char réponse = scan.next().charAt(0);
	    	while (réponse == 'o' || réponse == 'O') {
	    		Resultat card_joueur;
			    String card_joueur_str;
			    do {
			    	card_joueur = pickUniqueCard(jeu, signes, usedCards, valeur_joueur, liste_valeur);
			    	card_joueur_str = card_joueur.getStr();
			    	valeur_joueur = card_joueur.getEntier();
			    } while (usedCards.contains(card_joueur_str));
			    usedCards.add(card_joueur_str);
			    System.out.println("\n• " + card_joueur_str + "\nValeur : " + valeur_joueur	);
			    if (valeur_joueur <= 21) {
			    	System.out.println("Vous souhaitez tirer une nouvelle carte ? (o/n)");
			    	réponse = scan.next().charAt(0);	
			    } else {
			    	réponse = 'n';
			    	eliminated = true;
			    	System.out.println("BUST");
			    }
	    	}
	    	
	    	while (valeur_banque < 17 && eliminated == false) {
	    		System.out.println("\nAu tour du croupier...");
	    		Resultat card_banque;
			    String card_banque_str;
			    do {
			    	card_banque = pickUniqueCard(jeu, signes, usedCards, valeur_banque, liste_valeur);
			    	card_banque_str = card_banque.getStr();
			    	valeur_banque = card_banque.getEntier();
			    } while (usedCards.contains(card_banque_str));
			    usedCards.add(card_banque_str);
			    System.out.println("\n• " + card_banque_str + "\nValeur : " + valeur_banque);
	    	}
	    }
	    if (!blackjack) {
	    	if ((valeur_banque < valeur_joueur || valeur_banque > 21) && eliminated == false) {
	    		System.out.println("\nVictoire joueur, gains doublés : +" + mise);
	    		mise *= 2;
	    	} else if (valeur_joueur < valeur_banque || eliminated) {
	    		System.out.println("\nVictoire banque, gains perdus : -" + mise);
	    		mise = 0;
	    	} else if (valeur_joueur == valeur_banque) {
	    		System.out.println("\nÉgalité, retour de mise : + 0 ");
	    	}
	    } else {
	    	blackjack = false;
	    }
 
	    valeur_joueur = 0;
	    valeur_banque = 0;
	    Crédit += mise;
	    if (Crédit < 5) {
	    	System.out.println("Crédit insuffisant...");
	    	continuer = false;
	    } else {
	    	System.out.println("Voulez-vous rejouer ? (o/n)");
	    	char answer = scan.next().charAt(0);
	    	if (answer != 'o' && answer != 'O') {
	    		continuer = false;
	    	}
	    }
	    counter++;
	  }
  	System.out.println();
	  //Clear();
	  card_drawing();
	  System.out.println("Partie terminé, votre crédit est de " + Crédit + "€.");
	  scan.close();
	}
	
	private static void card_drawing() {
		System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣶⠟⠛⠛⠛⠻⠿⢿⣷⣶⣶⣤⣤⣀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⣠⣴⣶⣶⣾⣷⣶⣶⣶⣿⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠉⠙⠛⠻⠿⠿⣿⣿⣿⣿⣿⣿⣿⡿⠿⠿⠿⠿⠿⠿⠿⠿⠿⢷⣦⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⣼⡟⠁⠀⠀⠀⠀⠀⠀⢠⣿⠀⠀⢀⣾⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠉⠛⠛⠿⠿⠷⣶⣶⣤⣤⣤⣀⣀⣈⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⣿⡇⠀⠀⠀⢶⣶⡶⠀⣼⡏⠀⢀⣼⠟⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠉⠙⠛⠻⠿⠿⣷⣶⣤⣤⣤⣀⣀⠀⠀\r\n"
				+ "⠀⠀⣿⡇⠀⠀⠀⢸⣿⠀⢀⣿⠇⠀⣾⠿⢿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠙⠻⣷⣄\r\n"
				+ "⠀⠀⣿⡇⠀⠀⠀⢸⣿⠀⢸⡿⠀⠿⠿⠆⣤⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿\r\n"
				+ "⠀⠀⣿⡇⠀⣴⡆⢸⣿⠀⣼⡏⢀⣤⡀⠀⡀⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡿\r\n"
				+ "⠀⠀⣿⡇⠀⠙⠷⠾⠋⢠⣿⠃⢿⣿⣿⣾⣿⡷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⡇\r\n"
				+ "⠀⠀⣿⡇⠀⠀⠀⣀⡀⣸⡿⠀⠘⣿⣿⣿⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣿⠁\r\n"
				+ "⠀⠀⣿⡇⠀⠀⣰⣿⣷⣿⡇⠀⠀⠘⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⡟⠀\r\n"
				+ "⠀⠀⣿⡇⠀⣼⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⡇⠀\r\n"
				+ "⠀⠀⣿⡇⠀⠈⠉⣽⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⠀⠀\r\n"
				+ "⠀⠀⣿⡇⠀⠀⠀⢸⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⡇⠀⠀\r\n"
				+ "⠀⠀⣿⡇⠀⠀⠀⣸⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣿⠁⠀⠀\r\n"
				+ "⠀⠀⣿⡇⠀⠀⠀⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⡿⠀⠀⠀\r\n"
				+ "⠀⠀⣿⡇⠀⠀⢸⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣾⣿⣿⣿⣦⡄⠀⠀⣀⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠇⠀⠀⠀\r\n"
				+ "⠀⠀⣿⡇⠀⠀⣼⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣷⢀⣾⣿⣿⣿⣿⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⠀⠀⠀⠀\r\n"
				+ "⠀⠀⣿⡇⠀⢠⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⡇⠀⠀⠀⠀\r\n"
				+ "⠀⠀⣿⡇⠀⢸⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣿⠃⠀⠀⠀⠀\r\n"
				+ "⠀⠀⣿⡇⠀⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⡿⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⣿⡇⢰⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣿⣿⣿⣿⣿⣿⡿⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠇⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⣿⡇⣸⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⣿⣿⣿⡿⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⣿⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⣿⣧⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⣿⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⡏⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣿⠇⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⣿⣿⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⡇⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⣿⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣿⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⢀⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⡟⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⢸⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⠇⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⣾⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣴⣷⡀⠀⠀⢸⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⢀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣤⣾⣿⣿⣧⠀⠀⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⣼⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⡿⠿⣿⣿⠇⢠⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣤⣄⡀⢀⠈⠁⠀⣼⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣟⠁⢻⣿⠇⢀⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠈⠻⣷⣶⣤⣤⣤⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⢿⣿⠃⠀⢸⣿⢸⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠸⣿⡉⠉⠙⠛⠛⠻⠿⠿⢶⣶⣦⣤⣤⣤⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣾⠇⠀⠀⣿⣏⣸⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠙⢿⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣿⣿⣿⣿⣿⣿⣿⣶⣶⣶⣤⣤⣄⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠿⠋⠀⠀⢀⣿⡿⠛⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⠀⠀⠈⠉⠉⠉⠉⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠙⠛⠛⠻⠿⢷⣶⣶⣦⣤⣄⣀⣀⠀⠀⠀⠀⠀⠀⠀⣼⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠉⠛⠛⠛⠿⠿⣶⣶⣶⠿⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
	}

	public static Resultat Pick_card(String jeu[], String signes[], int valeur, int liste_valeur[]) {
		Random r = new Random();
		int card = r.nextInt(13);
		int new_valeur = Values(valeur, card, liste_valeur);
    int sign = r.nextInt(4);
    String card_chosen = jeu[card] + " " + signes[sign];
    return new Resultat(card_chosen, new_valeur);
	}
	public static Resultat pickUniqueCard(String[] jeu, String[] signes, List<String> usedCards, int valeur, int liste_valeur[]) {
	  Resultat card = Pick_card(jeu, signes, valeur, liste_valeur);
	  if (usedCards.contains(card.getStr())) {
	    return pickUniqueCard(jeu, signes, usedCards, valeur, liste_valeur);
	  }
	  return card;
	}
	
	private static int Values(int valeur, int carte, int[] liste_valeur) {
		int ajout = liste_valeur[carte];
		if (carte == 0 && valeur > 10) {
			ajout = 1;
		}
		return valeur + ajout;
	}
	public static void Clear() {
		for (int i = 0; i < 25; i++) {
		  System.out.println();
		 }
	}
	
}