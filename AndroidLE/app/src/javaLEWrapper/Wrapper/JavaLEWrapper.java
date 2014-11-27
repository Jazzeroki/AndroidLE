package javaLEWrapper.Wrapper;
import com.google.gson.Gson;





//import System.out;
//import java.io.BufferedReader;
//import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
//import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
//import java.io.Console;
import java.net.URL;
import java.awt.Desktop;
//import java.awt.List;




//import javaLEWrapper.Wrapper.OriginalResponse.Result.Prisoner;
import javaLEWrapper.Wrapper.Response.Messages;
import javaLEWrapper.Wrapper.Response.Result;
import javaLEWrapper.Wrapper.Response.Spies;
import javaLEWrapper.Wrapper.Response.Stars;
import javaLEWrapper.Wrapper.Spaceport.Target;
//import javaLEWrapper.Wrapper.Response.Spies.PossibleAssignments;
//import javaLEWrapper.Wrapper.Spaceport.Target;


public class JavaLEWrapper {
	/*class Names implements Comparable<Names> {
		String id, name;
		@Override
		public int compareTo(Names n){
			return name.compareTo(n.name);
		}
	} */

	static String version = "Alpha 0.03";
	static String sessionID = null;
	static String gameServer = null;
	static Gson gson = new Gson();
	static Server server = new Server();	
	static int newMessages;
	static boolean captchaValid = false;
	static HashMap <String, String> planetList = new HashMap <String, String>() ;
	static HashMap <String, Response.Result> stations = new HashMap<String, Response.Result>();
	static HashMap <String, Response.Result> planets = new HashMap<>();

	static ArrayList <String> planetNames = new ArrayList<String>(); //For storing a sorted collection of planet names
	static ArrayList <String> stationNames = new ArrayList<String>(); //For storing a sorted collection of station names

	public static void main(String[] args) {
		System.out.println("Jazz Command Console "+version);
		System.out.println("This console utility is in the Alpha stage and is used at the users own risk \nDamages inflicted on your computer or your empire are the users own responsibility");
		ClearLog();
		GetSession();
		MainMenu();
    }
    
    //Main Controls
	static void ClearLog(){
		PrintWriter writer;
		try {
			writer = new PrintWriter("Status.log");
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e){

		}

	}
	static void SaveToLog(String log){
		try(PrintWriter out = new PrintWriter(new FileWriter("Status.log", true))) {
			out.println(" ");
		    out.println(log);
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}

	}
    static void GetSession(){
    	AccountManager a = new AccountManager();
    	AccountInfo account = a.SelectAccountMenu();
    	gameServer = account.server;
    	Empire e = new Empire();
    	String request = e.Login(account.userName, account.password, 1);
    	String reply = server.ServerRequest(gameServer, e.url, request);
    	Response r = gson.fromJson(reply, Response.class);
    	
    	//setting values for empire details
    	sessionID = r.result.session_id;
    	newMessages = r.result.status.empire.has_new_messages;
    	planetList = r.result.status.empire.planets;
    		
    	
    }
    static void PrintMainMenu(){
    	System.out.println("Jazz Java LE Command Console, version: "+version);
    	System.out.println("Enter a number for your selection");
    	System.out.println("1, Planetary Controls");
    	System.out.println("2, Station Controls");
    	System.out.println("3, Messages: "+newMessages);
    	System.out.println("4, Account Management");
    	System.out.println("5, Logout");
    	System.out.println("6, Experiments");
    }
    static void MainMenu(){
    	//Captcha();
    	int i = 0;
    	int control = 0;
    	Scanner input = new Scanner(System.in);
    	do{
    		PrintMainMenu();
    		try{
    			//System.out.println("starting try block");
    			control = input.nextInt();
    			//System.out.println(control);
    			switch(control){
    			case 1:
    				PlanetControlsMenu();
    				break;
    			case 2:
    				SSControlsMenu();
    				break;
    			case 3:
    				MessageBoxMenu();
    				break;
    			case 4:
    				System.out.println("not implemented yet");
    				PrintMainMenu();
    				break;
    			case 5:
    				System.out.println("not implemented yet");
    				PrintMainMenu();
    				break;
    			case 6:
    				ExperimentalMethodsMenu();
    				break;
    			default:
    				System.out.println("Invalid Selection");
    				PrintMainMenu();
    			}	
    		}catch(InputMismatchException e){
    			System.out.println("Not a valid selection.");   			
    		}		
    	}while(i==0);
    	//input.close();
    }
    static void PrintExperimentalMenu(){
    	System.out.println("1: Captcha test");
    	System.out.println("0: to return to main menu");
    }
    static void ExperimentalMethodsMenu(){
    	int i = 0;
    	int control = 0;
    	Scanner input = new Scanner(System.in);
    	do{
    		PrintExperimentalMenu();
    		try{
    			control = input.nextInt();
    			switch(control){
    			case 1:
    				Captcha();
    				break;
    			case 0:
    				PlanetControlsMenu();
    				break;
    			default:
    				System.out.println("Invalid Selection");
    				PrintMainMenu();
    			}	
    		}catch(InputMismatchException e){
    			System.out.println("Not a valid selection.");   			
    		}		
    	}while(i==0);
    	//input.close();
    }
    static String GetSingleInputFromUser(String request){
    	System.out.println(request);
    	Scanner input = new Scanner(System.in);
    	String i = input.next();
    	//input.close();
    	return i;
    }
    static Spaceport.Target GetTarget(){
    	Spaceport.Target target = new Spaceport.Target();
    	System.out.println("Enter Target Info");
    	System.out.println("1: Enter Target by Name");
		System.out.println("2: Enter Target by ID");
		System.out.println("3: Enter Target by x,y");
		String choice = GetSingleInputFromUser("Enter a Selection");
		int selection = Integer.parseInt(choice);
		switch(selection){
		case 1: 
			target.bodyName = GetSingleInputFromUser("Enter Target Name");
			break;
		case 2:
			target.bodyID = GetSingleInputFromUser("Enter Target ID");
			break;
		case 3:
			target.x = GetSingleInputFromUser("Enter x coordinate");
			target.y = GetSingleInputFromUser("Enter y coordinate");
			break;
		}
    	return target;
    }
    
    //experimental methods
    static void Captcha(){
    	Captcha c = new Captcha();
    	String request = c.Fetch(sessionID);
		String reply = server.ServerRequest(gameServer, c.url, request);
		System.out.println(reply);
		Response r = gson.fromJson(reply, Response.class);
		System.out.println("Please wait a moment while the captcha is loaded in a browser window");
		System.out.println("After the captcha has appeared enter the answer and push enter");
		//Console console = System.console();

		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	        	URL url = new URL(r.result.url);
	            desktop.browse(url.toURI());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    //for getting the answer
	    //Console console = System.console();
	    //System.out.println("test");
	    Scanner input = new Scanner(System.in);
	    String solution = input.next();
	    //String solution = console.readLine("Enter Answer ");
	    Captcha d = new Captcha();
	    request = d.Solve(sessionID, r.result.guid, solution);
	    //System.out.println(request);
	    reply = server.ServerRequest(gameServer, d.url, request);
	    System.out.println("note what the reply here looks like to see the indicator that the captcha worked so the if statment can be written");
	    System.out.println(reply);

	    captchaValid = true;
	    //r = gson.fromJson(reply, Response.class);
	    //if(r.result.)
	    //System.out.println(reply);
	    //if r.result is equal to 1 the captcha worked

    }
    static ArrayList<Integer> FindAllBuildingIDs(String buildingName, HashMap<Integer, Response.Building> buildings){
    	ArrayList<Integer> i = new ArrayList<Integer>();
    	Set<Integer> buildingkeys = buildings.keySet();
    	for(int j: buildingkeys){
    		if(buildings.get(j).name.equals(buildingName)){
    			i.add(j);
    		}
    	} 	
    	return i;
    }
    static int FindBuildingID(String buildingName, HashMap<Integer, Response.Building> buildings){
    	int i = 0;
    	//String name ="";
    	Set<Integer> buildingkeys = buildings.keySet();
    	for(int j: buildingkeys){
    		if(buildings.get(j).name.equals(buildingName))
    			return j;
    	} 	
    	return i;
    }
    
