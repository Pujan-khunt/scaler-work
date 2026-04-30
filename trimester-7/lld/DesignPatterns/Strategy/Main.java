public class Main {
    public static void main(String[] args) {
        String origin = "Uniworld 2";
        String destination = "Krishnarajapuram Railway Station";

        // Initialize Context with a default strategy
        Navigator navigator = new Navigator(new DrivingStrategy());

        // User requests a route
        navigator.navigate(origin, destination);

        // We swap the strategy AT RUNTIME without creating a new Navigator.
        navigator.setRouteStrategy(new WalkingStrategy());
        navigator.navigate(origin, destination);

        navigator.setRouteStrategy(new PublicTransitStrategy());
        navigator.navigate(origin, destination);
    }
}
