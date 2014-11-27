package javaLEWrapper.Wrapper;

public class Security extends Buildings {

	Security(String buildingName) {
		super("security");
		// TODO Auto-generated constructor stub
	}
	String ViewPrisoners(String sessionID, String buildingID, String pageNumber){
		String i = Request("view_prisoners", sessionID, buildingID, pageNumber);
		return i;
	}
	String ExecutePrisoner(String sessionID, String buildingID, String prisonerID){
		String i = Request("execute_prisoner", sessionID, buildingID, prisonerID);
		return i;
	}
	String ReleasePrisoner(String sessionID, String buildingID, String prisonerID){
		String i = Request("release_prisoner", sessionID, buildingID, prisonerID);
		return i;
	}
	String ViewForeignSpies(String sessionID, String buildingID, String pageNumber){
		String i = Request("view_foreign_spies", sessionID, buildingID, pageNumber);
		return i;
	}

}
