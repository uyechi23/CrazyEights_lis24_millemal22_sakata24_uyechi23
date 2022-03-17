package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights;

import junit.framework.TestCase;

import org.junit.Test;

public class CardTest extends TestCase {

    /**
     * testSetValue
     *
     * Verifies that the value of the card based on it's face value is
     * being set correctly
     *
     * @return void
     */
    @Test
    public void testSetValue() {
        // general situation
        Card card1 = new Card("Queen", "Hearts");
        card1.setValue("Queen");
        assertEquals(10, card1.value);
    }

    /**
     * testMatchSuit
     *
     * Verifies that cards with matching suits are being compared correctly
     *
     * @return void
     */
    @Test
    public void testMatchSuit() {
        // general situation
        Card card1 = new Card("Queen", "Hearts");
        Card card2 = new Card("Jack", "Hearts");
        assertTrue(card1.matchSuit(card2));
        // invalid suit
        Card card3 = new Card("Queen", "Tuxedo ");
        Card card4 = new Card("Jack", "Hearts");
        assertFalse(card3.matchSuit(card4));
    }

    /**
     * testMatchFace
     *
     * Verifies that cards with matching faces are being compared correctly
     *
     * @return void
     */
    @Test
    public void testMatchFace() {
        // general situation
        Card card1 = new Card("Queen", "Diamonds");
        Card card2 = new Card("Queen", "Hearts");
        assertTrue(card1.matchFace(card2));
        // invalid face
        Card card3 = new Card("adsf ", "Hearts");
        Card card4 = new Card("Queen", "Hearts");
        assertFalse(card3.matchFace(card4));
    }

    /**
     * testIsValid
     *
     * Verifies that the two cards being played are valid
     *
     * @return void
     */
    @Test
    public void testIsValid() {
        // general situation
        Card card1 = new Card("Queen", "Hearts");
        Card card2 = new Card("Jack", "Hearts");
        assertTrue(card1.isValid(card2));
        // invalid face
        Card card3 = new Card("King", "Diamonds");
        Card card4 = new Card("Ace", "Spades");
        assertFalse(card3.isValid(card4));
    }
}