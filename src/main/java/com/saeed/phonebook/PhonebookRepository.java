package com.saeed.phonebook;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Repository
class PhonebookRepository {

    private final ConcurrentHashMap<String, PhoneNumber> database = new ConcurrentHashMap<>();

    PhoneNumber findByName(String name) {
        return simulateDatabaseInteraction(() -> database.get(name), 1000L);
    }

    PhoneNumber upsert(PhoneNumber phoneNumber) {
        return simulateDatabaseInteraction(() -> {
            database.put(phoneNumber.name(), phoneNumber);
            return phoneNumber;
        }, 500L);
    }

    void delete(String name) {
        simulateDatabaseInteraction(() -> database.remove(name), 200L);
    }

    public List<PhoneNumber> findByNumber(String number) {
        return simulateDatabaseInteraction(() -> database
                .values()
                .stream()
                .filter(phoneNumber -> phoneNumber.number().equals(number))
                .toList(), 2000L);
    }

    private <T> T simulateDatabaseInteraction(Supplier<T> block, long duration) {
        try {
            Thread.sleep(duration);
            return block.get();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
