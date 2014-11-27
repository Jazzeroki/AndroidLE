package javaLEWrapper.Wrapper;
//package JavaLEWrapper;

import java.io.IOException;

import com.google.gson.*;

public class Empire extends LESuperClass{
	String url = "empire";
	private Gson gson = new Gson();
	String Login(String Username, String Password, int requestID ){
		LoginObject login = new LoginObject(requestID, Username, Password);
		return gson.toJson(login);
	}
	String FetchCaptcha(String SessionID){
		StartOfObject(1, "fetch_captcha");
		String i = "0";
		try{
			writer.value(SessionID);
			//writer.value(BodyID);
			writer.endArray();
			writer.endObject();
			writer.close();
			i = gson.toJson(writer);
			//writer.flush();
			i = CleanJsonObject(i);
		}catch(IOException e){
			System.out.println("ioexception");
		}catch(NullPointerException e){
			System.out.println("null pointer exception");
		}finally{
		}
		return i;
	}
	private class LoginObject{

	    LoginObject(int i, String u, String p) {
	        params[0] = u;
	        params[1] = p;
	        params[2] = "6266769d-1f73-4325-a40f-6660c4c6440d";
	        id = i;
	    }
	    String jsonrpc = "2.0";
	    int id;
	    String method = "login";
	    String[] params = new String[3];
	}

}
