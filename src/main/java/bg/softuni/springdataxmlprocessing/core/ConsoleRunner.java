package bg.softuni.springdataxmlprocessing.core;

import bg.softuni.springdataxmlprocessing.services.interfaces.CategoryService;
import bg.softuni.springdataxmlprocessing.services.interfaces.ProductService;
import bg.softuni.springdataxmlprocessing.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public ConsoleRunner(UserService userService, CategoryService categoryService, ProductService productService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        productService.exportProductsInRange(new BigDecimal("500"), new BigDecimal("1000"));
        userService.exportUsersWithSoldProductsWithBuyers();
        categoryService.exportCategoriesWithAverageProductPriceAndTotalRevenueSortedByProductsSize();
        userService.exportUsersWithSoldProductsWithBuyersSortedBySoldProductsSizeAndLastName();
    }

    private void seedData() throws JAXBException, FileNotFoundException {
        userService.importUsers();
        categoryService.importCategories();
        productService.importProducts();
    }

}
