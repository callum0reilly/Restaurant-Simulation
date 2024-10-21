//import neccessary libraries
import java.util.LinkedList;
import java.util.List;
import javax.swing.SwingUtilities;

public class Restaurant {
	
	//create my lists to store the data
	public final List<Dish> dishes = new LinkedList<>();
	public final List<Dish> finishedDishes = new LinkedList<>();//displayed dishes after going through sleep and will be displayed when ready
	
	//constructor
	public Restaurant() {
		
	}

	public synchronized void addDish(Dish dish) {
		dishes.add(dish);
		//System.out.println("Waiter: Dish '" + dish.getItem() + "' for table " + dish.getTableNumber() + " added to queue.");
		notifyAll(); //Notifies the chef that a new dish has been added
	}//end of addDish

	public Dish getNextDish() throws InterruptedException {
		synchronized(this) {
			while (dishes.isEmpty()) {
				System.out.println("Waiter waiting as there are no orders now");
				this.wait(); // Wait until a dish is added to the queue
			}
            return dishes.remove(0); // Remove and return the first dish in the queue
		}
		
		
	}

	// Method to create a new order and notify all threads
	public synchronized void createOrder(String dishName, int tableNumber) {
		Dish newDish = new Dish(tableNumber, dishName);//dish object to store info
		dishes.add(newDish); //add it to the list
		System.out.println("New order: Dish '" + dishName + "' for table " + tableNumber);
		notifyAll(); //Notify all waiting threads that a new dish has been ordered
	}

	public static void main(String[] args) {
		 
	}
}// end of restaurant class