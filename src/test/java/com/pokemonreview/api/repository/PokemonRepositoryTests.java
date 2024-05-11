package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Pokemon;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTests {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void PokemonRepository_SaveAll_ReturnSavedPokemon() {

        //Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();

        //Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        //Assert
        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);
    }


    @Test
    public void PokemonRepository_SaveAll_ReturnMoreThanOnePokemon (){

        //Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();
        Pokemon pokemon2 = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();

        //Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        Pokemon savedPokemon1 = pokemonRepository.save(pokemon2);

        List<Pokemon> pokemonList = pokemonRepository.findAll();

        //Assert
        Assertions.assertThat(pokemonList).isNotNull();
        Assertions.assertThat(pokemonList.size()).isEqualTo(2);
    }


    @Test
    public void PokemonRepository_findByType_ReturnPokemonNotNull (){

        //Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();

        //Act
        pokemonRepository.save(pokemon);

     Pokemon pokemonList =  pokemonRepository.findByType(pokemon.getType()).get();

        //Assert
        Assertions.assertThat(pokemonList).isNotNull();
    }


    @Test
    public void PokemonRepository_UpdatePokemon_ReturnPokemonNotNull(){
        Pokemon pokemon = Pokemon.builder().name("pikachu").type("electric").build();

        pokemonRepository.save(pokemon);

        Pokemon pokemonSave = pokemonRepository.findById(pokemon.getId()).get();
        pokemonSave.setType("Electric");
        pokemonSave.setName("pikachu11");

        Pokemon updatedPokemon = pokemonRepository.save(pokemonSave);

        Assertions.assertThat(updatedPokemon.getName()).isNotNull();
        Assertions.assertThat(updatedPokemon.getType()).isNotNull();
    }

    @Test
    public void PokemonRepository_pokemonDelete_ReturnPokemonIsEmpty(){

        //Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();

        //Act
        pokemonRepository.save(pokemon);

        pokemonRepository.deleteById(pokemon.getId());
        Optional<Pokemon> pokemonReturn = pokemonRepository.findById(pokemon.getId());

        //Assert
        Assertions.assertThat(pokemonReturn).isEmpty();
    }
}