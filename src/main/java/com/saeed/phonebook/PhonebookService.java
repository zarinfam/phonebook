package com.saeed.phonebook;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class PhonebookService {

    private final PhonebookRepository phonebookRepository;

    PhonebookService(PhonebookRepository phonebookRepository) {
        this.phonebookRepository = phonebookRepository;
    }

    @Cacheable(value = "phonebook")
    PhoneNumber findByName(String name) {
        return phonebookRepository.findByName(name);
    }

    @CachePut(value = "phonebook", key = "#phoneNumber.name")
    PhoneNumber create(PhoneNumber phoneNumber) {
        return phonebookRepository.insert(phoneNumber);
    }

    @Caching(put = @CachePut(value = "phonebook", key = "#phoneNumber.name"),
            evict = @CacheEvict(value = "phonebook", key = "#name"))
    PhoneNumber update(String name, PhoneNumber phoneNumber) {
        return phonebookRepository.update(name, phoneNumber);
    }

    @CacheEvict(value = "phonebook")
    void delete(String name) {
        phonebookRepository.delete(name);
    }

    public List<PhoneNumber> findByNumber(String number) {
        return phonebookRepository.findByNumber(number);
    }
}
