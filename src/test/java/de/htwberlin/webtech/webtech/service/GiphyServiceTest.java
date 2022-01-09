package de.htwberlin.webtech.webtech.service;

import de.htwberlin.webtech.webtech.persistence.FavouriteGiphyEntity;
import de.htwberlin.webtech.webtech.persistence.GiphyRepository;
import de.htwberlin.webtech.webtech.web.api.FavouriteGiphy;
import de.htwberlin.webtech.webtech.web.api.GiphyManipulationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class  GiphyServiceTest {

    @Mock
    private GiphyRepository repository;

    @InjectMocks
    private GiphyService underTest;


    @Test
    @DisplayName("should return true if delete was successful")
    void should_return_true_if_delete_was_successful() {
        // given
        Long givenId = 111L;
        doReturn(true).when(repository).existsById(givenId);

        // when
        boolean result = underTest.deleteById(givenId);

        // then
        verify(repository).deleteById(givenId);
        assertThat(result).isTrue();
    }
    @Test
    @DisplayName("should return false if person to delete does not exist")
    void should_return_false_if_person_to_delete_does_not_exist() {
        // given
        Long givenId = 111L;
        doReturn(false).when(repository).existsById(givenId);

        // when
        boolean result = underTest.deleteById(givenId);

        // then
        verifyNoMoreInteractions(repository);
        assertThat(result).isFalse();
    }
    @Test
    @DisplayName("should transform PersonEntity to Person")
    void should_transform_person_entity_to_person() {
        // given
        var giphyEntity = Mockito.mock(FavouriteGiphyEntity.class);
        doReturn(111L).when(giphyEntity).getId();
        doReturn("Example Title").when(giphyEntity).getTitle();
        doReturn("https://example.com").when(giphyEntity).getLink();

        // when
        var result = underTest.transformEntity(giphyEntity);

        // then
        assertThat(result.getId()).isEqualTo(111L);
        assertThat(result.getTitle()).isEqualTo("Example Title");
        assertThat(result.getLink()).isEqualTo("https://example.com");
    }
}
