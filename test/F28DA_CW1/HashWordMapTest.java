package F28DA_Cword1;

import static org.junit.Assert.*;

import org.junit.Test;

public class HashWordMapTest {

	// Add your own tests, for example to test the method hashCode from HashWordMap
	
	@Test
	public void AddBlankword() {
		IWordMap map = new HashWordMap();
		String w1 = "";
		IPosition p1 = new WordPosition("", 0, w1);
		map.addPos(w1, p1);
        assertEquals(map.numberOfEntries(),1);
	}
	
	@Test
	public void add2WordsTest() {
		 IWordMap map = new HashWordMap();
		 String w1 = "lost";
		 String w2 = "high";
		 IPosition p1 = new WordPosition("test.txt", 1, w1);
		 IPosition p2 = new WordPosition("test.txt", 4, w2);
		 
		 map.addPos(w1, p1);
		 map.addPos(w2, p2);
		 assertEquals(map.numberOfEntries(),2);
		 
	}
	
	@Test
	public void AddRemove() throws WordException {
		IWordMap map = new HashWordMap();
		String w1 = "Data";
		IPosition pos = new WordPosition("test.txt", 1, w1);
		map.addPos(w1, pos);
		map.removeWord(w1);
		assertEquals(map.numberOfEntries(),0);
	}
	
	@Test
	public void TwoAddRemove() throws WordException {
		IWordMap map = new HashWordMap();
		String w1 = "Science";
		String w2 = "Dubai";
		IPosition p1 = new WordPosition("test.txt", 1, w1);
		IPosition p2 = new WordPosition("test.txt", 4, w2);

		map.addPos(w1, p1);
		map.addPos(w1, p2);
		map.removeWord(w1);
		map.removeWord(w2);
		assertEquals(map.numberOfEntries(),3);
	}
	
	@Test
	public void AddRemovePos() throws WordException {
		IWordMap map = new HashWordMap();
		String w1 = "List";
		IPosition pos = new WordPosition("test.txt", 1, w1);
		map.addPos(w1, pos);
		map.removePos(w1,pos);
		assertEquals(map.numberOfEntries(),0);
	}
	
	
	@Test
	public void signatureTest() {
        try {
            IWordMap map = new HashWordMap();
            String w1 = "test1";
            String w2 = "test2";
            IPosition p1 = new WordPosition("test.txt", 4, w1);
            IPosition p2 = new WordPosition("test.txt", 5, w2);      
            map.addPos(w1, p1);
            map.addPos(w2, p2);
            map.words();
            map.positions(w1);
            map.numberOfEntries();
            map.removePos(w1, p1);
            map.removeWord(w2);
        } catch (Exception e) {
            fail("Signature of solution does not conform");
        }
	}

}
