package de.effective;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: madi
 * Date: 29.05.12
 * Time: 20:22
 * To change this template use File | Settings | File Templates.
 */
public interface BookStoreRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {

    public Book findByIsbn(String isbn);

    @Query(value = "SELECT p from Book p where p.isbn=:isbn")
    public Book manuallyFindByIsbn(@Param(value = "isbn") String isbn);

    public List<Book> findByCategories(Category cat);

    @Query(value = "select p from Book p JOIN p.categories c where c=:cat ")
    public List<Book> manuallyFindByCategory(@Param(value = "cat")Category cat);
}
