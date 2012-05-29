package de.effective;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author martin.dilger
 */
@ContextConfiguration(classes = {ApplicationConfig.class})
public class TestBootsTrap extends AbstractJUnit4SpringContextTests {

    @Resource
    private BookStoreRepository repository;

    @Test
    public void testBootstrap(){
        assertNotNull(repository);
    }

    @Test
    public void saveAndDeleteBook(){
        assertTrue(repository.findAll().isEmpty());
        Book book = new Book("Domain Driven Design","abcdef",39.95);
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
    public void findInPages(){

        for(int i = 20; i>0; i--){
            repository.save(new Book("A Test Book",""+ i , i));
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
}
