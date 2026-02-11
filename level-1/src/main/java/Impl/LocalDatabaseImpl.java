package Impl;

import interfaces.LocalDatabase;

import java.util.HashMap;
import java.util.Optional;

public class LocalDatabaseImpl implements LocalDatabase {

    private final HashMap<String, HashMap<String, Integer>> inMemory = new HashMap<>();

    public LocalDatabaseImpl() {
    }

    @Override
    public void set(int timestamp, String key, String field, int value) {

        HashMap<String, Integer> currentMap = new HashMap<>();

        if (inMemory.containsKey(key))
        {
            currentMap = inMemory.get(key);
        }

        currentMap.put(field, value);
        inMemory.put(key, currentMap);

    }

    @Override
    public boolean compareAndSet(int timestamp, String key, String field, int value, int expectedValue, int newValue) {

        HashMap<String, Integer> currentMap = inMemory.get(key);

        int currentValue = currentMap.get(field);
        if (currentValue == expectedValue)
        {
            currentMap.put(field, newValue);
            inMemory.put(key, currentMap);
            return true;
        }

        return false;
    }

    @Override
    public boolean compareAndDelete(int timestamp, String key, String field, int expectedValue) {

        HashMap<String, Integer> currentMap = inMemory.get(key);

        int currentValue = currentMap.get(field);
        if (currentValue == expectedValue)
        {
            currentMap.remove(field);
            inMemory.put(key, currentMap);
            return true;
        }

        return false;
    }

    @Override
    public Optional<Integer> get(int timestamp, String key, String field) {

        if (!inMemory.containsKey(key) || !inMemory.get(key).containsKey(field))
        {
            return Optional.empty();
        }

        return Optional.of(inMemory.get(key).get(field));

    }
}
