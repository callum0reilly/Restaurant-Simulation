// The Waiter class represents a thread that takes orders and adds them to the queue
public class Waiter extends Thread {
	private final Restaurant restaurant;
	private Chef chef;

	public Waiter(Restaurant restaurant, Chef chef) {
		this.restaurant = restaurant;
		this.chef = chef;
	}


	@Override
	public void run() {
		System.out.print("Waiter started their shift\n");//welcoming message
		synchronized(restaurant) {
			while(true) {
                try {
                    if(chef.getDish() == null) {
                        //System.out.print("Taking order from queue");
                        takeOrders();
                    }
                    else if(!chef.getDish().isReady()) {
                        //System.out.print("Waiter waiting");
                        
                        restaurant.wait();
                        
                    }
                    else if(chef.getDish().isReady()) {
                        //System.out.print("Waiter serving");
                        serveDish();
                        synchronized(restaurant) {
                            restaurant.notifyAll(); // Notify that the dish has been served and the chef can start on a new dish
                        }
                    }
                    
                    
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
		}
		}//end of while
		
	}

	public void takeOrders() throws InterruptedException {
		//System.out.println("The waiter received the order");
		Dish dish = restaurant.getNextDish();
		chef.setDish(dish);
        synchronized(restaurant){
            restaurant.notifyAll();

        }
		
	}
	
	public void serveDish() {
		synchronized(chef) {
            Dish dish = chef.getDish();
            if(dish != null) { // Check if dish is not null before accessing it
                restaurant.finishedDishes.add(dish);
                //System.out.println("added dish to finishedDishes");
                chef.setDish(null); // Clear the dish after processing
            } else {
                System.out.println("No dish to serve at the moment."); //chef has everything prepared
            }
        }
		//System.out.println("Verifying the chef is empty");
        try{
            chef.dishToString();//displays the dish
        }
        catch(Exception e){
            //System.out.println("Chef was cleared correctly");
        }
	}
	}
