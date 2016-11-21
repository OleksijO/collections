package training.collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by oleksij.onysymchuk@gmail on 19.11.2016.
 */
public class MyArrayListImplTest {
    private List<Integer> emptyList;
    private List<Integer> listWithThreeElements = new MyArrayListImpl<>(3);
    private List<Integer> listWithThreeElementsOneIsNull = new MyArrayListImpl<>(3);
    private List<Integer> listWithOneNullElement = new MyArrayListImpl<>(1);
    private Integer[] empty;
    private Integer[] oneNull;
    private Integer[] array3NotNull;
    private Integer[] array3OneNull;

    @Before
    public void init() {
        emptyList = new MyArrayListImpl<>();
        listWithThreeElements.add(1);
        listWithThreeElements.add(2);
        listWithThreeElements.add(3);
        listWithThreeElementsOneIsNull.add(1);
        listWithThreeElementsOneIsNull.add(null);
        listWithThreeElementsOneIsNull.add(3);
        listWithOneNullElement.add(null);

        empty = new Integer[0];
        oneNull = new Integer[1];
        oneNull[0] = null;
        array3NotNull = new Integer[3];
        array3NotNull[0] = 1;
        array3NotNull[1] = 2;
        array3NotNull[2] = 3;
        array3OneNull = new Integer[3];
        array3OneNull[0] = 1;
        array3OneNull[1] = null;
        array3OneNull[2] = 3;
    }

    @Test
    public void testMyArrayListCreation() {
        Assert.assertNotNull("1. Not null", emptyList);
    }

    @Test
    public void testSize() throws Exception {
        assertEquals("1. Size=0", 0, emptyList.size());
        assertEquals("2. Size=3", 3, listWithThreeElements.size());
        assertEquals("3. Size=3", 3, listWithThreeElementsOneIsNull.size());
        listWithThreeElementsOneIsNull.remove(0);
        assertEquals("4. Size=2 after removing", 2, listWithThreeElementsOneIsNull.size());
        listWithThreeElementsOneIsNull.add(0);
        assertEquals("5. Size=3 after adding", 3, listWithThreeElementsOneIsNull.size());
        assertEquals("6. Size=1 with one null element", 1, listWithOneNullElement.size());
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue("1. Empty", emptyList.isEmpty());
        assertFalse("2. Not Empty", listWithThreeElements.isEmpty());
        assertFalse("3. Not Empty", listWithOneNullElement.isEmpty());
    }

    @Test
    public void testContains() throws Exception {
        assertFalse("1. Empty", emptyList.contains(null));
        assertFalse("2. Empty", emptyList.contains(0));
        assertFalse("3. Non empty not null not contains", listWithThreeElements.contains(0));
        assertTrue("4. Non empty not null contains", listWithThreeElements.contains(2));
        assertFalse("5. Non empty not null not contains null", listWithThreeElements.contains(null));
        assertTrue("6. Non empty with null contains null", listWithThreeElementsOneIsNull.contains(null));
    }

