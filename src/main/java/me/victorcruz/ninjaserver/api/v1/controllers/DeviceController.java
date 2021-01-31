package me.victorcruz.ninjaserver.api.v1.controllers;

import java.util.List;
import javax.validation.Valid;
import me.victorcruz.ninjaserver.domain.models.Device;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import me.victorcruz.ninjaserver.api.v1.mappers.DeviceMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import me.victorcruz.ninjaserver.domain.services.DeviceManager;
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
    private final DeviceManager deviceManager;

    public DeviceController(DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    @PostMapping
    public Device create(@Valid @RequestBody DeviceCreateRequest createRequest, @PathVariable String companyId) {
        Device device = DeviceMapper.mapDeviceFromCreateApi(createRequest);

        return deviceManager.store(companyId, device);
    }

    @GetMapping
    public List<Device> list(@PathVariable String companyId) {
        return deviceManager.getDevices(companyId);
    }

    @PutMapping("/{deviceId}")
    public Device update(@Valid @RequestBody DeviceUpdateRequest updateRequest, @PathVariable String companyId, @PathVariable String deviceId) {
        Device device = deviceManager.find(companyId, deviceId);
        device.setSystemName(updateRequest.getSystemName());
        device.setType(updateRequest.getType());

        return deviceManager.update(device);
    }

    @DeleteMapping("/{deviceId}")
    public void delete(@PathVariable String companyId, @PathVariable String deviceId) {
        deviceManager.delete(companyId, deviceId);
    }
}
