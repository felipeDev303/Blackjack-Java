package blackjack;

// Contiene todas las clases para crear el usuario interfaces y para pintar gráficos e imágenes.
import java.awt.*;
// Proporciona interfaces y clases para tratar con diferentes tipos de eventos activados por los componentes AWT.
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
// Proporciona un conjunto de "ligereza" (lenguaje Java) componentes que, En la medida de lo posible, trabaje lo mismo en todas las plataformas.
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

        // método que retorna el valor de la carta
        public int getValue(){
            if ("AJQK".contains(value)){
                if (value == "A"){
                    return 11; // As
                }
                return 10; // JQK
            }
            return Integer.parseInt(value); // 2-10
        }

        // método que comprueba si la carta es un As
        public boolean isAce(){
            return value == "A";
        }

        // método que genera la ruta de la imagen de la carta
        public String getImagePath(){
            return "./cards/" + toString() + ".png";
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

    // Windows
    int boardWidth =600;
    int boardHeight=boardWidth;

    int cardWidth = 110; // ratio 1/1.4
    int cardHeight = 154;

    JFrame frame = new JFrame("Black Jack");
    JPanel gamePanel = new JPanel(){
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            try {
                // Draw hidden card
                Image hiddenCardImg = new ImageIcon(getClass().getResource("./cards/BACK.png")).getImage();
                if (!stayButton.isEnabled()){
                    hiddenCardImg = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
                }
                g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);

                // draw dealer's hand
                for(int i = 0; i < dealerHand.size();i++){
                    Card card = dealerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, cardWidth + 25 + (cardWidth + 5)*i, 20, cardWidth, cardHeight, null);
                }

                // draw player's hand
                for (int i=0;i< playerHand.size();i++){
                    Card card = playerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, 20 + (cardWidth + 5)*i, 320, cardWidth, cardHeight, null);
                }

                // Comparación final
                if (!stayButton.isEnabled()){
                    dealerSum = reduceDealerAce();
                    playerSum = reducePlayerAce();
                    System.out.println("STAY: ");
                    System.out.println(dealerSum);
                    System.out.println(playerSum);

                    String message = "";
                    if (playerSum > 21 ){
                        message = "¡La casa gana!";
                    } else if (dealerSum > 21){
                        message = "¡Ganaste!";
                    } else if (playerSum==dealerSum){
                        message = "¡Al empate Lota Schwager!";
                    } else if (playerSum>dealerSum){
                        message = "¡Ganaste!";
                    } else if (playerSum<dealerSum) {
                        message = "¡La casa gana!";
                    }

                    g.setFont(new Font("Arial",Font.PLAIN, 30));
                    g.setColor(Color.white);
                    g.drawString(message,220,250);
                }

            } catch(Exception e){
                e.printStackTrace();
            }
        }
    };

    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton ("Stay");


    // Constructor de la clase BlackJack
    BlackJack(){
        // Función inicio del juego
        starGame();

        // Hacer visible la ventana
        frame.setVisible(true);
        // Tamaño ventana
        frame.setSize(boardWidth, boardHeight);
        // Locación central
        frame.setLocationRelativeTo(null);
        // No redimensionable
        frame.setResizable(false);
        // Cerrar al salir
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel);

        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        hitButton.addActionListener(new ActionListener(){
           public void actionPerformed (ActionEvent e){
               Card card = deck.remove(deck.size()-1);
               playerSum += card.getValue();
               playerAceCount += card.isAce()? 1 : 0;
               playerHand.add(card);
               if (reducePlayerAce() > 21) {
                   hitButton.setEnabled(false);
               }
               gamePanel.repaint();
           }
        });

        stayButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);

                while (dealerSum < 17){
                    Card card = deck.remove(deck.size()-1);
                    dealerSum += card.getValue();
                    dealerAceCount += card.isAce()? 1 : 0;
                    dealerHand.add(card);
                }
                gamePanel.repaint();
            }
        });

        gamePanel.repaint();

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

    public int reducePlayerAce(){
        while (playerSum > 21 && playerAceCount > 0){
            playerSum -= 10;
            playerAceCount -= 1;
        }
        return playerSum;
    }

    public int reduceDealerAce(){
        while (dealerSum > 21 && dealerAceCount > 0){
            dealerSum -= 10;
            dealerAceCount -= 1;
        }
        return dealerSum;
    }
}