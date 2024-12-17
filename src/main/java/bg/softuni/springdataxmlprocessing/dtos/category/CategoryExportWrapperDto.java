package bg.softuni.springdataxmlprocessing.dtos.category;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryExportWrapperDto {

    @XmlElement(name = "category")
    private List<CategoryWithProductCountAveragePriceTotalRevenueDto> categories;

    public List<CategoryWithProductCountAveragePriceTotalRevenueDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryWithProductCountAveragePriceTotalRevenueDto> categories) {
        this.categories = categories;
    }

}
