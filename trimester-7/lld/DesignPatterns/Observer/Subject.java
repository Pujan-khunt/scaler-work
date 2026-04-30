// This interface defines the methods required for managing the subscribers.
// This interface is implemented by the publisher.
public interface Subject {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers();
}
