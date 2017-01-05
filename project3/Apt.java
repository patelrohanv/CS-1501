/**
 * First you must create a class to store data about apartments.
 * Specifically, this class must contain the following information:
 *  A street address (e.g., 4200 Forbes Ave.)
 *  An apartment number (e.g., 3601)
 *  The city the apartment is in (e.g., Pittsburgh)
 *  The apartment's ZIP code (e.g., 15213)
 *  The price to rent (in US dollars per month)
 *  The square footage of the apartment
*/
 @SuppressWarnings("unchecked")
public class Apt{
     public static final String MAX_SQ_FT = "MAX";
     public static final String MIN_RENT = "MIN";
     private String street_address;
     private int apt_number;
     private String city;
     private String zip_code;
     private int rent;
     private int sq_foot;
     public final String type;
     public final int id;

     public Apt(String street_address, int apt_number, String city, String zip_code, int rent, int sq_foot, String type, int id){
              this.street_address = street_address;
              this.apt_number = apt_number;
              this.city = city;
              this.zip_code = zip_code;
              this.rent = rent;
              this.sq_foot = sq_foot;
              this.type = type;
              this.id = id;
     }

     /**
     * Returns value of street_address
     * @return
     */
     public String getStreet_address() {
          return street_address;
	}

	/**
	* Sets new value of street_address
	* @param
	*/
	public void setStreet_address(String street_address) {
		this.street_address = street_address;
	}

	/**
	* Returns value of apt_number
	* @return
	*/
	public int getApt_number() {
		return apt_number;
	}

	/**
	* Sets new value of apt_number
	* @param
	*/
	public void setApt_number(int apt_number) {
		this.apt_number = apt_number;
	}

	/**
	* Returns value of city
	* @return
	*/
	public String getCity() {
		return city;
	}

	/**
	* Sets new value of city
	* @param
	*/
	public void setCity(String city) {
		this.city = city;
	}

	/**
	* Returns value of zip_code
	* @return
	*/
	public String getZip_code() {
		return zip_code;
	}

	/**
	* Sets new value of zip_code
	* @param
	*/
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	/**
	* Returns value of rent
	* @return
	*/
	public int getRent() {
		return rent;
	}

	/**
	* Sets new value of rent
	* @param
	*/
	public void setRent(int rent) {
		this.rent = rent;
	}

	/**
	* Returns value of sq_foot
	* @return
	*/
	public int getSq_foot() {
		return sq_foot;
	}

	/**
	* Sets new value of sq_foot
	* @param
	*/
	public void setSq_foot(int sq_foot) {
		this.sq_foot = sq_foot;
	}
     /**
     * Compare methods for min rent
     * if equal return 0
     * if less than return -1
     * if greater than return 1
     */
     public int compareTo(Apt a){
          if(a.getRent() == rent){
               return 0;
          }
          else if (a.getRent() > rent){
               return -1;
          }
          else {//if (a.getRent() < rent)
               return 1;
          }
     }
     /**
     * Compare methods for max square foot
     * if equal return 0
     * if less than return -1
     * if greater than return 1
     */
     public int compareToo(Apt a){
          if(a.getSq_foot() == sq_foot){
               return 0;
          }
          else if (a.getSq_foot() > sq_foot){
               return -1;
          }
          else {//(a.getSq_foot() < sq_foot)
               return 1;
          }
     }
     /**
     * Equals methods
     * equal return true
     * if not return false
     * compares address, apt number, zip
     */
     public boolean equals(Apt a){
          if(a.getStreet_address().equals(street_address) && a.getApt_number() == apt_number && a.getZip_code().equals(zip_code)){
               return true;
          }
          return false;
     }

     public String toString(){
          StringBuilder s = new StringBuilder("Street Address: " + street_address);
          s.append("\nApartment Number: " + apt_number);
          s.append("\nCity: " + city);
          s.append("\nZip Code: " + zip_code);
          s.append("\nRent: " + rent);
          s.append("\nSquare Footage: " + sq_foot);
          return s.toString();

     }
     public static void main(String[] args){
          Apt a = new Apt("303 Falcon Gate Drive", 404, "Paris", "02942", 3000, 500, "MAX", 0);
          System.out.println(a);
     }
}
