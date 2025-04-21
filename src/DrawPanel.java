import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.util.ArrayList;

class  DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand;

    //Represents a rectangle and can detect when clicked
    private Rectangle button;
    int count;
    private ArrayList<Card> highlights;
    static private ArrayList<Card> deck;
    static boolean won;


    public DrawPanel() {
        won = false;
        deck = new ArrayList<Card>();
        button = new Rectangle(77, 330, 160, 26);
        this.addMouseListener(this);
        deck = Card.buildDeck(deck);
        System.out.println(deck.toString());
        hand = Card.buildHand(deck);
        System.out.println(1);
        count=0;
        highlights = new ArrayList<Card>();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50;
        int y = 10;
        int multi =1;
        for (int i = 0; i < hand.size(); i++) {
            if(i==3||i==6){
                y+=100;
                x=50;
            }
            Card c = hand.get(i);
            //If the card should be highlighted, draw the highlight around it
            if (c.getHighlight()) {
                g.drawRect(x, y, c.getImage().getWidth(), c.getImage().getHeight());
            }
            //establishes the location of the rectangle hitbox
            c.setRectangleLocation(x, y*multi);
            g.drawImage(c.getImage(), x, y*multi, null);
            x = x + c.getImage().getWidth() + 10;
        }
        //draws the bottom button while setting font
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("GET NEW CARDS", 80, 350);
        g.drawString("GET NEW CARDS", 80, 350);
        g.drawString("Cards left in deck: "+ deck.size(), 80, 390);

        if(won){
            g.setColor(Color.yellow);

            g.drawString("YOU WON", 80, 200);

        }

        boolean possible=false;

        for(Card card : hand){
            if((!card.getValue().equals("K"))||(!card.getValue().equals("Q"))||(!card.getValue().equals("J"))) {
                for (Card card1 : hand) {
                    if (Card.getIntegerValue(card) + Card.getIntegerValue(card1) == 11) {
                        possible = true;
                    }

                }
            }else {
                if(hand.toString().contains("K")&&hand.toString().contains("Q")&&hand.toString().contains("J")){
                    possible=true;
                }
            }
        }

        if(!possible){
            g.setColor(Color.red);
            g.drawString("YOU LOSE!", 80, 200);

        }


        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
    }

    public void mousePressed(MouseEvent e) {


        Point clicked = e.getPoint();
        //Left click
        if (e.getButton() == 1) {
            //if the click occurred in the rectangle
            if (button.contains(clicked)) {
                hand = Card.buildHand(deck);
                highlights.clear();
            }
            //checks all the cards to see if they were clicked, if so flip them
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipCard();
                }
            }
        }
        //Right click
        if (e.getButton() == 3) {

            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    if(hand.get(i).getHighlight()){
                        int ind = highlights.indexOf(hand.get(i));

//                        hand.get(i).replaceCard(hand, hand.get(i), deck);

                        hand.get(i).flipHighlight();
                        highlights.remove(ind);

                    }else  {
                        highlights.add(hand.get(i));


                    }
                    hand.get(i).flipHighlight();
                    System.out.println(highlights.toString());
                }
            }

            if(highlights.size()==2){
                if(Card.getIntegerValue(highlights.getFirst())+Card.getIntegerValue(highlights.getLast())==11){
                    Card.replaceCard(hand, highlights.getFirst(), deck);
                    Card.replaceCard(hand, highlights.get(1),deck);
                    highlights.clear();
                    System.out.println("ELEVEN");
                }
            }

            if(highlights.size()==3){
                boolean hasJ =false;
                boolean hasQ =false;
                boolean hasK =false;
                for(Card card : highlights){
                    if(card.getValue().equals("K")){
                        hasK=true;
                    }
                    if(card.getValue().equals("Q")){
                        hasQ=true;
                    }
                    if(card.getValue().equals("J")){
                        hasJ=true;
                    }

                }

                if(hasK&&hasQ&&hasJ){
                    Card.replaceCard(hand, highlights.getFirst(), deck);
                    Card.replaceCard(hand, highlights.get(1),deck);
                    Card.replaceCard(hand, highlights.get(2),deck);
                    highlights.clear();

                }


            }

            if(deck.size()==0 ){
                won =true;
            }


        }


    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}