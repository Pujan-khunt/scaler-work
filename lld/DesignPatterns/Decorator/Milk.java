public class Milk extends AddOnDecorator {
    public Milk(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        // Appends to the description of the wrapped object
        return super.getDescription() + ", Milk";
    }

    @Override
    public double getCost() {
        // Adds 50 to the cost of the wrapped object
        return super.getCost() + 50;
    }
}
