package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.dao.ProductDao;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

//    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
//    public MappingJacksonValue listeProduits() {
//         Iterable<Product> produits = productDao.findAll();
//        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
//        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
//
//        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
//
//        produitsFiltres.setFilters(listDeNosFiltres);
//
//        return produitsFiltres;
//    }

    @Operation(summary = "Recuperer tous les produits ")
    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    public List<Product> listeProduits() {
        return productDao.findAll();
    }

    @GetMapping(value = "/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        Product product = productDao.findById(id);
        if(product == null) {
            throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est Introuvable.");
        }
        return product;
    }

    @GetMapping(value = "test/produits/{prixLimit}")
    public List<Product> testDeRequetes(@PathVariable int prixLimit) {
        return productDao.findByPrixGreaterThan(prixLimit);
    }

    //ajouter un produit
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {
        Product productAdded =  productDao.save(product);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable int id) {
        productDao.deleteById(id);
    }

    @PutMapping(value = "/Produits")
    public void updateProduit(@RequestBody Product product) {
        productDao.save(product);
    }

    @GetMapping("/Produits/cher/{prixLimit}")
    public List<Product> chercherProduitCher(@PathVariable int prixLimit) {
        return productDao.chercherUnProduitCher(prixLimit);
    }
}
