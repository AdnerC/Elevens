import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand;

    //Represents a rectangle and can detect when clicked
    private Rectangle button;
    int count;
    private ArrayList<Card> highlights;


    public DrawPanel() {
        button = new Rectangle(77, 330, 160, 26);
        this.addMouseListener(this);
        hand = Card.buildHand();
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

        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
    }

    public void mousePressed(MouseEvent e) {


        Point clicked = e.getPoint();
        //Left click
        if (e.getButton() == 1) {
            //if the click occurred in the rectangle
            if (button.contains(clicked)) {
                hand = Card.buildHand();
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

                        hand.get(i).replaceCard(hand, hand.get(i));
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
                    System.out.println("ELEVEN");
                }
            }

        }


    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}