package de.effective;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

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

    public Book(){}

    public Book(String title, String isbn, double prize) {
        this.title = title;
        this.isbn = isbn;
        this.prize = prize;
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
        if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = title != null ? title.hashCode() : 0;
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        temp = prize != +0.0d ? Double.doubleToLongBits(prize) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
