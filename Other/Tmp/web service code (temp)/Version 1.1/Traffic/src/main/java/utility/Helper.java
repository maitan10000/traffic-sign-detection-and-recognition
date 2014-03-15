package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Helper {
	public static String runTSRT(String appDic, String imagePath)
	{
		String commands =  "./TSRT";
		
		//Run macro on target
	    ProcessBuilder pb = new ProcessBuilder(commands, imagePath);
	    pb.directory(new File(appDic));
	    pb.redirectErrorStream(true);
	    Process process;
		try {
			process = pb.start();
			//Read output
	        StringBuilder out = new StringBuilder();
	        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        String line = null, previous = null;
	        while ((line = br.readLine()) != null)
	            if (!line.equals(previous)) {
	                previous = line;
	                out.append(line).append('\n');
	                //System.out.println(line);
	            }

	        //Check result
	        if (process.waitFor() == 0)
	            //System.out.println("Success!");
	        //System.exit(0);

	        //Abnormal termination: Log command parameters and output and throw ExecutionException
	        //System.err.println(commands);
	        //System.out.println(out.toString());
	        //System.exit(1);
	        	return out.toString();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
		return "";
	}

}
