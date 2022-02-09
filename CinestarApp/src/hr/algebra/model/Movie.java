/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Mladen
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"title", "originalTitle", "publishDate", "description", "director", "actors", "duration", "genre", "poster", "link", "startDate"})
public class Movie {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    
    @XmlAttribute
    private int id;
    
    private String title;
    
    @XmlJavaTypeAdapter(PublishedDateAdapter.class)
    @XmlElement(name = "publisheddate")
    private LocalDateTime publishedDate;
    
    private String description;
    
    @XmlElement(name = "originaltitle")
    private String originalTitle;
    
    private Director director;
    
    private int duration;
    
    private String genre;
    
    @XmlElement(name = "poster")
    private String poster;//picturePath
    
    private String link;
    
    @XmlJavaTypeAdapter(PublishedDateAdapter.class)
    @XmlElement(name = "startddate")
    private LocalDateTime startDate;
    
    @XmlElementWrapper
    @XmlElement(name = "actor")
    private List<Actor> actors;

    public Movie() {
    }

    public Movie(int id, String title, LocalDateTime publishedDate, String description, String originalTitle, Director director, int duration, String genre, String poster, String link, LocalDateTime startDate, List<Actor> actors) {
        this(title, publishedDate, description, originalTitle, director, duration, genre, poster, link, startDate, actors);
        this.id = id;
    }

    public Movie(String title, LocalDateTime publishedDate, String description, String originalTitle, Director director, int duration, String genre, String poster, String link, LocalDateTime startDate, List<Actor> actors) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.description = description;
        this.originalTitle = originalTitle;
        this.director = director;
        this.duration = duration;
        this.genre = genre;
        this.poster = poster;
        this.link = link;
        this.startDate = startDate;
        this.actors = actors;
    }

    public int getId() {
        return id;
    }

    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return "Movie{" + "id=" + id + ", title=" + title + ", publishedDate=" + publishedDate + ", description=" + description + ", originalTitle=" + originalTitle + ", director=" + director + ", duration=" + duration + ", genre=" + genre + ", poster=" + poster + ", link=" + link + ", startDate=" + startDate + ", actors=" + actors + '}';
    }
    
    
}
