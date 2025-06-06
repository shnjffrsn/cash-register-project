/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cashregister;

/**
 *
 * @author Sean Jefferson
 */
import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CashRegister {

    static Scanner input = new Scanner(System.in);
    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Product> cart = new ArrayList<>();
    static String loggedInUser = null;

    public static void main(String[] args) {
        boolean signLog = true;

        while (signLog){
            System.out.println("------------------------------------------------------------------------");
            System.out.println(" - SIGN UP: \t 1");
            System.out.println(" - LOG IN: \t 2");
            System.out.println(" - EXIT: \t 3");
            System.out.println("------------------------------------------------------------------------");
            System.out.print("Enter choice: ");
            int choice = input.nextInt();
            input.nextLine(); 

            switch (choice) {
                case 1:
                    signUp();
                    break;
                case 2:
                    if (logIn()){
                        cashRegister();
                    }   
                    break;
                case 3:
                    System.out.println("------------------------------------------------------------------------");                    
                    System.out.println("Thank you for using the cash register!");
                    signLog = false;
                    break;
                default:
                    System.out.println("Error! Invalid choice.");
                    break;
            }
        }
    }

    public static void signUp(){
        while (true){
            System.out.println("------------------------------------------------------------------------");            
            System.out.print("Enter username (or type 'exit' to go back): ");
            String username = input.nextLine();

            if (username.equalsIgnoreCase("exit")){
                return;
            }

            System.out.print("Enter password: ");
            String password = input.nextLine();

            Pattern userPat = Pattern.compile("^[a-zA-Z0-9]{5,15}$");
            Pattern passPat = Pattern.compile("^(?=.*[A-Z])(?=.*\\d).{8,20}$");

            if (!userPat.matcher(username).matches()){
                System.out.println("Invalid username. Must be 5-15 characters (letters and numbers only).");
                continue;
            }
            if (!passPat.matcher(password).matches()){
                System.out.println("Invalid password. Must be 8-20 characters (with at least an uppercase letter and a number).");
                continue;
            }

            users.add(new User(username, password));
            System.out.println("------------------------------------------------------------------------");            
            System.out.println("Signup successful!");
            break;
        }
    }

    public static boolean logIn() {
        while (true){
            System.out.println("------------------------------------------------------------------------");            
            System.out.print("Enter username (or type 'exit' to go back): ");
            String username = input.nextLine();

            if (username.equalsIgnoreCase("exit")){
                return false;
            }

            System.out.print("Enter password: ");
            String password = input.nextLine();

            for (User user : users){
                if (user != null && user.username != null && user.password != null){
                    if (user.username.equals(username) && user.password.equals(password)){
                        System.out.println("------------------------------------------------------------------------");
                        System.out.println("Login successful!");
                        loggedInUser = username;
                        return true;
                    }
                }
            }

            System.out.println("Incorrect username or password. Please try again.");
        }
    }

    public static void cashRegister(){
        boolean rotonda = true;

        System.out.println("------------------------------------------------------------------------");
        System.out.println("Welcome to Kerrimo!");
        System.out.println("------------------------------------------------------------------------");

        System.out.println("Types \t\t\t S \t M \t L \t XL\n");           
        System.out.println("All Fries \t\t 49 \t 85 \t 95 \t 195");
        System.out.println("Double Decker Fries \t 70 \t 85 \t 105 \t 135");
        System.out.println("Overload Fries \t\t \t 100 \t 120 \t 150");
        System.out.println("Mozzarella Sticks \t\t 145 \t 179 \t");
        System.out.println("Potato Balls \t\t \t 125 \t 155 \t");
        System.out.println("Potato Wedges \t\t \t 129 \t 189 \t");
        System.out.println("Mozzafries \t\t \t 139 \t 179 \t\n"); 

        System.out.println("Flavors:");
        System.out.println(" Cheese (C)\n Sour Cream (S)\n BBQ (B)");            

        while (rotonda) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println(" - ADD YOUR ORDER: \t 1");
            System.out.println(" - REMOVE YOUR ORDER: \t 2");          
            System.out.println(" - REVIEW YOUR CART: \t 3");
            System.out.println(" - EDIT QUANTITY: \t 4");
            System.out.println(" - PROCEED TO CHECKOUT:  5");
            System.out.println(" - VIEW TRANSACTIONS: \t 6");
            System.out.println(" - EXIT: \t\t 7");
            System.out.println("------------------------------------------------------------------------");
            System.out.print("Enter number: ");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice){
                case 1:
                    addProduct();
                    break;
                case 2:
                    removeProduct();
                    break;
                case 3:
                    reviewCart();
                    break;
                case 4:
                    editQuantity();
                    break;                    
                case 5:
                    checkout();
                    break;
                case 6:
                    readLogTransactions();
                    break;
                case 7:
                    rotonda = false;
                    System.out.println("Thank you for buying at Kerrimo!");
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        }
    }

    public static void addProduct(){
        boolean adding = true;
        while (adding){
            System.out.println("------------------------------------------------------------------------");
            System.out.print("Enter order (Type & Flavor): ");
            String name = input.nextLine();
            System.out.print("Enter quantity: ");
            int quantity = input.nextInt();
            System.out.print("Enter price per order: ");
            double price = input.nextDouble();
            input.nextLine();

            cart.add(new Product(name, quantity, price));
            System.out.println("------------------------------------------------------------------------");            
            System.out.println(quantity + "x " + name + " has been added to your order!");

            System.out.println("------------------------------------------------------------------------");            
            System.out.print("Do you have any orders left? [Y/N]: ");
            String ordersLeft = input.nextLine();
            if (!ordersLeft.equalsIgnoreCase("y")){
                adding = false;
            }
        }
    }

    public static void removeProduct(){
        if (cart.isEmpty()){
            System.out.println("You have no order yet!");
            return;
        }

        System.out.print("Enter order number to remove: ");
        int toRemove = input.nextInt();
        input.nextLine();

        if (toRemove > 0 && toRemove <= cart.size()){
            cart.remove(toRemove - 1);
            System.out.println("Order removed successfully!");
        } else {
            System.out.println("Invalid selection.");
        }
    }

    public static void reviewCart(){
        if (cart.isEmpty()){
            System.out.println("You have no order yet!");
            return;
        }

        System.out.println("------------------------------ ORDERS ----------------------------------");
        System.out.println("# \t Qty. \tOrder \t\tUnit \t\tSubtotal");        

        for (int i = 0; i < cart.size(); i++){
            Product p = cart.get(i);
            System.out.println((i + 1) + ".\t " + p.quantity + "\t" + p.name + "\tPhp" + p.price + "\t\tPhp" + p.totalPriceTotal());
        }
    }

    public static void editQuantity() {
        if (cart.isEmpty()) {
            System.out.println("You have no order yet!");
            return;
        }

        reviewCart();
        System.out.print("Enter order number to edit quantity: ");
        int index = input.nextInt();
        input.nextLine();

        if (index > 0 && index <= cart.size()) {
            Product p = cart.get(index - 1);
            System.out.print("Enter new quantity for " + p.name + ": ");
            int newQty = input.nextInt();
            input.nextLine();

            if (newQty > 0) {
                p.quantity = newQty;
                System.out.println("Quantity updated!");
            } else {
                System.out.println("Invalid quantity. Must be greater than 0.");
            }
        } else {
            System.out.println("Invalid selection.");
        }
    }    
    
    public static void checkout(){
        if (cart.isEmpty()){
            System.out.println("You have no order yet!");
            return;
        }

        reviewCart();
        double totalPrice = totalPriceTotal();
        double discountAmount = 0;
        boolean hasDiscount = false;

        System.out.println("------------------------------------------------------------------------");
        System.out.println("Total Price: Php" + totalPrice);

        System.out.println("------------------------------------------------------------------------");
        System.out.print("Do you have a discount? [Y/N]: ");
        String isDiscounted = input.nextLine();

        if (isDiscounted.equalsIgnoreCase("Y")){
            System.out.print("Enter discount (enter number without the % symbol): ");
            double discountRate = input.nextDouble();
            input.nextLine();

            if (discountRate < 0 || discountRate > 100){
                System.out.println("Invalid input!");
            } else {
                discountAmount = totalPrice * (discountRate / 100);
                totalPrice -= discountAmount;
                hasDiscount = true;
                System.out.println("Discounted price: Php" + totalPrice);
            }
        }

        System.out.print("Enter payment amount: Php");
        double payment = input.nextDouble();
        input.nextLine();

        if (payment >= totalPrice){
            System.out.println("Change: Php" + (payment - totalPrice));
            logTransaction(totalPrice, hasDiscount, discountAmount);
            cart.clear();
            System.out.println("------------------------------------------------------------------------");            
            System.out.println("Payment Complete!");
        } else {
            System.out.println("------------------------------------------------------------------------");            
            System.out.println("Insufficient payment!");
        }
    }    
    
    public static void logTransaction(double totalPrice, boolean hasDiscount, double discountAmount){
        try {
            FileWriter writer = new FileWriter("transactions.txt", true);
            writer.write("Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
            writer.write("Handled by: " + loggedInUser + "\n");
            writer.write("Orders:\n");
            writer.write("# \t Qty. \tOrder \t\tUnit \t\tSubtotal\n");

            for (int i = 0; i < cart.size(); i++) {
                Product p = cart.get(i);
                writer.write((i + 1) + ".\t " + p.quantity + "\t" + p.name + "\tPhp" + p.price + "\t\tPhp" + p.totalPriceTotal() + "\n");
            }

            writer.write("------------------------------------------------------------------------\n");
            if (hasDiscount) {
                writer.write("Discount Applied: -Php" + String.format("%.2f", discountAmount) + "\n");
            }
            writer.write("Total Price: Php" + String.format("%.2f", totalPrice) + "\n");
            writer.write("------------------------------------------------------------------------\n");
            writer.close();
            System.out.println("Transaction saved to transactions.txt!");
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    public static void readLogTransactions() {
        System.out.println("--------------------------TRANSACTION HISTORY---------------------------");
        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("No transactions found yet.");
        } catch (IOException e) {
            System.out.println("Error reading transactions: " + e.getMessage());
        }
    }    

    public static double totalPriceTotal(){
        double total = 0;
        for (Product product : cart){
            total += product.totalPriceTotal();
        }
        return total;
    }

    static class Product{
        public String name;
        public int quantity;
        public double price;

        public Product(String name, int quantity, double price){
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }

        public double totalPriceTotal(){
            return quantity * price;
        }
    }

    static class User{
        public String username;
        public String password;

        public User(String username, String password){
            this.username = username;
            this.password = password;
        }
    }
}