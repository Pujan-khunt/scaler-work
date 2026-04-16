// This base decorator class implements the same methods as the other concrete classes ('Espresso'
// and 'DarkRoast')
// Its job is to define the wrapping behaviour of condiments.
public abstract class AddOnDecorator implements Beverage {

    protected final Beverage beverageWrapper;

    public AddOnDecorator(Beverage beverage) {
        this.beverageWrapper = beverage;
    }

    @Override
    public double getCost() {
        return this.beverageWrapper.getCost();
    }

    @Override
    public String getDescription() {
        return this.beverageWrapper.getDescription();
    }

}