    static void SeperateSSandPlanets(){
    	Set<String> buildingkeys = planetList.keySet();
    	int size, count;
    	size = buildingkeys.size();
    	count = 0;
    	int endloop = 0;
    	if (planets.isEmpty()) //stops the method from running if the planet list is full
    	{
    	for(String j: buildingkeys){
    		count++;
    		System.out.println("Getting "+count+" of "+size);
    		Body body = new Body();
    		String request = body.GetBuildings(1, sessionID, j);
    		String reply = server.ServerRequest(gameServer, body.url, request);
    		Response r = gson.fromJson(reply, Response.class);
    		if (r.result.body.surface_image.contentEquals("surface-station")) {  //equals("surface-station")) { 
    			stations.put(j, r.result);
    			stationNames.add(r.result.status.body.name);
    			System.out.println("Station added to list"+r.result.status.body.name);
    			System.out.println(r.result.status.body.x);
    			String station =  r.result.status.body.name+" Station id: "+j+ " Size: "+r.result.status.body.size+" x: "+r.result.status.body.x+" y: "+r.result.status.body.y;
    			System.out.println(station);
    			try(PrintWriter out = new PrintWriter(new FileWriter("Station.log", true))) {
    	    			out.println(" ");
    	    		    out.println(station);
    	    		}catch (IOException e) {
    	    		}
    		}
    		else{
    			planets.put(j, r.result);
    			planetNames.add(r.result.status.body.name);
    			System.out.println("Planet "+r.result.status.body.name);
    		}
    		
    		//System.out.println(j);
    		//endloop++;  //remove this when done testing
    		//if (endloop == 15)
    			//break;
    	}
    	}
    	Collections.sort(planetNames);
    	Collections.sort(stationNames);
    }//seperates planets from stations and retrieves their building info
    static void PlanetStatusCheck(){
    	//for(String r : planets)
    	for(Entry<String, Result> e : planets.entrySet()){
    		SaveToLog(e.getValue().status.body.name);
    		SaveToLog(e.getKey());
    		if(Double.parseDouble(e.getValue().status.body.num_incoming_enemy) > 0)
    			SaveToLog("Enemy Incoming: "+e.getValue().status.body.num_incoming_enemy);
    		if(Double.parseDouble(e.getValue().status.body.plots_available) < 0)
    			SaveToLog("Negative plots likely bleeders present or planet out of orbit: "+ e.getValue().status.body.plots_available);
    		
    		if(e.getValue().status.body.water_stored < 50000)
    			SaveToLog("Low Water: "+ e.getValue().status.body.water_stored);
    		if(e.getValue().status.body.water_hour < 50000)
    			SaveToLog("Low Water Production: "+ e.getValue().status.body.water_hour);
    		
    		if(e.getValue().status.body.energy_stored < 50000)
    			SaveToLog("Low Energy: "+ e.getValue().status.body.energy_stored);
    		if(e.getValue().status.body.energy_hour < 50000)
    			SaveToLog("Low Energy production: "+ e.getValue().status.body.energy_hour);
    		
    		if(e.getValue().status.body.food_stored < 50000)
    			SaveToLog("Low food: "+ e.getValue().status.body.food_stored);
    		if(e.getValue().status.body.food_hour < 50000)
    			SaveToLog("Low food production: "+ e.getValue().status.body.food_hour);
    		
    		if(e.getValue().status.body.ore_stored < 50000)
    			SaveToLog("Low ore: "+ e.getValue().status.body.ore_stored);
    		if(e.getValue().status.body.ore_hour < 50000)
    			SaveToLog("Low ore production: "+ e.getValue().status.body.ore_hour);
    				
    		if(e.getValue().status.body.happiness < 50000)
    			SaveToLog("Low happiness: "+ e.getValue().status.body.happiness);
    		if(e.getValue().status.body.happiness_hour < 50000)
    			SaveToLog("Low happiness: "+ e.getValue().status.body.happiness_hour);
    	}
    }
    static void StationStatusCheck(){
    	//for(String r : planets)
    	for(Entry<String, Result> e : planets.entrySet()){
    		SaveToLog(e.getValue().status.body.name);
    		SaveToLog(e.getKey());
    		if(Double.parseDouble(e.getValue().status.body.num_incoming_enemy) > 0)
    			SaveToLog("Enemy Incoming: "+e.getValue().status.body.num_incoming_enemy);
    		if(Double.parseDouble(e.getValue().status.body.plots_available) < 0)
    			SaveToLog("Negative plots likely bleeders present or planet out of orbit: "+ e.getValue().status.body.plots_available);
    		
    		if(e.getValue().status.body.water_stored < 50000)
    			SaveToLog("Low Water: "+ e.getValue().status.body.water_stored);
    		if(e.getValue().status.body.water_hour < 50000)
    			System.out.println("Low Water Production: "+ e.getValue().status.body.water_hour);
    		
    		if(e.getValue().status.body.energy_stored < 50000)
    			SaveToLog("Low Energy: "+ e.getValue().status.body.energy_stored);
    		if(e.getValue().status.body.energy_hour < 50000)
    			SaveToLog("Low Energy production: "+ e.getValue().status.body.energy_hour);
    		
    		if(e.getValue().status.body.food_stored < 50000)
    			SaveToLog("Low food: "+ e.getValue().status.body.food_stored);
    		if(e.getValue().status.body.food_hour < 50000)
    			SaveToLog("Low food production: "+ e.getValue().status.body.food_hour);
    		
    		if(e.getValue().status.body.ore_stored < 50000)
    			SaveToLog("Low ore: "+ e.getValue().status.body.ore_stored);
    		if(e.getValue().status.body.ore_hour < 50000)
    			SaveToLog("Low ore production: "+ e.getValue().status.body.ore_hour);
    		
    	}
    }
    
