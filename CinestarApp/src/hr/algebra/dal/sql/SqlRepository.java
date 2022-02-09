/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import hr.algebra.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Mladen
 */
public class SqlRepository implements Repository {
    //KONSTANTE
    
    //Actor
    private static final String ID_ACTOR = "IDActor";
    private static final String FIRST_NAME = "FirstName";
    private static final String SURNAME = "Surname";
    
    //Director
    private static final String ID_DIRECTOR = "IDDirector";
    private static final String DIR_FIRST_NAME = "FirstName";
    private static final String DIR_SURNAME = "Surname";
    
    //Movie
    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String DESCRIPTION = "MovieDescription";
    private static final String ORIGINAL_TITLE = "OriginalTitle";
    private static final String DIRECTOR_ID = "DirectorID";
    private static final String DURATION = "Duration";
    private static final String GENRE = "Genre";
    private static final String POSTER = "Poster";
    private static final String LINK = "Link";
    private static final String START_DATE = "StartDate";
    
    //User
    private static final String ID_USER = "IDUser";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "UserPassword";
    private static final String IS_ADMIN = "IsAdmin";
    
    
    //pozivi procedura
    //Movie
    private static final String CREATE_MOVIE = "{call createMovie (?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String SELECT_MOVIE = "{call selectMovie (?)}";
    private static final String SELECT_MOVIES = "{call selectMovies}";
    private static final String UPDATE_MOVIE = "{call updateMovie (?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String DELETE_MOVIE = "{call deleteMovie (?)}";
    
    //Actor
    private static final String CREATE_ACTOR = "{call createActor (?,?,?)}";
    private static final String SELECT_ACTOR = "{call selectActor (?)}";
    private static final String SELECT_ACTORS = "{call selectActors}";
    private static final String UPDATE_ACTOR = "{call updateActor (?,?,?)}";
    private static final String DELETE_ACTOR = "{call deleteActor (?)}";
    
    //Director
    private static final String CREATE_DIRECTOR = "{call createDirector (?,?,?)}";
    private static final String SELECT_DIRECTOR = "{call selectDirector (?)}";
    private static final String SELECT_DIRECTORS = "{call selectDirectors}";
    private static final String UPDATE_DIRECTOR = "{call updateDirector (?,?,?)}";
    private static final String DELETE_DIRECTOR = "{call deleteDirector (?)}";
    
    //User
    private static final String CREATE_USER = "{call createUser (?,?,?,?)}";
    private static final String SELECT_USER = "{call selectUser (?)}";
    private static final String SELECT_USERS = "{call selectUsers}";
    private static final String UPDATE_USER = "{call updateUser (?,?,?,?)}";
    private static final String DELETE_USER = "{call deleteUser (?)}";
    private static final String GET_USER = "{call getUser (?,?,?)}";
    private static final String FIND_USER = "{call findUser (?,?)}";
    private static final String CHECK_USERNAME = "{call checkUsername (?,?)}";
    
    //ActorsAndMovies
    private static final String SELECT_MOVIES_WITH_ACTOR = "{call selectMoviesWithActor(?)}";
    private static final String SELECT_ACTORS_IN_MOVIES = "{call selectActorsInMovie (?)}";
    private static final String INSERT_ACTOR_AND_MOVIE = "{call InsertActorAndMovie (?,?)}";
    private static final String REMOVE_MOVIE = "{call RemoveMovie (?)}";
    private static final String REMOVE_ACTOR = "{call RemoveActor (?)}";
    
    //delete
    private static final String DELETE_ALL = "{call DeleteAll}";

    @Override
    public void deleteAll() {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try ( Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_ALL)){
           stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(SqlRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Actor
    @Override
    public int createActor(Actor actor) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_ACTOR)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, actor.getFirstName());
            stmt.setString(3, actor.getSurname());

