/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mladen
 */
@XmlRootElement(name = "movies")
public class Movies {
    
    @XmlElementWrapper
    @XmlElement(name = "movie")
    private List<Movie> movies;

    public Movies() {
    }

    public Movies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "Movies{" + "movies=" + movies + '}';
    }
    
    
}
