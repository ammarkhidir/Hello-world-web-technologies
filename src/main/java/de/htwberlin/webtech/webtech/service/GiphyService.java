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
        var giphyEntity= giphyRepository.findById(id);
        return giphyEntity.map(this::transformEntity).orElse(null);
    }


    public FavouriteGiphy create(GiphyManipulationRequest request){
        var giphyEntity = new FavouriteGiphyEntity(request.getTitle(), request.getLink());
        giphyEntity = giphyRepository.save(giphyEntity);
        return transformEntity(giphyEntity);
    }
    public FavouriteGiphy update (Long id, GiphyManipulationRequest request){
        var giphyEntityOptional = giphyRepository.findById(id);
        if (giphyEntityOptional.isEmpty()){
            return null;
        }
        var favouriteGiphyEntity  = giphyEntityOptional.get();
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
    public FavouriteGiphy transformEntity(FavouriteGiphyEntity giphyEntity) {
        return new FavouriteGiphy(
                giphyEntity.getId(),
                giphyEntity.getTitle(),
                giphyEntity.getLink()

        );
    }
}
