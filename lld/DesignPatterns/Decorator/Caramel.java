public class Caramel extends AddOnDecorator {
    public Caramel(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Caramel";
    }

    @Override
    public double getCost() {
        return super.getCost() + 75;
    }
}
