package me.victorcruz.ninjaserver.domain.models;

import lombok.Data;
import lombok.Builder;
import javax.persistence.Id;
import java.time.LocalDateTime;
import javax.persistence.Table;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="device_services")
public class DeviceService {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne()
    @JoinColumn(name = "device_id")
    private Device device;

    @ManyToOne()
    @JoinColumn(name = "service_id")
    private Service service;

    private LocalDateTime createdAt;
}
