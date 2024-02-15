package F28DA_CW1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class HashWordMap implements IWordMap,IHashMonitor{
	class HashEntry
	{
		 	private String word;
	        private IPosition pos;
	        public HashEntry(String word,IPosition pos)
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
	private float mlf = 0.5f;
	public int capacity = 13;
	private int size = 0;
	public HashEntry table[];
	private float num_p = 0.0f;
	private float num_ops = 0.0f;
	private final HashEntry DEFUNCT = new HashEntry(null,null);
	public HashWordMap()
	{
		table = new HashEntry[capacity];
	}
	public HashWordMap(float mlf)
	{
		//setting the maximum load factor at construction time
		table = new HashEntry[capacity];
		this.mlf = mlf;
	}
	private boolean isAvailable(HashEntry e)
	{
		return e == null || e.equals(DEFUNCT);
	}
	
	@Override
	public void addPos(String word, IPosition pos) {
		//adds a new position to an entry of the map. 
		//It creates the entry if word is not already present in the map.

		int in = hash1(word);
		int skip = hash2(word);
		while(!isAvailable(table[in]))
		{
			in += skip;
			in %= table.length;
			num_p += 1;
		}
		table[in] = new HashEntry(word,pos);
		size++;
		num_ops += 1;
		checkLoadFactor();
				
		}

	@Override
	public void removeWord(String word) throws WordException {
		//removes from the map the entry for word. 
		//Throws exception if word is not present in the map.
		
		int in = hash1(word);
		int skip = hash2(word);
		int count = 0;
				
		while(!isAvailable(table[in]))
		{
			if(table[in].getWord().equals(word))
			{
				table[in] = DEFUNCT;
				size--;
				count++;
				num_ops += 1;
				return;
				
			}
			in += skip;
			in  %= table.length;
			num_p--;
			
		
		}
		if(count == 0)
		{
			throw new WordException();
		}
		}
	

	@Override
	public void removePos(String word, IPosition pos) throws WordException {
		//removes from the map position for word.0
		//Throws exception if word is not present in the map 
		//or if word is not associated to the given position.
		int in = hash1(word);
		int skip = hash2(word);
		int count = 0;
				
		while(!isAvailable(table[in]))
		{
			if(table[in].getPos().getFileName().equals(pos.getFileName())&&table[in].getPos().getLine() == pos.getLine()&&table[in].getWord().equals(word))
			{
				table[in] = null;
				size--;
				count++;
				num_ops += 1;
				return;
				
			}
			in += skip;
			in %= table.length;
			
			
		
		}
		if(count == 0)
		{
			throw new WordException();
		}
	}
	
	public int getCapacity()
	{
		return capacity;
	}

	@Override
	public Iterator<String> words() {
		//returns an Iterator over all words in the map.
		Iterator<HashEntry> hse = Arrays.asList(table).iterator();
		List<String> wordList = new ArrayList<>();
		while(hse.hasNext())
		{
			HashEntry h = hse.next();
			if(h!= null) {
				if (!wordList.contains(h.getWord())){
					wordList.add(h.getWord());
				}
			}
		}
		return wordList.iterator();
				
	}

	@Override
	public Iterator<IPosition> positions(String word) throws WordException {
		//returns an Iterator over all positions of word. 
		//The iteration is over objects of class IPosition. 
		//Throws exception if word is not present in the map.
		
		List<HashEntry> he = Arrays.asList(table);
		List<IPosition> ip = new ArrayList<>();
		Iterator<HashEntry> heItr = he.iterator();
		while(heItr.hasNext())
		{
			HashEntry h = heItr.next();
			if(h != null){
			{
				if (h.getWord().equals(word)){
					ip.add(h.getPos());
				}
				
			}
		}}
		Iterator<IPosition> itr = ip.iterator();
		return itr;

	}

	@Override
	public int numberOfEntries() {
		//returns the number of entries stored in the map.

		return size;
	}
	
	@Override
	public float getMaxLoadFactor() {
		//returns the maximum authorized load factor.
		return mlf;
	}

	@Override
	public float getLoadFactor() {
		//returns the current load factor.
		return (float)numberOfEntries()/capacity;
	}

	@Override
	public float averNumProbes() {
		//returns an average number of probes performed by your hash table so far
		
		return (float)num_p/num_ops;
	}

	@Override
	public int hashCode(String s) {
		//returns the hash code as an integer of a given string.
		
		int hash = 0;
		for(int i = 0;i<s.length();i++)
		{
			hash += s.charAt(i);
		}
		return hash;
	}
	private void checkLoadFactor()
	{
		HashEntry newTable[];
		if(getLoadFactor()>mlf)
		{
			HashEntry old[] = table;
			int newSize = generatePrime(2*table.length);
			newTable = new HashEntry[newSize];
			capacity = newSize;
			
			for(int i = 0;i<newTable.length;i++)
			{
				if(i<old.length)
				newTable[i] = old[i];
				else
				newTable[i] = null;
			}
			
			table = newTable;
		}
	}

	private int generatePrime(int num)
	
	{
	boolean isPrime = false;
	int sqrt = (int)Math.ceil(Math.sqrt(num));
	while(!isPrime)
	{
		num++;
		isPrime = true;
		for(int i = 2;i<sqrt;i++)
		{
			if(num % i == 0)
			{
				isPrime = false;
			}
		}
	}
    return num;
	}
	private int hash1(String s)
	{
		int hash = hashCode(s);
		return hash%table.length;
	}
	private int hash2(String s)
	{
		int hash = hashCode(s);
		return  capacity/2-hash%(capacity/2);
	}
}
