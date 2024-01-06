package com.devoir.microservicecommandes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author azzai
 **/
@Entity
@Data
public class Commande {
@Id
@GeneratedValue
    private Integer id ;
    private String description ;
    private Integer quantite ;
    private LocalDate date ;
    private Long montant ;
}
