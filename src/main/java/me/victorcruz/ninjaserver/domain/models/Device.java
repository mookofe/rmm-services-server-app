package me.victorcruz.ninjaserver.domain.models;

import lombok.Data;
import javax.persistence.Id;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;
import me.victorcruz.ninjaserver.domain.types.DeviceType;

@Data
@Entity
public class Device {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String companyId;
    private String systemName;
    private DeviceType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
