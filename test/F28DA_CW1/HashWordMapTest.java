package F28DA_CW1;
import static org.junit.Assert.*;
import org.junit.Test;

public class HashWordMapTest {


	@Test
	//adding an empty word to the file
	public void AddEmptyWord() {
		IWordMap map = new HashWordMap();
		String word1 = "";
		IPosition position1 = new WordPosition("", 0, word1);
		map.addPos(word1, position1);
        assertEquals(map.numberOfEntries(),1);
	}

	@Test
	//adding multiple words to the file
	public void AddWords() {
		 IWordMap map = new HashWordMap();
		 String word1 = "Syed";
		 String word2 = "Irfan";
		 String word3 = "Uddin";
		 IPosition position1 = new WordPosition("test.txt", 1, word1);
		 IPosition position2 = new WordPosition("test.txt", 4, word2);
		 IPosition position3 = new WordPosition("test.txt", 7, word3);
		 map.addPos(word1, position1);
		 map.addPos(word2, position2);
		 map.addPos(word3, position3);
		 assertEquals(map.numberOfEntries(),3);
		 
	}

	@Test
	//deleting words from the file
	public void Delete() throws WordException {
		IWordMap map = new HashWordMap();
		String word1 = "test";
		IPosition position1 = new WordPosition("test.txt", 1, word1);
		map.addPos(word1, position1);
		map.removeWord(word1);
		assertEquals(map.numberOfEntries(),0);
	}



	@Test
	public void signatureTest() {
        try {
            IWordMap map = new HashWordMap(0.5f);
            String word1 = "test1";
            String word2 = "test2";
            IPosition pos1 = new WordPosition("test.txt", 4, word1);
            IPosition pos2 = new WordPosition("test.txt", 5, word2);      
            map.addPos(word1, pos1);
            map.addPos(word2, pos2);
            map.words();
            map.positions(word1);
            map.numberOfEntries();
            map.removePos(word1, pos1);
            map.removeWord(word2);
        } catch (Exception e) {
            fail("Signature of solution does not conform");
        }
	}

}