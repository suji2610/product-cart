import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

class Product {
    int id, quantity;
    String name, category;
    double price;

    Product(int id, String name, String category,
            double price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    void display() {
        System.out.println(id + ". " + name + " | " + category
                + " | Rs." + price + " | Stock: " + quantity);
    }
}

public class Main {

    static double total(Product[] p, int[] cart) {
        double sum = 0;
        for (int i = 0; i < p.length; i++) {
            sum += p[i].price * cart[i];
        }
        return sum;
    }

    static double discount(double amount) {
        if (amount >= 5000)
            return amount * 0.10;
        else if (amount >= 3000)
            return amount * 0.05;
        else
            return 0;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Product[] p = {
            new Product(1, "Shirt", "Clothing", 500, 10),
            new Product(2, "Shoes", "Footwear", 1000, 8),
            new Product(3, "Bag", "Accessories", 700, 15)
        };

        int[] cart = new int[p.length];
        int choice;

        do {
            System.out.println("\n--- ONLINE SHOPPING CART ---");
            System.out.println("1. Display Products");
            System.out.println("2. Search Product");
            System.out.println("3. Add to Cart");
            System.out.println("4. Remove from Cart");
            System.out.println("5. Update Quantity");
            System.out.println("6. View Cart");
            System.out.println("7. Calculate Total");
            System.out.println("8. Apply Discount");
            System.out.println("9. Generate Final Bill");
            System.out.println("10. Save Order");
            System.out.println("11. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    for (int i = 0; i < p.length; i++) {
                        if (p[i].quantity > 0)
                            p[i].display();
                    }
                    break;

                case 2:
                    sc.nextLine();
                    System.out.print("Enter product name: ");
                    String search = sc.nextLine();
                    boolean found = false;

                    for (int i = 0; i < p.length; i++) {
                        if (p[i].name.equalsIgnoreCase(search)) {
                            p[i].display();
                            found = true;
                        }
                    }

                    if (!found)
                        System.out.println("Product not found");
                    break;

                case 3:
                    System.out.print("Enter product ID: ");
                    int id = sc.nextInt();

                    if (id >= 1 && id <= p.length) {
                        int i = id - 1;
                        System.out.print("Enter quantity: ");
                        int q = sc.nextInt();

                        if (q > 0 && q <= p[i].quantity) {
                            cart[i] += q;
                            p[i].quantity -= q;
                            System.out.println("Product added to cart");
                        } else {
                            System.out.println("Invalid quantity or stock");
                        }
                    } else {
                        System.out.println("Invalid product ID");
                    }
                    break;

                case 4:
                    System.out.print("Enter product ID: ");
                    id = sc.nextInt();

                    if (id >= 1 && id <= p.length && cart[id - 1] > 0) {
                        p[id - 1].quantity += cart[id - 1];
                        cart[id - 1] = 0;
                        System.out.println("Product removed");
                    } else {
                        System.out.println("Product not in cart");
                    }
                    break;

                case 5:
                    System.out.print("Enter product ID: ");
                    id = sc.nextInt();

                    if (id >= 1 && id <= p.length) {
                        int i = id - 1;
                        System.out.print("Enter new quantity: ");
                        int q = sc.nextInt();

                        if (q >= 0 && q <= cart[i] + p[i].quantity) {
                            p[i].quantity += cart[i] - q;
                            cart[i] = q;
                            System.out.println("Quantity updated");
                        } else {
                            System.out.println("Invalid quantity");
                        }
                    } else {
                        System.out.println("Invalid product ID");
                    }
                    break;

                case 6:
                    boolean empty = true;
                    System.out.println("--- YOUR CART ---");

                    for (int i = 0; i < p.length; i++) {
                        if (cart[i] > 0) {
                            System.out.println(p[i].name + " x "
                                    + cart[i] + " = Rs."
                                    + (p[i].price * cart[i]));
                            empty = false;
                        }
                    }

                    if (empty)
                        System.out.println("Cart is empty");
                    break;

                case 7:
                    System.out.println("Total Amount: Rs."
                            + total(p, cart));
                    break;

                case 8:
                    double amount = total(p, cart);
                    double disc = discount(amount);

                    System.out.println("Total: Rs." + amount);
                    System.out.println("Discount: Rs." + disc);
                    System.out.println("Final Amount: Rs."
                            + (amount - disc));
                    break;

                case 9:
                    amount = total(p, cart);
                    disc = discount(amount);

                    System.out.println("\n--- FINAL BILL ---");

                    for (int i = 0; i < p.length; i++) {
                        if (cart[i] > 0) {
                            System.out.println(p[i].name + " x "
                                    + cart[i] + " = Rs."
                                    + (p[i].price * cart[i]));
                        }
                    }

                    System.out.println("Total: Rs." + amount);
                    System.out.println("Discount: Rs." + disc);
                    System.out.println("Amount to Pay: Rs."
                            + (amount - disc));
                    break;

                case 10:
                    amount = total(p, cart);
                    disc = discount(amount);

                    try {
                        FileWriter f = new FileWriter("order.txt");

                        f.write("--- ORDER INFORMATION ---\n");

                        for (int i = 0; i < p.length; i++) {
                            if (cart[i] > 0) {
                                f.write(p[i].name + " x " + cart[i]
                                        + " = Rs."
                                        + (p[i].price * cart[i]) + "\n");
                            }
                        }

                        f.write("Total: Rs." + amount + "\n");
                        f.write("Discount: Rs." + disc + "\n");
                        f.write("Final Amount: Rs."
                                + (amount - disc) + "\n");

                        f.close();
                        System.out.println("Order saved in order.txt");

                    } catch (IOException e) {
                        System.out.println("Error saving order");
                    }
                    break;

                case 11:
                    System.out.println("Thank you for shopping!");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 11);

        sc.close();
    }
}