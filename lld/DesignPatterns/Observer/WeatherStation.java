import java.util.ArrayList;
import java.util.List;

public class WeatherStation implements Subject {
    private final List<Observer> observers;

    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherStation() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
        System.out.println("System: A new observer has been registered.");
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
        System.out.println("System: An observer has been deregistered.");
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this.temperature, this.humidity, this.pressure);
        }
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        System.out.println("\n--- Weather Station acquired new data ---");
        notifyObservers();
    }

    // ... all kinds of setters would call the `notifyObservers()` method at the end othe body
}