    //Planetary Controls
    static void PrintPlanetControlsMenu(){
    	System.out.println("1: Perform Status Check.  This will sort bodies from SS if it's the first time it's run");
    	System.out.println("2: Individual Planet Menu");
    	System.out.println("3: Empire Wide Options");
    	System.out.println("0: To return to the main menu");
    }
    static void PlanetControlsMenu(){
    	int i = 0;
    	int control = 0;
    	Scanner input = new Scanner(System.in);
    	
    	do{
    		//PrintPlanetControlsMenu();
    		if(planets.isEmpty()){
    			System.out.println("Getting planets and Stations, this may take a few minutes");
    			SeperateSSandPlanets();
    		}
    		PrintPlanetControlsMenu();
    		try{
    			control = input.nextInt();
    			switch(control){
    			case 1:
    				//SeperateSSandPlanets();
    				PlanetStatusCheck();
    				break;
    			case 2:
    				IndividualPlanetsMenu();
    				break;
    			case 3:
    				System.out.println("Empire wide options are not implemented yet");
    				break;
    			case 0:
    				MainMenu();
    				break;
    			default:
    				System.out.println("Invalid Selection");
    				PrintPlanetControlsMenu();
    			}
    				
    			
    		}catch(InputMismatchException e){
    			System.out.println("Not a valid selection.");
    		}
    		
    	}while(i==0);
    	//input.close();
    }
    static void PrintPlanetsListMenu(){
    	//Collections.sort(planetList);
    	//Set<String> id = planetList.keySet();
    	int i = 0;
    	System.out.println("Select the number of the planet");
    	for(String name: planetNames){
    		System.out.println(++i +": "+name);
    	}
    	System.out.println("Select 0 to return to the main menu");
    }
    static void IndividualPlanetsMenu(){
    	int control;
    	int i = 0;
    	Scanner input = new Scanner(System.in);
    	do{
    		try{
    		PrintPlanetsListMenu();
    		control = input.nextInt();
    		if(control == 0)
    			i = 1;
    		if(control > planetNames.size())
    			System.out.println("Invalid Selection.");
    		else{
    			IndividualPlanetOptionsMenu(GetPlanetID(planetNames.get(control -1)));
    		}
    		}catch(InputMismatchException e){
    			System.out.println("Invalid selection.");
    		}
    	}while (i == 0);
    	//input.close();
    }
    static void EmpireWidePlanetOptions(){
    	int i = 0;
    	int control = 0;
    	Scanner input = new Scanner(System.in);
    	
    	do{
    		//PrintPlanetControlsMenu();
    		if(planets.isEmpty()){
    			System.out.println("Getting planets and Stations, this may take a few minutes");
    			SeperateSSandPlanets();
    		}
    		System.out.println("All Planets Menu");
    		System.out.println("*warning* These methods are rpc intensive and may take several minutes to run");
    		System.out.println("1: ");
    		System.out.println("2: ");
    		System.out.println("3: ");
    		System.out.println("4: ");
    		System.out.println("5: ");
    		System.out.println("6: ");
    		//PrintPlanetControlsMenu();
    		/*Methods to implement
    		 * RepairAllGlyph
    		 * FillAllShipyardsWith
    		 * CheckNearbySystems
    		 * PlanetStatusCheck
    		 * RunSweepsAllPlanets
    		 * SpyTrainer
    		 * RunAllspiesontarget
    		 */
    		try{
    			control = input.nextInt();
    			switch(control){
    			case 1:
    				//RepairAllGlyphBuildings
    				
    				break;
    			case 2:
    				
    				break;
    			case 3:
    				System.out.println("Empire wide options are not implemented yet");
    				break;
    			case 0:
    				MainMenu();
    				break;
    			default:
    				System.out.println("Invalid Selection");
    				PrintPlanetControlsMenu();
    			}
    				
    			
    		}catch(InputMismatchException e){
    			System.out.println("Not a valid selection.");
    		}
    		
    	}while(i==0);
    	//input.close();
    }
    
    //method can also return the station id
    static String GetPlanetID(String name){
    	//String id = "";
    	Set<String> ids = planetList.keySet();
    	for (String i: ids){
    		if(planetList.get(i).equals(name)){
    			System.out.println(planetList.get(i) );
    			return i;
    		}
    	}
    	return "0";
    }

    static void PrintIndividualPlanetOptionsMenu(){
    	System.out.println("1: Repair All Buildings");
    	System.out.println("2: Fill all shipyards with ships of a selected type");
    	System.out.println("3: Execute All Prisoners");
    	System.out.println("4: Assign Spies");
    	System.out.println("5: Launch Fleet");
    	System.out.println("6: Print all buildings on planet");
    	System.out.println("7: StarMap Test *warning* experimental");
    	System.out.println("8: Large Fleet Sender Test *warning* experimental");
    	System.out.println("9: Glyphinator *potential high rpc use*");
    	System.out.println("10: Spy Trainer");
    	System.out.println("11: Send Spies *warning* Experimental");
    	System.out.println("12: Repair Glyph Buildings *warning* Experimental");
    	System.out.println("13: Upgrade Glyph Buildings *warning* Experimental");
    	System.out.println("0: Return to Planets Menu");
    }
    static void IndividualPlanetOptionsMenu(String planetID){
    	int i = 0;
    	int control = 0;
    	Scanner input = new Scanner(System.in);
    	do{
    		PrintIndividualPlanetOptionsMenu();
    		try{
    			control = input.nextInt();
    			switch(control){
    			case 1:
    				RepairAllPlanetBuildings(planetID);
    				break;
    			case 2:
    				MenuFillShipyardsWith(planetID);
    				break;
    			case 3:
    				ExecuteAllPrisoners(planetID);
    				
    				break;
    			case 4:
    				//Intelministry
    				ArrayList <Spies> spies =  GetSpies(planetID);
    				String assignment = SpyAssignmentSelectionMenu();
    				AssignAllSpies(spies, planetID, assignment);
    				break;
    			case 5:
    				//Trade
    				//SpaceportSpammer(planetID);
    				LaunchFleet(planetID);
    				break;
    			case 6:
    				PrintAllBuildingsOnPlanet(planetID);
    				break;
    			case 7:
    				System.out.println(planets.get(planetID).body.x);
    				GetAllBodiesInRange30(Integer.parseInt(planets.get(planetID).status.body.x), Integer.parseInt(planets.get(planetID).status.body.y));
    				break;
    			case 8:
    				LargeFleetSenderTest(planetID);
    				break;
    			case 9:
    				SendOutMaxExcavators(planetID);
    				break;
    			case 10:
    				AssignSpiesToTrain(planetID);
    				break;
    			case 11:
    				SendSpies(planetID);
    				break;
    			case 12:
    				RepairGlyphBuildings(planetID);
    				break;
    			case 13:
    				UpgradeGlyphBuildings(planetID);
    				break;
    			case 0:
    				PlanetControlsMenu();
    				break;
    			default:
    				System.out.println("Invalid Selection");
    			}
    		}catch(InputMismatchException e){
    			System.out.println("Not a valid selection.");    			
    		}catch(NoSuchElementException e) {
    			System.out.println("No Such Element Exception, contro ="+control);
    		}
    	}while(i==0);
    	//input.close();	
    }
    
// Archaelogy Methods
    static void SendOutMaxExcavators(String planetID){
    	int archID = FindBuildingID("Archaeology Ministry", planets.get(planetID).buildings);
    	if(archID != 0){
    		Response.Building archMin = planets.get(planetID).buildings.get(archID);
    		if(Integer.parseInt(archMin.level) > 10){
    			Archaeology arch = new Archaeology("archaeology");
    			String request = arch.ViewExcavators(sessionID, String.valueOf(archID));
    			String reply = server.ServerRequest(gameServer, arch.url, request);
    			Response r = gson.fromJson(reply, Response.class);
    			int toSend = Integer.parseInt(archMin.level) - 10 - r.result.excavators.size();
    			if(toSend > 0){
    				Spaceport space = new Spaceport("spaceport");
    				int spID = FindBuildingID("Space Port", planets.get(planetID).buildings);
    				request = space.ViewAllShips(sessionID, String.valueOf(spID), "excavator");
    				reply = server.ServerRequest(gameServer, space.url, request);
    				r = gson.fromJson(reply, Response.class);
    				ArrayList<Response.Ship> ships = r.result.ships;
    				if(r.result.ships.size()!=0){
    					ArrayList<Stars> stars = GetAllBodiesInRange30(Integer.parseInt(planets.get(planetID).status.body.x), Integer.parseInt(planets.get(planetID).status.body.y));
    					ArrayList<String> possibleTargets = new ArrayList<String>();
    					for(Stars s: stars){
    						if(!CheckSystemHostile(s.bodies)){
    							for(Response.Bodies h: s.bodies)
    								if(h.empire == null &&(h.type.contentEquals("habitable planet")||h.type.contentEquals("asteroid"))){
    									System.out.println("adding to target list");
    									possibleTargets.add(h.id);
    								}
    						}
    					}
    					if(possibleTargets.size() !=0 && ships.size() > toSend){
    						int counter = 0;
    						for(String t: possibleTargets){
    							Spaceport.Target target = new Spaceport.Target();
    							target.bodyID = t;
    							space = new Spaceport("spaceport");
    							request = space.SendShip(sessionID, ships.get(counter).id, target);
    							reply = server.ServerRequest(gameServer, space.url, request);
    							//System.out.println(reply);
    							if(!reply.contentEquals("error"))
    								counter++;
    							if(counter == toSend)
    								break;
    						}
	
    					}
    					else
    						System.out.println("No possible targets in rang of 30 found, or insufficient excavators");
    					//request = map.
    				}
    				else
    					System.out.println("No excavators available, recomend building some first");
    			}
    			else
    				System.out.println("Maximum number of Excavators reached");		
    		}
    		else
    			System.out.println("Archaeology Minstry must be level 11 or greater to use excavators");
    	}
    	else
    		System.out.println("No Archaeology Minstry found");
    }

