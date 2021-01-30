package me.victorcruz.ninjaserver.api.v1.controllers;

import javax.validation.Valid;
import me.victorcruz.ninjaserver.domain.models.Device;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import me.victorcruz.ninjaserver.domain.services.DeviceService;
import me.victorcruz.ninjaserver.api.v1.requests.DeviceCreateRequest;

/**
 * Loan controller
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
    public Device create(@Valid @RequestBody DeviceCreateRequest deviceCreate, @PathVariable String companyId) {
        Device device = deviceCreate.mapToDevice();

        return deviceService.store(companyId, device);
    }
}
