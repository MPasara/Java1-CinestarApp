/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mladen
 */
public class ActorTableModel extends AbstractTableModel {
    
    private List<Actor> actors;
    private static final String[] COLUMN_NAMES = {"Id", "FirstName", "Surname"};

    public ActorTableModel(List<Actor> actors) {
        this.actors = actors;
    }
    
    
    
    @Override
    public int getRowCount() {
        return actors.size();
    }

    @Override
    public int getColumnCount() {
        return Actor.class.getDeclaredFields().length - 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return actors.get(rowIndex).getId();
                
            case 1:
                return actors.get(rowIndex).getFirstName();
                
            case 2:
                return actors.get(rowIndex).getSurname();
                
            default:
                throw new RuntimeException("No such column");
        }
        
    }
    
    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0:
                return Integer.class;
        }
        return (Class<?>) getValueAt(0, columnIndex);
    }
    
    public void setActors(List<Actor> actors) {
        this.actors = actors;
        fireTableDataChanged();
    }
    
}
