package bg.softuni.springdataxmlprocessing.repositories;

import bg.softuni.springdataxmlprocessing.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByPriceBetweenOrderByPriceAsc(BigDecimal low, BigDecimal high);

}
