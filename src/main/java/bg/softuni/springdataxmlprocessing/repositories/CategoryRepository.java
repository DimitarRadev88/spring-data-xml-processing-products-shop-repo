package bg.softuni.springDataJsonProcessing.repositories;

import bg.softuni.springDataJsonProcessing.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("""
            FROM Category c
            ORDER BY size(c.products) DESC
            """)
    List<Category> findAllOrderByProductsSizeDesc();

    @Query("""
            SELECT COUNT(p.id)
            FROM Category c
            JOIN c.products p
            WHERE c.id = ?1
            GROUP BY c.id
            """)
    Integer countProductsByCategoryId(long id);

    @Query("""
            SELECT AVG(p.price)
            FROM Category c
            JOIN c.products p
            WHERE c.id = ?1
            GROUP BY c.id
            """)
    BigDecimal averageProductsPriceByCategoryId(long id);

    @Query("""
            SELECT SUM(p.price)
            FROM Category c
            JOIN c.products p
            WHERE c.id = ?1
            GROUP BY c.id
            """)
    BigDecimal totalProductsRevenueByCategoryId(long id);

}