    @Test
    public void testIterator() throws Exception {
        Iterator<Integer> iterator = listWithThreeElementsOneIsNull.iterator();
        assertTrue("1", iterator.hasNext());
        assertEquals("2", new Integer(1), iterator.next());
        assertTrue("3", iterator.hasNext());
        assertEquals("4", null, iterator.next());
        assertTrue("5", iterator.hasNext());
        assertEquals("6", new Integer(3), iterator.next());
        assertFalse("7", iterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorException() throws Exception {
        Iterator<Integer> iterator = listWithThreeElementsOneIsNull.iterator();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
    }

    @Test
    public void testToArray() throws Exception {
        assertArrayEquals("1. Empty", empty, emptyList.toArray());
        assertArrayEquals("2. One null element", oneNull, listWithOneNullElement.toArray());
        assertArrayEquals("3. not empty without null", array3NotNull, listWithThreeElements.toArray());
        assertArrayEquals("4. not empty with null", array3NotNull, listWithThreeElements.toArray());
    }

    @Test
    public void testToArray1() throws Exception {
        Object testArray[];
        testArray = emptyList.toArray();
        assertArrayEquals("1. Empty", empty, testArray);
        testArray = listWithOneNullElement.toArray();
        assertArrayEquals("2. One null element", oneNull, testArray);
        testArray = listWithThreeElements.toArray();
        assertArrayEquals("3. not empty without null", array3NotNull, testArray);
        testArray = listWithThreeElements.toArray();
        assertArrayEquals("4. not empty with null", array3NotNull, testArray);
    }

    @Test
    public void testAdd() throws Exception {
        emptyList.add(42);
        assertEquals("1. added not null to empty list", 1, emptyList.size());
        assertEquals("2. retrieved the same element", new Integer(42), emptyList.get(0));
        emptyList.add(null);
        assertEquals("1. added null list", 2, emptyList.size());
        assertNull("2. retrieved the same null", emptyList.get(1));
    }

    @Test
    public void testRemoveNull() throws Exception {
        listWithOneNullElement.remove(null);
        assertEquals("1. removed null", 0, listWithOneNullElement.size());
        assertFalse("2. removed not in list", listWithOneNullElement.contains(null));
    }

    @Test
    public void testRemoveNotNull() throws Exception {
        listWithThreeElementsOneIsNull.remove(new Integer(1));
        assertEquals("1. removed null", 2, listWithThreeElementsOneIsNull.size());
        assertEquals("2. check 1st elem after remove", null, listWithThreeElementsOneIsNull.get(0));
        assertEquals("2. check 1nd elem after remove", new Integer(3), listWithThreeElementsOneIsNull.get(1));

        listWithThreeElementsOneIsNull.remove(new Integer(3));
        assertEquals("2. check 1nd elem after 2 next remove", null, listWithThreeElementsOneIsNull.get(0));
    }

    @Test
    public void testContainsAll() throws Exception {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        List<Integer> list2 = new LinkedList<>();
        list2.add(2);
        System.out.println(list1.equals(list2));
    }

    @Test
    public void testAddAll() throws Exception {
        List<Integer> testList = new ArrayList<>(listWithThreeElementsOneIsNull);
        testList.addAll(listWithThreeElements);
        listWithThreeElementsOneIsNull.addAll(listWithThreeElements);

        assertEquals(testList, listWithThreeElementsOneIsNull);
    }

    @Test
    public void testAddAll1() throws Exception {
        List<Integer> testList = new ArrayList<>(listWithThreeElementsOneIsNull);
        testList.addAll(2, listWithThreeElements);
        listWithThreeElementsOneIsNull.addAll(2, listWithThreeElements);
        assertEquals("1. At the end", testList, listWithThreeElementsOneIsNull);

        testList.addAll(3, listWithThreeElements);
        listWithThreeElementsOneIsNull.addAll(3, listWithThreeElements);
        assertEquals("2. at the middle", testList, listWithThreeElementsOneIsNull);

        testList.addAll(0, listWithThreeElements);
        listWithThreeElementsOneIsNull.addAll(0, listWithThreeElements);
        assertEquals("3. at the begin", testList, listWithThreeElementsOneIsNull);
    }

    @Test
    public void testRemoveAll() throws Exception {
        List<Integer> testList = new ArrayList<>(listWithThreeElementsOneIsNull);
        listWithThreeElementsOneIsNull.add(4);
        listWithThreeElementsOneIsNull.add(5);
        listWithThreeElementsOneIsNull.add(6);
        listWithThreeElementsOneIsNull.removeAll(testList);
        testList.clear();
        testList.add(4);
        testList.add(5);
        testList.add(6);
        assertEquals("", testList, listWithThreeElementsOneIsNull);

    }

    @Test
    public void testRetainAll() throws Exception {
        List<Integer> testList = new ArrayList<>(listWithThreeElementsOneIsNull);
        listWithThreeElementsOneIsNull.add(4);
        listWithThreeElementsOneIsNull.add(5);
        listWithThreeElementsOneIsNull.add(6);
        listWithThreeElementsOneIsNull.retainAll(testList);
        testList.clear();
        testList.add(1);
        testList.add(null);
        testList.add(3);
        System.out.println(testList);
        System.out.println(listWithThreeElementsOneIsNull);

        assertEquals("", testList, listWithThreeElementsOneIsNull);
    }

    @Test
    public void testClear() throws Exception {
        listWithThreeElementsOneIsNull.clear();
        for (Object i : ((MyArrayListImpl) listWithThreeElementsOneIsNull).getInnerArray()) {
            assertNull(i);
        }
    }

    @Test
    public void testGet() throws Exception {
        assertEquals(new Integer(2), listWithThreeElements.get(1));
    }

    @Test
    public void testSet() throws Exception {
        listWithThreeElementsOneIsNull.set(1,2);
        assertEquals(new Integer(2), listWithThreeElementsOneIsNull.get(1));
    }

    @Test
    public void testAddToPosition() throws Exception {
        List<Integer> testList = new ArrayList<>(listWithThreeElementsOneIsNull);
        testList.add(1,2);
        listWithThreeElementsOneIsNull.add(1,2);
        assertEquals(testList, listWithThreeElementsOneIsNull);
    }

    @Test
    public void testRemoveByIndex() throws Exception {
        List<Integer> testList = new ArrayList<>(listWithThreeElementsOneIsNull);
        testList.remove(1);
        listWithThreeElementsOneIsNull.remove(1);
        assertEquals(testList, listWithThreeElementsOneIsNull);
    }

    @Test
    public void testIndexOf() throws Exception {
        assertEquals("1. No such element", -1, listWithThreeElementsOneIsNull.indexOf(5));
        assertEquals("2. Has element", 2, listWithThreeElementsOneIsNull.indexOf(3));
        assertEquals("3. Has element", 0, listWithThreeElementsOneIsNull.indexOf(1));
        assertEquals("4. Has null element", 1, listWithThreeElementsOneIsNull.indexOf(null));
        assertEquals("5. Has no null element", -1, listWithThreeElements.indexOf(null));
    }


    @Test
    public void testLastIndexOf() {
        assertEquals("1. No such element", -1, listWithThreeElementsOneIsNull.lastIndexOf(5));
        assertEquals("2. Has element", 2, listWithThreeElementsOneIsNull.lastIndexOf(3));
        assertEquals("3. Has element", 0, listWithThreeElementsOneIsNull.lastIndexOf(1));
        assertEquals("4. Has null element", 1, listWithThreeElementsOneIsNull.lastIndexOf(null));
        assertEquals("5. Has no null element", -1, listWithThreeElements.lastIndexOf(null));
    }

    @Test
    public void testListIterator() throws Exception {
        ListIterator<Integer> iterator = listWithThreeElementsOneIsNull.listIterator();
        assertTrue("1", iterator.hasNext());
        assertFalse("2", iterator.hasPrevious());
        assertEquals("3", new Integer(1), iterator.next());
        assertTrue("4", iterator.hasNext());
        assertTrue("5", iterator.hasPrevious());
        assertEquals("6", null, iterator.next());
        assertTrue("7", iterator.hasNext());
        assertTrue("8", iterator.hasPrevious());
        assertEquals("9", new Integer(3), iterator.next());
        assertTrue("10", iterator.hasPrevious());
        assertFalse("11", iterator.hasNext());
        assertEquals("12", new Integer(3), iterator.previous());
        assertTrue("13", iterator.hasNext());
        assertTrue("14", iterator.hasPrevious());
        assertEquals("15", new Integer(3), iterator.next());
    }

    @Test(expected = NoSuchElementException.class)
    public void testListIteratorException() throws Exception {
        ListIterator<Integer> iterator = listWithThreeElementsOneIsNull.listIterator();
        iterator.next();
        iterator.next();
        iterator.previous();
        iterator.previous();
        iterator.previous();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testListIteratorConcurrentException() throws Exception {
        for (Integer i : listWithThreeElementsOneIsNull) {
            listWithThreeElementsOneIsNull.remove(i);
        }
    }


    @Test(expected = ConcurrentModificationException.class)
    public void testListIteratorMustBeConcurrentException() throws Exception {
        List<Integer> arrayList = new MyArrayListImpl<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);
        int counter = 0;
        for (Integer i : arrayList) {
            counter++;
            if (counter == 3 ) {
                arrayList.remove(arrayList.indexOf(i));
            }
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testListIteratorConcurrentException2() throws Exception {
        for (Integer i : listWithThreeElementsOneIsNull) {
            if (i == null) {
                listWithThreeElementsOneIsNull.add(i);
            }
        }
    }

    @Test
    public void testListIterator1() throws Exception {
        ListIterator<Integer> iterator = listWithThreeElementsOneIsNull.listIterator(1);
        assertTrue("1", iterator.hasNext());
        assertTrue("2", iterator.hasPrevious());
        assertEquals("3", null, iterator.next());
    }

    @Test
    public void testSubList() throws Exception {
        List<Integer> testList = new ArrayList<>(listWithThreeElementsOneIsNull);
        List<Integer> subTestList = testList.subList(0, 1);
        List<Integer> listToAssert = listWithThreeElementsOneIsNull.subList(0, 1);
        assertEquals("1. One elem", subTestList.size(), listToAssert.size());
        assertEquals("1.1. One elem", subTestList.get(0), listToAssert.get(0));

        subTestList = testList.subList(0, 3);
        listToAssert = listWithThreeElementsOneIsNull.subList(0, 3);
        assertEquals("2. all elem", subTestList, listToAssert);
        assertEquals("2.1. all elem", subTestList.get(1), listToAssert.get(1));

        subTestList = testList.subList(1, 2);
        listToAssert = listWithThreeElementsOneIsNull.subList(1, 2);
        assertEquals("3. One null elem", subTestList, listToAssert);

        subTestList = testList.subList(1, 3);
        listToAssert = listWithThreeElementsOneIsNull.subList(1, 3);
        assertEquals("4. two elem", subTestList, listToAssert);
        assertEquals("4.1. two elem", subTestList.get(1), listToAssert.get(1));

        subTestList.remove(0);
        listToAssert.remove(0);
        assertEquals("5.1. original array changed after changes in sub",testList, listWithThreeElementsOneIsNull);
        assertEquals("5.1. original array changed after changes in sub",2, listWithThreeElementsOneIsNull.size());

        subTestList.add(4);
        listToAssert.add(4);
        subTestList.add(0,6);
        listToAssert.add(0,6);
        assertEquals("5.3. original array changed after changes in sub",testList, listWithThreeElementsOneIsNull);
        assertEquals("5.4. original array changed after changes in sub",4, listWithThreeElementsOneIsNull.size());
    }


    @Test
    public void testEquals() throws Exception {
        List<Integer> testList = new ArrayList<>(listWithThreeElementsOneIsNull);
        assertTrue("True", listWithThreeElementsOneIsNull.equals(testList));
        listWithThreeElementsOneIsNull.add(42);
        assertFalse("False", listWithThreeElementsOneIsNull.equals(testList));
    }

}