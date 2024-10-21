
/**Used this class to test the code before creating the GUI and calling the methods by calling them in action listeners etc */
import java.util.Scanner;

public class runInConsole {
    
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        Restaurant r = new Restaurant();
        Chef c = new Chef(r);
        Waiter w = new Waiter(r, c);
        
        //start threads
        c.start();
        w.start();
        
      
        
        while (true) {
            System.out.println("Please enter the name of a dish, or 'exit' to quit:");
            String dishName = scan.nextLine();
            
            // Exit condition
            if(dishName.equalsIgnoreCase("exit")) {
                break;
            }

            System.out.println("Please enter the table number for this dish:");
            int tableNumber;
            try {
                tableNumber = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid table number, please try again.");
                continue; 
            }
            
            // Now, we properly create a Dish object and add it using the method designed for it
            Dish dish = new Dish(tableNumber, dishName);
            r.addDish(dish); // Assuming createOrder is the corrected method to use
        }//end of while loop
        
        scan.close();
       for(Dish dish : r.finishedDishes){
            System.out.println(dish.toString());
       }
        System.out.println("Simulation ended.");
        System.exit(0);
    }//end of main method

}//end of class
