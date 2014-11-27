package javaLEWrapper.Wrapper;
import java.util.*;

public class Response {
    int id;
    String jsonrpc;
    Result result;
    
    
    //Inner Class Definitions
    class Result{
        String session_id, url, guid, number_of_ships_building, cost_to_subsidize, spy_count, fleet_send_limit, captured_count;
        DockedShips docked_ships;
        ArrayList <Spies> spies;
        ArrayList <String> possible_assignments;
        ArrayList <ShipsBuilding> ships_building;
        Status status;
        Error error;
        Body body;
        HashMap<Integer, Building>buildings;
        ArrayList<Messages> messages;
        //Messages messages[];//used when previewing multiple messages
        Messages message; //used when reading a single message
        ArrayList<Stars> stars;
        ArrayList<Orbiting> orbiting;
        ArrayList<Incoming> incoming;
        //ArrayList<Unavailable> unavailable;
        ArrayList<Available> available;
        ArrayList<Prisoner> prisoners;
        ArrayList<Excavators> excavators;
        ArrayList<Ship> ships;
        ArrayList<Glyph> glyphs;
        
    }
    class Glyph{
    	String quantity, name, type, id;
    }
    class Excavators{
    	String resource, artifact, date_landed, plan, id, glyph;
    	Body body;
    }
    class Prisoner{
    	String leve, name, task, id, sentence_expires;
    }
    class Incoming{
    	
    }
    class Orbiting{
    	
    }
    class Unavailable{
    	HashMap<String, String> reason;
    	Ship ship;
    }
    class Ship{
    	String can_recall, fleet_speed, name, date_available, task, max_occupants, combat, stealth, can_scuttle, speed, berth_level, hold_size, id, type, type_human, date_started;
    	//payload[]  Don't know how this would look like yet
    }
    class Available{
    	String estimated_travel_time, can_recall, fleet_speed, name, date_available, task, max_occupants, combat, stealth, can_scuttle, speed, berth_level, hold_size, id, type, type_human, date_started;
    	//payload[]  Don't know how this would look like yet
    }
    class Stars{
    	//Empire empire; //
    	String zone, name, x, y, color, id;
    	Station station;
    	ArrayList<Bodies> bodies;
    }
    class Bodies{
    	Empire empire;
    	Station station;
    	Ore ore;
    	String star_id, zone, name, x, y, size, image, orbit, id, type, star_name;
    }
    class DockedShips{
    	String excavator, observatory_seeker, spaceport_seeker, snark3, snark2, snark, short_range_colony_ship, sweeper, fighter, scow, scow_mega, probe, hulk, hulk_fast;
    }
    class Spies{
    	String started_assignment, defense_rating, id, available_on, intel, offense_rating, politics, assignment, name, level, is_available, mayhem, seconds_remaining, theft;
    	ArrayList <PossibleAssignments> possible_assignments;
    	AssignedTo assigned_to;
    	AssignedTo based_from;
    	class AssignedTo{ //can also be used for based from
    		String y, x, body_id, name;
    	}
    	class PossibleAssignments{
    		String skill, recovery, task;
    	}
    }
    class Work{
    	String start, end, seconds_remaining;
    }
    class ShipsBuilding{
    	String date_completed, id, type, type_human; //type is the server recognized name, type human is for human readibility
    }
    class Ore{
    	double flourite, zircon, anthracite, gypsum, chromite, sulfur, chalcopyrite, gold, trona, methane, magnetite, halite, rutile, goethite, bauxite, kerogen, uraninite, beryl, galena, monazite; 
    }
    class Building{
    	int x, y;
    	String name, url, efficiency, level, image;	
    	Work work;
    }
    class Station{
    	String x, y, name, id;
    }
    class IncomingShips{
    	String id, date_arrives;
    	int is_ally, is_own;
    }
    class Body{
    	
