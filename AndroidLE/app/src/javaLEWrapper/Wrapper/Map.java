package javaLEWrapper.Wrapper;

import java.io.IOException;

public class Map extends LESuperClass {
	String url = "map";
	String CheckStarForIncomingProbe(String sessionID, String starID){
		StartOfObject(1, "check_star_for_incoming_probe");
		String i = SessionAndBuildingIDRequests(sessionID, starID);
		return i;
	}
	String GetStar(String sessionID, String starID){
		StartOfObject(1, "get_star");
		String i = SessionAndBuildingIDRequests(sessionID, starID);
		return i;
	}
	String GetStarByName(String sessionID, String name){
		StartOfObject(1, "get_star_by_name");
		String i = SessionAndBuildingIDRequests(sessionID, name);
		return i;
	}
	String SearchStars(String sessionID, String name){
		StartOfObject(1, "search_stars");
		String i = SessionAndBuildingIDRequests(sessionID, name);
		return i;
	}
	String GetStars(String sessionID, String x1, String x2, String y1, String y2 ){
		String b = "0";
		try{
			writer.beginObject();
			writer.name("jsonrpc").value(2);
			writer.name("id").value(1);
			writer.name("method").value("get_stars");
			writer.name("params").beginArray();
			writer.value(sessionID);
			writer.value(x1);
			writer.value(x2);
			writer.value(y1);
			writer.value(y2);
			writer.endArray();
			writer.endObject();
			writer.close();
			b = gson.toJson(writer);
			//writer.flush();
			b = CleanJsonObject(b);
		}catch(IOException e){
			System.out.println("ioexception");
		}catch(NullPointerException e){
			System.out.println("null pointer exception");
		}finally{
		}
		return b;
	}
	String GetStarsByXY(String sessionID, String x1, String y1){
		String b = "0";
		try{
			writer.beginObject();
			writer.name("jsonrpc").value(2);
			writer.name("id").value(1);
			writer.name("method").value("get_stars_by_xy");
			writer.name("params").beginArray();
			writer.value(sessionID);
			writer.value(x1);
			writer.value(y1);
			writer.endArray();
			writer.endObject();
			writer.close();
			b = gson.toJson(writer);
			//writer.flush();
			b = CleanJsonObject(b);
		}catch(IOException e){
			System.out.println("ioexception");
		}catch(NullPointerException e){
			System.out.println("null pointer exception");
		}finally{
		}
		return b;
	}
	
}
/*
get_star_map(hash)
probe_summary_fissures(session_id, zone(optional))



*/