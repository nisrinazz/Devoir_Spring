package com.devoir.microservicecommandes.dao;

import com.devoir.microservicecommandes.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author azzai
 **/
@Repository
public interface CommandeDao
        extends JpaRepository<Commande, Integer> {
}
