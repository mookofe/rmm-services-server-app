package me.victorcruz.ninjaserver.api.v1.controllers;

import java.util.List;
import javax.validation.Valid;
import me.victorcruz.ninjaserver.domain.models.Device;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import me.victorcruz.ninjaserver.api.v1.mappers.DeviceMapper;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import me.victorcruz.ninjaserver.domain.services.DeviceService;
import me.victorcruz.ninjaserver.api.v1.requests.DeviceUpdateRequest;
import me.victorcruz.ninjaserver.api.v1.requests.DeviceCreateRequest;

/**
 * Device controller
 *
 * Entry point for device resources
 */
@RestController()
@RequestMapping("/api/v1/companies/{companyId}/devices")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public Device create(@Valid @RequestBody DeviceCreateRequest createRequest, @PathVariable String companyId) {
        Device device = DeviceMapper.mapDeviceFromCreateApi(createRequest);

        return deviceService.store(companyId, device);
    }

    @GetMapping
    public List<Device> list(@PathVariable String companyId) {
        return deviceService.getDevices(companyId);
    }

    @PutMapping("/{deviceId}")
    public Device update(@Valid @RequestBody DeviceUpdateRequest updateRequest, @PathVariable String companyId, @PathVariable String deviceId) {
        Device device = deviceService.find(companyId, deviceId);
        device.setSystemName(updateRequest.getSystemName());
        device.setType(updateRequest.getType());

        return deviceService.update(device);
    }
}
