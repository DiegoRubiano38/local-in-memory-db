package interfaces;

import java.util.Optional;

public interface LocalDatabase {

    void set(int timestamp, String key, String field, int value);
    boolean compareAndSet(int timestamp, String key, String field, int value, int expectedValue, int newValue);
    boolean compareAndDelete(int timestamp, String key, String field, int expectedValue);
    Optional<Integer> get(int timestamp, String key, String field);
}
