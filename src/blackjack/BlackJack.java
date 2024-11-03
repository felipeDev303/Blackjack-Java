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

        public String toString(){
            return value + "-" + type;
        }
    }

    // Mano (deck)
    ArrayList<Card> deck;

    // Constructor de la clase BlackJack
    BlackJack(){
        // Función inicio del juego
        starGame();
    }
    public void starGame(){
        // deck
        buildDeck();

    }

    public void buildDeck(){
        deck = new ArrayList<Card>();
        String[] types = {"C","D","H","S"};
        String[] values = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

        for (int i = 0; i < types.length; i++){
            for (int j = 0; j <values.length; j++){
                Card card = new Card(values[j],types[i]);
                deck.add(card);
            }
        }
        System.out.println("Build Deck:");
        System.out.println(deck);
    }
}
