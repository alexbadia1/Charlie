/*
 Copyright (c) 2014 Ron Coleman

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package charlie.sidebet.view;

import charlie.audio.SoundFactory;
import charlie.view.sprite.Chip;
import charlie.card.Hid;
import charlie.plugin.ISideBetView;
import charlie.view.AMoneyManager;

import charlie.view.sprite.ChipButton;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import java.awt.Image;

import static charlie.audio.Effect.CHIPS_IN;
import static charlie.audio.Effect.CHIPS_OUT;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import charlie.util.Constant;
import charlie.view.sprite.AtStakeSprite;
import java.awt.FontMetrics;

/**
 * This class implements the side bet view
 *
 * @author Alex Badia
 */
public class SideBetView implements ISideBetView {

    private enum Outcome {
        Superposition, Win, Lose
    };
    private final Logger LOG = Logger.getLogger(SideBetView.class);

    public final static int X = 400;
    public final static int Y = 200;
    public final static int DIAMETER = 50;

    protected Font font = new Font("Arial", Font.BOLD, 18);
    protected Font sideBetsFont = new Font("Arial", Font.BOLD, 16);
    protected Font outcomeFont = new Font("Arial", Font.BOLD, 18);

    protected BasicStroke stroke = new BasicStroke(3);
    protected Color looseColorBg = new Color(250, 58, 5);
    protected Color looseColorFg = Color.WHITE;
    protected Color winColorFg = Color.BLACK;
    protected Color winColorBg = new Color(116, 255, 4);

    // See http://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html
    protected float dash1[] = {10.0f};
    protected BasicStroke dashed
            = new BasicStroke(3.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f);

    protected List<ChipButton> buttons;
    protected int amt = 0;
    protected AMoneyManager moneyManager;

    // [Lab 9] Adds a Chip(), everytime the player increments the side bet amount
    protected List<Chip> chips = new ArrayList<>();
    protected Random ran = new Random();

    protected final static String[] UP_FILES
            = {"chip-100-1.png", "chip-25-1.png", "chip-5-1.png"};

    protected AtStakeSprite sidebet = new AtStakeSprite(X, Y, 0);

    private final int width;
    private Outcome outcome;

    public SideBetView() {
        ImageIcon icon = new ImageIcon(Constant.DIR_IMGS + UP_FILES[0]);
        Image img = icon.getImage();
        this.width = img.getWidth(null);
        this.outcome = Outcome.Superposition;
        LOG.info("side bet view constructed");
    }// SideBetView

    /**
     * Sets the money manager.
     *
     * @param moneyManager
     */
    @Override
    public void setMoneyManager(AMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
        this.buttons = moneyManager.getButtons();
    }// setMoneyManager

    /**
     * Registers a "right" click for the side bet. This method gets invoked on
     * right mouse click.
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    @Override
    public void click(int x, int y) {
        int oldAmt = amt;

        // Test if any chip button has been pressed.
        for (ChipButton button : buttons) {
            if (button.isPressed(x, y)) {
                int n = chips.size();
                int placeX = X + n * width / 3 + ran.nextInt(10) - 10;
                int placeY = Y + ran.nextInt(5) - 25;

                // [Lab 9]
                // If a chip button is clicked, it updates the bet 
                // amount and plays a sound for chips being placed on the 
                // table. Use SoundFactory for this with the CHIPS_IN effect.                
                Chip chip = new Chip(button.getImage(), placeX, placeY, amt);
                chips.add(chip);
                SoundFactory.play(CHIPS_IN);

                amt += button.getAmt();
                LOG.info("A. side bet amount " + button.getAmt() + " updated new amt = " + amt);
            } // if
        } // for

        // Left click!
        if (oldAmt == amt && this.sideBetIsPressed(x, y)) {
            // [Lab 9]
            //
            // If the at-stake area of the table is clicked, it
            // zeros the bet amount, clears chips (e.g., invokes chips),
            // and plays a sound for chips being removed from the table.
            SoundFactory.play(CHIPS_OUT);
            chips.clear();
            amt = 0;

            LOG.info("B. side bet amount cleared");
        } // if

        // [Lab 9] Otherwise ignore button clicks.
    } // click

    /**
     * Informs view the game is over and it's time to update the bankroll for
     * the hand.
     *
     * @param hid Hand id
     */
    @Override
    public void ending(Hid hid) {
        double bet = hid.getSideAmt();

        // [Lab 9] Sidebet outcome is currently unknown
        this.outcome = Outcome.Superposition;

        if (bet == 0) {
            return;
        }// if

        // [Lab 9]
        //
        // Renders highlight “WIN” or “LOSE”, respectively, over
        // the at-stake chips, if the side bet wins or loses respectively
        if (bet > 0) {
            this.outcome = Outcome.Win;
        }// if
        else if (bet < 0) {
            this.outcome = Outcome.Lose;
        }// if

        LOG.info("side bet outcome = " + bet);

        // Update the bankroll
        moneyManager.increase(bet);

        LOG.info("new bankroll = " + moneyManager.getBankroll());
    }// ending

