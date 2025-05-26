package uz.greenwhite.sampledashboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uz.greenwhite.sampledashboard.service.AnalyticsService;

@Controller
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("dealSummary", analyticsService.getDealSummary());
        model.addAttribute("dailySales", analyticsService.getDailySales());
        model.addAttribute("companyAnalytics", analyticsService.getCompanyAnalytics());
        return "dashboard";
    }
}