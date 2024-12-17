package bg.softuni.springdataxmlprocessing.dtos.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductExportWrapperDto {

    @XmlElement(name = "product")
    private List<ProductWithNamePriceBuyerDto> soldProducts;

    public List<ProductWithNamePriceBuyerDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductWithNamePriceBuyerDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
