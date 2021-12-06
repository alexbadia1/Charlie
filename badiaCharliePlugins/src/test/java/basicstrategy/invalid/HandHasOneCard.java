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
 * Hand has one card which should be NONE.
 *
 * @author Alex Badia
 */
public class HandHasOneCard {

    @Test
    public void test() {
        // Generate an initially empty hand
        Hand myHand = new Hand(new Hid(Seat.YOU));
        
        // Put one cards in hand: A
        Card card1 = new Card(1, Card.Suit.CLUBS);
        
        myHand.hit(card1);
        
        // Create dealer up card: 2
        Card upCard = new Card(2, Card.Suit.HEARTS);

        // Construct advisor and test it.
        IAdvisor advisor = new Advisor();

        Play advice = advisor.advise(myHand, upCard);

        // Validate the advice.
        assertEquals(advice, Play.NONE);
    } // test
} // HandHasOneCard