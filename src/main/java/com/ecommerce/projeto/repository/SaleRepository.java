package com.ecommerce.projeto.repository;

import com.ecommerce.projeto.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {


    @Query("SELECT s.region, SUM(s.total) FROM Sale s GROUP BY s.region")
    List<Object[]> findTotalSalesByRegion();


    @Query("SELECT FUNCTION('DATE', s.saleDate), SUM(s.total) FROM Sale s GROUP BY FUNCTION('DATE', s.saleDate) ORDER BY FUNCTION('DATE', s.saleDate) DESC")
    List<Object[]> findTotalSalesByDate();


    @Query("SELECT YEAR(s.saleDate), MONTH(s.saleDate), SUM(s.total) FROM Sale s GROUP BY YEAR(s.saleDate), MONTH(s.saleDate) ORDER BY YEAR(s.saleDate) DESC, MONTH(s.saleDate) DESC")
    List<Object[]> findTotalSalesByMonth();

    @Query("SELECT YEAR(s.saleDate), SUM(s.total) FROM Sale s GROUP BY YEAR(s.saleDate) ORDER BY YEAR(s.saleDate) DESC")
    List<Object[]> findTotalSalesByYear();


    @Query("SELECT YEAR(s.saleDate), QUARTER(s.saleDate), SUM(s.total) FROM Sale s GROUP BY YEAR(s.saleDate), QUARTER(s.saleDate) ORDER BY YEAR(s.saleDate) DESC, QUARTER(s.saleDate) DESC")
    List<Object[]> findTotalSalesByQuarter();

    @Query("SELECT YEAR(s.saleDate), CEILING(MONTH(s.saleDate) / 6.0), SUM(s.total) FROM Sale s GROUP BY YEAR(s.saleDate), CEILING(MONTH(s.saleDate) / 6.0) ORDER BY YEAR(s.saleDate) DESC, CEILING(MONTH(s.saleDate) / 6.0) DESC")
    List<Object[]> findTotalSalesBySemester();

}