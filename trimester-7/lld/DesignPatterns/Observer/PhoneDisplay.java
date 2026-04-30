// This class displays the weather information on a phone screen
// It needs to subscribe to the changes in the weather data.
public class PhoneDisplay implements Observer {
    private final String deviceName;

    public PhoneDisplay(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        // The mobile app only cares about temperature and humidity
        System.out.println(deviceName + " App Notification: Temp is " + temperature
                + "°C, Humidity is " + humidity + "%.");
    }
}
