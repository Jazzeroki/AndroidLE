package javaLEWrapper.Wrapper;

import java.io.IOException;

public class Captcha extends LESuperClass {
	String url = "captcha";
	String Fetch(String SessionID){
		StartOfObject(1, "fetch");
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
	String Solve(String SessionID, String guid, String solution){
		StartOfObject(1, "solve");
		String i = "0";
		try{
			writer.value(SessionID);
			writer.value(guid);
			writer.value(solution);
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
}
