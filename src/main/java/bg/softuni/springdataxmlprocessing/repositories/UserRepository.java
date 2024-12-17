package bg.softuni.springdataxmlprocessing.repositories;

import bg.softuni.springdataxmlprocessing.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT DISTINCT p.seller FROM Product p
            JOIN FETCH p.seller.soldProducts
            WHERE p.buyer IS NOT NULL
            """)
    List<User> findAllBySoldProductsBuyersNotEmpty();

    @Query("""
            FROM User u
            JOIN FETCH u.soldProducts sold
            ORDER BY size(sold) DESC, u.lastName ASC
            """)
    List<User> findAllBySoldProductsBuyerNotEmptyOrderBySoldProductsCountDescLastNameAsc();

}
