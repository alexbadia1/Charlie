/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlie.server.bot;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Dealer;
import charlie.dealer.Seat;
import charlie.plugin.IBot;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 * Huey is a player that will always stay.
 *
 * @author Alex Badia
 */
public class HueyStay implements IBot, Runnable {

    protected final int MAX_THINKING = 5;
    protected Seat mine;
    protected Hand myHand;
    protected Dealer dealer;
    protected Random ran = new Random();
    protected Logger LOG = null;

    public HueyStay() {
        LOG = Logger.getLogger(HueyStay.class);
    }// HueyStay

    /**
     * [Lab 10]
     *
     * In getHand, return myHand;
     *
     * @return Hand
     */
    @Override
    public Hand getHand() {
        return this.myHand;
    } // getHand

    /**
     * [Lab 10]
     *
     * In setDealer, set the instance member, dealer, to the formal parameter
     * dealer.
     *
     * @param dealer Dealer
     */
    @Override
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    } // setDealer

    /**
     * [Lab 10]
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
    } // endGame

    @Override
    public void deal(Hid hid, Card card, int[] values) {
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
    } // blackjack

    @Override
    public void charlie(Hid hid) {
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
     * Starts a new thread for HueyStay bot.
     *
     * @param hid Hid
     */
    @Override
    public void play(Hid hid) {
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

        // Models a human playing by waiting (thinking) before
        // submitting a play. Try-catch block because of Thread.sleep.
        try {
            int thinking = ran.nextInt(MAX_THINKING * 1000);
            Thread.sleep(thinking);
            
            
            // TODO: IBot is not using the Basic Strategy. 
            //   It ignores it and always stays which would not play
            //   like a human! In a subsequent assignment, you address this.
            dealer.stay(this, myHand.getHid());
        } // try
        catch (Exception e) {
            this.LOG.info(e);
        } //catch
    } // run
} // HueyStay
