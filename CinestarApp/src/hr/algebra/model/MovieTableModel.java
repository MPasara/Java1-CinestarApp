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
public class MovieTableModel extends AbstractTableModel{
    
    List<Movie>movies;
    private static final String COLUMN_NAMES[] = {"Id", "Title","Published date", "Duration", "Genre"};

    public MovieTableModel(List<Movie> movies) {
        this.movies = movies;
    }
    
    
    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        //return Movie.class.getDeclaredFields().length -1;
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return movies.get(rowIndex).getId();
                
            case 1:
                return movies.get(rowIndex).getTitle();
                
            case 2:
                return movies.get(rowIndex).getPublishedDate();
                
            case 3:
                return movies.get(rowIndex).getDuration();
                
            case 4:
                return movies.get(rowIndex).getGenre();
                
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
        return getValueAt(0, columnIndex).getClass();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        fireTableDataChanged();
    }
    
    
}
