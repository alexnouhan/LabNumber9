package co.grandcircus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LabNumber9 {

	public static void main(String[] args) {

		// Alex Nouhan
		// Grand Circus 2019

		Scanner scan = new Scanner(System.in);

		// Arrays with the store's stock
		String[] items = { "apple", "banana", "cauliflower", "dragonfruit", "Elderberry", "fig", "grapefruit",
				"honeydew" };
		double[] prices = { 0.99, 0.59, 1.59, 2.19, 1.79, 2.09, 1.99, 3.49 };

		// Map for the menu
		Map<String, Double> menu = new HashMap<>();

		for (int i = 0; i < items.length; i++) {
			menu.put(items[i], prices[i]);
		}

		// flag for asking user to continue
		boolean flag = true;

		// Lists for tracking the users order
		ArrayList<String> userItems = new ArrayList<String>();
		ArrayList<Double> userPrices = new ArrayList<Double>();

		// Greet User
		System.out.println("Welcome to Guenther's Marketplace!");
		System.out.println();

		// Main shopping loop
		while (flag) {
			showMenu(menu);

			int order = getOrder(scan, items, "What would you like to order?: ");

			//receives int from the getOrder method and adds the items and prices to the user Lists
			userItems.add(items[order]);
			userPrices.add(prices[order]);

			System.out.println("Adding " + items[order] + " to cart at $" + prices[order]);

			System.out.println();

			//continue shopping loop
			while (flag) {
				int go = getGo(scan, "Would you like to order anything else (y/n/cart)?: ");

				//conditions for user choices to continue, quit, or show cart
				if (go == 1) {
					break;
				} else if (go == 2) {
					flag = false;
				} else if (go == 3) {
					System.out.println();
					System.out.println("Your cart:");
					for (int i = 0; i < userItems.size(); i++) {
						System.out.printf("%-18s $%s\n", userItems.get(i), userPrices.get(i));
					}
					cartTotal(userPrices);
					continue;
				}
			}
		}

		//show shopping summary and end program
		System.out.println();
		System.out.println("Thanks for your order!");
		System.out.println("Here's what you got:");

		for (int i = 0; i < userItems.size(); i++) {
			System.out.printf("%-18s $%s\n", userItems.get(i), userPrices.get(i));
		}
		
		cartTotal(userPrices);
		cartAverage(userPrices);
		cartHigh(userItems, userPrices);
		cartLow(userItems, userPrices);
		
		scan.close();

	}

	public static void showMenu(Map<String, Double> menu) {
		System.out.printf("%-18s %s \n", "Item", "Price");
		System.out.println("==================================");
		menu.forEach((i, p) -> System.out.printf("%-18s $%s \n", i, p));
		;
	}
	
	public static void cartTotal(ArrayList<Double> cart) {
		double sum = 0;
		for (double num : cart) {
			sum += num;
		}
		sum = round(sum, 2);
		System.out.println("__________________________");
		System.out.printf("%-18s $%s\n", "Total",sum);
		System.out.println();
	}
	
	public static void cartAverage(ArrayList<Double> cart) {
		double avg = 0;
		double sum = 0;
		for (double num : cart) {
			sum += num;
		}
		avg = (sum / cart.size());
		avg = round(avg, 2);
		System.out.println("Average price per item in order was $" + avg);
	}
	
	public static void cartHigh(ArrayList<String> items, ArrayList<Double> cart) {
		Double max = Collections.max(cart);
		int i = cart.indexOf(max);
		String item = items.get(i);
		System.out.println("The most expensive item you ordered is the " + item);
	}
	
	public static void cartLow(ArrayList<String> items, ArrayList<Double> cart) {
		Double min = Collections.min(cart);
		int i = cart.indexOf(min);
		String item = items.get(i);
		System.out.println("The least expensive item you ordered is the " + item);
	}

	public static int getOrder(Scanner scan, String[] items, String prompt) {
		int num = -1;
		boolean b = true;
		while (b) {
			try {
				System.out.println();
				String in = getString(scan, prompt);
				for (int i = 0; i < items.length; i++) {
					if (in.equalsIgnoreCase(items[i])) {
						num = i;
						b = false;
					}
				}
				if (b) {
					throw new IllegalArgumentException("Sorry, we don't have those. Please try again");
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println("Sorry, we don't have those. Please try again");
			}
		}
		return num;
	}

	public static int getGo(Scanner scan, String prompt) {
		String go = "";
		int num = 0;
		while (num == 0) {
			try {
				go = getString(scan, prompt);
			} catch (Exception e) {
				System.out.println("error!");
			}
			if (go.equalsIgnoreCase("y")) {
				num = 1;
			} else if (go.equalsIgnoreCase("n")) {
				num = 2;
			} else if (go.equalsIgnoreCase("cart")) {
				num = 3;
			} else {
				System.out.println();
				System.out.println("Invalid entry.");
				continue;
			}
		}
		return num;
	}

	public static String getString(Scanner sc, String prompt) {
		System.out.print(prompt);
		String s = sc.next(); // read user entry
		sc.nextLine(); // discard any other data entered on the line
		return s;
	}
	
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	 
	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
