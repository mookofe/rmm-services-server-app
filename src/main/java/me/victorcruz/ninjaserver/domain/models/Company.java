package me.victorcruz.ninjaserver.domain.models;

import lombok.Data;
import javax.persistence.Id;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Company {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
