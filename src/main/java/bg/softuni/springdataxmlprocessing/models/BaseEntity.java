package bg.softuni.springDataJsonProcessing.models;

import jakarta.persistence.*;
import org.springframework.context.annotation.Configuration;

@MappedSuperclass
public abstract class BaseEntity {

    private Long id;

    protected BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
