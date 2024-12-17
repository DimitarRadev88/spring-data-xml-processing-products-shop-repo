package bg.softuni.springdataxmlprocessing.utils;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Component
public class XMLParser implements Parser {

    private JAXBContext jaxbContext;

    @Override
    public <T> T fromFile(String path, Class<T> tclass) throws JAXBException, FileNotFoundException {
        jaxbContext = JAXBContext.newInstance(tclass);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return (T) unmarshaller.unmarshal(new FileInputStream(path));
    }

    @Override
    public <T> void toFile(String path, T object) throws JAXBException {
        jaxbContext = JAXBContext.newInstance(object.getClass());

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(object, new File(path));
    }
}