            stmt.executeUpdate();
            return stmt.getInt(1);
        }
    }


    @Override
    public Optional<Actor> selectActor(int id) throws SQLException {
       DataSource dataSource = DataSourceSingleton.getInstance();
       try(Connection con = dataSource.getConnection();
               CallableStatement stmt = con.prepareCall(SELECT_ACTOR)){
           stmt.setInt(1, id);
           try(ResultSet rs = stmt.executeQuery()){
               if (rs.next()) {
                   return Optional.of(new Actor(
                           rs.getInt(ID_ACTOR),
                           rs.getString(FIRST_NAME),
                           rs.getString(SURNAME)
                   ));
               }
           }
       }
       return Optional.empty();
    }

    @Override
    public List<Actor> selectActors() throws SQLException {
        List<Actor> actors = new ArrayList<>();
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection();
               CallableStatement stmt = con.prepareCall(SELECT_ACTORS); ResultSet rs = stmt.executeQuery()){
           {
                  while (rs.next()) {                
                actors.add(new Actor(
                        rs.getInt(ID_ACTOR),
                        rs.getString(FIRST_NAME),
                        rs.getString(SURNAME)
                ));
            }
        }
           
       }
        return actors;
    }

    @Override
    public void updateActor(int id, Actor actor) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_ACTOR)){
            stmt.setInt(1, id);
            stmt.setString(2, actor.getFirstName());
            stmt.setString(3, actor.getSurname());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteActor(int id) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_ACTOR)){
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    @Override
    public void createActors(List<Actor> actors) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_ACTOR)){
            for (Actor actor : actors) {
                stmt.registerOutParameter(1, Types.INTEGER);
                stmt.setString(2, actor.getFirstName());
                stmt.setString(3, actor.getSurname());
                
                stmt.executeUpdate();
            }
        }
    }
    
    
    //Director
    @Override
    public int createDirector(Director director) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, director.getFirstName());
            stmt.setString(3, director.getSurname());

            stmt.executeUpdate();
            return stmt.getInt(1);
        }
    }

    @Override
    public Optional<Director> selectDirector(int id) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
       try(Connection con = dataSource.getConnection();
               CallableStatement stmt = con.prepareCall(SELECT_DIRECTOR)){
           stmt.setInt(1, id);
           try(ResultSet rs = stmt.executeQuery()){
               if (rs.next()) {
                   return Optional.of(new Director(
                           rs.getInt(ID_DIRECTOR),
                           rs.getString(DIR_FIRST_NAME),
                           rs.getString(DIR_SURNAME)
                   ));
               }
           }
       }
       return Optional.empty();
    }

    @Override
    public List<Director> selectDirectors() throws SQLException {
        List<Director> directors = new ArrayList<>();
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection();
               CallableStatement stmt = con.prepareCall(SELECT_DIRECTORS); ResultSet rs = stmt.executeQuery()){
           {
                  while (rs.next()) {                
                directors.add(new Director(
                        rs.getInt(ID_DIRECTOR),
                        rs.getString(DIR_FIRST_NAME),
                        rs.getString(DIR_SURNAME)
                ));
            }
        }
           
       }
        return directors;
    }

    @Override
    public void updateDirector(int id, Director director) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_DIRECTOR)){
            stmt.setInt(1, id);
            stmt.setString(2, director.getFirstName());
            stmt.setString(3, director.getSurname());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteDirector(int id) throws SQLException {
         DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_DIRECTOR)){
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    @Override
    public void createDirectors(List<Director> directors) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR)){
            for (Director director : directors) {
                stmt.registerOutParameter(1, Types.INTEGER);
                stmt.setString(2, director.getFirstName());
                stmt.setString(3, director.getSurname());
                
                stmt.executeUpdate();
            }
        }
    }
    
    
    //Movie
    @Override
    public int createMovie(Movie movie) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, movie.getTitle());
            stmt.setString(3, movie.getPublishedDate().format(DateTimeFormatter.ISO_DATE_TIME));
            stmt.setString(4,movie.getDescription());
            stmt.setString(5, movie.getOriginalTitle());
            stmt.setInt(6, createDirector(movie.getDirector()));
            stmt.setInt(7,movie.getDuration());
            stmt.setString(8, movie.getGenre());
            stmt.setString(9, movie.getPoster());
            stmt.setString(10, movie.getLink());
            stmt.setString(11, movie.getStartDate().format(DateTimeFormatter.ISO_DATE_TIME));
           

            stmt.executeUpdate();
            return stmt.getInt(1);
        }
    }

    @Override
    public Optional<Movie> selectMovie(int id) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
       try(Connection con = dataSource.getConnection();
               CallableStatement stmt = con.prepareCall(SELECT_MOVIE)){
           stmt.setInt(1, id);
           try(ResultSet rs = stmt.executeQuery()){
               if (rs.next()) {
                   return Optional.of(new Movie(
                           rs.getInt(ID_MOVIE),
                           rs.getString(TITLE),
                           LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_TIME_FORMATTER),
                           rs.getString(DESCRIPTION),
                           rs.getString(ORIGINAL_TITLE),
                           selectDirector(rs.getInt(DIRECTOR_ID)).get(),
                           rs.getInt(DURATION),
                           rs.getString(GENRE),
                           rs.getString(POSTER),
                           rs.getString(LINK),
                           LocalDateTime.parse(rs.getString(START_DATE), Movie.DATE_TIME_FORMATTER),
                           selectActorsInMovie(rs.getInt(ID_MOVIE))
                   ));
               }
           }
       }
       return Optional.empty();
    }

    @Override
    public List<Movie> selectMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIES);ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                movies.add(new Movie(
                           rs.getInt(ID_MOVIE),
                           rs.getString(TITLE),
                           LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_TIME_FORMATTER),
                           rs.getString(DESCRIPTION),
                           rs.getString(ORIGINAL_TITLE),
                           selectDirector(rs.getInt(DIRECTOR_ID)).get(),
                           rs.getInt(DURATION),
                           rs.getString(GENRE),
                           rs.getString(POSTER),
                           rs.getString(LINK),
                           LocalDateTime.parse(rs.getString(START_DATE), Movie.DATE_TIME_FORMATTER),
                           selectActorsInMovie(rs.getInt(ID_MOVIE))                        
                ));
            }
        }
        
        return movies;
    }

    @Override
    public void updateMovie(int id, Movie movie) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)){
            stmt.setInt(1, id);
            stmt.setString(2, movie.getTitle());
            stmt.setString(3, movie.getPublishedDate().format(Movie.DATE_TIME_FORMATTER));
            stmt.setString(4, movie.getDescription());
            stmt.setString(5,movie.getOriginalTitle());
            stmt.setInt(6,createDirector(movie.getDirector()));
            stmt.setInt(7,movie.getDuration());
            stmt.setString(8, movie.getGenre());
            stmt.setString(9, movie.getPoster());
            stmt.setString(10, movie.getLink());
            stmt.setString(11, movie.getStartDate().format(Movie.DATE_TIME_FORMATTER));
             
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteMovie(int id) throws SQLException {
         DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_MOVIE)){
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    @Override
    public void createMovies(List<Movie> movies) throws SQLException {
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE)){
            for (Movie movie : movies) {
             stmt.registerOutParameter(1, Types.INTEGER);
             stmt.setString(2, movie.getTitle());
            stmt.setString(3, movie.getPublishedDate().format(Movie.DATE_TIME_FORMATTER));
            stmt.setString(4, movie.getDescription());
            stmt.setString(5,movie.getOriginalTitle());
            stmt.setInt(6,createDirector(movie.getDirector()));
            stmt.setInt(7,movie.getDuration());
            stmt.setString(8, movie.getGenre());
            stmt.setString(9, movie.getPoster());
            stmt.setString(10, movie.getLink());
            stmt.setString(11, movie.getStartDate().format(Movie.DATE_TIME_FORMATTER));
                
            stmt.executeUpdate();
            }
        }
        
    }
    
    
    //User
    @Override
    public int createUser(User user) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_USER)) {
            stmt.registerOutParameter(4, Types.INTEGER);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getIsAdmin() ? 1 : 0);

            stmt.executeUpdate();
            return stmt.getInt(4);
        }
    }

    @Override
    public Optional<User> selectUser(int id) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
       try(Connection con = dataSource.getConnection();
               CallableStatement stmt = con.prepareCall(SELECT_USER)){
           stmt.setInt(1, id);
           try(ResultSet rs = stmt.executeQuery()){
               if (rs.next()) {
                   return Optional.of(new User(
                           rs.getInt(ID_USER),
                           rs.getString(USERNAME),
                           rs.getString(PASSWORD),
                           rs.getBoolean(IS_ADMIN)
                   ));
               }
           }
       }
       return Optional.empty();
    }

    @Override
    public List<User> selectUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection();
               CallableStatement stmt = con.prepareCall(SELECT_USERS); ResultSet rs = stmt.executeQuery()){
           {
                  while (rs.next()) {                
                users.add(new User(
                        rs.getInt(ID_USER),
                           rs.getString(USERNAME),
                           rs.getString(PASSWORD),
                           rs.getBoolean(IS_ADMIN)
                ));
            }
        }
           
       }
        return users;
    }

    @Override
    public void updateUser(int id, User user) throws SQLException {
         DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_USER)){
            stmt.setInt(1, id);
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setBoolean(4, user.getIsAdmin());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteUser(int id) throws SQLException {
         DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_USER)){
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    @Override
    public void createUsers(List<User> users) throws SQLException {
         DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_USER)){
            for (User user : users) {
                stmt.registerOutParameter(1, Types.INTEGER);
                stmt.setString(2, user.getUsername());
                stmt.setString(3, user.getPassword());
                stmt.setBoolean(4, user.getIsAdmin());
                
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public User findUser(String username, String password) throws SQLException {
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(FIND_USER)){
            stmt.setString(1, username);
            stmt.setString(2, password);
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    return new User(
                            rs.getInt(ID_USER),
                            rs.getString(USERNAME),
                            rs.getString(PASSWORD),
                            (rs.getInt(IS_ADMIN) == 1)
                    );
                }
            }
        }
        return null;
    }

    @Override
    public boolean checkUsername(String username) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CHECK_USERNAME)){
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, username);
            stmt.execute();
            
            return stmt.getInt(1) == 1;
        }
    }

    @Override
    public boolean checkExistence(String username, String password) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(GET_USER)){
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, username);
            stmt.setString(3, password);
            
            stmt.execute();
            
            return stmt.getInt(1) == 1;
        }
    }
    
    
    //ActorsAndMovies
    @Override
    public List<Movie> selectMoviesWithActor(int actorId) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIES_WITH_ACTOR)){
            stmt.setInt(1, actorId);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    movies.add(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            LocalDateTime.parse(rs.getString(PUBLISHED_DATE),DateTimeFormatter.ISO_DATE),
                            rs.getString(DESCRIPTION),
                            rs.getString(ORIGINAL_TITLE),
                           selectDirector(rs.getInt(DIRECTOR_ID)).get(),
                           rs.getInt(DURATION),
                           rs.getString(GENRE),
                           rs.getString(POSTER),
                           rs.getString(LINK),
                           LocalDateTime.parse(rs.getString(START_DATE),DateTimeFormatter.ISO_DATE),
                           selectActorsInMovie(rs.getInt(ID_MOVIE))
                    ));
                }
            }
        }
        return movies;
    }

    @Override
    public List<Actor> selectActorsInMovie(int movieId) throws SQLException {
        List<Actor> actors = new ArrayList<>();
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ACTORS_IN_MOVIES)){
            stmt.setInt(1, movieId);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    actors.add(new Actor(
                            rs.getInt(ID_ACTOR),
                            rs.getString(FIRST_NAME),
                            rs.getString(SURNAME)
                    ));     
                }
            }
        }
        return actors;
    }

    @Override
    public void insertActorAndMovie(int movieId, int actorId) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(INSERT_ACTOR_AND_MOVIE)){
            stmt.setInt(1, actorId);
            stmt.setInt(2, movieId);
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeMovie(int movieId) throws SQLException {
         DataSource dataSource = DataSourceSingleton.getInstance();
         
         try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(REMOVE_MOVIE)){
             stmt.setInt(1,movieId);
             
             stmt.execute();
         }
    }

    @Override
    public void removeActor(int actorId) throws SQLException {
         DataSource dataSource = DataSourceSingleton.getInstance();
         
         try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(REMOVE_ACTOR)){
             stmt.setInt(1,actorId);
             
             stmt.execute();
         }
    }
    
}
