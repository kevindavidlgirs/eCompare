package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import miniproject.InvalidTransferException;
import miniproject.Model;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModelTest {
    
    private static final List<String> TEST_LIST = Arrays.asList(
            "Réussir les tests unitaires",
            "Modulariser mon code",
            "Remettre fièrement mon projet"
    );
    
    private Model model;
    
    @Before
    public void setUp() {
        model = new Model(TEST_LIST);
    }
       
    @Test
    public void testToDoListNotEmptyAfterConstruction() {
        assertFalse(model.getToDoList().isEmpty());
    }

    @Test
    public void testDoneListEmptyAfterConstruction() {
        assertTrue(model.getDoneList().isEmpty());
    }

    @Test
    public void testToDoListInit() {
        assertEquals(TEST_LIST, model.getToDoList());
    }
 
    @Test
    public void testDefaultConstructor() {
        Model aux = new Model();
        assertTrue(aux.getToDoList().isEmpty());
        assertTrue(aux.getDoneList().isEmpty());
    }
  
    @Test
    public void testInitWithSmallElems() {
        Model aux = new Model(Arrays.asList("", "a", "aa", "aaa", "aa"));
        assertEquals(Arrays.asList("aaa"), aux.getToDoList());
    }
    
 
    @Test
    public void testInitWithDoubles() {
        Model aux = new Model(Arrays.asList("aaa", "bbb", "aaa"));
        assertEquals(Arrays.asList("aaa", "bbb"), aux.getToDoList());
    }
       
    @Test(expected = Exception.class)
    public void testToDoListImmutable() {
        model.getToDoList().add("Erreur");
    }
      
    @Test(expected = Exception.class)
    public void testDoneListImmutable() {
        model.getDoneList().add("Erreur");
    }
  
    @Test
    public void testSetDoneSimple() {
        int initSize = model.getToDoList().size();                
        model.setDone(0);
        assertEquals(initSize - 1, model.getToDoList().size());
        assertEquals(1, model.getDoneList().size());
        assertEquals(TEST_LIST.get(0), model.getDoneList().get(0));
        assertEquals(TEST_LIST.get(1), model.getToDoList().get(0));
    }

    @Test
    public void testSetDoneAll() {
        while(!model.getToDoList().isEmpty())
            model.setDone(0);
        assertEquals(TEST_LIST.size(), model.getDoneList().size());
        assertEquals(new HashSet<>(TEST_LIST), new HashSet<>(model.getDoneList()));
    }
       
 
    @Test
    public void testSetDoneAllSetToDoAll() {
        while(!model.getToDoList().isEmpty())
            model.setDone(0);
        while(!model.getDoneList().isEmpty())
            model.setToDo(0);
        assertTrue(model.getDoneList().isEmpty());
        assertEquals(TEST_LIST, model.getToDoList());
    }
   
    @Test(expected = InvalidTransferException.class)
    public void testSetDoneInvalidIndex1() {
        model.setDone(-1);
    }
 

    @Test(expected = InvalidTransferException.class)
    public void testSetDoneInvalidIndex2() {
        model.setDone(model.getToDoList().size());
    }
     
    @Test(expected = InvalidTransferException.class)
    public void testSetToDoInvalidIndex1() {
        model.setToDo(0);
    }
    
    @Test(expected = InvalidTransferException.class)
    public void testSetToDoInvalidIndex2() {
        model.setDone(0);
        model.setToDo(-1);
    }
    
    @Test(expected = InvalidTransferException.class)
    public void testSetToDoInvalidIndex3() {
        model.setDone(0);
        model.setToDo(model.getDoneList().size());
    }
    
    @Test
    public void testAddToDoNormal() {
        int initSize = model.getToDoList().size(); 
        final String TXT = "Hello";
        assertTrue(model.addToDo(TXT));
        assertTrue(model.getToDoList().size() == initSize + 1);
        assertEquals(TXT, model.getToDoList().get(initSize));
        assertTrue(model.getDoneList().isEmpty());
    }
/*
    @Test
    public void testAddToDoEmptyString() {
        int initSize = model.getToDoList().size(); 
        final String TXT = "";
        assertFalse(model.addToDo(TXT));
        assertTrue(model.getToDoList().size() == initSize);
        assertFalse(model.getToDoList().contains(TXT));
        assertTrue(model.getDoneList().isEmpty());
    }

    @Test
    public void testAddToDoSmallString() {
        int initSize = model.getToDoList().size(); 
        final String TXT = "Hi";
        assertFalse(model.addToDo(TXT));
        assertTrue(model.getToDoList().size() == initSize);
        assertFalse(model.getToDoList().contains(TXT));
        assertTrue(model.getDoneList().isEmpty());
    }

    @Test
    public void testAddToDoExisting1() {
        final String TXT = model.getToDoList().get(0);
        assertFalse(model.addToDo(TXT));
        assertEquals(TEST_LIST, model.getToDoList());
        assertTrue(model.getDoneList().isEmpty());
    }

    @Test
    public void testAddToDoExisting2() {
        model.setDone(0);
        model.setDone(0);
        List<String> doneList = model.getDoneList();
        final String TXT = doneList.get(0);
        assertFalse(model.addToDo(TXT));
        assertTrue(model.getToDoList().size() == TEST_LIST.size() - 2);
        assertEquals(doneList, model.getDoneList());
    }
*/
}
