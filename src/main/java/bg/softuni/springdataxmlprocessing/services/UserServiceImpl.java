package bg.softuni.springDataJsonProcessing.services;

import bg.softuni.springDataJsonProcessing.dtos.*;
import bg.softuni.springDataJsonProcessing.repositories.UserRepository;
import bg.softuni.springDataJsonProcessing.services.interfaces.UserService;
import bg.softuni.springDataJsonProcessing.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addAll(UserDto[] userDtos) {
        List<User> users = Arrays.stream(userDtos).map(dto -> modelMapper.map(dto, User.class)).toList();

        userRepository.saveAll(users);
    }

    @Override
    public List<UserWithSoldProductsDto> getUsersWithSuccessfullySoldProducts() {
        return userRepository.findAllBySoldProductsBuyerNotEmpty()
                .stream()
                .map(user -> modelMapper.map(user, UserWithSoldProductsDto.class))
                .toList();
    }

    @Override
    public UsersWrapperDto getUsersWithSoldProductsWrapper() {
        List<UserWithSoldProductsWrapperDto> list = userRepository
                .findAllByHavingOneOrMoreSoldProductsBuyer()
                .stream()
                .map(u ->  getUserWithSoldProductsWrapperDto(u))
                .toList();

        UsersWrapperDto wrapperDto = new UsersWrapperDto();
        wrapperDto.setUsers(list);
        wrapperDto.setUsersCount(list.size());

        return wrapperDto;
    }

    private UserWithSoldProductsWrapperDto getUserWithSoldProductsWrapperDto(User u) {
        UserWithSoldProductsWrapperDto dto = modelMapper
                .map(u, UserWithSoldProductsWrapperDto.class);
        ProductWrapperDto productWrapperDto = new ProductWrapperDto();
        List<ProductDto> soldProductDtos = u.getSoldProducts()
                .stream()
                .map(sp -> modelMapper.map(sp, ProductDto.class))
                .toList();
        productWrapperDto.setCount(soldProductDtos.size());
        productWrapperDto.setProducts(soldProductDtos);
        dto.setSoldProducts(productWrapperDto);
        return dto;
    }
}
