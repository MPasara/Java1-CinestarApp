/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Mladen
 */
public class AdaptedPerson {
    @XmlAttribute
    public int id;
    
    @XmlElement
    public String firstName;
    
    @XmlElement
    public String surname;
    
    public String type;
}
