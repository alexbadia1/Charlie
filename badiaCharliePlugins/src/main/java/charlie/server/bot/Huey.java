package charlie.server.bot;

import charlie.card.Card;
import charlie.card.Hid;
import charlie.dealer.Dealer;
import charlie.dealer.Seat;
import charlie.plugin.IBot;
import java.util.List;

import charlie.card.Hand;
import charlie.client.BotBasicStrategy;
import charlie.plugin.IPlayer;
import charlie.util.Play;
import java.util.Random;

/**
 * [Phase 1]
 * 
 * Bot playing the basic strategy for blackjack.
 * 
 * @author Alex Badia
 */
public class Huey implements IBot, Runnable {

    /**
     * Pseudo random delay (in seconds) simulating the bot "thinking."
     */
    protected Random ran = new Random();

    /**
     * The max amount of delay in seconds for "thinking."
     */
    protected final int MAX_THINKING = 5;

    /**
     * Seat at table: left | right
     */
    protected Seat mine;

    /**
     * Local instance of Huey's current hand.
     */
    protected Hand myHand;

    /**
     * Local instance of the dealer
     */
    protected Dealer dealer;

    /**
     * Dealer's up card
     */
    protected Card dealerUpCard;

    /**
     * Basic Strategy for Blackjack, split not supported.
     */
    protected BotBasicStrategy bbs = new BotBasicStrategy();

    /**
     * [Phase 2]
     * 
     * Send only valid plays for a hand id. For instance, 
     * do not send a double-down after the first hit request.
     */
    protected boolean canDoubleDown = false;

    /**
     * [Phase 2]
     *
     * In getHand, return myHand;
     *
     * @return myHand Hand
     */
    @Override
    public Hand getHand() {
        return this.myHand;
    } // getHand

    /**
     * [Phase 2]
     *
     * Set the instance member, dealer, to the formal parameter dealer.
     *
     * @param dealer Dealer
     */
    @Override
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    } // setDealer

    /**
     * [Phase 2]
     *
     * Setup up the HueyStay seat and hand
     *
     * @param seat
     */
    @Override
    public void sit(Seat seat) {
        // Set the instance member, mine , to the formal parameter seat.
        this.mine = seat;

        // Construct a new hand id or Hid instance called, hid, using seat.
        Hid hid = new Hid(seat);

        // Construct a new Hand using hid and set it to the member myHand.
        this.myHand = new Hand(hid);
    } // sit

    @Override
    public void startGame(List<Hid> hids, int shoeSize) {
    } // startGame

    @Override
    public void endGame(int shoeSize) {
        // [Phase 2]
        //
        // Don't invoke System.exit as this stops the server.
    } // endGame

    /**
     * Traps cards from dealer to players and dealer itself
     *
     * @param hid Hid
     * @param card Card
     * @param values int[]
     */
    @Override
    public void deal(Hid hid, Card card, int[] values) {
        Seat seat = hid.getSeat();

        // [Phase 2]
        //
        // Don't subvert the system, e.g., don’t look 
        // at the hole card before Dealer reveals it.
        //
        // Note: Dealer's up card, hole gets overwritten
        if (seat == Seat.DEALER) {
            this.dealerUpCard = card;
        } // if
        
        // [Phase 2]
        //
        // Always respond to Dealer by spawning
        // or waking up a worker thread which ends 
        // immediately after it sends its request to Dealer.
        this.play(hid);
    } // deal

    @Override
    public void insure() {
    } // insure

    @Override
    public void bust(Hid hid) {
    } // bust

    @Override
    public void win(Hid hid) {
    } // win

    @Override
    public void blackjack(Hid hid) {
        // [Phase 2]
        //
        // If IBot gets a Blackjack or Charlie,
        // it does not get another deal for its hand,
        // although it may receive cards for other players.
    } // blackjack

    @Override
    public void charlie(Hid hid) {
        // [Phase 2]
        //
        // If IBot gets a Blackjack or Charlie,
        // it does not get another deal for its hand,
        // although it may receive cards for other players.
    } // charlie

    @Override
    public void lose(Hid hid) {
    } // lose

    @Override
    public void push(Hid hid) {
    } // push

    @Override
    public void shuffling() {
    } // shuffling

    /**
     * [Phase 2]
     *
     * Starts a new thread for Huey bot.
     *
     * @param hid Hid
     */
    @Override
    public void play(Hid hid) {
        // [Phase 2]
        //
        // Do not make plays before or after the bot’s turn.
        if (hid.getSeat() != mine) {
            return;
        } // if

        new Thread(this).start();
    } // play

    @Override
    public void split(Hid newHid, Hid origHid) {
    } // split

    @Override
    public void run() {
        try {
            // [Phase 2]
            //
            // Have the bot play like a human, namely, add delay
            // to decisions, otherwise, things will happen too fast
            // and we won't be able to see or know what it really did or
            // why. Any delay should be randomized on average 2.5 seconds.
            int thinking = ran.nextInt(MAX_THINKING * 1000);
            Thread.sleep(thinking);
            
            // Calculates play according to the Basic Strategy modified for a bot.
            Play play = bbs.getPlay(this.myHand, this.dealerUpCard);
            
            // [Phase 2]
            //
            // Send one play at a time.
            switch (play) {
                case STAY -> botStay(this, myHand.getHid());
                case DOUBLE_DOWN -> {
                    if (this.canDoubleDown) {
                        botDoubleDown(this, myHand.getHid());
                    } // if
                    else {
                        botStay(this, myHand.getHid());
                    } // else
                } // case
                case HIT -> botHit(this, myHand.getHid());
                default -> botStay(this, myHand.getHid());
            }// switch
        } // try
        catch (InterruptedException e) {
            // [Phase 2]
            //
            // Do not send requests after the bot 
            // stays, breaks, gets a Blackjack, or Charlie.
        } //catch
    } // run
    
    /**
     * [Phase 2]
     * 
     * If STAY is successful, IBot does not
     * get another deal to its hand, although 
     * it may receive cards for other players.
     * 
     * @param iplayer IPlayer
     * @param hid Hid
     */
    protected void botStay (IPlayer iplayer, Hid hid) {
        // Stay
        this.dealer.stay(this, myHand.getHid());
        
        // Re-enable double down
        this.canDoubleDown = true;
    } // botStay
    
    /**
     * [Phase 2]
     * 
     * If DOUBLE DOWN is successful, IBot 
     * does not get another card for its hand, 
     * although it may receive cards for other players.
     * 
     * @param iplayer IPlayer
     * @param hid Hid
     */
    protected void botDoubleDown (IPlayer iplayer, Hid hid) {
        // [Phase 2]
        //
        // Do not change the bet amount or side bet 
        // amount after the game has started. On double-down
        // Dealer automatically invokes dubble on the Hand instance,
        // ie, there’s no need for the bot to invoke dubble.
        //
        // Not sure if I should call this or not...
        this.dealer.doubleDown(this, myHand.getHid());
        
        // Re-enable double down,
        // this is technically defensive code...
        this.canDoubleDown = true;
    } // botDoubleDown
    
    /**
     * [Phase 2]
     * 
     * If HIT is successful, IBot may get 
     * another card for its hand or it may 
     * not if it breaks; if it breaks, the 
     * bot may receive cards for other players.
     * 
     * @param iplayer IPlayer
     * @param hid Hid
     */
    protected void botHit (IPlayer iplayer, Hid hid) {
        // Hit
        this.dealer.hit(this, myHand.getHid());
        
        // Disable double down
        this.canDoubleDown = false;
    } // botHit
} // Huey
