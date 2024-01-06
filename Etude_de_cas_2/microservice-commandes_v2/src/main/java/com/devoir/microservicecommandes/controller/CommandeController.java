package com.devoir.microservicecommandes.controller;

import com.devoir.microservicecommandes.configs.ApplicationPropertiesConfiguration;
import com.devoir.microservicecommandes.dao.CommandeDao;
import com.devoir.microservicecommandes.model.Commande;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author azzai
 **/
@RestController
@EnableHystrixDashboard
@EnableCircuitBreaker
@Configuration
public class CommandeController implements HealthIndicator {
    @Autowired
    CommandeDao commandeDao ;
    @Autowired
    ApplicationPropertiesConfiguration appProps ;
    @GetMapping("/testHystrix")
    @HystrixCommand(fallbackMethod = "myHistrixbuildFallbackMessage",
    commandProperties = {@HystrixProperty(
            name="execution.isolation.thread.timeoutInMilliseconds",value="2000"
    )},threadPoolKey = "messageThreadPool")
    public String testHystrix() throws InterruptedException {
        System.out.println("Message from CommandeController.testHystrix() : Begin to sleep for 4 seconds");
        Thread.sleep(4000);
        return "Message from CommandeController.testHystrix() : End from sleep for 4 seconds";
    }

    private String myHistrixbuildFallbackMessage() {
        return "Message from myHistrixbuildFallbackMessage() : Hystrix Fallback message ( after timeout : 2 second )";
    }

    @GetMapping("/Commandes")
    public List<Commande> getAllCommandes(){
        List<Commande> commandes = commandeDao.findAll();

        if(commandes.isEmpty())
            throw new RuntimeException("Aucune commande n'est disponible");

        List<Commande> liste_commandes_limitee = commandes.stream().filter(
                commande -> {
                    LocalDate tenDaysAgo = LocalDate.now().minusDays(appProps.getCommandes_last());
                    LocalDate commandeDate = commande.getDate();
                    return !commandeDate.isBefore(tenDaysAgo) && !commandeDate.isAfter(LocalDate.now());
                }
        ).collect(Collectors.toList());
        return liste_commandes_limitee;
    }

    @GetMapping("Commandes/{id}")
    public Commande getCommandeById(@PathVariable Integer id){
        Commande commande = commandeDao.findById(id).orElse(null);
        if(commande==null) throw new RuntimeException("La commade correspondante à l'id "+ id + " n'exite pas :(");
        return commande;
    }

    @DeleteMapping("/Commandes/{id}")
    public void deleteCommande(@PathVariable Integer id){
        Commande commande = commandeDao.findById(id).orElse(null);
        if(commande==null) throw new RuntimeException("La commade correspondante à l'id "+ id + " n'exite pas :(");
        commandeDao.delete(commande);
    }

    @PutMapping("/Commandes/{id}")
    public Commande updateCommande(@RequestBody Commande commande ,
                                   @PathVariable Integer id){
        Commande old_commande = commandeDao.findById(id).orElse(null);
        if(commande==null) throw new RuntimeException("La commade correspondante à l'id "+ id + " n'exite pas :(");
       return commandeDao.save(commande);
    }

    @PostMapping("/Commandes")
    public Commande createCommande(@RequestBody Commande commande){
        return commandeDao.save(commande);
    }

    @Override
    public Health health() {
        List<Commande> commandes = commandeDao.findAll();
        if(commandes.isEmpty())
            return Health.down().build();
        return Health.up().build();
    }
}
