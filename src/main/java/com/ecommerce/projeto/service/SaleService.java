package com.ecommerce.projeto.service;

import com.ecommerce.projeto.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;



    public List<Object[]> getSalesByRegion() {
        return saleRepository.findTotalSalesByRegion();
    }

    public List<Object[]> getSalesByDate() {
        return saleRepository.findTotalSalesByDate();
    }

    public List<Object[]> getSalesByMonth() {
        return saleRepository.findTotalSalesByMonth();
    }

    public List<Object[]> getSalesByYear() {
        return saleRepository.findTotalSalesByYear();
    }

    public List<Object[]> getSalesByQuarter() {
        return saleRepository.findTotalSalesByQuarter();
    }

    public List<Object[]> getSalesBySemester() {
        return saleRepository.findTotalSalesBySemester();
    }
}
