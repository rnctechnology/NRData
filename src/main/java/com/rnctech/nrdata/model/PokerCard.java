package com.rnctech.nrdata.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PokerCard implements Comparable, Serializable {	
	
    public static final char[] RANKS = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'D', 'J', 'Q', 'K'};     
    public static final char[] SUITS = {'♠','♥', '♦', '♣'}; 
    public static Map<Character, String> stot = new HashMap<>();
	public static final String[] trumps = {"S","H","D","C","NT"};

	public static String ranks = "0A23456789DJQK";
	public static String rankValue = "23456789DJQKA";
	public static String suitValue = "♣♦♥♠@";   //@ represent as NT
    public static char toRank(int i) {
        return ranks.charAt(i);
    }
  
    public static String toTrump(char suit) {
    	if(stot.isEmpty()) {
    		stot.put('♠', trumps[0]);
    		stot.put('♥', trumps[1]);
    		stot.put('♦', trumps[2]);
    		stot.put('♣', trumps[3]);
    		stot.put('@', trumps[4]);
    	}
    	return stot.get(suit);
    }
    
    public int getPoint() {
    	if(this.rank == 'A')
    		return 4;
    	else if(this.rank == 'K')
    		return 3;
    	else if(this.rank == 'Q')
    		return 2;
    	else if(this.rank == 'J')
    		return 1;
    	
    	return 0;
    }
    
	public PokerCard(char rank, char suit) {
		this.rank = rank;
		this.suit = suit;
		this.label = (rank == 'D')? suit+"10":new String(new char[]{suit,rank});
		this.blabel = toTrump(suit)+(rank=='D'?"10":rank);
	}
	public char suit;
	public char rank;
	public String label;		//general lable such as ♣3
	public String blabel;   //label for bridge game such as C3
	
	public char getSuit() {
		return suit;
	}
	public char getRank() {
		return rank;
	}
	public String getLabel() {
		return label;
	}
	public String getBlabel() {
		return blabel;
	}
	
	@Override
	public int compareTo(Object o) {
		if(this == o) 
			return 0;
		
		PokerCard c2 = (PokerCard)o;
		
		if(this.suit == c2.suit) {
			if(this.rank == c2.rank)
				return 0;
			return  rankValue.indexOf(c2.rank)-rankValue.indexOf(this.rank);
		}else {
			return  suitValue.indexOf(c2.suit)-suitValue.indexOf(this.suit);
		}

	}
	
}
