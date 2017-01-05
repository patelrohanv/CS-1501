import java.util.Scanner;

public class CarTracker {

	public static void main(String[] args) {
		//
		MinPQ loPrice = new MinPQ(100, 0);
		MinPQ loMile = new MinPQ(100, 1);
		
		Scanner kbd = new Scanner(System.in);
		int input = 0;
		do{
			System.out.println("Do you wish to:\n"
					+ "1. Add a car\n"
					+ "2. Update a car(Enter VIN)\n"
					+ "3. Remove a car (enter VIN)\n"
					+ "4. Find the car with the lowest price\n"
					+ "5. Find the car with the lowest millage\n"
					+ "6. Find the lowest price by make/model\n"
					+ "7. Find the lowest millage by make/model\n"
					+ "0. Exit");
			input = kbd.nextInt();
			
			if(input == 1){
				System.out.println("What is the VIN");
				String newVin = kbd.next();
				System.out.println("What is the Make");
				String newMake = kbd.next();
				System.out.println("What is the Model");
				String newModel = kbd.next();
				System.out.println("What is the Price");
				int newPrice = kbd.nextInt();
				System.out.println("What is the Millage");
				int newMile = kbd.nextInt();
				System.out.println("What is the Color");
				String newColor = kbd.next();
				Car newCar = new Car(newVin, newMake, newModel, newPrice, newMile, newColor);
				loPrice = addCar(loPrice, newCar);
				loMile = addCar(loMile, newCar);
			}
			else if(input == 2){
				System.out.println("Please enter vin");
				String v = kbd.next();
				try{
					loPrice.update(v);
					System.out.println("Please enter again");
					loMile.update(v);
				}
				catch(NullPointerException e){
					System.out.println("Could not find");
				}
			}
			else if(input == 3){
				System.out.println("Please enter vin");
				String v = kbd.next();
				try{
					loPrice.remove(v);
					loMile.remove(v);
				}
				catch(NullPointerException e){
					System.out.println("Could not find");
				}
			}
			else if(input == 4){
				loPrice.min().print();
			}
			else if(input == 5){
				loMile.min().print();
			}
			else if(input == 6){
				try{
					System.out.println("What Make?");
					String make = kbd.next();
					System.out.println("What Model?");
					String model = kbd.next();
					loPrice.findLoPriceMake(make,model);
				}
				catch (NullPointerException e){
					System.out.println("please initialize priority queue");
				}
				
			}
			else if(input ==7){
				try{
					System.out.println("What Make?");
					String make = kbd.next();
					System.out.println("What Model?");
					String model = kbd.next();
					loMile.findLoMileeMake(make, model);
				}
				catch(NullPointerException n){
					System.out.println("Please initalize priority queue");
				}
			}
			else if(input == 8){
				loPrice.printAll();
				loMile.printAll();
			}
		}while(input != 0);
		kbd.close();

	}
	
	public static MinPQ addCar(MinPQ pq, Car c){
		pq.insert(c);
		return pq;
	}
}
