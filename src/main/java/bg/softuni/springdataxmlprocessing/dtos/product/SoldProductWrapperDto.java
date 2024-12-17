package bg.softuni.springdataxmlprocessing.dtos.product;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductWrapperDto {

    @XmlAttribute(name = "count")
    private Integer count;
    @XmlElement(name = "product")
    private List<ProductWithNamePriceDto> soldProducts;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProductWithNamePriceDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductWithNamePriceDto> soldProducts) {
        this.soldProducts = soldProducts;
    }

}
