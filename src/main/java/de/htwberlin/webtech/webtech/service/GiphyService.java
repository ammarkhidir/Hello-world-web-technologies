package de.htwberlin.webtech.webtech.service;

import de.htwberlin.webtech.webtech.persistence.FavouriteGiphyEntity;
import de.htwberlin.webtech.webtech.persistence.GiphyRepository;
import de.htwberlin.webtech.webtech.web.api.FavouriteGiphy;
import de.htwberlin.webtech.webtech.web.api.GiphyManipulationRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiphyService {

    private final GiphyRepository giphyRepository;

    public GiphyService(GiphyRepository personRepository) {
        this.giphyRepository = personRepository;
    }
    public List<FavouriteGiphy> findAll(){
        List<FavouriteGiphyEntity> giphy = giphyRepository.findAll();
        return giphy.stream()
                .map(this::transformEntity)
                .collect(Collectors.toList());
    }
    public FavouriteGiphy findById(Long id){
        var personEntity= giphyRepository.findById(id);
        return personEntity.map(this::transformEntity).orElse(null);
    }


    public FavouriteGiphy create(GiphyManipulationRequest request){
        var giphyEntity = new FavouriteGiphyEntity(request.getTitle(), request.getLink());
        giphyEntity = giphyRepository.save(giphyEntity);
        return transformEntity(giphyEntity);
    }
    public FavouriteGiphy update (Long id, GiphyManipulationRequest request){
        var personEntityOptional = giphyRepository.findById(id);
        if (personEntityOptional.isEmpty()){
            return null;
        }
        var favouriteGiphyEntity  = personEntityOptional.get();
        favouriteGiphyEntity.setTitle(request.getTitle());
        favouriteGiphyEntity.setLink(request.getLink());
        favouriteGiphyEntity = giphyRepository.save(favouriteGiphyEntity);

        return transformEntity(favouriteGiphyEntity);
    }
    public boolean deleteById(Long id) {
        if (!giphyRepository.existsById(id)) {
            return false;
        }

        giphyRepository.deleteById(id);
        return true;
    }
    private FavouriteGiphy transformEntity(FavouriteGiphyEntity personEntity) {
        return new FavouriteGiphy(
                personEntity.getId(),
                personEntity.getTitle(),
                personEntity.getLink()

        );
    }
}
