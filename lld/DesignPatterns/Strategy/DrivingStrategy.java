public class DrivingStrategy implements RouteStrategy {
    @Override
    public void buildRoute(String startLocation, String destination) {
        System.out.println(
                "Calculating FASTEST DRIVING route from " + startLocation + " to " + destination);
        System.out.println(" -> Routing via highways. Avoiding traffic jams.");
    }
}
