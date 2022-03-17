package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights;

import junit.framework.TestCase;

import org.junit.Test;

public class CardTest extends TestCase {

    @Test
    public void testSetValue() {
        // general situation
        Card card1 = new Card("Queen", "Hearts");
        card1.setValue("Queen");
        assertEquals(10, card1.value);
    }

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

    @Test
    public void testIsValid() {

    }
}