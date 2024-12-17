package bg.softuni.springdataxmlprocessing.services;

import bg.softuni.springdataxmlprocessing.dtos.user.*;
import bg.softuni.springdataxmlprocessing.models.User;
import bg.softuni.springdataxmlprocessing.repositories.UserRepository;
import bg.softuni.springdataxmlprocessing.services.interfaces.UserService;
import bg.softuni.springdataxmlprocessing.utils.XMLParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final String USERS_AND_PRODUCTS_XML_FILE_PATH = "src/main/resources/output/xml-files/users-and-products.xml";
    private static final String USERS_SOLD_PRODUCTS_XML_FILE_PATH = "src/main/resources/output/xml-files/users-sold-products.xml";
    private static final String USERS_XML_FILE_PATH = "src/main/resources/input/xml-files/users.xml";
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final XMLParser parser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, XMLParser parser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.parser = parser;
    }

    public void importUsers() throws JAXBException, FileNotFoundException {
        UserWrapperDto users = parser.fromFile(USERS_XML_FILE_PATH, UserWrapperDto.class);

        List<User> list = users
                .getUsers()
                .stream()
                .map(dto -> modelMapper.map(dto, User.class))
                .toList();

        userRepository.saveAll(list);
    }

    @Override
    public void exportUsersWithSoldProductsWithBuyers() throws JAXBException {
        List<User> users = userRepository.findAllBySoldProductsBuyersNotEmpty();

        List<UserWithSoldProductDto> userDtos = users
                .stream()
                .map((element) -> modelMapper.map(element, UserWithSoldProductDto.class))
                .toList();

        UserExportWrapperDto wrapper = new UserExportWrapperDto();
        wrapper.setUsers(userDtos);

        parser.toFile(USERS_SOLD_PRODUCTS_XML_FILE_PATH, wrapper);
    }

    @Override
    public void exportUsersWithSoldProductsWithBuyersSortedBySoldProductsSizeAndLastName() throws JAXBException {
        List<User> users = userRepository.findAllBySoldProductsBuyerNotEmptyOrderBySoldProductsCountDescLastNameAsc();

        List<UserWithSoldProductWithNameAndPriceDto> list = users
                .stream()
                .map(u -> getUserWithSoldProductWithNameAndPriceDto(u))
                .toList();

        UserWrapperWithCountDto wrapper = new UserWrapperWithCountDto();
        wrapper.setCount(list.size());
        wrapper.setUsers(list);

        parser.toFile(USERS_AND_PRODUCTS_XML_FILE_PATH, wrapper);
    }

    private UserWithSoldProductWithNameAndPriceDto getUserWithSoldProductWithNameAndPriceDto(User u) {
        UserWithSoldProductWithNameAndPriceDto map = modelMapper.map(u, UserWithSoldProductWithNameAndPriceDto.class);
        map.getSoldProducts().setCount(map.getSoldProducts().getSoldProducts().size());
        return map;
    }
}
