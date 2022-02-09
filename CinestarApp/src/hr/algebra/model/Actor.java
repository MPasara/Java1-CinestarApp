/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

/**
 *
 * @author Mladen
 */
public class Actor extends Person  {

    public Actor(int id, String firstName, String surname) {
        super(id, firstName, surname);
    }

    public Actor(String firstName, String surname) {
        super(firstName, surname);
    }

    public Actor(String firstName) {
        super(firstName);
    }

    @Override
    public String toString() {
        return "Actor - " + this.getFirstName() + " " + this.getSurname();
    }

    
    
   
}
