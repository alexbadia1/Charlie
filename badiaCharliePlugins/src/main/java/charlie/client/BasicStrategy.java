package charlie.client;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;

/**
 * [Phase 1]
 *
 * @author Alex Badia
 */
public class BasicStrategy {

    public final static Play P = Play.SPLIT;
    public final static Play H = Play.HIT;
    public final static Play S = Play.STAY;
    public final static Play D = Play.DOUBLE_DOWN;

    /**
     * [Phase 1]
     *
     * Rules for section 1; see Instructional Services (2000) pocket card
     */
    Play[][] section1Rules = {
        /*         2  3  4  5  6  7  8  9  T  A  */
        /* 21 */{S, S, S, S, S, S, S, S, S, S},
        /* 20 */ {S, S, S, S, S, S, S, S, S, S},
        /* 19 */ {S, S, S, S, S, S, S, S, S, S},
        /* 18 */ {S, S, S, S, S, S, S, S, S, S},
        /* 17 */ {S, S, S, S, S, S, S, S, S, S},
        /* 16 */ {S, S, S, S, S, H, H, H, H, H},
        /* 15 */ {S, S, S, S, S, H, H, H, H, H},
        /* 14 */ {S, S, S, S, S, H, H, H, H, H},
        /* 13 */ {S, S, S, S, S, H, H, H, H, H},
        /* 12 */ {H, H, S, S, S, H, H, H, H, H},};

    /**
     * [Phase 1]
     *
     * Rules for section 2; see Instructional Services (2000) pocket card
     */
    Play[][] section2Rules = {
        /*         2  3  4  5  6  7  8  9  T  A  */
        /* 11 */{D, D, D, D, D, D, D, D, D, H},
        /* 10 */ {D, D, D, D, D, D, D, D, H, H},
        /*  9 */ {H, D, D, D, D, H, H, H, H, H},
        /*  8 */ {H, H, H, H, H, H, H, H, H, H},
        /*  7 */ {H, H, H, H, H, H, H, H, H, H},
        /*  6 */ {H, H, H, H, H, H, H, H, H, H},
        /*  5 */ {H, H, H, H, H, H, H, H, H, H},};

    /**
     * [Phase 1]
     *
     * Rules for section 3; see Instructional Services (2000) pocket card
     */
    Play[][] section3Rules = {
        /*         2  3  4  5  6  7  8  9  T  A  */
        /* A,10 */{S, S, S, S, S, S, S, S, S, S},
        /* A,9 */ {S, S, S, S, S, S, S, S, S, S},
        /* A,8 */ {S, S, S, S, S, S, S, S, S, S},
        /* A,7 */ {S, D, D, D, D, S, S, H, H, H},
        /* A,6 */ {H, D, D, D, D, H, H, H, H, H},
        /* A,5 */ {H, H, D, D, D, H, H, H, H, H},
        /* A,4 */ {H, H, D, D, D, H, H, H, H, H},
        /* A,3 */ {H, H, H, D, D, H, H, H, H, H},
        /* A,2 */ {H, H, H, D, D, H, H, H, H, H},};

    /**
     * [Phase 1]
     *
     * Rules for section 4; see Instructional Services (2000) pocket card
     */
    Play[][] section4Rules = {
        /*         2  3  4  5  6  7  8  9  T  A  */
        /* 10, 10 */{S, S, S, S, S, S, S, S, S, S},
        /*   9 ,9 */ {P, P, P, P, P, S, P, P, S, S},
        /*   8, 8 */ {P, P, P, P, P, P, P, P, P, P},
        /*   7, 7 */ {P, P, P, P, P, P, H, H, H, H},
        /*   6, 6 */ {P, P, P, P, P, H, H, H, H, H},
        /*   5, 5 */ {D, D, D, D, D, D, D, D, H, H},
        /*   4, 4 */ {H, H, H, P, P, H, H, H, H, H},
        /*   3, 3 */ {P, P, P, P, P, P, H, H, H, H},
        /*   2, 2 */ {P, P, P, P, P, P, H, H, H, H},
        /*   A, A */ {P, P, P, P, P, P, P, P, P, P},};

