package me.victorcruz.ninjaserver.api.v1.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import me.victorcruz.ninjaserver.domain.services.CompanyServiceFetcher;
import me.victorcruz.ninjaserver.api.v1.responses.CompanyServiceResponse;
import me.victorcruz.ninjaserver.domain.aggregations.CompanyServiceCount;

/**
 * Company services controller
 *
 * Entry point for device resources
 */
@RestController()
@RequestMapping("/api/v1/companies/{companyId}/services")
public class CompanyServiceController {
    private final CompanyServiceFetcher companyServiceFetcher;

    public CompanyServiceController(CompanyServiceFetcher companyServiceFetcher) {
        this.companyServiceFetcher = companyServiceFetcher;
    }

    @GetMapping
    public List<CompanyServiceResponse> list(@PathVariable String companyId) {
        List<CompanyServiceCount> aggregates = companyServiceFetcher.fetch(companyId);

        return CompanyServiceResponse.listFromAggregate(aggregates);
    }
}
