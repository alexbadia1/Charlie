package charlie.client;

import charlie.card.Card;
import charlie.plugin.ITrap;
import charlie.message.Message;
import org.apache.log4j.Logger;
import charlie.message.view.to.Deal;
import charlie.message.view.to.Shuffle;
import charlie.message.view.to.GameStart;
import java.text.DecimalFormat;

/**
 *
 * @author Alex Badia
 */
public class CountingTrap implements ITrap {
    protected Logger LOG = null;
    protected DecimalFormat dfShoeSize = new DecimalFormat("##.#");
    protected DecimalFormat dfRunningCount = new DecimalFormat("###");
    protected DecimalFormat dfTrueCount = new DecimalFormat("###.#");
    protected DecimalFormat dfBet = new DecimalFormat("###");
    protected int shoeSize = 52; // [Lab 5] Number of cards in the shoe
    protected int runningCount = 0; // [Lab 5] Hi-Lo Counting System
    protected double trueCount = 0; // [Lab 5] Hi-Lo Counting System
    public int bet = 1; // [Lab 5] Hi-Lo Counting System
    protected boolean shufflePending = false; // [Lab 5] Shuffles deck on next "Game Start"
    
    public CountingTrap() {
        LOG = Logger.getLogger(CountingTrap.class);
    }// CountingTrap

    @Override
    public void onSend(Message msg) {

    }// onSend

    @Override
    public void onReceive(Message msg) {
        // [Lab 5] Card Counting: Card Log Diagnostic
        StringBuilder log4jMsg = new StringBuilder();
        
        if (msg == null) {
            LOG.info("[CountingTrap.onReceive()] - NULL: Message is null!");
        }// if
        
        log4jMsg.append("[CountingTrap.onReceive()] - ");
        log4jMsg.append(msg.getClass().getSimpleName());
        log4jMsg.append(": ");
        
        if (msg instanceof GameStart gameStartMessage) {
            this.shoeSize = gameStartMessage.shoeSize();
            log4jMsg.append("Shoe: ");
            log4jMsg.append(this.dfShoeSize.format(this.shoeSize));
        
            if (this.shufflePending) {
                this.resetRunningCount();
                this.shufflePending = false;
            }// if
        
        }// if
        
        if (msg instanceof Deal dealMessage) {
            log4jMsg.append("Shoe: ");
            log4jMsg.append(this.dfShoeSize.format(this.shoeSize));
            
            Card card = dealMessage.getCard();
            
            if (card == null) {
                log4jMsg.append(", Null Card");
            }// if
            else {
                this.calcRunningCount(card);
                log4jMsg.append(", Running Count: ");
                log4jMsg.append(this.dfRunningCount.format(this.runningCount));

                trueCount = this.calcTrueCount();
                log4jMsg.append(", True Count: ");
                log4jMsg.append(this.dfTrueCount.format(trueCount));

                bet = this.calcBet(trueCount);
                log4jMsg.append(", Bet:  ");
                log4jMsg.append(this.dfBet.format(bet));
            }// else
        }// else if
        
        
        if (msg instanceof Shuffle shuffleMessage) {
            this.shufflePending = true;
        }// else if

        LOG.info(log4jMsg.toString());
    }// onReceive
    
    /**
     * [Lab 5]
     * 
     * Simple, very efficient card counting system
     * Maintains “running count.”
     *      If you see A, 10, J, Q, K → Add -1 to count
     *      If you see 2 - 6 → Add +1 to count
     *      If you see 7 - 9 → Add 0 to count
     *      Count resets when deck shuffled.
     * @param newCard 
     */
    private void calcRunningCount(Card newCard) {
        // [Lab 5] Card is: A, 10, J, Q, K
        if (newCard.isAce() || newCard.value() == 10) {
            --this.runningCount; // [Lab 5] -1 to count
        }// if
        // [Lab 5] Card is: 2, 3, 4, 5, 6
        else if (newCard.value() >= 2 && newCard.value() <= 6) {
            ++this.runningCount; // [Lab 5] +1 to count
        }// else if
        // [Lab 5] Card is: 7, 8, 9
        else {
            // [Lab 5] +0 to count
        }// else 
    }// calcRunningCount
    
    /**
     * [Lab 5]
     * 
     * Running count only gives relative deck richness
     * Must still convert to bet amount.
     * True count…
     *  Estimate of player advantage
     *  True count = running count / decks in shoe
     *      Be careful…
     *          Decks in shoe = cards remaining in shoe size / 52
     *          Use floating point, not integer, division
     *          Do not round here.
     */
    private double calcTrueCount(){
        return (double) this.runningCount / ((double) this.shoeSize / 52.00);
    }// trueCount
    
    
    private void resetRunningCount() {
        this.runningCount = 0; // [Lab 5] Count resets when deck shuffled
    }// resetRunningCount
    
    /**
     * [Lab 5]
     * 
     * Suppose min bet is 1 chip then…
     * Bet this amount in chips:
     *  bet amt = max [ 1, (1 + true_count) ]
     *      Round bet amt
     *      E.g., 3.35 chips => 3 chips
     *      E.g., 3.62 chips => 4 chips
    */
    private int calcBet(double trueCount) {
        // Bet as number of whole chips...
        return (int) Math.round(Math.max(1.0, 1.0 + trueCount));
    }// calcBet
}// CountingTrap
