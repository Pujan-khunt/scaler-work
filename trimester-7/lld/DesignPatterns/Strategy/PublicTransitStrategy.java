public class PublicTransitStrategy implements RouteStrategy {
    @Override
    public void buildRoute(String startLocation, String destination) {
        System.out.println(
                "Calculating PUBLIC TRANSIT route from " + startLocation + " to " + destination);
        System.out.println(" -> Fetching bus and train schedules. Calculating layovers.");
    }
}
