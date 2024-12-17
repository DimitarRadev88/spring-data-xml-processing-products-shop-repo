package bg.softuni.springdataxmlprocessing.repositories;

import bg.softuni.springdataxmlprocessing.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("""
            FROM Category c
            JOIN c.products p
            ORDER BY size(p) DESC
            """)
    List<Category> findAllOrderByProductsCountDesc();

    @Query("""
            SELECT size(p) FROM Category c
            JOIN c.products p
            WHERE c.id = ?1
            """)
    Integer countProductIdByCategoryId(Long id);

    @Query("""
            SELECT AVG(p.price) FROM Category c
            JOIN c.products p
            WHERE c.id = ?1
            GROUP BY c
            """)
    BigDecimal averageProductPriceByCategoryId(Long id);

    @Query("""
            SELECT SUM(p.price) FROM Category c
            JOIN c.products p
            WHERE c.id = ?1
            GROUP BY c
            """)
    BigDecimal sumProductPriceByCategoryId(Long id);
}
