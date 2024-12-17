package bg.softuni.springdataxmlprocessing.services.interfaces;


import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface UserService {

    void importUsers() throws JAXBException, FileNotFoundException;


    void exportUsersWithSoldProductsWithBuyers() throws JAXBException;

    void exportUsersWithSoldProductsWithBuyersSortedBySoldProductsSizeAndLastName() throws JAXBException;

}
