package charlie.plugin;

import charlie.card.Card;
import java.awt.Graphics2D;

/**
 * [Phase 3]
 *
 * Client side interface to the ATable for counting cards.
 *
 * @author Alex Badia
 */
public interface ICardCounter {

    /**
     * Resets the count as needed on the start of a new game.
     *
     * @param shoeSize Size of the shoe in number of cards.
     */
    public void startGame(int shoeSize);

    /**
     * Updates the counter.
     *
     * @param card New card from shoe, might be null if dealer's hole card.
     */
    public void update(Card card);
    /**
     * Renders card count info.
     *
     * @param g Graphics context referencing the ATable canvas.
     */
    public void render(Graphics2D g);

    /**
     * Notifies the system that a shuffle is on the way after the game ends but
     * before the start of the next game.
     */
    public void shufflePending();
    
    /**
     * Recommended number of chips to bet
     * @return 
     */
    public int getBetChipsAmt();
} // ICardCounter