    /**
     * [Phase 1]
     *
     * Gets the play for player's hand vs. dealer up-card.
     *
     * @param hand Hand player hand
     * @param upCard Dealer up-card
     * @return Play based on basic strategy
     */
    public Play getPlay(Hand hand, Card upCard) {
        Card card1 = hand.getCard(0);
        Card card2 = hand.getCard(1);

        if (hand.isPair()) {
            return doSection4(hand, upCard);
        } else if (hand.size() == 2 && (card1.getRank() == Card.ACE || card2.getRank() == Card.ACE)) {
            return doSection3(hand, upCard);
        } else if (hand.getValue() >= 5 && hand.getValue() < 12) {
            return doSection2(hand, upCard);
        } else if (hand.getValue() >= 12) {
            return doSection1(hand, upCard);
        } // else-if

        return Play.NONE;
    } // getPlay

    /**
     * [Phase 1]
     *
     * Does section 1 processing of the basic strategy, 12-21 (player) vs. 2-A
     * (dealer)
     *
     * @param hand Player's hand
     * @param upCard Dealer's up-card
     */
    protected Play doSection1(Hand hand, Card upCard) {
        int value = hand.getValue();

        // Section 1 currently only supports hands >= 12 (see above).
        if (value < 12) {
            return Play.NONE;
        } // if

        ////// Get the row in the table.
        // Subtract 21 since the player's hand starts at 21 and we're working
        // our way down through section 1 from index 0.
        int rowIndex = 21 - value;

        Play[] row = section1Rules[rowIndex];

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
    } // doSection1

    /**
     * [Phase 1]
     *
     * Does section 2 processing of the basic strategy, 5-11 (player) vs. 2-A
     * (dealer)
     *
     * @param hand Player's hand
     * @param upCard Dealer's up-card
     */
    protected Play doSection2(Hand hand, Card upCard) {
        int value = hand.getValue();

        // Section 2 currently only supports 5 <= hands <= 11 (see above).
        if (value < 5 || value > 11) {
            return Play.NONE;
        } // if

        ////// Get the row in the table.
        // Subtract 11 since the player's hand starts at 21 and we're working
        // our way down through section 1 from index 0.
        int rowIndex = 11 - value;

        Play[] row = section2Rules[rowIndex];

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
     * Does section 3 processing of the basic strategy, A, (2-10) (player) vs.
     * 2-A (dealer)
     *
     * @param hand Player's hand
     * @param upCard Dealer's up-card
     */
    protected Play doSection3(Hand hand, Card upCard) {
        // Get value of non-Ace card
        Card card1 = hand.getCard(0);
        Card card2 = hand.getCard(1);
        int value = card1.getRank() != Card.ACE ? card1.getRank() : card2.getRank();

        // Section 3 currently only supports 2 <= hands <= 10 (see above).
        if (value < 2 || value > 10) {
            return Play.NONE;
        } // if

        ////// Get the row in the table.
        // Subtract 10 since the player's hand starts at 21 and we're working
        // our way down through section 1 from index 0.
        int rowIndex = 10 - value;

        Play[] row = section3Rules[rowIndex];

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
    } // doSection3

    /**
     * [Phase 1]
     *
     * Does section 4 processing of the basic strategy, pairs (player) vs. 2-A
     * (dealer)
     *
     * @param hand Player's hand
     * @param upCard Dealer's up-card
     */
    protected Play doSection4(Hand hand, Card upCard) {
        // Get value, Ace's are treated as 1's
        int value = hand.getCard(0).getRank() == Card.ACE ? 1 : hand.getCard(0).getRank();

        // Section 4 currently only supports pairs (see above).
        if (value < 1 || value > 10) {
            return Play.NONE;
        } // if

        ////// Get the row in the table.
        // Subtract 10 since the player's hand starts at 21 and we're working
        // our way down through section 1 from index 0.
        int rowIndex = 10 - value;

        Play[] row = section4Rules[rowIndex];

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
    } // doSection4
} // BasicStrategy
