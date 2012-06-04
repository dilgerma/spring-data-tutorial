package de.effective;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author martin.dilger
 */
@Entity
@Table(name="CATEGORY")
public class Category implements Serializable {

    @Id
    @Column(name="CATEGORY")
    private String category;

    public Category(String category){
        this.category = category;
    }

    public Category(){}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category1 = (Category) o;

        if (category != null ? !category.equals(category1.category) : category1.category != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return category != null ? category.hashCode() : 0;
    }
}
