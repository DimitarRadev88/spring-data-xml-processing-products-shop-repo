package bg.softuni.springdataxmlprocessing.dtos.user;

import bg.softuni.springdataxmlprocessing.dtos.product.ProductExportWrapperDto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithSoldProductDto {

    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlElement(name = "sold-products")
    private ProductExportWrapperDto soldProducts;

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

    public ProductExportWrapperDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductExportWrapperDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}
