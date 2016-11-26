package training.collection;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by oleksij.onysymchuk@gmail on 26.11.2016.
 */
public class MyTreeSetTest {
    private MyTreeSet<Integer> testSet;

    @Before
    public void init() {
        testSet = new MyTreeSet();
    }

    @Test
    public void testInitialSize() {
        assertEquals(0, testSet.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue("1.", testSet.isEmpty());
        testSet.add(1);
        assertFalse("2.", testSet.isEmpty());
        testSet.remove(1);
        assertTrue("3.", testSet.isEmpty());
    }

    @Test
    public void testSize() {
        testSet.add(1);
        assertEquals("1.",1, testSet.size());
        testSet.add(2);
        assertEquals("2.",2, testSet.size());
        testSet.remove(2);
        assertEquals("3.",1, testSet.size());
        testSet.clear();
        assertEquals("4.",0, testSet.size());
    }


    @Test
    public void testAddContains() {
        addNumbers_1_20();
        assertTrue("20.",testSet.contains(1));
        assertTrue("21.",testSet.contains(2));
        assertTrue("22.",testSet.contains(3));
        assertTrue("23.",testSet.contains(4));
        assertTrue("24.",testSet.contains(5));
        assertTrue("25.",testSet.contains(6));
        assertTrue("25.1.",testSet.contains(7));
        assertTrue("26.",testSet.contains(8));
        assertTrue("27.",testSet.contains(9));
        assertTrue("28.",testSet.contains(10));
        assertTrue("29.",testSet.contains(11));
        assertTrue("30.",testSet.contains(12));
        assertTrue("31.",testSet.contains(13));
        assertTrue("32.",testSet.contains(14));
        assertTrue("33.",testSet.contains(15));
        assertTrue("34.",testSet.contains(16));
        assertTrue("35.",testSet.contains(17));
        assertTrue("36.",testSet.contains(18));
        assertTrue("37.",testSet.contains(19));
        assertTrue("38.",testSet.contains(20));
        assertFalse("39.",testSet.contains(21));
        assertFalse("40.",testSet.contains(0));
   }

    @Test
    public void testRemoveContains() {
        addNumbers_1_20();
        removeNumbers_1_20();
        assertFalse("19.1.remove", testSet.remove(1));
        assertFalse("20.",testSet.contains(1));
        assertFalse("21.",testSet.contains(2));
        assertFalse("22.",testSet.contains(3));
        assertFalse("23.",testSet.contains(4));
        assertFalse("24.",testSet.contains(5));
        assertFalse("25.",testSet.contains(6));
        assertFalse("25.1.",testSet.contains(7));
        assertFalse("26.",testSet.contains(8));
        assertFalse("27.",testSet.contains(9));
        assertFalse("28.",testSet.contains(10));
        assertFalse("29.",testSet.contains(11));
        assertFalse("30.",testSet.contains(12));
        assertFalse("31.",testSet.contains(13));
        assertFalse("32.",testSet.contains(14));
        assertFalse("33.",testSet.contains(15));
        assertFalse("34.",testSet.contains(16));
        assertFalse("35.",testSet.contains(17));
        assertFalse("36.",testSet.contains(18));
        assertFalse("37.",testSet.contains(19));
        assertFalse("38.",testSet.contains(20));
        assertFalse("39.",testSet.contains(21));
        assertFalse("40.",testSet.contains(0));
    }
   
   private void addNumbers_1_20(){
       assertTrue("1.", testSet.add(10));
       assertTrue("2.", testSet.add(5));
       assertTrue("3.", testSet.add(15));
       assertTrue("4.", testSet.add(20));
       assertTrue("5.", testSet.add(12));
       assertTrue("6.", testSet.add(6));
       assertTrue("7.", testSet.add(4));
       assertTrue("8.", testSet.add(18));
       assertTrue("9.", testSet.add(19));
       assertTrue("10.1", testSet.add(7));
       assertTrue("10.", testSet.add(17));
       assertTrue("11.", testSet.add(11));
       assertTrue("12.", testSet.add(2));
       assertTrue("13.", testSet.add(13));
       assertTrue("14.", testSet.add(16));
       assertTrue("15.", testSet.add(8));
       assertTrue("16.", testSet.add(1));
       assertTrue("17.", testSet.add(3));
       assertTrue("18.", testSet.add(9));
       assertTrue("19.", testSet.add(14));
   }
   
   private void removeNumbers_1_20(){
       assertTrue("1.remove", testSet.remove(10));
       assertTrue("2.remove", testSet.remove(5));
       assertTrue("3.remove", testSet.remove(15));
       assertTrue("4.remove", testSet.remove(20));
       assertTrue("5.remove", testSet.remove(12));
       assertTrue("6.remove", testSet.remove(6));
       assertTrue("7.remove", testSet.remove(4));
       assertTrue("8.remove", testSet.remove(18));
       assertTrue("9.remove", testSet.remove(19));
       assertTrue("10.1.remove", testSet.remove(7));
       assertTrue("10.remove", testSet.remove(17));
       assertTrue("11.remove", testSet.remove(11));
       assertTrue("12.remove", testSet.remove(2));
       assertTrue("13.remove", testSet.remove(13));
       assertTrue("14.remove", testSet.remove(16));
       assertTrue("15.remove", testSet.remove(8));
       assertTrue("16.remove", testSet.remove(1));
       assertTrue("17.remove", testSet.remove(3));
       assertTrue("18.remove", testSet.remove(9));
       assertTrue("19.remove", testSet.remove(14));
   }



}
