package bg.softuni.springDataJsonProcessing.services.interfaces;

import bg.softuni.springDataJsonProcessing.dtos.UserDto;
import bg.softuni.springDataJsonProcessing.dtos.UserWithSoldProductsDto;
import bg.softuni.springDataJsonProcessing.dtos.UserWithSoldProductsWrapperDto;
import bg.softuni.springDataJsonProcessing.dtos.UsersWrapperDto;

import java.util.List;
import java.util.stream.Stream;

public interface UserService {
    void addAll(UserDto[] userDtos);

    List<UserWithSoldProductsDto> getUsersWithSuccessfullySoldProducts();

    UsersWrapperDto getUsersWithSoldProductsWrapper();
}
