import java.util.*;
import java.io.*;
/**
1.  You must write a terminal menu-based driver program (again, no GUI).
	Specifically, your driver must present the user with the following options:
	1.  Add an apartment
		*  This will (one at a time) prompt the user for all of the above-listed attributes to keep track of
	1.  Update an apartment
		*  This option will prompt the user for a street address, apartment number, and zip code of an apartment, and then ask the user if they would like to update the price of the apartment.
	1.  Remove a specific apartment from consideration
		*  This option will prompt the user for a street address, apartment number, and zip code of an apartment to remove from the data structure (e.g., if it is no longer available for rent)
		*  Note that this mean you will need to support removal of apartments other than the minimum price or maximum square footage
	1.  Retrieve the lowest price apartment
	1.  Retrieve the highest square footage apartment
	1.  Retrieve the lowest price apartment by city
		* This option will prompt the user to enter a city and then return the lowest priced apartment for that city.
	1.  Retrieve the highest square footage apartment by city
		* This option will prompt the user to enter a city and then return the biggest apartment for that city.
1.  Retrieval operations should not remove the apartment with minimum price or maximum square footage from the datastructure, just return information about that apartment.
	Apartments should only be removed via the "remove a specific apartment from consideration" menu option.
*/
@SuppressWarnings("unchecked")
public class AptTracker{
     static BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
     //static Scanner k = new Scanner (System.in);
     static IndexMaxPQ max = new IndexMaxPQ(200);
     static IndexMinPQ min = new IndexMinPQ(200);
     static HashMap<String, Integer> hash = new HashMap<String, Integer>();
     static HashMap<String, String> hash_cities = new HashMap<String, String>();
     static HashMap<String, IndexMaxPQ> max_cities = new HashMap<String, IndexMaxPQ>();
     static HashMap<String, IndexMinPQ> min_cities = new HashMap<String, IndexMinPQ>();
     public static final String MAX_SQ_FT = "MAX";
     public static final String MIN_RENT = "MIN";
     public static int aptID = 0;
     public static void main(String[] args){
          int input;
          do{
               System.out.println("Would you like to:" +
               "\n\t1. Add new apartment" +
               "\n\t2. Update an apartment" +
               "\n\t3. Remove an apartment" +
               "\n\t4. Get lowest rent" +
               "\n\t5. Get highest square footage" +
               "\n\t6. Get lowest rent by city" +
               "\n\t7. Get highest square footage by city" +
               "\n\t8. Exit" +
               "");
               input = Integer.parseInt(userInput());
               switch (input){
                    case 1:
                         addApt();
                         break;
                    case 2:
                         updateApt();
                         break;
                    case 3:
                         removeApt();
                         break;
                    case 4:
                         lowestRent();
                         break;
                    case 5:
                         highestSqft();
                         break;
                    case 6:
                         lowestRentCity();
                         break;
                    case 7:
                         highestSqftCity();
                         break;
               }
          } while(input <= 7);
     }
     public static void addApt(){
          String street_address;
          int apt_number;
          String city;
          String zip_code;
          int rent;
          int sq_foot;
          System.out.println("Please enter street address");
          street_address = userInput();
          System.out.println("Please enter apartment number");
          apt_number = Integer.parseInt(userInput());
          System.out.println("Please enter city");
          city = userInput();
          System.out.println("Please enter zip code");
          zip_code = userInput();
          System.out.println("Please enter rent");
          rent = Integer.parseInt(userInput());
          System.out.println("Please enter square footage");
          sq_foot = Integer.parseInt(userInput());

          min.insert(aptID, new Apt(street_address,apt_number,city, zip_code, rent, sq_foot, MIN_RENT, aptID));
          max.insert(aptID, new Apt(street_address,apt_number,city, zip_code, rent, sq_foot, MAX_SQ_FT, aptID));
          String key = street_address +  apt_number + zip_code;
          hash.put(key, aptID);
          hash_cities.put(key, city);
          if(max_cities.containsKey(city)){
               IndexMaxPQ tempMaxPQ = max_cities.get(city);
               tempMaxPQ.insert(aptID, new Apt(street_address,apt_number,city, zip_code, rent, sq_foot, MAX_SQ_FT, aptID));
               max_cities.put(city,tempMaxPQ);
          }
          else{
               max_cities.put(city, new IndexMaxPQ(200));
               IndexMaxPQ tempMaxPQ = max_cities.get(city);
               tempMaxPQ.insert(aptID, new Apt(street_address,apt_number,city, zip_code, rent, sq_foot, MAX_SQ_FT, aptID));
               max_cities.put(city,tempMaxPQ);
          }
          if(min_cities.containsKey(city)){
               IndexMinPQ tempMinPQ = min_cities.get(city);
               tempMinPQ.insert(aptID, new Apt(street_address,apt_number,city, zip_code, rent, sq_foot, MIN_RENT, aptID));
               min_cities.put(city,tempMinPQ);
          }
          else{
               min_cities.put(city, new IndexMinPQ(200));
               IndexMinPQ tempMinPQ = min_cities.get(city);
               tempMinPQ.insert(aptID, new Apt(street_address,apt_number,city, zip_code, rent, sq_foot, MIN_RENT, aptID));
               min_cities.put(city,tempMinPQ);
          }
          aptID++;
          // System.out.println("Print MAX");
          // max.print();
          // System.out.println("Print MIN");
          // min.print();
     }
     public static void updateApt(){
          String street_address;
          int apt_number;
          String zip_code;
          int rent;
          System.out.println("Please enter street address");
          street_address = userInput();
          System.out.println("Please enter apartment number");
          apt_number =Integer.parseInt(userInput());
          System.out.println("Please enter zip code");
          zip_code = userInput();
          System.out.println("Please enter updated rent");
          rent = Integer.parseInt(userInput());
          String key = street_address +  apt_number + zip_code;
          int id = hash.get(key);
          // int i_max = max.getAptID(street_address, apt_number, zip_code);
          // int i_min = min.getAptID(street_address, apt_number, zip_code);
          // Apt a_max = max.AptOf(i_max);
          // Apt a_min = max.AptOf(i_min);
          Apt a_max = max.AptOf(id);
          Apt a_min = max.AptOf(id);
          a_max.setRent(rent);
          a_min.setRent(rent);
          max.changeApt(id, a_max);
          min.changeApt(id, a_min);
          String city = hash_cities.get(key);
          max_cities.get(city).changeApt(id, a_max);
          min_cities.get(city).changeApt(id, a_min);

          IndexMaxPQ tempMaxPQ = max_cities.get(city);
          tempMaxPQ.changeApt(id, a_max);
          max_cities.put(city,tempMaxPQ);
          IndexMinPQ tempMinPQ = min_cities.get(city);
          tempMinPQ.changeApt(id, a_min);
          min_cities.put(city,tempMinPQ);
     }
     public static void removeApt(){
          String street_address;
          int apt_number;
          String zip_code;
          System.out.println("Please enter street address");
          street_address = userInput();
          //k.nextLine();
          System.out.println("Please enter apartment number");
          apt_number = Integer.parseInt(userInput());
          System.out.println("Please enter zip code");
          zip_code = userInput();
          // int i_max = max.getAptID(street_address, apt_number, zip_code);
          // int i_min = min.getAptID(street_address, apt_number, zip_code);
          String key = street_address +  apt_number + zip_code;
          int id = hash.get(key);
          // max.delete(i_max);
          // min.delete(i_min);
          max.delete(id);
          min.delete(id);
          String city = hash_cities.get(key);

          IndexMaxPQ tempMaxPQ = max_cities.get(city);
          tempMaxPQ.delete(id);
          max_cities.put(city,tempMaxPQ);
          IndexMinPQ tempMinPQ = min_cities.get(city);
          tempMinPQ.delete(id);
          min_cities.put(city,tempMinPQ);
     }
     public static void lowestRent(){
          Apt a = min.minApt();
          if(a != null){
               System.out.println(a);
          }
          else{
               System.out.println("Returned null");
          }
          //System.out.println(min.minApt());
     }
     public static void highestSqft(){
          Apt a = max.maxApt();
          if(a != null){
               System.out.println(a);
          }
          else{
               System.out.println("Returned null");
          }
          //System.out.println(max.maxApt());
     }
     public static void lowestRentCity(){
          String city;
          System.out.println("Please enter city");
          city = userInput();
          IndexMinPQ tempMinPQ = min_cities.get(city);
          Apt a = tempMinPQ.minApt();
          if(a != null){
               System.out.println(a);
          }
          else{
               System.out.println("No apartments found for that city");
          }
     }
     public static void highestSqftCity(){
          String city;
          System.out.println("Please enter city");
          city = userInput();
          IndexMaxPQ tempMaxPQ = max_cities.get(city);
          Apt a = tempMaxPQ.maxApt();
          if(a != null){
               System.out.println(a);
          }
          else{
               System.out.println("No apartments found for that city");
          }
     }
     public static String userInput(){
          try{
               return br.readLine();
          }
          catch (IOException ioe){
               System.out.println("IOException caught");
               return null;
          }
     }

}
