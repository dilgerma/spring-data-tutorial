package de.effective;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author martin.dilger
 */
@Entity
public class Book implements Serializable {

    @Id
    @Column(name="isbn")
    private String isbn;
    @Column(name="title")
    private String title;
    @Column(name="prize")
    private double prize;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "BOOK_CATEGORY", joinColumns = @JoinColumn(name="ISBN"),
            inverseJoinColumns = @JoinColumn(name="CATEGORY"))
    private List<Category> categories;


    public Book(){}

    public Book(String title, String isbn, double prize) {
        this(title, isbn, prize, new ArrayList<Category>());
    }

    public Book(String title, String isbn, double prize, List<Category> categories){
        this.title = title;
        this.isbn = isbn;
        this.prize = prize;
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public double getPrize() {
        return prize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (Double.compare(book.prize, prize) != 0) return false;
        if (categories != null ? !categories.equals(book.categories) : book.categories != null) return false;
        if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = isbn != null ? isbn.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        temp = prize != +0.0d ? Double.doubleToLongBits(prize) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        return result;
    }
}
