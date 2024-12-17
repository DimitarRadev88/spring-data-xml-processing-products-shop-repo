package bg.softuni.springdataxmlprocessing.services;

import bg.softuni.springdataxmlprocessing.dtos.product.ProductDto;
import bg.softuni.springdataxmlprocessing.dtos.product.ProductOutputWrapperDto;
import bg.softuni.springdataxmlprocessing.dtos.product.ProductWithNamePriceAndSellerDto;
import bg.softuni.springdataxmlprocessing.dtos.product.ProductWrapperDto;
import bg.softuni.springdataxmlprocessing.models.Category;
import bg.softuni.springdataxmlprocessing.models.Product;
import bg.softuni.springdataxmlprocessing.models.User;
import bg.softuni.springdataxmlprocessing.repositories.CategoryRepository;
import bg.softuni.springdataxmlprocessing.repositories.ProductRepository;
import bg.softuni.springdataxmlprocessing.repositories.UserRepository;
import bg.softuni.springdataxmlprocessing.services.interfaces.ProductService;
import bg.softuni.springdataxmlprocessing.utils.Parser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCTS_XML_FILE_PATH = "src/main/resources/input/xml-files/products.xml";
    private static final String PRODUCTS_IN_RANGE_XML_FILE_PATH = "src/main/resources/output/xml-files/products-in-range.xml";
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final Parser parser;

    @Autowired
    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, Parser parser) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.parser = parser;
    }

    @Override
    public void importProducts() throws JAXBException, FileNotFoundException {
        ProductWrapperDto wrapperDto = parser.fromFile(PRODUCTS_XML_FILE_PATH, ProductWrapperDto.class);

        List<Product> list = wrapperDto
                .getProducts()
                .stream()
                .map(dto -> getProduct(dto))
                .toList();

        productRepository.saveAll(list);
    }

    private Product getProduct(ProductDto dto) {
        Product product = modelMapper.map(dto, Product.class);
        product.setSeller(getRandomSeller());
        product.setBuyer(getRandomBuyer());
        product.setCategories(getRandomCategories().stream().toList());
        return product;
    }

    @Override
    public void exportProductsInRange(BigDecimal from, BigDecimal to) throws JAXBException {
        List<ProductWithNamePriceAndSellerDto> list = productRepository
                .findAllByPriceBetweenOrderByPriceAsc(from, to).stream()
                .map(product -> modelMapper.map(product, ProductWithNamePriceAndSellerDto.class))
                .toList();

        ProductOutputWrapperDto wrapper = new ProductOutputWrapperDto();
        wrapper.setProducts(list);

        parser.toFile(PRODUCTS_IN_RANGE_XML_FILE_PATH, wrapper);
    }

    private Set<Category> getRandomCategories() {
        long numberOfCategories = ThreadLocalRandom.current().nextLong(categoryRepository.count());

        if (numberOfCategories == 0) {
            numberOfCategories++;
        }

        Set<Category> categories = new HashSet<>();

        LongStream.
                rangeClosed(1L, numberOfCategories)
                .forEach(n -> categories.add(categoryRepository
                                .findById(ThreadLocalRandom.current().nextLong(categoryRepository.count()) + 1)
                                .orElse(null)
                        )
                );

        return categories;
    }

    private User getRandomSeller() {
        long id = ThreadLocalRandom.current().nextLong(userRepository.count()) + 1;

        Optional<User> byId = userRepository.findById(id);
        while (byId.isEmpty()) {
            byId = userRepository.findById(id);
        }

        return byId.get();
    }

    private User getRandomBuyer() {
        int num = ThreadLocalRandom.current().nextInt(100);
        long id = ThreadLocalRandom.current().nextLong(userRepository.count()) + 1;

        if (isPrime(num)) {
            return null;
        }

        return userRepository.findById(id).orElse(null);
    }

    private boolean isPrime(int num) {
        if (num <= 1)
            return false;

        for (int i = 2; i <= num / 2; i++)
            if (num % i == 0)
                return false;

        return true;
    }

}
