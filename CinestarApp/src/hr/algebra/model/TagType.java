/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.util.Optional;

/**
 *
 * @author Mladen
 */
public enum TagType {
    ITEM("item"),
    TITLE("title"),
    PUBLISHED_DATE("pubDate"),
    DESCRIPTION("description"),
    ORIGINAL_TITLE("orignaziv"),
    DIRECTOR("redatelj"),
    ACTORS("glumci"),
    DURATION("trajanje"),
    GENRE("zanr"),
    POSTER("plakat"),
    LINK("link"),
    START_DATE("pocetak");
    
    private final String name;

    private TagType(String name) {
        this.name = name;
    }
    
    public static Optional<TagType> parseFromString(String name){
        
        for (TagType type : values()) {
            if (type.name.equals(name)) {
                return Optional.of(type);
            }
        }
        
        return Optional.empty();
    }
    
}
