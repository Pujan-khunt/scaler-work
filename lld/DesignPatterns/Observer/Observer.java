// Any class which wants to get notified on the state change of the `Subject`
// would implement this interface.
public interface Observer {
    // The class implementing this interface would implement this method.
    // It would then receive the updated values of specified parameters.
    // It will then decide to update its logic/state based on the Subject's updated state.
    void update(float temperature, float humidity, float pressure);
}
