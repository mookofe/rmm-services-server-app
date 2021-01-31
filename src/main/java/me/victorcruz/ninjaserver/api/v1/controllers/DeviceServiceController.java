package me.victorcruz.ninjaserver.api.v1.controllers;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import me.victorcruz.ninjaserver.domain.models.DeviceService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import me.victorcruz.ninjaserver.api.v1.mappers.DeviceServiceMapper;
import me.victorcruz.ninjaserver.domain.services.DeviceServiceManager;
import me.victorcruz.ninjaserver.api.v1.responses.DeviceServiceResponse;
import me.victorcruz.ninjaserver.api.v1.requests.DeviceServiceCreateRequest;

/**
 * Device services controller
 *
 * Entry point for device resources
 */
@RestController()
@RequestMapping("/api/v1/companies/{companyId}/devices/{deviceId}/services")
public class DeviceServiceController {
    private final DeviceServiceManager deviceServiceManager;

    public DeviceServiceController(DeviceServiceManager deviceServiceManager) {
        this.deviceServiceManager = deviceServiceManager;
    }

    @PostMapping
    public DeviceServiceResponse create(
            @Valid @RequestBody DeviceServiceCreateRequest createRequest,
            @PathVariable String companyId,
            @PathVariable String deviceId
    ) {
        DeviceService deviceService = deviceServiceManager.addService(companyId, deviceId, createRequest.getServiceId());

        return DeviceServiceMapper.responseFromModel(deviceService);
    }

    @DeleteMapping("/{deviceServiceId}")
    public void delete(@PathVariable String companyId, @PathVariable String deviceId,@PathVariable String deviceServiceId) {
        deviceServiceManager.deleteService(companyId, deviceId, deviceServiceId);
    }
}
