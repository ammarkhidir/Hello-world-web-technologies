package de.htwberlin.webtech.webtech.service;

import de.htwberlin.webtech.webtech.persistence.FavouriteGiphyEntity;
import de.htwberlin.webtech.webtech.persistence.PersonRepository;
import de.htwberlin.webtech.webtech.web.api.FavouriteGiphy;
import de.htwberlin.webtech.webtech.web.api.GiphyManipulationRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public List<FavouriteGiphy> findAll(){
        List<FavouriteGiphyEntity> persons = personRepository.findAll();
        return persons.stream()
                .map(this::transformEntity)
                .collect(Collectors.toList());
    }
    public FavouriteGiphy findById(Long id){
        var personEntity= personRepository.findById(id);
        return personEntity.map(this::transformEntity).orElse(null);
    }


    public FavouriteGiphy create(GiphyManipulationRequest request){
        var personEntity = new FavouriteGiphyEntity(request.getTitle(), request.getLink());
        personEntity = personRepository.save(personEntity);
        return transformEntity(personEntity);
    }
    public FavouriteGiphy update (Long id, GiphyManipulationRequest request){
        var personEntityOptional = personRepository.findById(id);
        if (personEntityOptional.isEmpty()){
            return null;
        }
        var favouriteGiphyEntity  = personEntityOptional.get();
        favouriteGiphyEntity.setTitle(request.setTitle());
        favouriteGiphyEntity.setLink(request.setLink());
        favouriteGiphyEntity = personRepository.save(favouriteGiphyEntity);

        return transformEntity(favouriteGiphyEntity);
    }
    public boolean deleteById(Long id) {
        if (!personRepository.existsById(id)) {
            return false;
        }

        personRepository.deleteById(id);
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
