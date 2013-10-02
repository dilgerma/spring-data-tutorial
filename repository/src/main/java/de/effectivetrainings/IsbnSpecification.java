package de.effectivetrainings;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author martin.dilger
 */
public class IsbnSpecification implements Specification<Book> {

    private String isbn;

    public IsbnSpecification(String isbn){
          this.isbn = isbn;
    }

    @Override
    public Predicate toPredicate(Root<Book> bookRoot, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.equal(bookRoot.get("isbn"),isbn);
    }
}
