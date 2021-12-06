package charlie.sidebet.test;
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



import charlie.card.Card;

/**
 * A unit test shoe
 * @author Ron.Coleman
 */
public class Shoe extends charlie.card.Shoe {
	/**
	 * Initializes the shoe.
	 */
    @Override
    public void init() {
        cards.clear();
        this.testCase1();
        this.testCase2();
        this.testCase3();
        this.testCase4();
        this.testCase5();
        this.testCase6();
        this.testCase7();
        this.testCase8();
        this.testCase9();
        this.testCase10();
    }
    
    private void testCase1() {
        cards.add(new Card(7,Card.Suit.HEARTS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        cards.add(new Card(9,Card.Suit.SPADES));
        cards.add(new Card(9,Card.Suit.DIAMONDS));
        cards.add(new Card(3,Card.Suit.CLUBS));
    }// testCase1
    
    private void testCase2() {
        cards.add(new Card(7,Card.Suit.HEARTS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        cards.add(new Card(9,Card.Suit.SPADES));
        cards.add(new Card(8,Card.Suit.DIAMONDS));
        cards.add(new Card(3,Card.Suit.CLUBS));
    }// testCase2
    
    private void testCase3() {
        cards.add(new Card(9,Card.Suit.HEARTS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        cards.add(new Card(7,Card.Suit.SPADES));
        cards.add(new Card(8,Card.Suit.DIAMONDS));
        cards.add(new Card(3,Card.Suit.CLUBS));
    }// testCase3
    
    private void testCase4() {
        cards.add(new Card(7,Card.Suit.HEARTS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        cards.add(new Card(9,Card.Suit.SPADES));
        cards.add(new Card(10,Card.Suit.DIAMONDS));
        cards.add(new Card(3,Card.Suit.CLUBS));
    }// testCase4
    
    private void testCase5() {
        cards.add(new Card(9,Card.Suit.HEARTS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        cards.add(new Card(7,Card.Suit.SPADES));
        cards.add(new Card(10,Card.Suit.DIAMONDS));
        cards.add(new Card(3,Card.Suit.CLUBS));
    }// testCase5
    
    private void testCase6() {
        cards.add(new Card(Card.KING,Card.Suit.HEARTS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        cards.add(new Card(Card.QUEEN,Card.Suit.HEARTS));
        cards.add(new Card(8,Card.Suit.DIAMONDS));
    }// testCase6
    
    private void testCase7() {
        cards.add(new Card(Card.KING,Card.Suit.HEARTS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        cards.add(new Card(Card.QUEEN,Card.Suit.SPADES));
        cards.add(new Card(8,Card.Suit.DIAMONDS));
    }// testCase7
    
    private void testCase8() {
        cards.add(new Card(8,Card.Suit.HEARTS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        cards.add(new Card(5,Card.Suit.SPADES));
        cards.add(new Card(6,Card.Suit.DIAMONDS));
        cards.add(new Card(Card.KING,Card.Suit.DIAMONDS));
    }// testCase8
    
    private void testCase9() {
        cards.add(new Card(7,Card.Suit.HEARTS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        cards.add(new Card(6,Card.Suit.SPADES));
        cards.add(new Card(6,Card.Suit.DIAMONDS));
        cards.add(new Card(Card.KING,Card.Suit.DIAMONDS));
    }// testCase9
    
    private void testCase10() {
        cards.add(new Card(6,Card.Suit.HEARTS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        cards.add(new Card(8,Card.Suit.SPADES));
        cards.add(new Card(6,Card.Suit.DIAMONDS));
        cards.add(new Card(Card.KING,Card.Suit.DIAMONDS));
    }// testCase10
    
    /**
     * Returns true if shuffle needed.
     * @return True if shuffle needed, false otherwise.
     */
    @Override
    public boolean shuffleNeeded() {
        return false;
    }
}