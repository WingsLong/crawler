package crawler.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author Handsomeli
 *
 */
public class StreamOperator {

	public static ByteArrayOutputStream redirectSystemOutput()
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos); 
		System.setOut(ps);
		return baos;
	}
}
