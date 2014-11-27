package javaLEWrapper.Wrapper;
import java.io.*;
import java.net.*;

public class Server {
	public String ServerRequest(String gameServer, String methodURL, String JsonRequest) {
        String output = "";
        try {
        	try { //putting thread to sleep for just over a second to throttle client because of the limit of 60 calls per minute
        	    Thread.sleep(1100);
        	} catch(InterruptedException ex) {
        	    Thread.currentThread().interrupt();
        	}
        	System.out.println((gameServer+"/" + methodURL));
        	SaveToLog("Request");
        	SaveToLog((gameServer+"/" + methodURL));
        	SaveToLog(JsonRequest);
        	SaveToLog("");
            URL url = new URL(gameServer+"/" + methodURL);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(JsonRequest);
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            output = in.readLine();
            SaveToLog("Reply");
        	SaveToLog(output);
        	SaveToLog("");

        } catch (java.net.MalformedURLException e) {
        	System.out.println("Server Error IO Exception possible bad url");
            output = "error";
            SaveToLog("Reply");
            SaveToLog("Malformed URL Exception");
        	SaveToLog(output);
        	SaveToLog(e.getMessage());
        } catch (java.io.IOException e) {
        	System.out.println("Server Error IO Exception possible bad url");
            output = "error";
            SaveToLog("Reply");
            SaveToLog("IO Exception");
        	SaveToLog(output);
        	SaveToLog(e.getMessage());
        }
        return output;

    }
	private void SaveToLog(String log){
		 try(PrintWriter printWriter = new PrintWriter(new FileWriter("IO.log", true))) {
         	printWriter.println(log);
 		}catch (IOException e) {

 		}
	}
}