		String surface_image, name, type, zone, x, y, surface_refresh, size, orbit, surface_version, image, num_incoming_own, num_incoming_ally, num_incoming_enemy, star_name, propaganda_boost, plots_available; 
    	Double population, ore_capacity, water_stored, waste_stored, food_stored, ore_stored, ore_hour, energy_capacity, water_hour, happiness, happiness_hour, food_hour, building_count, water_capacity, energy_stored, energy_hour, waste_hour, food_capacity;
    	Ore ore;
    	Station station;
    	IncomingShips[] incoming_enemy_ships, incoming_own_ships;
    }
    class Status{
        Empire empire;
        Body body;
        Server server;
    }
    class Empire{
        int rpc_count;
        int has_new_messages;
        HashMap <String, String> planets;
        HashMap <String, String> space_stations;
        String self_destruct_active;
        String name;
        String status_message;
        String self_destruct_date;
        String is_isolationist;
        String latest_message_id;
        String home_planet_id;
        String tech_level;
        String id;
        String essentia;
        Server server;
        String alignment;
    }
    class Server{
        String rpc_limit;
        String version;
        String time;
        
    }

    class Error{
        int code;
        Data data;
    }
    class Data{
        String guid, url;
    }
    class Messages{
    	String from, to, subject, date, in_reply_to, body_preview, body;
    	String [] tags, recipients;
    	int has_read, has_replied, has_archived, has_trashed, id, from_id, to_id;
    }

}

class OriginalResponse
{
  public int id;
  public String jsonrpc;

  public class Error
  {
    public int code; 
    public class Data
    {
      public String guid;
      public String url;
    }
    public Data data;  
    public String message;
  }
  public Error error;
  public class Result
  {
    public String session_id;
    public class Body
    {
      public String surface_image;
    }
    public Body body;
    public Dictionary<String,String> boosts;
    
    public class Buildable
    {
      public class Build
      {
        public class Cost
        {
          public int waste;
          public int energy;
          public int ore;
          public int food;
          public int time;
          public int water;
        }
        public String no_plot_use;
        public String reason;
        public String can;
        public String [] tags;
      }
      public class Production
      {
        public int waste_hour;
        public int waste_capacity;
        public int food_capacity;
        public int food_hour;
        public int ore_capacity;
        public int energy_hour;
        public int water_capacity;
        public int happiness_hour;
        public int energy_capacity;
        public int ore_hour;
        public int water_hour;
      }
      public Build build;
      public Production production;
      public String url;
      public String image;
    }
    public Dictionary<String,Buildable> buildable;

    public class Building
    {
      public String id; // Internal use only, not part of response
      public int index; // Not part of response, for use in Jazz Tools for mapping out buildings on a planet
      public int x;
      public int y;
      public String url;
      public String name;
      public String image;
      public String level;
      public String efficiency;

      public class Work
      {
        public int seconds_remaining;

        public String end;
        public String searching;  // Archaeology glyph search
        public String start;
      }
      public Work pending_build;
      public Work work;
    }
    public Dictionary<String,Building> buildings;
    public Building [] moved;

    public class EffectBHG
    {
      public Dictionary<String,String> fail;
      public Dictionary<String,String> side;
      public Dictionary<String,String> target;
    }
    public EffectBHG effect;

    public class Glyph
    {
      public String id;
      public String name;
      public String quantity;
      public String type;
    }
    public Glyph [] glyphs;

    public class Map
    {
      public String planetID; // Internal use only, not part of response

      public String surface_image;
      //public Buildings [] buildings;  This is throwing an error need to fix
    }
    public Map map;

    public class Plan
    {
      public String id;
      public String name;
      public String level;
      public String extra_build_level;
    }
    public Plan [] plans;

    public class Prisoner
    {
      public String id;
      public String name;
      public String level;
    }
    public Prisoner [] prisoners;

    public class Reserve
    {
      public int seconds_remaining;
      public int can;
      public int max_reserve_duration;
      public int max_reserve_size;

      public class Resource
      {
        public String quantity;
        public String type;
      }
      public Resource [] resources;
    }
    public Reserve reserve;

