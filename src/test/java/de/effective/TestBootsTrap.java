package de.effective;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author martin.dilger
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {ApplicationConfig.class})
public class TestBootsTrap extends AbstractJUnit4SpringContextTests {

    @Resource
    private BookStoreRepository repository;


    @Test
    public void testBootstrap() {
        assertNotNull(repository);
        assertNotNull(em);
    }

    @Test
    public void saveAndDeleteBook() {
        assertTrue(repository.findAll().isEmpty());
        Book book = new Book("Domain Driven Design", "abcdef", 39.95);
        repository.save(book);
        assertFalse(repository.findAll().isEmpty());
        assertTrue(repository.exists(book.getIsbn()));
        repository.delete(book);
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    public void manuallyFindByIsbn() {
        Book book = new Book("Domain Driven Design", "abcdef", 39.95);
        repository.save(book);
        assertNull(repository.manuallyFindByIsbn("unknown_isbn"));
        assertEquals(book, repository.manuallyFindByIsbn(book.getIsbn()));
    }

    @Test
    public void findInPages() {

        for (int i = 20; i > 0; i--) {
            repository.save(new Book("A Test Book", "" + i, i));
        }
        assertEquals(20, repository.findAll().size());

        Page<Book> page = repository.findAll(new PageRequest(1, 3));
        List<Book> books = page.getContent();
        assertEquals(3, books.size());
    }

    @Test
    public void findByIsbn() {
        Book book = new Book("Domain Driven Design", "abcdef", 39.95);
        repository.save(book);
        assertNull(repository.findByIsbn("unknown_isbn"));
        assertEquals(book, repository.findByIsbn(book.getIsbn()));
    }


    @Resource
    private EntityManagerFactory em;

    @Test
    public void simpleCriteriaAPITest() {

        Book ddd = new Book("Domain Driven Design", "1", 39.95);
        //neu
        Book tdd = new Book("Feature Driven Development", "2", 29.95);
        //gebraucht mit anderer isbn:)
        Book fdd = new Book("Feature Driven Development","3", 19.95);
        repository.save(ddd);
        repository.save(tdd);

        EntityManager manager = em.createEntityManager();
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(Book.class);
        Root<Book> bookRoot = query.from(Book.class);
        Predicate title = builder.equal(bookRoot.get("title"), "Feature Driven Development");

        assertEquals(2, manager.createQuery(query).getResultList().size());

        Predicate price = builder.greaterThan(bookRoot.get("prize").as(Double.class), 20d);

        //override
        query.where(title, price);
        assertEquals(1, manager.createQuery(query).getResultList().size());

    }

    @Test
    public void findByIsbnWithSpecification() {
        Book ddd = new Book("Domain Driven Design", "abcdef", 39.95);
        Book tdd = new Book("Test-Driven-Development", "abcdefg", 29.95);
        repository.save(ddd);
        repository.save(tdd);
        IsbnSpecification specification = new IsbnSpecification(ddd.getIsbn());
        List<Book> result = repository.findAll(specification);
        assertEquals(1, result.size());
        assertEquals(ddd, result.get(0));

    }
}
