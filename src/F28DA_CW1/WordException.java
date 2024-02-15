package F28DA_CW1;

public class WordException extends Exception {
	
	private static final long serialVersionUID = -8580292064038678448L;
	public WordException()
    {
        super("Error! Word doesn't exist");
    }

}
