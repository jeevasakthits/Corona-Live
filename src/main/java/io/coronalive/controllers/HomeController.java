package io.coronalive.controllers;

import io.coronalive.models.LocationStats;
import io.coronalive.services.CoronaVirusDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalConfirmedCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDayConfirmedCases()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        
        
        int totalReportedCases1 = allStats.stream().mapToInt(stat -> stat.getLatestTotalDeathCases()).sum();
        int totalNewCases1 = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDeathCases()).sum();
        //model.addAttribute("locationStats", allStats);
        model.addAttribute("totalDeathCases", totalReportedCases1);
        model.addAttribute("totalNewDeathCases", totalNewCases1);
        
        
        int totalReportedCases2 = allStats.stream().mapToInt(stat -> stat.getLatestTotalRecoveredCases()).sum();
        int totalNewCases2 = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDayRecoveredCases()).sum();
       // model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedRecoveredCases", totalReportedCases2);
        model.addAttribute("totalNewRecoveredCases", totalNewCases2);

        return "home";
    }
}
