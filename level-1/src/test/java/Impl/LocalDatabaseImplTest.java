package Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LocalDatabaseImplTest {

    private LocalDatabaseImpl database;

    @BeforeEach
    void setUp() {

        database = new LocalDatabaseImpl();

    }

    @Test
    void level1_FlowTest() {

        int timestamp = 1;

        database.set(timestamp, "A", "B", 25);
        database.set(timestamp, "A", "C", 100);

        assertEquals(Optional.of(25), database.get(timestamp, "A", "B"));
        assertEquals(Optional.of(100), database.get(timestamp, "A", "C"));
        assertTrue(database.compareAndSet(timestamp, "A", "B", 25, 25, 30));
        assertEquals(Optional.of(30), database.get(timestamp, "A", "B"));
        assertFalse(database.compareAndSet(timestamp, "A", "C", 100, 999, 200));
        assertEquals(Optional.of(100), database.get(timestamp, "A", "C"));
        assertTrue(database.compareAndDelete(timestamp, "A", "C", 100));
        assertEquals(Optional.empty(), database.get(timestamp, "A", "C"));
        assertEquals(Optional.of(30), database.get(timestamp, "A", "B"));
        assertFalse(database.compareAndSet(1, "unknownKey", "age", 0, 10, 20));
        assertFalse(database.compareAndSet(1, null, "age", 0, 10, 20));
    }
}