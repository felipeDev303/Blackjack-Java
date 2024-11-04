package blackjack;

// Contiene todas las clases para crear el usuario interfaces y para pintar gráficos e imágenes.
import java.awt.*;
// Proporciona interfaces y clases para tratar con diferentes tipos de eventos activados
// por los componentes AWT.
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
// Proporciona un conjunto de "ligereza" (lenguaje Java) componentes que, En la medida de lo
// posible, trabaje lo mismo en todas las plataformas.
import javax.swing.*;

// Clase BlackJack
public class BlackJack {

    // Clase Card (Carta)
    private class Card{
        String value;
        String type;

        // Constructor de la clase Card
        Card(String value, String type){
            this.value = value;
            this.type = type;
        }

        // Sobrecarga del método toString para que no imprima los espacios en memoria
        public String toString(){
            return value + "-" + type;
        }

        public int getValue(){
            if ("AJQK".contains(value)){
                if (value == "A"){
                    return 11; // As
                }
                return 10; // JQK
            }
            return Integer.parseInt(value); // 2-10
        }

        public boolean isAce(){
            return value == "A";
        }
    }

    // Mazo (deck)
    ArrayList<Card> deck;
    Random random = new Random(); // shuffleDeck

    // dealer
    Card hiddenCard; // Carta oculta del crupier
    ArrayList<Card> dealerHand; // mano del crupier
    int dealerSum; // suma de la mano del crupier
    int dealerAceCount;

    // player
    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    // Constructor de la clase BlackJack
    BlackJack(){
        // Función inicio del juego
        starGame();
    }
    public void starGame(){
        // llamamos a la construcción del mazo (deck)
        buildDeck();
        // Llamamos a la función barajar
        shuffleDeck();

        // dealer
        dealerHand = new ArrayList<Card>();
        dealerSum =0;
        dealerAceCount=0;

        hiddenCard = deck.remove(deck.size()-1);
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size()-1);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        System.out.println("DEALER:");
        System.out.println(hiddenCard);
        System.out.println(dealerHand);
        System.out.println(dealerSum);
        System.out.println(dealerAceCount);

        // Player
        playerHand = new ArrayList<Card>();
        playerSum =0;
        playerAceCount=0;

        for(int i=0;i<2;i++){
            card = deck.remove(deck.size()-1);
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }

        System.out.println("PLAYER: ");
        System.out.println(playerHand);
        System.out.println(playerSum);
        System.out.println(playerAceCount);

    }

    // Método que construye el mazo
    public void buildDeck(){
        deck = new ArrayList<Card>();
        String[] types = {"C","D","H","S"};
        String[] values = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

        // Ciclo que recorre los arrays para construir el mazo
        for (int i = 0; i < types.length; i++){
            for (int j = 0; j <values.length; j++){
                // Objeto tarjeta
                Card card = new Card(values[j],types[i]);
                // Al mazo le agrego la tarjeta
                deck.add(card);
            }
        }
        System.out.println("Build Deck:");
        System.out.println(deck);
    }

    public void shuffleDeck(){
        for(int i=0;i<deck.size();i++){
            int j = random.nextInt(deck.size());
            Card currCard = deck.get(i);
            Card randomCard = deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, currCard);
        }

        System.out.println("AFTER SHUFFLE");
        System.out.println(deck);
    }
}
