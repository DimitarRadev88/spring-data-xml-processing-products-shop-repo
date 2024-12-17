package bg.softuni.springdataxmlprocessing.services.interfaces;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface CategoryService {

    void importCategories() throws JAXBException, FileNotFoundException;

    void exportCategoriesWithAverageProductPriceAndTotalRevenueSortedByProductsSize() throws JAXBException;

}
