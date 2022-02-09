/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal;

import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import hr.algebra.model.User;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Mladen
 */
public interface Repository {
    //delete
    void deleteAll();
    
    
    //Actor
    int createActor(Actor actor) throws Exception;
    Optional<Actor> selectActor(int id)throws Exception;
    List<Actor> selectActors()throws Exception;
    void updateActor(int id, Actor actor)throws Exception;
    void deleteActor(int id)throws Exception;
    void createActors(List<Actor> actors)throws Exception;
    
    //Director
    int createDirector(Director director)throws Exception;
    Optional<Director> selectDirector(int id)throws Exception;
    List<Director> selectDirectors()throws Exception;
    void updateDirector(int id, Director director)throws Exception;
    void deleteDirector(int id)throws Exception;
    void createDirectors(List<Director> directors)throws Exception;
    
    //Movie
    int createMovie(Movie movie)throws Exception;
    Optional<Movie> selectMovie(int id)throws Exception;
    List<Movie> selectMovies()throws Exception;
    void updateMovie(int id, Movie movie)throws Exception;
    void deleteMovie(int id)throws Exception;
    void createMovies(List<Movie> movies)throws Exception;
    
    //User
    int createUser(User user)throws Exception;
    Optional<User> selectUser(int id)throws Exception;
    List<User> selectUsers()throws Exception;
    void updateUser(int id, User user)throws Exception;
    void deleteUser(int id)throws Exception;
    void createUsers(List<User> users)throws Exception;
    
    User findUser(String username, String password)throws Exception;
    boolean checkUsername(String username)throws Exception;
    boolean checkExistence(String username, String password)throws Exception;
    
    
    //ActorsAndMovies
    List<Movie> selectMoviesWithActor(int actorId)throws Exception;
    List<Actor> selectActorsInMovie(int movieId)throws Exception;
    void insertActorAndMovie(int movieId, int actorId)throws Exception;
    void removeMovie(int movieId)throws Exception;
    void removeActor(int actorId)throws Exception;

}
