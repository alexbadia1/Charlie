package charlie.server;
import charlie.card.Shoe;

/**
 *
 * @author Alex Badia
 */
public class MyShoe01 extends Shoe{
    public MyShoe01 () {
        super(1);
    }// Shoe
    
    @Override
    public void init() {     
        super.numDecks = 1;
        super.load();
        super.shuffle();
    }// init
}// MyShoe01
