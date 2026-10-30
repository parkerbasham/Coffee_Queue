/*
Developer: Parker Basham
Assignment: Campus Coffee Queue
Date: 2/22/26

My switch menu is in the main method.
My if rules to determine adjusted price are implemented in the Order.getTotal method.
To find the top customer, I used parallel Arrays; I added each customerName once to an Array named "names"
as well as a corresponding price to an Array named "costs." Then, I used a separate for loop to find the highest cost and set the
topSpender to the corresponding name.

TESTING DATA: For those who are lazy like me.
This should still allow to test the full scope of the project, i.e. adding at least 6 orders,
calculating topSpender across orders with the same name, etc.
 */

package coffee_queue;

import java.util.ArrayList;
import java.util.Scanner;

// This class is used to create order objects.
class Order {

    // These are the variables for the Order class.
    private String customerName;
    private String drinkType;
    private int quantity;
    private double priceEach;

    // These are the constructors for the Order class.
    // This constructor will create the object without any data input.
    public Order() {
    }

    // This constructor will create the object and initialize all fields.
    public Order(String customerName, String drinkType, int quantity, double priceEach) {
        this.customerName = customerName;
        this.drinkType = drinkType;
        this.quantity = quantity;
        this.priceEach = priceEach;
    }

    // These are the Getters and Setters for the Order class.
    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getPriceEach() {
        return this.priceEach;
    }

    public void setPriceEach(double priceEach) {
        this.priceEach = priceEach;
    }

    public  String getDrinkType() {
        return this.drinkType;
    }

    public  void setDrinkType(String drinkType) {
        this.drinkType = drinkType;
    }

    public  int getQuantity() {
        return this.quantity;
    }

    public  void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // These are the methods for the Order class.
    // This method returns the total cost of the order.
    public double getTotal() {

        // Instantiate variables.
        double total;

        // Calculate total.
        total = quantity * priceEach;

        // This if statement applies a discount if the order has more than 3 drinks.
        if (quantity >= 3) {
            total = total - 1;
        }

        // This if statement adds a flat rate if the order contains lattes.
        if (drinkType.equalsIgnoreCase("Latte")) {
            total = total + .50;
        }

        // Return total.
        return (double) Math.round(total * 100)/100;
    }

    // This method returns a String describing the order.
    public String getInfo () {
        return " Customer Name: " + customerName + " Drink Type: " + drinkType + " Quantity:  " + quantity + " Price Per Drink: " + priceEach + " Total: $" + getTotal();
    }

// This is the end of the Order class.
}

public class Coffee_queue {

