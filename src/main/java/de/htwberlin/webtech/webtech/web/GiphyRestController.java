package de.htwberlin.webtech.webtech.web;

import de.htwberlin.webtech.webtech.service.PersonService;
import de.htwberlin.webtech.webtech.web.api.FavouriteGiphy;
import de.htwberlin.webtech.webtech.web.api.GiphyManipulationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class PersonRestController {

    private final PersonService personService;

    public PersonRestController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping(path = "/api/v1/persons")
    public ResponseEntity<List<FavouriteGiphy>> fetchPersons() {
        return ResponseEntity.ok(personService.findAll());
    }
    @GetMapping(path = "/api/v1/persons/{id}")
    public ResponseEntity<FavouriteGiphy> fetchPersonById(@PathVariable Long id) {
        var person = personService.findById(id);
        return person != null? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
    }
    @PostMapping(path = "/api/v1/persons")
    public ResponseEntity<Void> createPerson(@RequestBody GiphyManipulationRequest request)throws URISyntaxException{
    var person  = personService.create(request);
    URI uri= new URI("/api/v1/persons" + person.getId());
    return ResponseEntity.created(uri).build();
    }
    @PutMapping(path = "/api/v1/persons/{id}")
        public ResponseEntity<FavouriteGiphy> updatePerson(@PathVariable Long id, @RequestBody GiphyManipulationRequest request) {
            var person = personService.update(id, request);
        return person != null? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
    }
    @DeleteMapping(path = "/api/v1/persons/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        boolean successful = personService.deleteById(id);
        return successful? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
