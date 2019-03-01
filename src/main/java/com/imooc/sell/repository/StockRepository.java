package com.imooc.sell.repository;

import com.imooc.sell.dataobject.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer>{
}
