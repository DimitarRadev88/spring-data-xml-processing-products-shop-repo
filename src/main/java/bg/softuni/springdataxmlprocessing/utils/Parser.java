package bg.softuni.springdataxmlprocessing.utils;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface Parser {

    <T> T fromFile(String path, Class<T> tclass) throws JAXBException, FileNotFoundException;

    <T> void toFile(String path, T object) throws JAXBException;

}
