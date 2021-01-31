package me.victorcruz.ninjaserver.api.v1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import me.victorcruz.ninjaserver.domain.models.MonthlyCostSummary;
import me.victorcruz.ninjaserver.domain.services.MonthlyCostCalculator;

@RestController
@RequestMapping("/api/v1/companies/{companyId}/monthly-cost")
public class MonthlyCostController {
    private final MonthlyCostCalculator monthlyCostCalculator;

    public MonthlyCostController(MonthlyCostCalculator monthlyCostCalculator) {
        this.monthlyCostCalculator = monthlyCostCalculator;
    }

    @GetMapping
    public MonthlyCostSummary calculate(@PathVariable String companyId) {
        return monthlyCostCalculator.calculate(companyId);
    }
}
