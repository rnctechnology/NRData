package com.rnctech.nrdata.utils;

/*
 * sample class for generate 4 hands cards for bridge game
 * with some kind UI Pane for testing
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class BridgeCards {

	public static String[] positions = {"North","East","South","West"};
    public static void main(String[] args) {
        new BridgeCards();
    }
    
    public BridgeCards() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                Deck deck = new Deck();
                deck.shuffle();
                List<Hand> players = new ArrayList<>(4);
                for (int index = 0; index < 4; index++) {
                    players.add(new Hand(positions[index]));
                }

                for (int index = 0; index < 13; index++) {
                    for (Hand hand : players) {
                        hand.add(deck.pop());
                    }
                }

                for (Hand hand : players) {
                    Collections.sort(hand.cards);
                }
                
                JFrame frame = new JFrame("Bridge");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new BridgePane(players));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class Hand {

    	String position;
        private List<Card> cards;

        public Hand(String p) {
        	position = p;
            cards = new ArrayList<>(13);
        }

        public void add(Card card) {
            cards.add(card);
        }

        public int size() {
            return cards.size();
        }

        public Iterable<Card> cards() {
            return cards;
        }
        
        public int getTotalValue() {
        	return cards.stream().mapToInt(c -> c.getValue()).sum();
        }

        public Iterable<Card> reveresed() {
            List<Card> reversed = new ArrayList<>(cards);
            Collections.reverse(reversed);
            return reversed;
        }
    }

    public static class Card implements Comparable{

        private Suit suit;
        private Rank face;

        public Card(Suit suit, Rank face) {
            this.suit = suit;
            this.face = face;
        }

        public Suit getSuit() {
            return suit;
        }

        public Rank getFace() {
            return face;
        }
        
        public int getValue() {
        	if(face.value == "A")
        		return 4;
        	else if(face.value == "K")
        		return 3;
        	else if(face.value == "Q")
        		return 2;
        	else if(face.value == "J")
        		return 1;
        	else
        		return 0;        	
        }

		@Override
		public int compareTo(Object o) {
			if(this == o) 
				return 0;
			
			Card c2 = (Card)o;
			
			if(this.suit.value == c2.suit.value) {
				if(this.face.value == c2.face.value)
					return 0;
				return  c2.face.ordinal() - this.face.ordinal();
			}else {
				return  c2.suit.ordinal() - this.suit.ordinal();
			}
		}

    }

    public class Deck {

        private List<Card> cards;
        private List<Card> playDeck;

        private Deck() {
            cards = new ArrayList<>(52);
            for (Suit suit : Suit.items) {
                for (Rank face : Rank.items) {
                    cards.add(new Card(suit, face));
                }
            }
            playDeck = new ArrayList<>(cards);
        }

        public void shuffle() {
            playDeck.clear();
            playDeck.addAll(cards);
            Collections.shuffle(playDeck);
        }

        public Card pop() {
            if (playDeck.isEmpty()) {
                return null;
            }
            return playDeck.remove(0);
        }

        public void push(Card card) {
            playDeck.add(card);
        }

    }

    enum Rank {
        TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"), JACK("J"), QUEEN("Q"), KING("K"), ACE("A");

        private String value;

        private Rank(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        private static Rank[] items = new Rank[]{
            TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
        };
    }

    enum Suit {
        CLUB("♣"), DIAMOND("♦"), HEART("♥"), SPADE("♠");

        private String value;

        private Suit(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Suit[] items = new Suit[]{
            CLUB, DIAMOND, HEART, SPADE
        };
    }

    public class BridgePane extends JPanel {

		private static final long serialVersionUID = 1L;

		private List<Hand> players;

        private List<Map<Card, Rectangle>> mapCards = new ArrayList<>();

        private Card[] selecteds;

        public BridgePane(final List<Hand> players) {
            this.players = players;
            for(int p=0; p<players.size();p++){
            	mapCards.add(new HashMap<Card,Rectangle>());
            }
            selecteds = new Card[players.size()];
            
            //add listener for all 4 hands
            addMouseListener(getListener(2));
            addMouseListener(getListener(0));
            addMouseListener(getListener(3));
            addMouseListener(getListener(1));

        }
        
        protected MouseAdapter getListener(final int idx){
        	return new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selecteds[idx] != null) {
                        Rectangle bounds =  mapCards.get(idx).get(selecteds[idx]);
                        if(idx == 2){
                        	bounds.y += 20;
                        }else if(idx == 0){
                        	bounds.y -= 20;
                        }else if(idx == 1){
                        	bounds.x -= 20;
                        }else if(idx == 3){
                        	bounds.x += 20;
                        }
                        
                        repaint();
                    }
                    selecteds[idx] = null;
                    for (Card card : players.get(idx).reveresed()) {
                        Rectangle bounds =  mapCards.get(idx).get(card);
                        if (bounds.contains(e.getPoint())) {
                        	selecteds[idx] = card;
                            if(idx == 2){
                            	bounds.y -= 20;
                            }else if(idx == 0){
                            	bounds.y += 20;
                            }else if(idx == 1){
                            	bounds.x += 20;
                            }else if(idx == 3){
                            	bounds.x -= 20;
                            }
                            repaint();
                            break;
                        }
                    }
                }
            };
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1200, 800);
        }

        @Override
        public void invalidate() {
            super.invalidate();           
            genMapCards(2);
            genMapCards(0);
            genMapCards(3);
            genMapCards(1);
        }
        
        protected void genMapCards(int idx){
            mapCards.get(idx).clear();
            Hand hand = players.get(idx);
            int cardHeight = (getHeight() - 20) / 5;
            int cardWidth = (int) (cardHeight * 0.6);
            int xDelta = cardWidth / 2;
            
            if(0 == idx % 2){ // North & South
	            int xPos = (int) ((getWidth() / 2) - (cardWidth * (hand.size() / 4.0)));
	            int yPos = (getHeight() - 20) - cardHeight;
	            if(hand.position == positions[0]){
	            	yPos = 20;
	            }
	            for (Card card : hand.cards()) {
	                Rectangle bounds = new Rectangle(xPos, yPos, cardWidth, cardHeight);
	                mapCards.get(idx).put(card, bounds);
	                xPos += xDelta;
	            }
            }else{  //East & West
	            int yPos = (int) ((getHeight() / 2) - (cardWidth * (hand.size() / 4.0)));
	            int xPos = (getWidth() - 20) - cardHeight;
	            if(hand.position == positions[1]){
	            	xPos = 20;
	            }
	            for (Card card : hand.cards()) {
	                Rectangle bounds = new Rectangle(xPos, yPos, cardHeight, cardWidth);
	                mapCards.get(idx).put(card, bounds);
	                yPos += xDelta;
	            }
            }
        }
        

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            Font currentFont = g2d.getFont();
            Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.6F);
            g2d.setFont(newFont); 
        	
            paintCards(g2d, 2);
            paintCards(g2d, 0);
            paintCards(g2d, 3);
            paintCards(g2d, 1);
            g2d.dispose();
        }

        protected void paintCards(Graphics2D g2d, int idx){
        	Hand hand = players.get(idx);
        	int rangle = 0;
        	if(1 == idx || 3 == idx) rangle = 90;
        	AffineTransform affineTransform = new AffineTransform();
        	affineTransform.rotate(Math.toRadians(rangle), 0, 0);
        	Font rotatedFont = g2d.getFont().deriveFont(affineTransform); 
        	
            for (Card card : hand.cards) {
                Rectangle bounds = mapCards.get(idx).get(card);
                if (bounds != null) {
                    g2d.setColor(Color.WHITE);
                    g2d.fill(bounds);
                    g2d.setColor(Color.BLACK);
                    g2d.draw(bounds);
                    Graphics2D copy = (Graphics2D) g2d.create();
                    paintCard(copy, card, bounds, idx, rotatedFont);
                    copy.dispose();
                }
            }
        }
        
        protected void paintCard(Graphics2D g2d, Card card, Rectangle bounds, int idx, Font rotatedFont) {
            g2d.translate(bounds.x + 5, bounds.y + 5);
            g2d.setClip(0, 0, bounds.width - 5, bounds.height - 5);
            
            Color c = (card.getSuit().ordinal() == 2 || card.getSuit().ordinal() == 1)?Color.RED:Color.BLACK; 
            g2d.setColor(c);
            String text =  card.getSuit().getValue()+card.getFace().getValue();
            FontMetrics fm = g2d.getFontMetrics();
            if(idx == 2){
            	g2d.drawString(text, 0, fm.getAscent());
            }else if(idx == 0){
            	//g2d.setFont(rotatedFont);
            	g2d.drawString(text, 0, bounds.height - 20);
            }else if(idx == 3){
            	g2d.setFont(rotatedFont);
            	g2d.drawString(text, 0, fm.getAscent()-10);
            }else if(idx == 1){
            	g2d.setFont(rotatedFont);
            	g2d.drawString(text, bounds.width-30, fm.getAscent()-10);
            }
        }
    }

}
