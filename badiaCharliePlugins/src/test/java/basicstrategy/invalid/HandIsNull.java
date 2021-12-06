package basicstrategy.invalid;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.client.Advisor;
import charlie.plugin.IAdvisor;
import charlie.util.Play;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Hand is null which should be NONE.
 *
 * @author Alex Badia
 */
public class HandIsNull {

    @Test
    public void test() {
        // Generate an initially empty hand
        Hand myHand = null;

        // Create dealer up card: 2
        Card upCard = new Card(2, Card.Suit.HEARTS);

        // Construct advisor and test it.
        IAdvisor advisor = new Advisor();

        Play advice = advisor.advise(myHand, upCard);

        // Validate the advice.
        assertEquals(advice, Play.NONE);
    } // test
} // HandIsNull
