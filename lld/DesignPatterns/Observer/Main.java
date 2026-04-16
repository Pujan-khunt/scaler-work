public class Main {

    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();

        Observer[] observers = {new PhoneDisplay("Iphone 17 Pro"), new WindowDisplay()};

        for (Observer o : observers) {
            station.registerObserver(o);
        }

        station.setMeasurements(25.5f, 65.0f, 1013.1f);
        station.setMeasurements(27.0f, 70.0f, 1012.5f);

        station.removeObserver(observers[1]);

        station.setMeasurements(22.0f, 90.0f, 1010.0f);
    }
}
