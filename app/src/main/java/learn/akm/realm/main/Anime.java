package learn.akm.realm.main;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by AKM on 1/15/18.
 */

public class Anime extends RealmObject {

    @PrimaryKey
    private int id;
    private String tittle;
    private String description;
    private String genre;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
