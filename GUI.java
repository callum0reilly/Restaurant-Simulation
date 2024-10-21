
//import neccessary libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class GUI extends JFrame {

	private JTextArea orderSummary;
	private JLabel totalPriceLabel;
	private JTextField tableNumberInput;
	private final HashMap<String, Double> menuItems = new HashMap<>();
	private double total = 0.0;

	// Constructor
	public GUI(Restaurant restaurant, Chef chef, Waiter waiter) {
		setTitle("Restaurant Ordering System");
		setSize(600, 400); // size of the GUI
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// what food we have & their prices
		menuItems.put("Steak", 20.00);
		menuItems.put("Small Pizza", 14.99);
		menuItems.put("Large Pizza", 18.99);
		menuItems.put("Salad", 8.99);
		menuItems.put("Chicken Nugget", 11.00);
		menuItems.put("Beef Burger", 16.00);

		// panel for menu
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(menuItems.size(), 1));
		add(menuPanel, BorderLayout.WEST);

		// buttons for each food item
		for (String item : menuItems.keySet()) {
			JButton button = new JButton(item + " - €" + menuItems.get(item));
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					addToOrder(item, menuItems.get(item));
				}
			});
			menuPanel.add(button);
		}

		// Order summary and the total price
		orderSummary = new JTextArea();
		orderSummary.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(orderSummary);
		add(scrollPane, BorderLayout.CENTER);

		// Panel for bottom section
		JPanel bottomPanel = new JPanel(new BorderLayout());

		// Total price
		totalPriceLabel = new JLabel("Total: €0.00");
		bottomPanel.add(totalPriceLabel, BorderLayout.EAST);

		tableNumberInput = new JTextField(10); // Set the width of the text field
		JPanel tableNumberPanel = new JPanel();
		tableNumberPanel.setBorder(BorderFactory.createTitledBorder("Enter Table Number"));
		tableNumberPanel.add(new JLabel("Table:"));
		tableNumberPanel.add(tableNumberInput);
		bottomPanel.add(tableNumberPanel, BorderLayout.WEST);

		// Order button
		JButton orderButton = new JButton("Order");
		orderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitOrder(restaurant);
			}
		});
		bottomPanel.add(orderButton, BorderLayout.SOUTH);

		add(bottomPanel, BorderLayout.SOUTH);
	}

	// adds the items to the order
	private void addToOrder(String item, double price) {
		orderSummary.append(item + " - €" + price + "\n");
		total += price;
		totalPriceLabel.setText("Total: €" + String.format("%.2f", total));
	}// end of addToOrder

	// submit order to the restaurant
	private void submitOrder(Restaurant restaurant) {
		String[] lines = orderSummary.getText().split("\n");
		for (String line : lines) {
			String item = line.split(" - ")[0];
			restaurant.createOrder(item, Integer.parseInt(tableNumberInput.getText()));
		}
		orderSummary.setText("");
		totalPriceLabel.setText("Total: €0.00");
		total = 0.0;
	}// end of sibmitOrder

	public static void main(String[] args) {
		Restaurant restaurant = new Restaurant();
		Chef chef = new Chef(restaurant);
		Waiter waiter = new Waiter(restaurant, chef);

		//start the threads
		chef.start();
		waiter.start();

		new GUI(restaurant, chef, waiter).setVisible(true);

	}//end of main method
}//end of class GUI