    public Dictionary<String,Integer> resources;

    public class Ship
    {
      public String planetID; // Internal use only, not part of response

      public String id;
      public String name;
      public String task;
      public String type;
      public String combat;
      public String stealth;
      public String speed;
      public String berth_level;
      public String hold_size;
      public String type_human;
      public String fleet_speed;
      public String date_started;
      public String date_available;
      public String estimated_travel_time;
      public int can_recall;
      public int can_scuttle;
      public int max_occupants;

      public String [] payload;
    }
    public Ship [] ships;

    public class Star
    {
      public String buildingID; // Internal use only, not part of response

      public String id;
      public String name;
      public String color;
      public String x,y;
      public String zone;

      public Status.Body [] bodies;
    }
    public Star [] stars;
    public Star star;

    public class Status
    {
      public class Body
      {
          public class Station
          {
              public String id;
              public String name;
              public String x, y;
          }
        public class Empire
        {
          public String id;
          public String name;
          public String alignment;
          public String is_isolationist;
        }
        public Station station;
        public Empire empire;
        public String energy_capacity;
        public String energy_hour;
        public String energy_stored;
        public String food_capacity;
        public String food_hour;
        public String food_stored;
        public String happiness;
        public String happiness_hour;
        public class Alliance
        {
            public String name;
            public String id;
        }
        public Alliance alliance;
        public String building_count;
        public class Incoming
        {
          public String id;
          public String date_arrives;
          public String is_own;
          public String is_ally;
        }
        public Incoming [] incoming_foreign_ships;
        public Incoming [] incoming_ally_ships;
        public Incoming [] incoming_own_ships;

        public Dictionary<String,Integer> ore;
        public String ore_capacity;
        public String ore_hour;
        public String ore_stored;
        public String plots_available;
        public String population;

        public String id;
        public String image;
        public class Influence
        {
            public String spent;
            public String total;
        }
        public Influence influence;
        public String name;
        public String orbit;
        public String size;
        public String star_id;
        public String star_name;
        public String type;
        public String waste_capacity;
        public String waste_hour;
        public String waste_stored;
        public String water;
        public String water_capacity;
        public String water_hour;
        public String water_stored;
        public String x,y;
        public String zone;
        public int needs_surface_refresh; // 1 if client needs to call get_buildings
        public String num_incoming_ally;
        public String num_incoming_enemy;
        public String num_incoming_own;
          //public string orbit;

      }
      public Body body;

      public class Empire
      {
          public String essentia;
          public String has_new_messages;
          public String home_planet_id;
          public String id;
          public String is_isolationist;
          public String latest_messaged_id;
          public String name;
        public Dictionary<String, String> planets;
        //public string home_planet_id;
        public int rpc_count;
        public String self_destruct_active;
        public String self_destruct_date;
        public String status_message;
        public String tech_level;
      }
      public Empire empire;

      public class Server
      {
        public class MapSize
        {
          public int [] x,y;
        }
        public MapSize star_map_size;
        
        public int rpc_limit;
        public String time;
        public String version;
      }
      public Server server;
    }
    public Status status;

    public class TasksBHG
    {
      public int base_fail;
      public int body_id;
      public String dist;
      public String min_level;
      public String name;
      public String occupied;
      public String range;
      public String reason;
      public String recovery;
      public String side_chance;
      public String success;
      //public String @throw;
      public String [] types;
      public String waste_cost;
    }
    public TasksBHG [] tasks;
    
    public class Trade
    {
      public Status.Body body;
      public Status.Body.Empire empire;
      
      public class Delivery
      {
        public int duration;
      }
      public Delivery delivery;
      
      public String [] offer;
      public String date_offered;
      public String id;
      public String ask;
    }
    public Trade [] trades;

    public String cargo_space_used_each;
    public String guid;
    public String number_of_ships;
    //public string session_id;
    public String star_count;
    public String url;
  }
  public Result result;
}
