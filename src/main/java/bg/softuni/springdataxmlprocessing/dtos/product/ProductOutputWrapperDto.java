package bg.softuni.springdataxmlprocessing.dtos.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductOutputWrapperDto {

    @XmlElement(name = "product")
    private List<ProductWithNamePriceAndSellerDto> products;

    public List<ProductWithNamePriceAndSellerDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductWithNamePriceAndSellerDto> products) {
        this.products = products;
    }

}