    //Map Methods
    static ArrayList<Stars> GetAllBodiesInRange30(int x, int y){
    	int x1, x2, y1, y2;
    	x1 = x-15;
    	x2 = x+15;
    	y1 = y-15;
    	y2 = y+15;
    	Map map = new Map();
    	String request = map.GetStars(sessionID, Integer.toString(x1), Integer.toString(y1), Integer.toString(x2), Integer.toString(y2));
    	String reply = server.ServerRequest(gameServer, map.url, request);
    	Response r = gson.fromJson(reply, Response.class);
    	return r.result.stars;
    }
    static ArrayList<Stars> GetAllBodiesInRange(int centerx, int centery, int range){
    	//method to support getting stars by a much larger range still in development
    	
    	int x1, x2, y1, y2;
    	x1 = centerx-15;
    	x2 = centerx+15;
    	y1 = centery-15;
    	y2 = centery+15;
    	Map map = new Map();
    	String request = map.GetStars(sessionID, Integer.toString(x1), Integer.toString(y1), Integer.toString(x2), Integer.toString(y2));
    	String reply = server.ServerRequest(gameServer, map.url, request);
    	Response r = gson.fromJson(reply, Response.class);
    	return r.result.stars;
    }
    static boolean CheckSystemHostile(ArrayList<Response.Bodies> bodies){
    	
    	for(Response.Bodies b: bodies){
    		System.out.println("checking if null");
    		if(b.empire != null){
    			System.out.println("checking if hostile");
    			if(b.empire.alignment.contentEquals("hostile"))
    				return true;
    		}
    	}
    	return false;
    }
    //Spaceport methods
    static void ViewShips(String planetID){
    	int bID = FindBuildingID("Space Port", planets.get(planetID).buildings);
    	Spaceport spaceport = new Spaceport("spaceport");
    	String request = spaceport.View(sessionID, String.valueOf(bID));//spaceport.ViewAllShips(sessionID, String.valueOf(bID));
    	System.out.println(request);
    	System.out.println(spaceport.url);
    	String reply = server.ServerRequest(gameServer, spaceport.url, request);
    	System.out.println(reply);
    }
    static ArrayList<Response.Available>  GetShipsForTargetFromPlanet(String planetID){
    	Spaceport spaceport = new Spaceport("spaceport");
    	Spaceport.Target target = GetTarget();
    	//Spaceport.Target target = new Spaceport.Target();
    	//target.bodyName = GetSingleInputFromUser("Enter the target planet name");
    	String request = spaceport.GetShipsFor(sessionID, planetID, target);//spaceport.ViewAllShips(sessionID, String.valueOf(bID));
    	String reply = server.ServerRequest(gameServer, spaceport.url, request);
    	Response r = gson.fromJson(reply, Response.class);
    	return r.result.available;
    }
    static ArrayList<Response.Available>  GetShipsForTargetFromPlanet(String planetID, Spaceport.Target target){
    	Spaceport spaceport = new Spaceport("spaceport");
    	String request = spaceport.GetShipsFor(sessionID, planetID, target);//spaceport.ViewAllShips(sessionID, String.valueOf(bID));
    	String reply = server.ServerRequest(gameServer, spaceport.url, request);
    	Response r = gson.fromJson(reply, Response.class);
    	return r.result.available;
    }

    static void SpaceportSpammer(String planetID){
    	//String bodyID = "432750";
    	int x = -5;
    	int y = 5;
    	do{
    		if(x != 0 && y != 0){
    			Spaceport spaceport = new Spaceport("spaceport");
    			String request = spaceport.Build(sessionID, planetID, String.valueOf(x), String.valueOf(y));
    			System.out.println(request);
    			String reply = server.ServerRequest(gameServer, spaceport.url, request);
    			System.out.println(reply);
    		}
    		x++;
    		if(x==6){
    			x=-5;
    			y--;
    		}
    	}while(y!=-6);
    }
    static Response.Available FindSpaceshipOfType(ArrayList<Response.Available> ships, String typeToFind){
    	for(Response.Available a: ships){
			System.out.println(a.type_human);
			System.out.println(a.combat);
			System.out.println(a.stealth);

			if(a.type_human.equals(typeToFind)){
				return a;
			}
		}
		return null;
    }
 
    static void LaunchFleet(String planetID){
    	//int bID = FindBuildingID("spaceport", planets.get(planetID).buildings);
    	Spaceport spaceport = new Spaceport("spaceport");
    	Spaceport.Target target  = new Spaceport.Target();
    	target.bodyID = GetSingleInputFromUser("Input target id");
    	String request = spaceport.GetShipsFor(sessionID, planetID, target);
    	String reply = server.ServerRequest(gameServer, spaceport.url, request);
    	Response response = gson.fromJson(reply, Response.class);
    	ArrayList<String> ships = new ArrayList<String>();
    	Captcha();
    	if (response.result.available.size() != 0){
    		String shipType = GetSingleInputFromUser("Enter ship type");
    		for(Response.Available a: response.result.available){
    			System.out.println(a.type);
    			if(a.type.contains(shipType))
    				ships.add(a.id);
    		
    		}
    	}
    	ArrayList<String> temp = new ArrayList<String>();
    	int z = 0;
    	if(ships.size()!=0){
    		//GetSingleInputFromUser("hi");
    		System.out.println("Launching fleet "+z);
    		int count = 0;
    		for(String s: ships){
    			
    			temp.add(s);
    			count++;
    			System.out.println(count);
    			if(count == 20){
    				Spaceport space = new Spaceport("spaceport");
    				String r = space.SendFleet(sessionID, temp, target);
    				SaveToLog(r);
    				r =server.ServerRequest(gameServer, spaceport.url, r);
    				SaveToLog(r);
    				temp.clear();
    				count = 0;
    				//break;
    			}
    		}
    	}
    		
    	//System.out.println(reply);
    }
    
