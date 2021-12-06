package charlie.client;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;

/**
 * [Phase 2]
 * 
 * The best practice which is also the simplest for Q3 with
 * the least risk is to use object-orientation. OO was intended 
 * just for this purpose. That is, do not change BasicStrategy but 
 * rather subclass it as BotBasicStrategy and override the getPlay 
 * method. Then, as the first step of getPlay, invoke super.getPlay 
 * and if SPLIT is indicated, re-look up pairs in Sections 1 & 2. A 
 * small wrinkle here is Section 2 does not have “4” corresponding to 2+2.
 * 
 * @author Alex Badia
 */
public class BotBasicStrategy extends BasicStrategy {
    /**
     * [Phase 2]
     *
     * Rules for section 2; see Instructional Services (2000) pocket card.
     */
    Play[][] section2Rules = {
        /*         2  3  4  5  6  7  8  9  T  A  */
        /*  11   */ {D, D, D, D, D, D, D, D, D, H},
        /*  10   */ {D, D, D, D, D, D, D, D, H, H},
        /*   9   */ {H, D, D, D, D, H, H, H, H, H},
        /*   8   */ {H, H, H, H, H, H, H, H, H, H},
        /*   7   */ {H, H, H, H, H, H, H, H, H, H},
        /*   6   */ {H, H, H, H, H, H, H, H, H, H},
        /*   5   */ {H, H, H, H, H, H, H, H, H, H},
        
        // Incorporates new logic for 2, 2.
        /* 2 + 2 */ {P, P, P, P, P, P, H, H, H, H},};
    
    @Override
    public Play getPlay(Hand hand, Card upCard) {
        if (!this.isValid(hand, upCard)) { return Play.NONE; } // if
        
        Play play = super.getPlay(hand, upCard);
        
        if (play == Play.SPLIT) {
            if (hand.getValue() >= 4 && hand.getValue() < 12) {
                return doSection2(hand, upCard);
            } else if (hand.getValue() >= 12) {
                return super.doSection1(hand, upCard);
            } // else-if
        } // if
        
        return play;
    } // getPlay
    
    
    /**
     * [Phase 2]
     *
     * Does section 2 processing of the basic strategy, 5-11 (player) vs. 2-A
     * (dealer)
     *
     * @param hand Player's hand
     * @param upCard Dealer's up-card
     */
    protected Play doSection2(Hand hand, Card upCard) {
        int value = hand.getValue();

        // Section 2 currently only supports 4 <= hands <= 11 (see above).
        if (value < 4 || value > 11) {
            return Play.NONE;
        } // if

        ////// Get the row in the table.
        // Subtract 11 since the player's hand starts at 21 and we're working
        // our way down through section 1 from index 0.
        int rowIndex = 11 - value;

        Play[] row = this.section2Rules[rowIndex];

        ////// Get the column in the table
        // Subtract 2 since the dealer's up-card starts at 2
        int colIndex = upCard.getRank() - 2;

        if (upCard.isFace()) {
            colIndex = 10 - 2;
        } // Ace is the 10th card (index 9)
        else if (upCard.isAce()) {
            colIndex = 9;
        } // else-if

        // At this row, col we have the correct play defined.
        Play play = row[colIndex];

        return play;
    } // doSection2
    
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
} // BotBasicStrategy
