public class Chef extends Thread {
	private final Restaurant restaurant;
	private Dish dish;

	public Chef(Restaurant restaurant) {
		this.restaurant = restaurant;
	}


public void run() {
    try {
        while (!interrupted()) {
            synchronized(restaurant) {
                // Wait until a dish is given to him
                while(this.dish == null) {
                    restaurant.wait();
                }
            }
            
            // Outside of the synchronized block to prevent holding the lock while sleeping
            System.out.println("Chef: Cooking '" + dish.getItem() + "' for table " + dish.getTableNumber() + ".");
            Thread.sleep(5000); // cooking time
            
            synchronized(this) { // Synchronize on chef object to safely check and modify the dish
                if(this.dish != null) { // Check to ensure dish is not null
                    this.dish.setReady(true);
                    System.out.println("Chef: Dish '" + dish.getItem() + "' for table " + dish.getTableNumber() + " is ready.");
                }
            }
            
            //Notifies waiter that the dish is ready and the other waiting threads
            synchronized(restaurant) {
                restaurant.notifyAll();
            }
            
        }
    } catch (InterruptedException e) {
        System.out.println("Chef thread interrupted. Shutting down the kitchen.");
    }
}




public Dish getDish() {
	return this.dish;
}

public void setDish(Dish dish) {
	this.dish = dish;
}

public String dishToString(){
    return this.dish.toString();
}

}

