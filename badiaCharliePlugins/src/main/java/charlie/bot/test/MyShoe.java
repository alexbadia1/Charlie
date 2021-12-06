package charlie.bot.test;

import charlie.card.Shoe;
import java.util.Random;

/**
 * Test shoe for the bots
 * 
 * @author Alex Badia
 */
public class MyShoe extends Shoe {
    public MyShoe () {
        // Playtesting 6 * 52 * 4 Cards is way to many cards
        super(1);
    }// Shoe
    
    @Override
    public void init( ) {
        ran = new Random(1);
        load();
        shuffle();  
    } // init
} // MyShoe
