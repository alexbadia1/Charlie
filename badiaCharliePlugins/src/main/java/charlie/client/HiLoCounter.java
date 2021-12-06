package charlie.client;

import charlie.card.Card;
import charlie.plugin.ICardCounter;
import charlie.util.Constant;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

/**
 * 
 * @author Alex Badia
 */
public class HiLoCounter implements ICardCounter {
    /**
     * Relative origin X coordinate.
     */
    public final int X = 20;
    
    /**
     * Relative origin Y coordinate.
     */
    public final int Y = 295;
    
    /**
     * Utility hard-coded margin value for Graphics2D.
     */
    protected double fontToPixelScale = 2.0;
    
    /**
     * Type of Card Counting System
     */
    protected final String systemType = "Ho-Lo";
    
    /**
     * Default font
     */
    protected Font hiLoFont = new Font("Arial", Font.BOLD, 16);
    
    /**
     * Keeps track of the Y position of a new line.
     */
    private int startLinePositionY = Y;
    
    // Card Counting Variables
    protected DecimalFormat dfShoeSize = new DecimalFormat("##.#");
    protected DecimalFormat dfRunningCount = new DecimalFormat("###");
    protected DecimalFormat dfTrueCount = new DecimalFormat("###.#");
    protected DecimalFormat dfBet = new DecimalFormat("###");
    protected int shoeSize = 52;
    protected int runningCount = 0;
    protected double trueCount = 0;
    protected int bet = 1;
    protected boolean shufflePending = false;

    @Override
    public void startGame(int newShoeSize) {
        if (shufflePending) {
            resetRunningCount();
            calcTrueCount();
            calcBet();
            shufflePending = false;
        } // if
        shoeSize = newShoeSize;
    } // startGame

    @Override
    public void update(Card card) {
        this.calcRunningCount(card);
        this.calcTrueCount();
        this.calcBet();
    } // update

    @Override
    public void render(Graphics2D g) {
        // Default styles
        g.setFont(this.hiLoFont);
        g.setColor(Color.WHITE);
        
        // System
        this.startLinePositionY = Y;
        g.drawString("System: " + this.systemType, this.X, this.startLinePositionY);
        
        // Shoe Size Label
        this.advanceLine();
        String label = "Shoe Size:";
        g.drawString(label, this.X, this.startLinePositionY);
        
        // Shoe Size Value
        int marginRight = this.calcMarginRight(label);
        String formattedValue = this.dfShoeSize.format(((double) this.shoeSize / Constant.STANDARD_SHOE_SIZE));
        g.drawString(formattedValue, this.X + marginRight, this.startLinePositionY);
        
        // Running Count Label
        this.advanceLine();
        label = "Running Count: ";
        g.drawString(label, this.X, this.startLinePositionY);
        
        // Running Count Value
        this.runningCountColor(g);
        marginRight = this.calcMarginRight(label);
        formattedValue = this.dfRunningCount.format((double) this.runningCount);
        g.drawString(formattedValue, this.X + marginRight, this.startLinePositionY);
        
        // True Count Label
        this.advanceLine();
        g.setColor(Color.WHITE);
        label = "True Count: ";
        g.drawString(label, this.X, this.startLinePositionY);
        
        // Running Count Value
        marginRight = this.calcMarginRight(label);
        this.trueCountColor(g);
        formattedValue = this.dfTrueCount.format((double) this.trueCount);
        g.drawString(formattedValue, this.X + marginRight, this.startLinePositionY);
        
        // Bet Chips Label
        this.advanceLine();
        g.setColor(Color.WHITE);
        label = "Bet Chips: ";
        g.drawString(label, this.X, this.startLinePositionY);
        
        // Bet Chips Value
        marginRight = this.calcMarginRight(label);
        formattedValue = this.dfBet.format((double) this.bet);
        g.drawString(formattedValue, this.X + marginRight, this.startLinePositionY);
    } // render

    @Override
    public void shufflePending() {
        shufflePending = true;
    } // shufflePending
    
    @Override
    public int getBetChipsAmt() {
        return bet;   
    } // getBet
    
    /**
     * [Phase 3]
     * 
     * For instance, for fun you could try this: when the running count 
     * and true count are negative, render the values (e.g., the number, 
     * not the entire text) red; when positive low (e.g., 0 < true count 
     * <2.0) render the values in yellow; and when high positive (e.g., 
     * true count > 2.0), render the values in bright green.
     * 
     * @param tCount int
     * @param g Graphics2D
     */
    private void trueCountColor(Graphics2D g) {
        // Change color depending on value
        if (this.trueCount < 0) {
            g.setColor(Color.RED);
        } else if (this.trueCount > 2.0) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.YELLOW);
        } // else
    } // trueCountColor
    
    /**
     * [Phase 3]
     * 
     * For instance, for fun you could try this: when the running count 
     * and true count are negative, render the values (e.g., the number, 
     * not the entire text) red; when positive low (e.g., 0 < true count 
     * <2.0) render the values in yellow; and when high positive (e.g., 
     * true count > 2.0), render the values in bright green.
     * 
     * @param rCount int
     * @param g Graphics2D
     */
    private void runningCountColor(Graphics2D g) {
        // Change color depending on value
        if (this.runningCount < 0) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.WHITE);
        } // else 
    } // runningCountColor
    
    /**
     * Calculates the X offset for the labels corresponding value
     * 
     * @param label String
     * @return margin-right
     */
    private int calcMarginRight(String label) {
        return (int) ((this.hiLoFont.getSize() / fontToPixelScale) * label.length()) + 5;
    } // calcMarginRight
    
    /**
     * Advances the X, Y coordinate for a new line
     */
    private void advanceLine() {
        this.startLinePositionY += 20;
    } // AdvanceLine
    
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
    private void calcTrueCount(){
        this.trueCount = (double) this.runningCount / ((double) this.shoeSize / 52.00);
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
    private void calcBet() {
        // Bet as number of whole chips...
        this.bet = (int) Math.round(Math.max(1.0, 1.0 + this.trueCount));
    }// calcBet
} // HiLoCounter
