package charlie.sidebet.rule;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.ISideBetRule;
import charlie.view.AHand;
import org.apache.log4j.Logger;

/**
 * This class implements the side bet rule for Super 7.
 *
 * @author Alex Badia
 */
public class SideBetRule implements ISideBetRule {

    private final Logger LOG = Logger.getLogger(SideBetRule.class);
    private final Double PAYOFF_SUPER7 = 3.0;
    private final Double PAYOFF_EXACTLY13 = 1.0;
    private final Double PAYOFF_ROYAL_MATCH = 25.0;

    public enum Outcome {
        Exactly13, Super7, RoyalMatch, None
    };
    protected AHand.Outcome outcome = AHand.Outcome.None;
    
    /**
     * Apply rule to the hand and return the payout if the rule matches and the
     * negative bet if the rule does not match.
     *
     * @param hand Hand to analyze.
     * @return Payout amount: <0 lose, >0 win, =0 no bet
     */
    @Override
    public double apply(Hand hand) {
        Double bet = hand.getHid().getSideAmt();
        LOG.info("side bet amount = " + bet);

        if (bet == 0) {
            return 0.0;
        }

        LOG.info("side bet rule applying hand = " + hand);
        
        if (this.isRoyalMatch(hand) == 1) {
            LOG.info("side bet ROYAL MATCH matches");
            return bet * PAYOFF_ROYAL_MATCH;
        }// if
        
        // Higher payout wins?
        if (this.isSuper7(hand) == 1) {
            LOG.info("side bet SUPER 7 matches");
            return bet * PAYOFF_SUPER7;
        }// if
        
        if (this.isExactly13(hand) == 1) {
            LOG.info("side bet EXACTLY 13 matches");
            return bet * PAYOFF_EXACTLY13;
        }// if

        // No side bet lost
        LOG.info("side bet rule no match");
        
        return -bet;
    }// apply

    /**
     * [Lab 7]
     *
     * Two cards dealt are a King and Queen of the Same Suit.
     *
     * @param hand Hand
     * @return 0 | 1
     */
    private int isRoyalMatch(Hand hand) {
        Card a = hand.getCard(0);
        Card b = hand.getCard(1);

        // Card A is NOT a [King | Queen]
        System.out.println("[isRoyalMatch] a = " + a.toString() + ", b = " + b.toString());
        if ((a.getName().compareTo("K") != 0 && a.getName().compareTo("Q") != 0)) {
            return 0;
        }// if

        // Card B is NOT a [King | Queen]
        if ((b.getName().compareTo("K") != 0 && b.getName().compareTo("Q") != 0)) {
            return 0;
        }// if

        // Card A and Card B are BOTH a [King | Queen]
        if (b.getName().compareTo(a.getName()) == 0) {
            return 0;
        }// if

        // King and Queen of DIFFERENT Suits
        if (a.getSuit().compareTo(b.getSuit()) != 0) {
            return 0;
        }// if

        // Royal Match: King and Queen of SAME Suits!
        return 1;
    }// isRoyalMatch

    /**
     * [Lab 8]
     *
     * Exactly 13 wins if the first two player cards are exactly 13.
     *
     * @param hand Hand
     * @return 0 | 1
     */
    private int isExactly13(Hand hand) {
        return hand.getCard(0).value() + hand.getCard(1).value() == 13 ? 1 : 0;
    }// isExactly13

    /**
     * [Lab 8]
     *
     * First card in hand is a 7.
     *
     * @param h hand
     * @return 0 | 1
     */
    private int isSuper7(Hand hand) {
        return hand.getCard(0).getRank() == 7 ? 1 : 0;
    }// isSuper7
}// SideBetRule
