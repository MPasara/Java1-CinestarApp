/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Mladen
 */
public class PublishedDateAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String date) throws Exception {
        
        return LocalDateTime.parse(date,DateTimeFormatter.ISO_DATE);
        
    }

    @Override
    public String marshal(LocalDateTime dateTime) throws Exception {
        
        return dateTime.format(DateTimeFormatter.ISO_DATE);
        
    }
    
    
    
}
