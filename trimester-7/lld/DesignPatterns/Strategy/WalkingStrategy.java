public class WalkingStrategy implements RouteStrategy {
    @Override
    public void buildRoute(String startLocation, String destination) {
        System.out.println(
                "Calculating SAFEST WALKING route from " + startLocation + " to " + destination);
        System.out.println(
                " -> Routing via sidewalks and pedestrian crossings. Ignoring one-way street rules.");
    }
}
