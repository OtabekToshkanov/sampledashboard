package uz.greenwhite.sampledashboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uz.greenwhite.sampledashboard.model.UserContext;
import uz.greenwhite.sampledashboard.service.AnalyticsService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping("/")
    public String dashboard(Model model) {
        try {
            // Get current user context
            UserContext userContext = analyticsService.getCurrentUserContext();

            // Add user info to model
            model.addAttribute("currentUser", userContext);

            // Get analytics data filtered by user's companies
            model.addAttribute("dealSummary", analyticsService.getDealSummary());
            model.addAttribute("dailySales", analyticsService.getDailySales());
            model.addAttribute("companyAnalytics", analyticsService.getCompanyAnalytics());

            return "dashboard";
        } catch (Exception e) {
            log.error("Error loading dashboard", e);
            model.addAttribute("error", "Failed to load dashboard data");
            return "error";
        }
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        try {
            UserContext userContext = analyticsService.getCurrentUserContext();
            model.addAttribute("user", userContext);
            return "profile";
        } catch (Exception e) {
            log.error("Error loading profile", e);
            model.addAttribute("error", "Failed to load profile");
            return "error";
        }
    }
}