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
 * Tests my 11 vs dealer 6 which should be Double Down.
 * 
 * @author Alex Badia
 */
public class Test05_11_6 {
    @Test
     public void test() {
        // Generate an initially empty hand
        Hand myHand = new Hand(new Hid(Seat.YOU));
        
        // Put two cards in hand: 5 + 6
        Card card1 = new Card(6, Card.Suit.CLUBS);
        Card card2 = new Card(5, Card.Suit.DIAMONDS);
        
        myHand.hit(card1);
        myHand.hit(card2);
        
        // Create dealer up card: 6
        Card upCard = new Card(6, Card.Suit.HEARTS);
        
        // Construct advisor and test it.
        IAdvisor advisor = new Advisor();
        
        Play advice = advisor.advise(myHand, upCard);
        
        // Validate the advice.
        assertEquals(advice, Play.DOUBLE_DOWN);
     } // test
} // Test05_11_6
