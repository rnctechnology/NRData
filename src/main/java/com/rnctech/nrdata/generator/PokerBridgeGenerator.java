package com.rnctech.nrdata.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import com.rnctech.nrdata.model.PokerCard;

import static com.rnctech.nrdata.model.PokerCard.*;

public class PokerBridgeGenerator {

	static int totalCards = 52;
	static String[] suiteFull = {"spade","heard","diamond","club"};
	static String[] hands = {"North","East","South","West"};

	
    public char unicodeChar(String suit){
        suit = suit.toLowerCase();
        switch(suit){
            case "heart":
                return '♥';
            case "diamond":
                return '♦';
            case "club":
                return '♣';
            case "spade":
                return '♠';
            default:
                return suit.charAt(0);
        }
    }
	
	static int[] deal() {
		int[] cards = new int[52];
		Random r = new Random(shuff());
		List<Integer> l = new ArrayList<>();
		
		int i=0;
		for(;i<39;i++) {
			int ri = 1 + r.nextInt(52);
			while(l.contains(ri)) {
				ri = 1 + r.nextInt(52);				
			}	
			l.add(ri);
			cards[i] = ri;
		}

		
		for(int j=1; j<=52 && i<52; j++) {
			if(!l.contains(j)) {
				cards[i] = j;
				i++;
			}
		}

		return cards;
	}
	
	static int shuff() {
		return (int)Math.ceil(Math.random() * 1000);
	}
	
	static Map<String, Set<PokerCard>> getFourHands(int[] cards){
		//init a deck
		List<PokerCard> deck = new ArrayList<>();
		for(int i=0; i<RANKS.length; i++) {
			for(int j=0; j<SUITS.length; j++) {
				deck.add(new PokerCard(RANKS[i], SUITS[j]));
			}
		}
		System.out.println("Deck inited "+deck.size());
		
		Map<String, Set<PokerCard>> allhands= new HashMap<>();
		allhands.put(hands[0], new TreeSet<PokerCard>());
		allhands.put(hands[1], new TreeSet<PokerCard>());
		allhands.put(hands[2], new TreeSet<PokerCard>());
		allhands.put(hands[3], new TreeSet<PokerCard>());

		int h=0;		
		for(int i = 0; i<52; i++) {
			h = i / 13;
			PokerCard card = deck.get(cards[i]-1);
			allhands.get(hands[h]).add(card);
		}		
		
		return allhands;
	}
	
	
	static Map<String, Set<String>> getFourHandsImages(int[] cards){		
		Map<String, Set<String>> allhands= new HashMap<>();
		allhands.put(hands[0], new TreeSet<String>());
		allhands.put(hands[1], new TreeSet<String>());
		allhands.put(hands[2], new TreeSet<String>());
		allhands.put(hands[3], new TreeSet<String>());

		int h=0;		
		for(int i = 0; i<52; i++) {
			h = i / 13;
			String card = "resources/imgs"+cards[i]+".jpg";
			allhands.get(hands[h]).add(card);
		}		
		
		return allhands;
	}
	
	static void printCards(int[] cards) {
		
		for(int i =0; i<cards.length; i++) {
			char suit=  SUITS[i/13];
			int c = (1+cards[i]%13);
			String s = (c==10)?"10":""+ranks.charAt(c);
			System.out.print(suit+s+" ");
			if(i>0 &&  0 == i % 13) {
				System.out.println("\n");

			}
		}
	}

	public static void main(String[] args) {
		int[] cs = deal();
		//printCards(cs);
		
		System.out.println(cs.length);
		Map<String, Set<PokerCard>> hands = getFourHands(cs);
		for(Map.Entry<String, Set<PokerCard>> entry: hands.entrySet()) {
			System.out.print(entry.getKey()+":\t");
			entry.getValue().stream().forEach(c -> System.out.print(c.label+" "));
			
			System.out.println("\t"+entry.getValue().size()+"\n");
		}
		
	}


}
