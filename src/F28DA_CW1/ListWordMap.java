package F28DA_CW1;

import java.util.Iterator;
import java.util.LinkedList;

public class ListWordMap implements IWordMap{
    public class Entry{
        private String word;
        private IPosition pos;
        public Entry(String word,IPosition pos)
        {
            this.word = word;
            this.pos = pos;
        }
        public String getWord()
        {
            return word;
        }
        
        public IPosition getPos()
        {
            return pos;
        }
    }
    LinkedList<Entry> linked_l;
    int size;
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public ListWordMap()
    {
        linked_l = new LinkedList();
        size = 0;
    }

    @Override
    public void addPos(String word, IPosition pos) {
    	//adds a new position to an entry of the map. 
    	//It creates the entry if word is not already present in the map.
    	
       Iterator<Entry> iter = linked_l.iterator();
       int count = 0;
       while(iter.hasNext())
       {
           Entry e = (Entry)iter.next();
           String word1 = e.getWord();
           IPosition pos1 = e.getPos();
           if(word.equals(word1)&&pos.equals(pos1))
           {
               count++;
           }
       }
           if(count == 0)
           {
        	 
               Entry entry = new Entry(word,pos);
               linked_l.add(entry);
               size++;
           }
       
    }

    @Override
    public void removeWord(String word) throws WordException {
    	//removes from the map the entry for word. 
    	//Throws exception if word is not present in the map.
    	
        Iterator <Entry>iter = linked_l.iterator();
        int count=0;
        while(iter.hasNext())
        {
            Object obj = iter.next();
            Entry ent = (Entry)obj;
            if(ent.getWord().equals(word))
            {
                linked_l.remove(ent);
                count++;
                size--;
            }
        }
        if(count == 0)
        {
            throw new WordException();
        }
    }

    @Override
    public void removePos(String word, IPosition pos) throws WordException {
    	//removes from the map position for word. 
    	//Throws exception if word is not present in the map 
    	//or if word is not associated to the given position.
    	
    	Iterator<Entry> iter = linked_l.iterator();
        int count = 0;
        while(iter.hasNext())
        {
            Object obj = iter.next();
            Entry ent = (Entry)obj;
            if(ent.getWord().equals(word)&&ent.getPos().equals(pos))
            {
                this.removeWord(word);
                count++;
                size--;
            }
        }
        if(count == 0)
        {
            throw new WordException();
        }
        
    }

    @Override
    public Iterator<String> words() {
    	// returns an Iterator1over all words in the map. 
    	//The iteration is over objects of class String.

    	
        LinkedList<String> w_linkedl=new LinkedList<String>();
        Iterator<Entry> iter = linked_l.iterator();
        while(iter.hasNext())
        {
        	Entry ent = (Entry)iter.next();
        	w_linkedl.add(ent.getWord());
        	
        }
        Iterator<String> i = w_linkedl.iterator();
        return i;
    }

    @Override
    public Iterator<IPosition> positions(String word) throws WordException {
    	//returns an Iterator over all positions of word. 
    	//The iteration is over objects of class IPosition. 
    	//Throws exception if word is not present in the map.
    	
    	LinkedList<IPosition> p_linkedl = new LinkedList<IPosition>();
        Iterator<Entry> iter = linked_l.iterator();
        while(iter.hasNext())
        {
        	Entry ent = (Entry)iter.next();
        	if(ent.getWord().equals(word)) {
        		p_linkedl.add(ent.getPos());
        	}
        	
        	
        }
        Iterator<IPosition> i = p_linkedl.iterator();
        return i;
    }

    @Override
    public int numberOfEntries() {
    	//returns the number of entries stored in the map.
        return size;
    }
}
