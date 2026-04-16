public class Main {
    public static void main(String[] args) {
        // 1. Order a plain Espresso
        Beverage order1 = new Espresso();
        System.out.println("Order 1: " + order1.getDescription());
        System.out.println("Cost: $" + order1.getCost());
        System.out.println("-----------------------");

        // 2. Order an Espresso with Milk
        Beverage order2 = new Espresso();
        order2 = new Milk(order2); // Wrap the espresso with Milk
        System.out.println("Order 2: " + order2.getDescription());
        System.out.println("Cost: $" + order2.getCost());
        System.out.println("-----------------------");

        // 3. Order an Espresso with Double Caramel and Milk
        // Notice the dynamic nesting!
        Beverage order3 = new Espresso();
        order3 = new Milk(order3);
        order3 = new Caramel(order3);
        order3 = new Caramel(order3); // Double caramel!

        System.out.println("Order 3: " + order3.getDescription());
        System.out.println("Cost: " + order3.getCost());
    }
}
