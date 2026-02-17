public class Bird {

    public void fly() {
        System.out.println("flying...");
    }

    public void eat() {
        System.out.println("eating...");
    }

    public void walk() {
        System.out.println("walking...");
    }
}

// Penguin inherits all properties and methods of Bird including fly() which it can't.
// Limitation: even though penguin can't fly it still inherits this method from Bird.
// So now if a user uses Bird penguin = new Penguin(); and tries to call penguin.fly()
// it will throw an exception. Which is a bad design. The user shouldn't be allowed to
// call the method itself.
class Penguin extends Bird {

    // Since a penguin can't fly, we throw the UnsupportedOperationException
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguins can't fly");
    }

    @Override
    public void eat() {
        System.out.println("Penguin is eating...");
    }

    @Override
    public void walk() {
        System.out.println("Penguin is walking...");
    }
}

class Pigeon extends Bird {

    @Override
    public void fly() {
        System.out.println("Pigeon is walking...");
    }

    @Override
    public void eat() {
        System.out.println("Pigeon is eating...");
    }

    @Override
    public void walk() {
        System.out.println("Pigeon is walking...");
    }
}
