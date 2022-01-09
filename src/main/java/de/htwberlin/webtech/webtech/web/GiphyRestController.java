package de.htwberlin.webtech.webtech.web;

import de.htwberlin.webtech.webtech.service.GiphyService;
import de.htwberlin.webtech.webtech.web.api.FavouriteGiphy;
import de.htwberlin.webtech.webtech.web.api.GiphyManipulationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@Validated
public class GiphyRestController {

    private final GiphyService giphyService;

    public GiphyRestController(GiphyService giphyService) {
        this.giphyService = giphyService;
    }


    @GetMapping(path = "/api/v1/giphys")
    public ResponseEntity<List<FavouriteGiphy>> fetchGiphy() {
        return ResponseEntity.ok(giphyService.findAll());
    }
    @GetMapping(path = "/api/v1/giphys/{id}")
    public ResponseEntity<FavouriteGiphy> fetchGiphyById(@PathVariable Long id) {
        var giphy = giphyService.findById(id);
        return giphy != null? ResponseEntity.ok(giphy) : ResponseEntity.notFound().build();
    }
    @PostMapping(path = "/api/v1/giphys")
    public ResponseEntity<Void> createPerson( @Valid @RequestBody GiphyManipulationRequest request)throws URISyntaxException{
    var giphy  = giphyService.create(request);
    URI uri= new URI("/api/v1/giphys" + giphy.getId());
    return ResponseEntity.created(uri).build();
    }
    @PutMapping(path = "/api/v1/giphys/{id}")
        public ResponseEntity<FavouriteGiphy> updatePerson(@PathVariable Long id, @RequestBody GiphyManipulationRequest request) {
            var giphy = giphyService.update(id, request);
        return giphy != null? ResponseEntity.ok(giphy) : ResponseEntity.notFound().build();
    }
    @DeleteMapping(path = "/api/v1/giphys/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        boolean successful = giphyService.deleteById(id);
        return successful? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