    // This function adds orders to the orders ArrayList.
    public static void addOrder(ArrayList<Order> theAL, Scanner theScan) {

        // Instantiate variables.
        String name = "";
        String drinkType = "";
        int qty = 0;
        double priceEach = 0.0;
        String done = "n";

        // This boolean loop runs until the user is finished adding orders.
        while (!done.equals("y")) {

            try {
                // Prompt user for name.
                System.out.println("What is the name of the order?\n");
                name = theScan.nextLine();

                // Prompt user for drinkType.
                System.out.println("What type of drink would you like?\n");
                drinkType = theScan.nextLine();

                // Prompt the user for quantity.
                System.out.println("How many " + drinkType + "'s would you like?\n");
                qty = Integer.parseInt(theScan.nextLine());

                // Prompt the user for priceEach.
                System.out.println("What is the price per drink?\n");
                priceEach = Double.parseDouble(theScan.nextLine());

            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // Create a new order object.
            Order newOrder = new Order(name, drinkType, qty, priceEach);

            // Add newOrder to the ArrayList.
            theAL.add(newOrder);

            // Ask the user if they are finished adding orders.
            if (theAL.size() >= 6)
                try {
                    System.out.println("Are you finished adding orders (y/n)?\n");
                    done = theScan.nextLine();

                } catch (Exception e) {
                    System.out.println("Invalid input. Please try again.\n");
                }
        }
    }

    // This function removes orders from the orders ArrayList.
    public static void removeOrder(ArrayList<Order> theAL, Scanner theScan) {

        // Instantiate variables.
        int choice = -1;
        String done = "n";

        // This boolean loop will run until the user is finished removing orders.
        while (!done.equals("y")) {

            // Display orders to the user.
            listOrders(theAL);

            // Prompt user for a choice.
            try {
                System.out.println("Please choose an order number to delete.\n");
                choice = Integer.parseInt(theScan.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.\n");
                continue;
            }

            // Remove the order from orders list.
            try {
            choice = choice - 1;
            theAL.remove(choice);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // Ask the user if they are finished removing orders.
            try {
                System.out.println("Are you finished removing orders (y/n)?\n");
                done = theScan.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.\n");
            }
        }
    }

    // This function lists all orders.
    public static void listOrders(ArrayList<Order> theAL) {

        for (int c = 0; c < theAL.size(); c++) {
            System.out.println((c+1) + ": " + theAL.get(c).getInfo());
        }
        System.out.println();
    }

    // This function prints a report of totals and biggest spender.
    public static void report(ArrayList<Order> theAL) {

        // Instantiate variables.
        double grandTotal = 0.0;
        int totalDrinks = 0;
        String topSpender = "";
        double answer = 0.0;

        // Declare and initialize Arrays.
        String[] names = new String[theAL.size()];
        double[] costs = new double[theAL.size()];

        // Populate the parallel Arrays.
        for (int n = 0; n < names.length; n++) {
            names[n] = "";
            costs[n] = 0.0;
        }

        // This for each loop calculates the grand total of all orders and total drinks to be made.
        for (Order order : theAL) {
             grandTotal = grandTotal + order.getTotal();
             totalDrinks = totalDrinks + order.getQuantity();
        }

        // This for loop adds names and costs to the corresponding Arrays only once.
        for (Order order : theAL) {
            for (int n = 0; n < names.length; n++) {
                if (names[n].isBlank()) {
                    names[n] = order.getCustomerName();
                    costs[n] = order.getTotal();
                    break;
                } else if (names[n].equals(order.getCustomerName())) {
                    costs[n] = costs[n] + order.getTotal();
                    break;
                }
            }
        }

        // This for loop calculates the biggest spender according to the name on the order.
        for (int c = 0; c < costs.length ; c++) {
            if (costs[c] > answer) {
                answer = costs[c];
                topSpender = names[c];
            }
        }

        // Display the report to the user.
        System.out.println("Grand total: $" + (double) Math.round(grandTotal * 100)/100);
        System.out.println("Total Drinks: " + totalDrinks);
        System.out.println("Biggest spender: " + topSpender + "\n");
    }

    // The main method is the menu system for the Coffee Queue Program.
    public static void main(String[] args) {

        // Instantiate variables.
         int choice = -1;
         boolean run = true;
         String msg = "Please choose one of the following options (1-5):\n" +
                "1 - Add an Order\n" +
                "2 - Remove an Order\n" +
                "3 - List all Orders\n" +
                "4 - Print Report \n" +
                "5 - Quit";

        // Initialize the ArrayList.
        ArrayList<Order> orders = new ArrayList<>();

        // Initialize the Scanner.
        Scanner userInput = new Scanner(System.in);

        // This while loop will run until the user terminates the session.
        while (run) {

            // Display the menu options to the user.
            System.out.println(msg);

            // Prompt the user for a choice.
            try {
                choice = Integer.parseInt(userInput.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.\n");
                continue;
            }

            // This switch statement calls the appropriate method based on choice.
            switch (choice) {
                case 1:
                    System.out.println("Add an order\n");
                    addOrder(orders, userInput);
                    break;
                case 2:
                    // If there are no orders, prevent calling removeOrder.
                    if (orders.isEmpty()) {
                        System.out.println("No active orders. Please add an order.\n");
                    } else {
                        System.out.println("Remove an order\n");
                        removeOrder(orders, userInput);
                    }
                    break;
                case 3:
                    System.out.println("List All orders\n");
                    listOrders(orders);
                    break;
                case 4:
                    if (orders.size() < 6) {
                        System.out.println("Please add at least 6 orders.\n");
                    } else {
                    System.out.println("Print Report\n");
                    report(orders);
                    }
                    break;
                case 5:
                    run = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.\n");
            }
        }

        // Say goodbye to the user.
        System.out.println("\nGoodbye\n");

    } // End of main.
}
