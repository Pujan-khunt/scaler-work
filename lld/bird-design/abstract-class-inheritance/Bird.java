public abstract class Bird {

    public abstract void fly();

    public abstract void eat();

    public abstract void walk();
}

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
