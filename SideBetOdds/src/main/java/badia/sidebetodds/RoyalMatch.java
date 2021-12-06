package badia.sidebetodds;

import charlie.card.Shoe;
import charlie.card.Card;
import java.text.DecimalFormat;
import org.apache.log4j.Logger;
import java.util.function.Function;

/**
 * Monte Carlo Simulation for Royal Match, a BlackJack side bet.
 * @author Alex Badia
 */
public class RoyalMatch {
    private Shoe shoe;
    private int numDecks = 1;
    private int maxIter = 100;
    protected Logger LOG = null;
    final private DecimalFormat dfOdds = new DecimalFormat("###:1");
    final private DecimalFormat dfProb = new DecimalFormat("0.######");
    
    class YourHand {
        public Card a;
        public Card b;
        
        YourHand(Card a, Card b) {
            this.a = a;
            this.b = b;
        }// Hand
    }// Hand
    
    public RoyalMatch(int numDecks, int maxIter) {
        this.maxIter = maxIter;
        this.numDecks = numDecks;
        this.shoe = new Shoe(numDecks);
        this.shoe.init();
        LOG = Logger.getLogger(RoyalMatch.class);
    }// RoyalMatch
    
    
    public double royalMatchProbability() {
        return this.monteCarloSimulation((YourHand yh) -> this.isRoyalMatch(yh.a, yh.b));
    }// calcRoyalMatchProbability
    
    public double exactly13Probability() {
        return this.monteCarloSimulation((YourHand yh) -> this.isExactly13(yh.a, yh.b));
    }// calcRoyalMatchProbability
    
    /**
     * [Lab 7]
     * 
     * Monte Carlo simulation is an alternative approach that 
     * similarly uses game theory.It’s a powerful tool with the
     * advantage that it can evaluate complex situations in which exact 
     * probabilities may not be known or they are problematic to assess.
     * 
     * @param func
     * @return odds
     */
    public double monteCarloSimulation(Function<YourHand,Integer> func) {
        int matchCount = 0;
        for (int i = 0; i < this.maxIter; ++i) {
            // 1.) Initialize shoe
            this.shoe = new Shoe(this.numDecks);
            this.shoe.init();
            
            // 2.) YOUR first card
            Card myCard1 = this.shoe.next();
            
            // Ignore, Dealer's 1st Card
            this.shoe.next();
            
            // 2.) YOUR second card
            Card myCard2 = this.shoe.next();
            
            // Ignore Dealer's 2nd card, for optimization puposes
            // this.shoe.next();
            
            // 3.) If YOU have a Royal Match, increment the count by one
            YourHand yh = new YourHand(myCard1, myCard2);
            System.out.println(func.apply(yh));
            matchCount += func.apply(yh);
        }// for
        
        
        double p = (double) matchCount / (double) this.maxIter;
        double odds = p == 0 ? 0 : (1- p) / p;
        
        System.out.println("Matches = " + matchCount);
        System.out.println("Match prob = " + this.dfProb.format(p) + ", odds = " + this.dfOdds.format(odds));
        
        return odds;
    }// monteCarloSimulation
    
    /**
     * [Lab 7]
     * 
     * Two cards dealt are a King and Queen of the Same Suit.
     * 
     * @param a Card
     * @param b Card
     * @return 0 | 1
     */
    private int isRoyalMatch(Card a, Card b) {
        // Card A is NOT a [King | Queen]
        System.out.println("[isRoyalMatch] a = " + a.toString() +", b = "+ b.toString());
        if ((a.getName().compareTo("K") != 0 && a.getName().compareTo("Q") != 0)) { 
            return 0;
        }// if
        
        // Card B is NOT a [King | Queen]
        if ((b.getName().compareTo("K") != 0 && b.getName().compareTo("Q") != 0)) { 
            return 0;
        }// if
        
        // Card A and Card B are BOTH a [King | Queen]
        if (b.getName().compareTo(a.getName()) == 0) { return 0; }// if
        
        // King and Queen of DIFFERENT Suits
        if (a.getSuit().compareTo(b.getSuit()) != 0) { return 0; }// if
        
        // Royal Match: King and Queen of SAME Suits!
        return 1;
    }// isRoyalMatch
    
    /**
     * [Lab 7]
     * 
     * Exactly 13 wins if the first two player cards are exactly 13.
     * 
     * @param a Card
     * @param b Card
     * @return 0 | 1
     */
    private int isExactly13(Card a, Card b) {
        System.out.println("[isExactly13] a = " + a.value() +", b = "+ b.value());
        return a.value() + b.value() == 13 ? 1 : 0;
    }// isExactly13
    
//    public static void main(String[] args) {
//        RoyalMatch rm = new RoyalMatch(1, 100_000);
//        // rm.royalMatchProbability();
//        // rm.exactly13Probability();
//    }// main
}// RoyalMatch