package me.victorcruz.ninjaserver.domain.models;

import lombok.Data;
import lombok.Builder;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Table;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="services")
public class Service {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;
    private BigDecimal price;
    private String description;
    private Boolean supportsWindows;
    private Boolean supportsMac;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
