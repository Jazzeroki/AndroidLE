package javaLEWrapper.Wrapper;

import java.io.IOException;

public class Archaeology extends Buildings {

	Archaeology(String buildingName) {
		super("archaeology");
	}
	String SubsidizeSearch(String sessionID, String buildingID){
		StartOfObject(1, "subsidize_search");
		String i = SessionAndBuildingIDRequests(sessionID, buildingID);
		return i;
	}
	String GetGlyphs(String sessionID, String buildingID){
		StartOfObject(1, "get_glyphs");
		String i = SessionAndBuildingIDRequests(sessionID, buildingID);
		return i;
	}
	String GetGlyphSummary(String sessionID, String buildingID){
		StartOfObject(1, "get_glyph_summary");
		String i = SessionAndBuildingIDRequests(sessionID, buildingID);
		return i;
	}
	String GetOresAvailableForProcessing(String sessionID, String buildingID){
		StartOfObject(1, "get_ores_available_for_processing");
		String i = SessionAndBuildingIDRequests(sessionID, buildingID);
		return i;
	}
	String ViewExcavators(String sessionID, String buildingID){
		StartOfObject(1, "view_excavators");
		String i = SessionAndBuildingIDRequests(sessionID, buildingID);
		return i;
	}
	String SearchForGlyph(String sessionID, String buildingID, String oreType){
		String b = "0";
		try{
			writer.beginObject();
			writer.name("jsonrpc").value(2);
			writer.name("id").value(1);
			writer.name("method").value("search_for_glyph");
			writer.name("params").beginArray();
			writer.value(sessionID);
			writer.value(buildingID);
			writer.value(oreType);
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
	String AbandonExcavator(String sessionID, String buildingID, String siteID){
		String b = "0";
		try{
			writer.beginObject();
			writer.name("jsonrpc").value(2);
			writer.name("id").value(1);
			writer.name("method").value("abandon_excavator");
			writer.name("params").beginArray();
			writer.value(sessionID);
			writer.value(buildingID);
			writer.value(siteID);
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
	String AssembleGlyphs(String sessionID, String buildingID, int quantity, String... glyphs){
		String b = "0";
		try{
			writer.beginObject();
			writer.name("jsonrpc").value(2);
			writer.name("id").value(1);
			writer.name("method").value("assemble_glyphs");
			writer.name("params").beginArray();
			writer.value(sessionID);
			writer.value(buildingID);
			for(String i: glyphs)
				writer.value(i);
			writer.value(quantity);
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
