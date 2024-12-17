package bg.softuni.springdataxmlprocessing.dtos.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserExportWrapperDto {

    @XmlElement(name = "user")
    private List<UserWithSoldProductDto> users;

    public List<UserWithSoldProductDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithSoldProductDto> users) {
        this.users = users;
    }
}
