package bg.softuni.springdataxmlprocessing.services.interfaces;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

public interface ProductService {

    void importProducts() throws JAXBException, FileNotFoundException;

    void exportProductsInRange(BigDecimal from, BigDecimal to) throws JAXBException;

}
