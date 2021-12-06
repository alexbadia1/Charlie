package basicstrategy.invalid;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.client.Advisor;
import charlie.dealer.Seat;
import charlie.plugin.IAdvisor;
import charlie.util.Play;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Hand has no cards which should be NONE.
 *
 * @author Alex Badia
 */
public class HandHasNoCards {

    @Test
    public void test() {
        // Generate an initially empty hand
        Hand myHand = new Hand(new Hid(Seat.YOU));

        // Create dealer up card: 2
        Card upCard = new Card(2, Card.Suit.HEARTS);

        // Construct advisor and test it.
        IAdvisor advisor = new Advisor();

        Play advice = advisor.advise(myHand, upCard);

        // Validate the advice.
        assertEquals(advice, Play.NONE);
    } // test
} // HandHasNoCards
