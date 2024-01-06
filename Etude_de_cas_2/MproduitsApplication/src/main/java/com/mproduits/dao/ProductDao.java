package com.mproduits.dao;

import com.mproduits.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author azzai
 **/
@Repository
public interface ProductDao extends JpaRepository<Product,Integer> {
}