    /**
     * Informs view the game is starting.
     */
    @Override
    public void starting() {
    }// starting

    /**
     * Gets the side bet amount.
     *
     * @return Bet amount
     */
    @Override
    public Integer getAmt() {
        return amt;
    }// getAmt

    /**
     * Updates the view.
     */
    @Override
    public void update() {
    }// update

    /**
     * Renders the view.
     *
     * @param g Graphics context
     */
    @Override
    public void render(Graphics2D g) {
        // [Lab 9] Draw the at-stake place on the table
        g.setColor(Color.RED);
        g.setStroke(dashed);
        g.drawOval(X - DIAMETER / 2, Y - DIAMETER / 2, DIAMETER, DIAMETER);

        // [Lab 9] Draw the at-stake amount
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("" + amt, X - 5, Y + 5);

        // [Lab 9]
        //
        // Renders on the table to the right of the 
        // at-stake “SUPER 7 pays 3:1”, “ROYAL MATCH pays 25:1”
        // and “EXACTLY 13 pays 1:1” in an appropriate color and font.
        g.setFont(sideBetsFont);
        g.setColor(Color.WHITE);
        g.drawString("SUPER 7 pays 3:1", X + 50, Y + 140);
        g.drawString("ROYAL MATCH pays 25:1", X + 50, Y + 120);
        g.drawString("EXACTLY 13 pays 1:1", X + 50, Y + 100);

        // [Lab 9]
        //
        // Renders the chips to the right of the at-
        // stake area using randomness to simulate chips being 
        // placed on the table as Charlie does for the main bet. 
        for (int i = 0; i < chips.size(); i++) {
            Chip chip = chips.get(i);
            chip.render(g);
        }// for

        // [Lab 9]
        //
        // Renders highlight “WIN” or “LOSE”, respectively, over
        // the at-stake chips, if the side bet wins or loses respectively            
        String outcomeText = "";
        if (outcome != Outcome.Superposition) {
            outcomeText += " " + outcome.toString().toUpperCase() + " ! ";
        }// if

        FontMetrics fm = g.getFontMetrics(outcomeFont);
        int w = fm.charsWidth(outcomeText.toCharArray(), 0, outcomeText.length());
        int h = fm.getHeight();

        // Lost           
        if (this.outcome == Outcome.Lose) {
            g.setColor(looseColorBg);
        }// if
        // Won
        else {
            g.setColor(winColorBg);
        }// else 

        // Fill background
        g.fillRoundRect(X, Y - h + 5, w, h, 5, 5);

        // Loss          
        if (this.outcome == Outcome.Win) {
            g.setColor(looseColorFg);
        } // if 
        // Won
        else {
            g.setColor(winColorFg);
        } // else

        // Fill Foreground
        g.setFont(outcomeFont);
        g.drawString(outcomeText, X, Y);

        this.moneyManager.render(g);
    }// render

    private boolean sideBetIsPressed(int x, int y) {
        int r = DIAMETER / 2;
        return (x < X + r && x > X - r) && (y < r && y > Y - r);
    }// sideBetIsPressed
}// SideBetView
