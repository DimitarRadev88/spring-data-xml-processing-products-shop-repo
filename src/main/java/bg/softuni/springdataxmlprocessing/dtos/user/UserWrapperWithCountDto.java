package bg.softuni.springdataxmlprocessing.dtos.user;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWrapperWithCountDto {

    @XmlAttribute(name = "count")
    private Integer count;
    @XmlElement(name = "user")
    private List<UserWithSoldProductWithNameAndPriceDto> users;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<UserWithSoldProductWithNameAndPriceDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithSoldProductWithNameAndPriceDto> users) {
        this.users = users;
    }

}
