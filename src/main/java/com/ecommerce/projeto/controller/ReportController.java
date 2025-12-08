package com.ecommerce.projeto.controller;

import com.ecommerce.projeto.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/reportes") // ¡Protegido por /admin/!
public class ReportController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public String showReportPage(Model model) {

        model.addAttribute("reportByRegion", saleService.getSalesByRegion());
        model.addAttribute("reportByDate", saleService.getSalesByDate());
        model.addAttribute("reportByMonth", saleService.getSalesByMonth());
        model.addAttribute("reportByYear", saleService.getSalesByYear());
        model.addAttribute("reportByQuarter", saleService.getSalesByQuarter());
        model.addAttribute("reportBySemester", saleService.getSalesBySemester());

        return "admin/admin-reportes";
    }
}