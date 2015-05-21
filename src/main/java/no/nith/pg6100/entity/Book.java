package no.nith.pg6100.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@SequenceGenerator(name = "SEQ_BOOK", initialValue = 100)
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "select b from Book b"),
    @NamedQuery(name = "Book.findAllInGenre", query = "select b from Book b where b.genre like :genre"),
    @NamedQuery(name = "Book.findAllByAuthor", query = "select b from Book b where b.author like :author")
})
@XmlRootElement
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOOK")
    private int id;
    @NotNull
    private String author;
    @NotNull
    private String title;
    private String genre;
    @Size(min = 4, max = 8)
    private String isbn;

    public Book() {

    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
