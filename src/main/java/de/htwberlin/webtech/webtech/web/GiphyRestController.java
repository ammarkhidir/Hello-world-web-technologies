package de.htwberlin.webtech.webtech.web;

import de.htwberlin.webtech.webtech.service.GiphyService;
import de.htwberlin.webtech.webtech.web.api.FavouriteGiphy;
import de.htwberlin.webtech.webtech.web.api.GiphyManipulationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class GiphyRestController {

    private final GiphyService giphyService;

    public GiphyRestController(GiphyService personService) {
        this.giphyService = personService;
    }


    @GetMapping(path = "/api/v1/giphys")
    public ResponseEntity<List<FavouriteGiphy>> fetchPersons() {
        return ResponseEntity.ok(giphyService.findAll());
    }
    @GetMapping(path = "/api/v1/giphys/{id}")
    public ResponseEntity<FavouriteGiphy> fetchPersonById(@PathVariable Long id) {
        var person = giphyService.findById(id);
        return person != null? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
    }
    @PostMapping(path = "/api/v1/giphys")
    public ResponseEntity<Void> createPerson(@RequestBody GiphyManipulationRequest request)throws URISyntaxException{
    var person  = giphyService.create(request);
    URI uri= new URI("/api/v1/giphys" + person.getId());
    return ResponseEntity.created(uri).build();
    }
    @PutMapping(path = "/api/v1/giphys/{id}")
        public ResponseEntity<FavouriteGiphy> updatePerson(@PathVariable Long id, @RequestBody GiphyManipulationRequest request) {
            var person = giphyService.update(id, request);
        return person != null? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
    }
    @DeleteMapping(path = "/api/v1/giphys/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        boolean successful = giphyService.deleteById(id);
        return successful? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