    //    	
//Shipyard methods
    static void MenuFillShipyardsWith(String planetID){
    	int i = 0;
    	int control = 0;
    	Scanner input = new Scanner(System.in);
    	do{
    		System.out.println("Fill shipyards with:");
        	System.out.println("1: Snark3");
        	System.out.println("2: Sweeper");
        	System.out.println("3: Fighter");
        	System.out.println("4: Scow");
        	System.out.println("5: Scow Fast");
        	System.out.println("6: Scow Large");
        	System.out.println("7: Scow Mega, Warning these take a long time to build");
        	System.out.println("8: Fissure Sealer");
        	System.out.println("9: Excavator");
        	System.out.println("10: Hulk Fast");
        	System.out.println("11: Hulk Huge");
        	System.out.println("12: detonator");
        	System.out.println("13: Smuggler Ship");
        	System.out.println("14: Placebo 6");
        	System.out.println("15: Bleeder");
        	System.out.println("16: Thud");
        	System.out.println("17: Sec. Min. Seeker");
        	System.out.println("18: Supply Pod 4");
        	System.out.println("19: Scanner");
        	System.out.println("0: Return to previous menu");
        	
    		try{
    			control = input.nextInt();
    			switch(control){
    			case 1:
    				//System.out.println("Starting to build Snark 3's");
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "snark3");
    				break;
    			case 2:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "sweeper");
    				break;
    			case 3:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "fighter");
    				break;
    			case 4:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "scow");
    				break;
    			case 5:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "scow_fast");
    				break;
    			case 6:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "scow_large");
    				break;
    			case 7:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "scow_mega");
    				break;
    			case 8:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "fissure_sealer");
    				break;
    			case 9:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "excavator");
    				break;
    			case 10:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "hulk_fast");
    				break;
    			case 11:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "hulk_huge");
    				break;
    			case 12:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "detonator");
    				break;
    			case 13:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "smuggler_ship");
    				break;
    			case 14:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "placebo6");
    				break;
    			case 15:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "bleeder");
    				break;
    			case 16:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "thud");
    				break;
    			case 17:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "security_ministry_seeker");
    				break;
    			case 18:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "supply_pod4");
    				break;
    			case 19:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "scanner");
    				break;
    			case 0:
    				PlanetControlsMenu();
    				break;
    			default:
    				System.out.println("Invalid Selection");
    			}

    		}catch(InputMismatchException e){
    			System.out.println("Not a valid selection.");    			
    		}    		
    	}while(i==0);
    	//input.close();
    	
    }
    static void FillAllShipyardsWithShipTypeOnePlanet(String planetID, String shipType){
    	ArrayList<Integer> shipyards = FindAllBuildingIDs("Shipyard", planets.get(planetID).buildings);
    	String buildingLevel, request, reply;
    	int shipCount = 0;
    		for(int bID: shipyards){
    			//System.out.println(bID);
    			Shipyard shipyard = new Shipyard();
    			buildingLevel = planets.get(planetID).buildings.get(bID).level;
    			request = shipyard.BuildShip(sessionID, String.valueOf(bID), shipType, Integer.valueOf(buildingLevel));
    			reply = server.ServerRequest(gameServer, shipyard.url, request);
    			shipCount += Integer.valueOf(buildingLevel);
    		}
    	System.out.println(shipCount +" "+ shipType +" now under construction on "+ planetList.get(planetID));
    }
    

