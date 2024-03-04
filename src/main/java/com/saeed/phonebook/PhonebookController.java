package com.saeed.phonebook;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/phonebooks/phones")
class PhonebookController {

    private final PhonebookService phonebookService;

    PhonebookController(PhonebookService phonebookService) {
        this.phonebookService = phonebookService;
    }

    @GetMapping("/{name}")
    PhoneNumber getByName(@PathVariable String name) {
        return phonebookService.findByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PhoneNumber create(@RequestBody PhoneNumber phoneNumber) {
        return phonebookService.create(phoneNumber);
    }

    @PutMapping("/{name}")
    PhoneNumber update(@PathVariable String name, @RequestBody PhoneNumber phoneNumber) {
        return phonebookService.update(name, phoneNumber);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable String name) {
        phonebookService.delete(name);
    }

    @GetMapping("/numbers/{number}")
    List<PhoneNumber> getByNumber(@PathVariable String number) {
        return phonebookService.findByNumber(number);
    }

}
