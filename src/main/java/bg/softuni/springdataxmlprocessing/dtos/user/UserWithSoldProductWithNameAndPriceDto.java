package bg.softuni.springdataxmlprocessing.dtos.user;

import bg.softuni.springdataxmlprocessing.dtos.product.SoldProductWrapperDto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithSoldProductWithNameAndPriceDto {

    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlAttribute(name = "age")
    private Integer age;

    @XmlElement(name = "sold-products")
    private SoldProductWrapperDto soldProducts;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public SoldProductWrapperDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(SoldProductWrapperDto soldProducts) {
        this.soldProducts = soldProducts;
    }

}
