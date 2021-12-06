package basicstrategy.section2;

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
 * Tests my 5 vs dealer 7 which should be HIT.
 * 
 * @author Alex Badia
 */
public class Test06_5_7 {
    @Test
     public void test() {
        // Generate an initially empty hand
        Hand myHand = new Hand(new Hid(Seat.YOU));
        
        // Put two cards in hand: 2+3
        Card card1 = new Card(2, Card.Suit.CLUBS);
        Card card2 = new Card(3, Card.Suit.DIAMONDS);
        
        myHand.hit(card1);
        myHand.hit(card2);
        
        // Create dealer up card: 7
        Card upCard = new Card(7, Card.Suit.HEARTS);
        
        // Construct advisor and test it.
        IAdvisor advisor = new Advisor();
        
        Play advice = advisor.advise(myHand, upCard);
        
        // Validate the advice.
        assertEquals(advice, Play.HIT);
     } // test
} // Test06_5_7
