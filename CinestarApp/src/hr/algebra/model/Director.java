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
public class Director extends Person  {

    public Director(int id, String firstName, String surname) {
        super(id, firstName, surname);
    }

    public Director(String firstName, String surname) {
        super(firstName, surname);
    }

    public Director(String firstName) {
        super(firstName);
    }

    
    @Override
    public String toString() {
        return "Director - " + this.getFirstName() + " " + this.getSurname();
    }
    
}