//Map Controls
    
    
    static void PrintAllBuildingsOnPlanet(String planetIDNumber){
    	HashMap<Integer, Response.Building> buildings = planets.get(planetIDNumber).buildings;
    	Set<Integer> buildingkeys = buildings.keySet();
    	Response.Building b;
    	//String bnumber;
    	//int buildingsRepaired = 0;
    	for(Integer j: buildingkeys){
    		//bnumber = String.valueOf(j);
    		b = buildings.get(j);
    		System.out.println(b.name);
    	}
    }
    
    static void RepairBuilding(String buildingID, String buildingName){
    	buildingName.toLowerCase();
    	buildingName.replace(" ", "");
    	//String y = buildingName.toLowerCase();
    	//y = y.replace(" ", "");
    	//System.out.println(buildingName);
		Buildings b = new Buildings(buildingName);
		String request = b.Repair(sessionID, buildingID);
		System.out.println(request);
		String reply = server.ServerRequest(gameServer, b.url, request);
		System.out.println(reply);
		//Response r = gson.fromJson(reply, Response.class);
    }
    static void UpgradeBuilding(String buildingID, String buildingName){
    	buildingName.toLowerCase();
    	buildingName.replace(" ", "");
    	//String y = buildingName.toLowerCase();
    	//y = y.replace(" ", "");
    	//System.out.println(buildingName);
		Buildings b = new Buildings(buildingName);
		String request = b.Upgrade(sessionID, buildingID);
		System.out.println(request);
		String reply = server.ServerRequest(gameServer, b.url, request);
		System.out.println(reply);
		//Response r = gson.fromJson(reply, Response.class);
    }
    static void RepairAllPlanetBuildings(String planetIDNumber){
    	//dumbly iterates through all buildings on a planet attempting repairs if efficiency is less than 0
    	//Will save lost city for last as it's repairs are the most expensive
    	HashMap<Integer, Response.Building> buildings = planets.get(planetIDNumber).buildings;
    	Set<Integer> buildingkeys = buildings.keySet();
    	Response.Building b;
    	String bnumber;
    	int buildingsRepaired = 0;
    	for(Integer j: buildingkeys){
    		bnumber = String.valueOf(j);
    		b = buildings.get(j);
    		if(Integer.parseInt(b.efficiency)<100){
    			System.out.println("Repairing Building: "+b.name);
    			RepairBuilding(bnumber,b.name );
    			buildingsRepaired++;
    		}
    	}
    	System.out.println(buildingsRepaired + " buildings repaired");
    }
    //static void RepairAllBuildings(){}
    static void RepairGlyphBuildings(String planetID){
    	HashMap<Integer, Response.Building> buildings = planets.get(planetID).buildings;
    	Set<Integer> buildingkeys = buildings.keySet();
    	Response.Building b;
    	String bnumber;
    	int buildingsRepaired = 0;
    	for(Integer j: buildingkeys){
    		bnumber = String.valueOf(j);
    		b = buildings.get(j);
    		if(Integer.parseInt(b.efficiency)<100){
    			if(b.name.contentEquals("Interdimensional Rift")
    					||b.name.contentEquals("Pyramid Junk Sculpture")
    					||b.name.contentEquals("Black Hole Generator")
    					||b.name.contentEquals("Citadel of Knope")
    					||b.name.contentEquals("Denton Brambles")
    					||b.name.contentEquals("Ravine")
    					||b.name.contentEquals("Oracle of Anid")
    					||b.name.contentEquals("Gratch's Gauntlet")
    					||b.name.contentEquals("Crashed Ship Site")
    					||b.name.contentEquals("Natural Spring")
    					||b.name.contentEquals("Geo Thermal Vent")
    					||b.name.contentEquals("Algea Pond")
    					||b.name.contentEquals("Volcano")
    					||b.name.contentEquals("Beeldeban Nest")
    					||b.name.contentEquals("Junk Henge Sculpture")
    					||b.name.contentEquals("Great Ball of Junk")
    					||b.name.contentEquals("Metal Junk Arches")
    					||b.name.contentEquals("Kalavian Ruins")
    					||b.name.contentEquals("Malcud Field")
    					||b.name.contentEquals("Temple of the Drajilites")
    					||b.name.contentEquals("Space Junk Park")
    					||b.name.contentEquals("Pantheon of Hagness")
    					){
    				System.out.println("Repairing Building: "+b.name);
    				RepairBuilding(bnumber,b.name );
    				buildingsRepaired++;
    			}
    		}
    	}
    	System.out.println(buildingsRepaired + " buildings repaired");
    }
    static void UpgradeGlyphBuildings(String planetID){
    	//Copied code from repair since it'll be similar
    	HashMap<Integer, Response.Building> buildings = planets.get(planetID).buildings;
    	Set<Integer> buildingkeys = buildings.keySet();
    	Response.Building b;
    	String bnumber;
    	int buildingsRepaired = 0;
    	for(Integer j: buildingkeys){
    		bnumber = String.valueOf(j);
    		b = buildings.get(j);
    		if(Integer.parseInt(b.level)<30){
    			if(b.name.contentEquals("Interdimensional Rift")
    					//||b.name.contentEquals("Pyramid Junk Sculpture")
    					||b.name.contentEquals("Black Hole Generator")
    					||b.name.contentEquals("Citadel of Knope")
    					||b.name.contentEquals("Denton Brambles")
    					||b.name.contentEquals("Ravine")
    					||b.name.contentEquals("Oracle of Anid")
    					||b.name.contentEquals("Gratch's Gauntlet")
    					||b.name.contentEquals("Crashed Ship Site")
    					||b.name.contentEquals("Natural Spring")
    					||b.name.contentEquals("Geo Thermal Vent")
    					||b.name.contentEquals("Algea Pond")
    					||b.name.contentEquals("Volcano")
    					||b.name.contentEquals("Beeldeban Nest")
    					//||b.name.contentEquals("Junk Henge Sculpture")
    					//||b.name.contentEquals("Great Ball of Junk")
    					//||b.name.contentEquals("Metal Junk Arches")
    					||b.name.contentEquals("Kalavian Ruins")
    					||b.name.contentEquals("Malcud Field")
    					||b.name.contentEquals("Temple of the Drajilites")
    					//||b.name.contentEquals("Space Junk Park")
    					||b.name.contentEquals("Pantheon of Hagness")
    					){
    				System.out.println("Repairing Building: "+b.name);
    				UpgradeBuilding(bnumber,b.name );
    				buildingsRepaired++;
    			}
    		}
    	}
    	System.out.println(buildingsRepaired + " buildings repaired");
    }
    //Spy Methods
    static ArrayList<Spies> GetSpies(String planetID){
    	int bID = FindBuildingID("Intelligence Ministry", planets.get(planetID).buildings);
    	Intelligence intel = new Intelligence("intelligence");
    	String request = intel.ViewAllSpies(sessionID, String.valueOf(bID));
    	String reply = server.ServerRequest(gameServer, intel.url, request);
    	Response r = gson.fromJson(reply, Response.class);
    	return r.result.spies;
    }
    static String SpyAssignmentSelectionMenu(){

    	int i = 0;
    	int control = 0;
    	Scanner input = new Scanner(System.in);
    	do{
    		System.out.println("Select spy assignment");
    		System.out.println("1: Idle");
    		System.out.println("2: Counter Espionage");
    		System.out.println("3: Security Sweep");
    		System.out.println("4: Intel Training");
    		System.out.println("5: Mayhem Training");
    		System.out.println("6: Politics Training");
    		System.out.println("7: Theft Training");
    		System.out.println("8: Political Propaganda");
    		System.out.println("9: Gather Resource Intelligence");
    		System.out.println("10: Gather Empire Intelligence");
    		System.out.println("11: Gather Operative Intelligence");
    		System.out.println("12: Gather Operative Intelligence");
    		System.out.println("13: Sabotage Probes");
    		System.out.println("14: Rescue Comrades");
    		System.out.println("15: Sabotage Resources");
    		System.out.println("16: Appropriate Resources");
    		System.out.println("17: Assassinate Operatives");
    		System.out.println("18: Sabotage Infrastructure");
    		System.out.println("19: Sabotage BHG");
    		System.out.println("20: Incite Mutiny");
    		System.out.println("21: Abduct Operatives");
    		System.out.println("22: Appropriate Technology");
    		System.out.println("23: Incite Rebellion");
    		System.out.println("24: Incite Insurrection");
    		try{
    			control = input.nextInt();
    			switch(control){
    			case 1:
    				return "idle";
				case 2:
					return "Counter Espionage";
				case 3:
					return "Security Sweep";
				case 4:
					return "Intel Training";
				case 5:
					return "Mayhem Training";
				case 6:
					return "Politics Training";
				case 7:
					return "Theft Training";
				case 8:
					return "Political Propaganda";
				case 9:
					return "Gather Resource Intelligence";
				case 10:
					return "Gather Empire Intelligence";
				case 11:
					return "Gather Operative Intelligence";
				case 12:
					return "Hack Network 19";
				case 13:
					return "Sabotage Probes";
				case 14:
					return "Rescue Comradese";
				case 15:
					return "Sabotage Resources";
				case 16:
					return "Appropriate Resources";
				case 17:
					return "Assassinate Operatives";
				case 18:
					return "Sabotage Infrastructure";
				case 19:
					return "Sabotage BHG";
				case 20:
					return "Incite Mutiny";
				case 21:
					return "Abduct Operatives";
				case 22:
					return "Appropriate Technology";
				case 23:
					return "Incite Rebellion";
    			case 24:
    				return "Incite Insurrection";
    			default:
    				System.out.println("Invalid Selection");
    			}
    				
    			
    		}catch(InputMismatchException e){
    			System.out.println("Not a valid selection.");
    		}
    		
    	}while(i==0);
    	//input.close();
		return null;
    }
    static void AssignAllSpies(ArrayList <Spies> spies, String planetID, String assignment){
    	if(captchaValid==false)
    		Captcha();
    	int bID = FindBuildingID("Intelligence Ministry", planets.get(planetID).buildings);
    	String request, reply;
    	
    	for(Spies s: spies){//only assigns spies that are available
    		if(s.is_available.contentEquals("1")){
    			Intelligence intel = new Intelligence("intelligence");
    			request = intel.AssignSpy(sessionID, String.valueOf(bID), s.id, assignment);
    			reply = server.ServerRequest(gameServer, intel.url, request);
    		}
    		
    	}
    }
    static void AssignSpiesToTrain(String planetID){
    	Captcha();
    	System.out.println("This assigns an equal number of spies to each type of training \nand all remaining and max level spies on the planet to Counter Espionage");
    	//this method is still under development and has lots of test code.  It doesn't do anything really yet.
    	String numb = GetSingleInputFromUser("Enter the number of spies to have training in each assignment");
    	int numbToTrain = Integer.parseInt(numb);
    	//ArrayList<String> intelTraining, politicalTraining, theftTraining, mayhemTraining, counter;
    	ArrayList<Spies> spyList = GetSpies(planetID);
    	int buildingID = FindBuildingID("Intelligence Ministry", planets.get(planetID).buildings);
    	int intelCounter = 0;
    	int politicalCounter = 0;
    	int theftCounter = 0;
    	int mayhemCounter = 0;
    	int counterEsp = 0;
    	for(Spies s: spyList){
    		if(s.assigned_to.body_id.contentEquals(planetID) && s.is_available.contentEquals("1")){
    		if(Integer.parseInt(s.level) >= 78 && !s.assignment.contentEquals("Counter Espionage")){
    			//if spy is already max level and not already on counter espionage
    			Intelligence intel = new Intelligence("intelligence");
    			String request = intel.AssignSpy(sessionID, String.valueOf(buildingID), s.id, "Counter Espionage");
    			//System.out.println(request);
    			String reply = server.ServerRequest(gameServer, intel.url, request);
    			//System.out.println(reply);
    			counterEsp++;
    			System.out.println("Assigning "+s.name+" to Counter Espionage");
    		}
    		else if(Integer.parseInt(s.mayhem) < 2600 && mayhemCounter < numbToTrain){
    			Intelligence intel = new Intelligence("intelligence");
    			String request = intel.AssignSpy(sessionID, String.valueOf(buildingID), s.id, "Mayhem Training");
    			//System.out.println(request);
    			String reply = server.ServerRequest(gameServer, intel.url, request);
    			//System.out.println(reply);
    			mayhemCounter++;
    			System.out.println("Assigning "+s.name+" to Mayhem Training");
    		}
    		else if(Integer.parseInt(s.intel) < 2600 && intelCounter < numbToTrain){
    			Intelligence intel = new Intelligence("intelligence");
    			String request = intel.AssignSpy(sessionID, String.valueOf(buildingID), s.id, "Intel Training");
    			//System.out.println(request);
    			String reply = server.ServerRequest(gameServer, intel.url, request);
    			//System.out.println(reply);
    			intelCounter++;
    			System.out.println("Assigning "+s.name+" to Intel Training");
    		}
    		else if(Integer.parseInt(s.politics) < 2600 && politicalCounter < numbToTrain){
    			Intelligence intel = new Intelligence("intelligence");
    			String request = intel.AssignSpy(sessionID, String.valueOf(buildingID), s.id, "Political Propaganda");
    			//System.out.println(request);
    			String reply = server.ServerRequest(gameServer, intel.url, request);
    			//System.out.println(reply);
    			politicalCounter++;
    		}
    		else if(Integer.parseInt(s.theft) < 2600 && theftCounter < numbToTrain){
    			Intelligence intel = new Intelligence("intelligence");
    			String request = intel.AssignSpy(sessionID, String.valueOf(buildingID), s.id, "Theft Training");
    			//System.out.println(request);
    			String reply = server.ServerRequest(gameServer, intel.url, request);
    			//System.out.println(reply);
    			theftCounter++;
    			System.out.println("Assigning "+s.name+" to Theft Training");
    		}
    		else{
    			Intelligence intel = new Intelligence("intelligence");
    			String request = intel.AssignSpy(sessionID, String.valueOf(buildingID), s.id, "Counter Espionage");
    			String reply = server.ServerRequest(gameServer, intel.url, request);
    			counterEsp++;
    			System.out.println("Assigning "+s.name+" to Counter Espionage");
    		}
    		}
    			
    	}
    	System.out.println(2600/(30/numbToTrain)+" is the approximate number of hours until max training is reached \nif training building is level 30");
    }
    static void SendSpies(String planetID){
    	Captcha();
    	String toBodyID = GetSingleInputFromUser("Enter target body ID");
    	
    	Spaceport spaceport = new Spaceport("spaceport");
    	String request = spaceport.PrepareSendSpies(sessionID, planetID, toBodyID);
    	System.out.println(request);
    	String reply = server.ServerRequest(gameServer, spaceport.url, request);
    	System.out.println(reply);
    	Response r = gson.fromJson(reply, Response.class);
    	System.out.println("Max number of spies available to send "+r.result.spies.size());
    	
    	String maxToSend = GetSingleInputFromUser("Enter the maximum number of spies to send");
    	String minimumLevel = GetSingleInputFromUser("Enter the minimum Level of the spies to send/nMax level is 78");
    	ArrayList<String> spiesToSend = new ArrayList<String>();
    	if(r.result.spies.size() != 0){
    		int send = Integer.parseInt(maxToSend);
    		int minLevel = Integer.parseInt(minimumLevel);
    		int counter = 0;
    		for(Spies s: r.result.spies){
    			if(counter <= send){
    				if(Integer.parseInt(s.level) >= minLevel){
    					spiesToSend.add(s.id);
    					counter++;
    			}
    				
    			else{
    				break;
    				}
    			}
    			
    		}
    		if(spiesToSend.size() != 0){
    			String shipID;
    			for(Response.Ship s: r.result.ships){
    				if(s.type.contentEquals("smuggler_ship")){
    					shipID = s.id;
    					spaceport = new Spaceport("spaceport");
    			    	request = spaceport.SendSpies(sessionID, planetID, toBodyID, shipID, spiesToSend);
    			    	System.out.println(request);
    			    	reply = server.ServerRequest(gameServer, spaceport.url, request);
    			    	System.out.println(reply);
    			    	System.out.println(spiesToSend.size()+" Spies sent");
    			    	break;
    				}
    				System.out.println("No Smuggler Ship Found");
    			}
    		}
    		else
    			System.out.println("No spies meet the requirements to send");
    	}
    	
    }
    static void SpyRun(){
    	
    }
    static void FetchSpies(String planetID){
    	//Spaceport spaceport = new Spaceport("spaceport");
    	//String request = spaceport.PrepareFetchSpies(sessionID, planetID, fromBodyID);
    	//System.out.println(request);
    	//String reply = server.ServerRequest(gameServer, spaceport.url, request);
    	//System.out.println(reply);
    }
    static void TrainNewSpies(String planetID){
    	Intelligence intelligence = new Intelligence("intelligence");
    	int buildingID = FindBuildingID("Intelligence Ministry", planets.get(planetID).buildings);
    	//String request = intelligence.ViewAllSpies(sessionID, buildingID);
    }
    static void SetSpiesMinistryTraining(){
    	
    }
    
    static void ExecuteAllPrisoners(String planetID){
    	int bID = FindBuildingID("Security Ministry", planets.get(planetID).buildings);
		boolean i = true;
		do{
			Security security = new Security("security");
	    	String request = security.ViewPrisoners(sessionID, String.valueOf(bID), "1");
			String reply = server.ServerRequest(gameServer, security.url, request);
			Response r = gson.fromJson(reply, Response.class);
			if(r.result.captured_count.contentEquals("0"))
				i=false;
			else{
				for(Response.Prisoner p: r.result.prisoners){
					Security s = new Security("security");
					request = s.ExecutePrisoner(sessionID, String.valueOf(bID), p.id);
					server.ServerRequest(gameServer, security.url, request);
					System.out.println("Prisoner: "+p.name+" Executed for "+p.task);
				}
			}
		}while(i == true);
		//if()
		//Nothing useful is returned in the reply of execution
    	
    	//result.captured_count shows how many prisoners are on the planet
    }
    
    
    //Station Controls
    static void PrintSSControlsMenu(){
    	System.out.println("1: Perform Status Check.  This will sort bodies from SS if it's the first time it's run");
    	System.out.println("2: Save SS Details to file");
    	System.out.println("0: To return to the main menu");
    }
    static void SSControlsMenu(){
    	int i = 0;
    	int control = 0;
    	Scanner input = new Scanner(System.in);
    	do{
    		if(planets.isEmpty()){
    			System.out.println("Getting planets and Stations, this may take a few minutes");
    			SeperateSSandPlanets();
    		}
    		PrintSSControlsMenu();
    		try{
    			control = input.nextInt();
    			switch(control){
    			case 1:
    				SeperateSSandPlanets();
    				StationStatusCheck();
    				break;
    			case 2:
    				PrintStationDetailsToFile();
    				break;
    			case 0:
    				MainMenu();
    				break;
    			default:
    				System.out.println("Invalid Selection");
    				MessageBoxMenu();
    			}					
    		}catch(InputMismatchException e){
    			System.out.println("Not a valid selection.");			
    		}		
    	}while(i==0);
    	//input.close();
    }
    static void PrintSSList(){}
    
    static void PrintStationDetailsToFile(){
    	//Response.Result r = new Response.Result();
    	//System.out.println("starting 2");
    	System.out.println(stations.size());
    	String stationID;
    	for(String s: stationNames){
    		System.out.println(s);
    		stationID = GetPlanetID(s);
    		System.out.println(stationID);
    		String station = "";
			if(stations.containsKey(stationID)){
				station = "Station id: "+stationID+ " Size: "+stations.get(stationID).status.body.size+" x: "+stations.get(stationID).status.body.x+" y: "+stations.get(s).status.body.y;
			}
    			//station = "Station id: "+stationID+ " Size: "+stations.get(stationID).status.body.size+" x: "+stations.get(stationID).status.body.x+" y: "+stations.get(s).status.body.y;
    		else
    			System.out.println("station id not found in list");
    		//System.out.println(station);
    		//try(PrintWriter out = new PrintWriter(new FileWriter("Station.log", true))) {
    		//	out.println(" ");
    		//    out.println(station);
    		//}catch (IOException e) {
    		    //exception handling left as an exercise for the reader
    		//}
    	}
    	
    	
    }
     //Message Controls
    static ArrayList <Messages> messages = new ArrayList<Messages>();
    static void PrintMessageBoxMenu(){
    	System.out.println("Mail Box: You have "+newMessages +" new messages");
    	System.out.println("Enter a number for your selection");
    	System.out.println("1, Correspondence");
    	System.out.println("2, Intelligence");
    	System.out.println("3, Spies");
    	System.out.println("4, Trade");
    	System.out.println("5, Complaint");
    	System.out.println("6, Excavator");
    	System.out.println("7, Parliament");
    	System.out.println("8, Alert");
    	System.out.println("9, Attack");
    	System.out.println("0, Return to Main Menu");
    }
    static void MessageBoxMenu(){
    	int i = 0;
    	int control = 0;
    	
    	do{
    		//Scanner input = new Scanner(System.in);
    		PrintMessageBoxMenu();
    		try{
    			//System.out.println("starting try block");
    			//control = input.nextInt();
    			control = Integer.parseInt(GetSingleInputFromUser("Enter a selection"));
    			//System.out.println(control);
    			switch(control){
    			case 1:
    				MessageBoxReader("Correspondence");
    				break;
    			case 2:
    				MessageBoxReader("Intelligence");
    				break;
    			case 3:
    				MessageBoxReader("Spies");
    				break;
    			case 4:
    				MessageBoxReader("Trade");
    				break;
    			case 5:
    				MessageBoxReader("Complaint");
    				break;
    			case 6:
    				MessageBoxReader("Excavator");
    				break;
    			case 7:
    				MessageBoxReader("Parliament");
    				break;
    			case 8:
    				MessageBoxReader("Alert");
    				break;
    			case 9:
    				MessageBoxReader("Attack");
    				break;
    			case 0:
    				MainMenu();
    				break;
    			default:
    				System.out.println("Invalid Selection");
    				MessageBoxMenu();
    			}
    				
    			
    		}catch(InputMismatchException e){
    			System.out.println("Not a valid selection.");
    			//input.close();
    		}catch(java.util.NoSuchElementException e){	
    			//input.close();
    		}
    		//input.close();
    	}while(i==0);
    	
    }
    static void MessageBoxReader(String type){
    	System.out.println(type+" Messages");
    	Inbox in = new Inbox();
    	String i = in.ViewInbox(sessionID, type);
    	
    	//sends out request
    	i = server.ServerRequest(gameServer, in.url, i);
    	Response r = gson.fromJson(i, Response.class);
    	messages = r.result.messages;
    	int o = 1; //for menu control
    	//for(Messages message : r.result.messages){ //iterates through received messages and prints out a selection menu
    	for(Messages message : messages){	
    		System.out.println("\nPress "+o+" to read message");
    		System.out.println("From : "+message.from);
    		System.out.println("Subject : "+message.subject);
    		System.out.println(message.body_preview);
    		o++;
    	}
    	System.out.println("\nEnter 0 to return to Message Main Menu");
    	
    	int c = 0;
    	int control = 0;
    	//Scanner input = new Scanner(System.in);
    	do{
    		System.out.println("Select a message to read or 0 to return");
    		try{
    			control = Integer.parseInt(GetSingleInputFromUser(""));//input.nextInt();
    			if(control == 0){
    				c=0;
    				MessageBoxMenu();}
    			else{
    				control--;
    				in = new Inbox();
    				//i = in.ReadMessage(1, sessionID, Integer.toString(r.result.messages.get(control).id ));
    				i = in.ReadMessage(1, sessionID, Integer.toString(messages.get(control).id ));
    				i = server.ServerRequest(gameServer, in.url, i);
    				//System.out.println(i);
    				r = gson.fromJson(i, Response.class);
    				System.out.println(r.result.message.body);
    				//GetSingleInputFromUser("Press any key plus enter to continue");
    	    	}	
    		}catch(InputMismatchException e){
    			System.out.println("Not a valid selection.");
    		}catch(java.util.NoSuchElementException e){
    			
    		}
    		
    	}while(c==0);
    	//input.close();
    	//System.out.println(r.result.messages[1].subject);
    }


    static void LargeFleetSenderTest(String planetID){
    	Captcha();
    	//Target selection
    	String choice;
    	Spaceport.Target target = new Spaceport.Target();
    	System.out.println("Enter Target Info");
    	System.out.println("1: Enter Target by Name");
		System.out.println("2: Enter Target by ID");
		System.out.println("3: Enter Target by x,y");
		choice = GetSingleInputFromUser("Enter a Selection");
		int selection = Integer.parseInt(choice);
		switch(selection){
		case 1: 
			target.bodyName = GetSingleInputFromUser("Enter Target Name");
			break;
		case 2:
			target.bodyID = GetSingleInputFromUser("Enter Target ID");
			break;
		case 3:
			target.x = GetSingleInputFromUser("Enter x coordinate");
			target.y = GetSingleInputFromUser("Enter y coordinate");
			break;
		}

		//Ship Type Selection
		ArrayList<Response.Available> availableShips = GetShipsForTargetFromPlanet(planetID, target);
		HashSet<String> availableType = new HashSet<String>();
		if(availableShips.size() !=0){
			for(Response.Available a: availableShips){
				availableType.add(a.type);
			}
		}
		//int count = 0;
		if(availableType.size()!=0){ //this is to print out a selection menu
			for(String a: availableType){
				System.out.println(a);
			}
		}
		choice = GetSingleInputFromUser("Type in the ship name");
		Spaceport.Type type = new Spaceport.Type();
		for(Response.Available a: availableShips){
			if(a.type.contentEquals(choice)){
				type.combat = a.combat;
				type.speed = a.speed;
				type.stealth = a.stealth;
				type.type = a.type;
				type.quantity = GetSingleInputFromUser("Enter a number to send");
				break;
			}
		}
		Set<Spaceport.Type> t = new HashSet<Spaceport.Type>(); //adding type to a hashset that the spaceport method request, multiple different ship types can be sent
		t.add(type);
		
		//Arrival Time Selection
		System.out.println("Time selection is based on the server time and date not a projection of how far in the future, \n Day, Hour, Minute, Second");
		System.out.println("Be sure your arrival time is valid, otherwise the request will not go through");
		Spaceport.Arrival arrival = new Spaceport.Arrival();
		arrival.day = GetSingleInputFromUser("Enter the day of the month for the ships to arrive");
		arrival.hour = GetSingleInputFromUser("Enter the hour in 24 hour time");
		arrival.minute = GetSingleInputFromUser("Enter the minute");
		arrival.second = GetSingleInputFromUser("Enter the second");
		
		
		Spaceport spaceport = new Spaceport("spaceport");
		String request = spaceport.SendShipTypes(sessionID, planetID, target, type, arrival);
		System.out.println(request);
		String reply = server.ServerRequest(gameServer, spaceport.url, request);
		System.out.println(reply);
		
	/*
		//Spaceport.Arrival arrival = new Spaceport.Arrival();
		Spaceport.Type type = new Spaceport.Type();
		int d = 0;
		count = 0;
		for(Response.Available a: availableShips){
			System.out.println("Type Human "+a.type_human);
			System.out.println("Combat "+a.combat);
			System.out.println("Stealth "+a.stealth);

			if(a.type_human.equals("Scanner")){
				//System.out.println("found Scanner");
				type.combat = a.combat;
				type.speed = a.speed;
				type.stealth = a.stealth;
				type.type = a.type;
				//System.out.println
				d = 1;
			}
			if(a.type.equals("scanner"))
					count++;
		}
		type.quantity = "1";
		arrival.day = "28";
		arrival.hour = "10";
		arrival.minute = "5";
		arrival.second = "0";
		target.bodyName = "Omicron";
		Set<Spaceport.Type> t = new HashSet<Spaceport.Type>();
		t.add(type);
		String request = spaceport.SendShipTypes(sessionID, planetID, target, type, arrival);
		System.out.println(request);
		String reply = server.ServerRequest(gameServer, spaceport.url, request);
		System.out.println(reply);
*/
    }
    
}