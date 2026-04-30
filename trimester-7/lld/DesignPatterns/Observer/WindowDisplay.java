public class WindowDisplay implements Observer {
    @Override
    public void update(float temperature, float humidity, float pressure) {
        // The LED window display shows everything
        System.out.println("LED Window Display: [T: " + temperature + "°C | H: " + humidity
                + "% | P: " + pressure + " hPa]");
    }
}
