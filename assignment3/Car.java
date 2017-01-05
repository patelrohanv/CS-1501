public class Car{
	//fields
	private String VIN;
	private String make;
	private String model;
	private int price;
	private int millage;
	private String color;

	//gets and setters
	public String getVIN() {
		return VIN;
	}

	public void setVIN(String VIN) {
		VIN = VIN;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMillage() {
		return millage;
	}

	public void setMillage(int millage) {
		this.millage = millage;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	//full constructor
	public Car (String VIN, String make, String model, int price, int millage, String color){
		this.VIN = VIN;
		this.make = make;
		this.model = model;
		this.price = price;
		this.millage = millage;
		this.color = color;
	}
	
	public boolean less(){
		return true;
	}
	//comparing only for millage
	public int compareMillage (Car c){
		if(millage < c.getMillage()){
			return -1;
		}
		else if(millage == c.getMillage()){
			return 0;
		}
		else if (millage > c.getMillage()){
			return 1;
		}
		return -1;
	}

	//comparing only for price
	public int comparePrice (Car c){
		if(price < c.getPrice()){
			return -1;
		}
		else if(price == c.getPrice()){
			return 0;
		}
		else if (price > c.getPrice()){
			return 1;
		}
		return -1;
	}
	
	//print for testing
	public void print(){
		System.out.println("VIN:" + VIN
				+ "\nMake: " + make
				+ "\nModel: " + model
				+ "\nPrice: " + price
				+ "\nMillage: " + millage
				+ "\nColor: "+ color);
	}
}