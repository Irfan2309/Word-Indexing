package F28DA_CW1;

import java.io.File;

import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.*;

/** Main class for the Word Index program */
public class WordIndex {

	static final File textFilesFolder = new File("TextFiles");
	static final FileFilter commandFileFilter = (File file) -> file.getParent()==null;
	static final FilenameFilter txtFilenameFilter = (File dir, String filename) -> filename.endsWith(".txt");

	public static void main(String[] argv) {
		
		argv = new String[1];
		argv[0] = "commands.txt";
		
		if (argv.length != 1 ) {
			System.err.println("Usage: WordIndex commands.txt");
			System.exit(1);
		}
		try{
			int p1 = 0; 
			int d1 = 0;  
			int count = 0; 
			
			File commandFile = new File(argv[0]);
			if (commandFile.getParent()!=null) {
				System.err.println("Use a command file in current directory");
				System.exit(1);
			}

			// creating a command reader from a file
			WordTxtReader commandReader = new WordTxtReader(commandFile);

			// initialize map
			IWordMap wordPossMap;
			
			//List implementation of a word map
			wordPossMap = new ListWordMap();
			
			// hash table implementation of a word map
			wordPossMap = new HashWordMap();	

			// reading the content of the command file
			while(commandReader.hasNextWord()) {
				// getting the next command
				String command = commandReader.nextWord().getWord();

				switch (command) {
				
				case "addall":
					//Adding to the map the words and their positions in each file for all the .txt files 
					
					assert(textFilesFolder.isDirectory());
					File[] listOfFiles = textFilesFolder.listFiles(txtFilenameFilter);
					Arrays.sort(listOfFiles);
					for (File textFile : listOfFiles) {
						@SuppressWarnings("unused")
						int num_in = 0;		
						WordTxtReader wordReader = new WordTxtReader(textFile);
						count++;
						while (wordReader.hasNextWord()) {
							WordPosition wordPos = wordReader.nextWord();
							// adding word to the map
							
							IPosition pos = (IPosition) wordPos; 
							wordPossMap.addPos(wordPos.getWord(), pos); 
							p1++;
							num_in++;
						}
					}
					System.out.println(p1 + " entries have been indexed from "+ textFilesFolder.list().length + " files");
					break;

				case "add":
					//Adding to the map the words and their positions in the given file.
					File textFile = new File(textFilesFolder, commandReader.nextWord().getWord()+".txt");
					WordTxtReader wordReader = new WordTxtReader(textFile);
					int no_add = 0;
					count++;
					while (wordReader.hasNextWord()) {
						WordPosition word = wordReader.nextWord();
						// adding word to the map
						// ...
						IPosition position = (IPosition) word; 
						wordPossMap.addPos(word.getWord(), position);
						no_add++;
						p1++;
					}
					System.out.println(no_add+" entries have been indexed from file "+'"'+ textFile+'"');
					break;

				case "search":
					//Searching for a word in the text files that have been indexed.
					//The output will be in order of most occurrence in a file, 
					//and will be limited to the number of file given as argument 
					
					int nb = Integer.parseInt(commandReader.nextWord().getWord());
					String word = commandReader.nextWord().getWord();
					// search for word entry in map
					// ...
					HashMap<String, Integer> map_1 = new HashMap<String, Integer>();
					IPosition posit1;
					String key;
					int num_words = 0;
					try {
						Iterator<IPosition> poss = wordPossMap.positions(word);
						int i = 0;
						while(poss.hasNext()) {
							posit1 = poss.next();
							
							if(map_1.containsKey(posit1.getFileName())) {
								key = posit1.getFileName();
								num_words = map_1.get(key).intValue();
								num_words++;
								map_1.put(key, num_words);
							}
							else {
								key = posit1.getFileName();
								num_words = 1;
								map_1.put(key, num_words);
							}
							
							i++;
						}
						System.out.println("The word '" + word + "' occurs " + i + " times in " + map_1.size() + " files: ");
						
						Set<String> Ordered = map_1.keySet();
						Object setArr[] = Ordered.toArray();
						Arrays.sort(setArr, Collections.reverseOrder());
						
						for (int j = 0; j < nb; j++) {
							poss = wordPossMap.positions(word);
							System.out.println(map_1.get(setArr[j]).intValue() + " time in " + setArr[j]);
							System.out.print("(lines ");
							
							ArrayList<Integer> line_num = new ArrayList<Integer>();
							while (poss.hasNext()) {
								IPosition printpos = poss.next();
								if(setArr[j].equals(printpos.getFileName())) {
									line_num.add(printpos.getLine());
									
								}
							}
							
							String pline_num = String.join(", ", line_num.toString());
							pline_num = pline_num.replace("[", "").replace("]", "");
							System.out.printf(pline_num, ")\n");
						}	
							
					} catch (WordException e) 
					{
						System.err.println("Error! Word does not exist");

					}
					
					break;

				case "remove":
					//Removing from the map the positions of words from the given file. 
					//If a word has no more positions associated with it, we remove it from the map.
					//the file itself will not be removed from the TextFiles directory.
					
					File textFileToRemove = new File(textFilesFolder, commandReader.nextWord().getWord()+".txt");
					// remove word-positions
					WordTxtReader txtReader = new WordTxtReader(textFileToRemove);
					count--;
					
					while(txtReader.hasNextWord()) {
						WordPosition tempWord = txtReader.nextWord(); 
						
						IPosition pos = (IPosition) tempWord; 
															
						try {
							
							
							wordPossMap.removePos(tempWord.getWord(), pos);
							d1++;
							
						} catch (WordException e) {
							
						}
						
					}
					System.out.println(d1 + " entries have been removed from file "+ '"' + textFileToRemove.getName() + '"' );
					break;

				case "overview":
					// print overview
					//Printing a summary of the number of indexed words, indexed positions and indexed files.
					Iterator<String> iterate = wordPossMap.words();
					int num = 0;
					
					while (iterate.hasNext()) { 
						if ((iterate.next().equals("")) == false) 
						{
							num++;
						}
					}
					

					System.out.println("Overview:\n" + "      number of words: " + num + "\n" + "      number of positions: " + (p1) + "\n" + "      number of files: " + count + "\n");
					break;

				default:
					break;
				}

			}

		}
		catch (IOException e){ // catch exceptions caused by file input/output errors
			System.err.println("Check your file name");
			System.exit(1);
		}  
	}
}
