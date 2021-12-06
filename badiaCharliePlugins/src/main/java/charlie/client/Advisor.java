package charlie.client;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;
import charlie.plugin.IAdvisor;

/**
 * [Phase 1]
 *
 * Charlie consults Advisor, if one is installed and enabled, prior to each play
 * on behalf of the player. Only if the player’s action deviates from the
 * Advisor’s advice does Charlie spring into action and pop-up a warning dialog
 * to give the player a chance to reconsider or proceed with the play. If the
 * player’s action does not deviate from the advice, Charlie is silent.
 *
 * @author Alex Badia
 */
public class Advisor implements IAdvisor {

    protected BasicStrategy bs = new BasicStrategy();

    /**
     * [Phase 1]
     *
     * Gets advice using the Basic Strategy.
     *
     * @param myHand Player hand
     * @param upCard Dealer up-card
     * @return Play advice
     */
    @Override
    public Play advise(Hand myHand, Card upCard) {
        if (!this.isValid(myHand, upCard)) { return Play.NONE; } // if
        
        return bs.getPlay(myHand, upCard);
    } // advise
    
    /**
     * [Phase 1]
     * 
     * Ron Coleman
     * 
     * I would have coded validation in it's own method...
     * eg, isValid. In fact, isValid could be deployed in IAdvisor.
     * 
     * @param myHand Hand
     * @param upCard Card
     * @return true | false
     */
    private boolean isValid(Hand myHand, Card upCard) {
        // Null hand or up card
        if (myHand == null || upCard == null) {
            return false;
        } // if
        
        Card card1 = myHand.getCard(0);
        Card card2 = myHand.getCard(1);

        if (!this.isValidCard(card1)
                || !this.isValidCard(card2)
                || !this.isValidCard(upCard)) {
            return false;
        } // if
        
        return true;
    } // isValid
    
    /**
     * [Phase 1]
     * 
     * Determines if a card is valid or not.
     * 
     * @param card Card
     * @return true | false
     */
    private boolean isValidCard(Card card) {
        // Hand doesn't have at least 2 cards.
        if (card == null) {
            return false;
        } // if

        // Card value is valid
        return card.getRank() >= 1 && card.getRank() <= 10;
    } // isValidCard
} // Advisor
