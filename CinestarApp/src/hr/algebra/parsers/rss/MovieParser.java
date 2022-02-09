/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.parsers.rss;

import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import hr.algebra.model.TagType;
import hr.algebra.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Mladen
 */
public class MovieParser {
    private static final String URL = "https://www.blitz-cinestar.hr/rss.aspx?najava=1";
    private static final int TIMEOUT = 10000;
    private static final String REQUEST_METHOD = "GET";
    private static final String ATTRIBUTE_URL = "url";
    private static final String EXT = ".jpg";//ekstenzija za postere
    private static final String DIR = "assets";//direktorij za spremanje slika
    
    private static final DateTimeFormatter START_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;
    
    private static final Random RANDOM = new Random();
    
    public static List<Movie> parse() throws IOException, XMLStreamException, ParseException{
        List<Movie> movies = new ArrayList<>();
        //
        //Set<Movie> movieSet = new HashSet<>();
        //Map<Movie, List<Person>> movieMap = new HashMap<>();
        //
        
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(URL, TIMEOUT, REQUEST_METHOD);
        
        XMLEventReader reader = ParserFactory.createStaxParser(con.getInputStream());
        
        Optional<TagType> tagType = Optional.empty();
        Movie movie = null;
        StartElement startElement = null;
        //int firstIteration = 0;
        
        while(reader.hasNext()){
            XMLEvent event = reader.nextEvent();
            switch(event.getEventType()){
                case XMLStreamConstants.START_ELEMENT:
                    startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    tagType = TagType.parseFromString(qName);
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (tagType.isPresent()) {
                        Characters characters = event.asCharacters();
                        String data = characters.getData().trim();
                        
                        switch(tagType.get()){
                            case ITEM:
                                movie = new Movie();
                                movies.add(movie);
                                break;
                            case TITLE:
                                if(movie != null && !data.isEmpty()){
                                    movie.setTitle(data);
                                }
                                break;
                            case PUBLISHED_DATE:
                                if (movie != null && !data.isEmpty()) {
                                    LocalDateTime publishedDate = LocalDateTime.parse(data, DATE_TIME_FORMATTER);
                                    movie.setPublishedDate(publishedDate);
                                }
                                break;
                            case DESCRIPTION:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setDescription(data);
                                }
                                break;
                            case ORIGINAL_TITLE:
                                if(movie != null && !data.isEmpty()){
                                    movie.setOriginalTitle(data);
                                }
                                break;
                            case DIRECTOR:
                                if (movie != null && !data.isEmpty()) {
                                    String director = data;
                                    
                                    String firstName = director.substring(0,director.indexOf(" "));
                                    String surname = director.substring(director.indexOf(" ")+ 1);
                                    
                                    movie.setDirector(new Director(firstName, surname));
                                }
                                break;
                            case ACTORS:
                                if(movie != null && !data.isEmpty()){
                                    List<Actor> actors = new ArrayList<>();
                                    
                                    //String content = data;
                                    String[] actorsArray = data.split(", ");
                                    
                                    for (String actor : actorsArray) {
                                        String[] details = actor.split(" ");
                                        if (details.length >= 2) {
                                            actors.add(new Actor(details[0], details[details.length - 1]));
                                        }else{
                                            actors.add(new Actor(details[0]));
                                        }
                                        movie.setActors(actors);
                                    }
                                }
                                break;
                            case DURATION:
                                if(movie != null && !data.isEmpty()){
                                    String duration = data;
                                    
                                    movie.setDuration(Integer.valueOf(duration));
                                }
                                break;
                            case GENRE:
                                 if(movie != null && !data.isEmpty()){
                                    String genre = data;
                                    movie.setGenre(genre);
                                }
                                break;
                           case POSTER:
                                if (movie != null && startElement != null) {
                                    Attribute urlAttribute = startElement.getAttributeByName(new QName(ATTRIBUTE_URL));
                                    if (urlAttribute != null) {
                                        handlePicture(movie, urlAttribute.getValue());
                                    }
                                    String imageUrl = data;
                                    if (imageUrl != null) {
                                        imageUrl = imageUrl.substring(0,4) + "s" + imageUrl.substring(4, imageUrl.length());
                                        handlePicture(movie, imageUrl);
                                    } 
                                }
                                break;
                           case LINK:
                               if (movie != null && !data.isEmpty()) {
                                   movie.setLink(data);
                               }
                               break;
                           case START_DATE:
                               if (movie != null && !data.isEmpty()) {
                                   String date = parseDate(data, "d.M.yyyy", "yyyy-MM-dd");
                                   LocalDateTime startDate = LocalDate.parse(date, START_DATE_FORMATTER).atStartOfDay();
                                   movie.setStartDate(startDate);
                               }
                               break;
                        }
                    }
            }
        }
        
        return movies;
    }
    
    private static void handlePicture(Movie movie, String pictureUrl) throws IOException{
        String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
        
        if (ext.length()>4) {
            ext = EXT;
        }
        
        String pictureName = Math.abs(RANDOM.nextInt()) + ext;
        String localPicturePath = DIR + File.separator + pictureName;
        
        FileUtils.copyFromUrl(pictureUrl, localPicturePath);
        movie.setPoster(localPicturePath);
    }
    
    private static String parseDate(String data, String originalFormat, String requestedFormat) throws ParseException{
        SimpleDateFormat parser = new SimpleDateFormat(originalFormat);
        
        Date date = parser.parse(data);
        
        SimpleDateFormat formatter = new SimpleDateFormat(requestedFormat);
        
        return formatter.format(date);
    }
}
