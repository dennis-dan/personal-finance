package it.personalfinance.finance.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.personalfinance.finance.dto.MonthlySummaryDto;
import it.personalfinance.finance.service.MonthlySummaryService;

@RestController
@RequestMapping("/api/summary")
public class SummaryApi {

    private final MonthlySummaryService summaryService;

    public SummaryApi(MonthlySummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/monthly")
    public MonthlySummaryDto getMonthlySummary(
            @RequestParam int year,
            @RequestParam int month) {

        return summaryService.getMonthlySummary(year, month);
    }
}