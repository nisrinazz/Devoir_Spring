package com.devoir.microservicecommandes.model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * @author azzai
 **/
@Entity
@Data
public class Commande {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String description ;
    private Integer quantite ;
    private LocalDate date ;
    private Long montant ;
    private Integer id_produit ;
}
