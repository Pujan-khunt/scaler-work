public class Navigator {
    private RouteStrategy routeStrategy;

    public Navigator(RouteStrategy routeStrategy) {
        this.routeStrategy = routeStrategy;
    }

    public RouteStrategy getRouteStrategy() {
        return routeStrategy;
    }

    public void setRouteStrategy(RouteStrategy routeStrategy) {
        this.routeStrategy = routeStrategy;
    }


    public void navigate(String source, String destination) {
        if (routeStrategy == null) {
            throw new Error("Cannot navigate: No routing strategy is set");
        }
        routeStrategy.buildRoute(source, destination);
    }
}
