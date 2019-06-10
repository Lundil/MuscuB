package com.example.muscu.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class UtilisateurModel extends Model {

    //Attributes
    @Column(name = "_id")
    public Long id;
    @Column(name = "age")
    public Integer age;
    @Column(name = "sexe")
    public String sexe;
    @Column(name = "poids")
    public Double poids;
    @Column(name = "taille")
    public Double taille;
    @Column(name = "dateNaissance")
    public Date dateNaissance;
    @Column(name = "activitePhysique")
    public String activitePhysique;
    @Column(name = "objectif")
    public String objectif;
    @Column(name = "nbRepas")
    public String nbRepas;

    //Constructor
    public UtilisateurModel(){

    }

    //Method
    public Double getUserDailyNeeds(){
        BigDecimal bg = BigDecimal.ZERO, height = BigDecimal.ZERO, weight = BigDecimal.ZERO, age = BigDecimal.ZERO;
        //Harris-Benedict
        if("M".equalsIgnoreCase(this.getSexe())){
            //bg
            bg = bg.add(BigDecimal.valueOf(66.473));
            weight = BigDecimal.valueOf(13.7516).multiply(BigDecimal.valueOf(this.poids));
            height = BigDecimal.valueOf(5.0033).multiply(BigDecimal.valueOf(this.taille));
            age = BigDecimal.valueOf(6.7550).multiply(BigDecimal.valueOf(this.age));
            bg = bg.add(weight);
            bg = bg.add(height);
            bg = bg.subtract(age);
        }else if("F".equalsIgnoreCase(this.getSexe())){
            //bg
            bg = bg.add(BigDecimal.valueOf(655.0955));
            weight = BigDecimal.valueOf(9.5634).multiply(BigDecimal.valueOf(this.poids));
            height = BigDecimal.valueOf(1.8496).multiply(BigDecimal.valueOf(this.taille));
            age = BigDecimal.valueOf(4.6756).multiply(BigDecimal.valueOf(this.age));
            bg = bg.add(weight);
            bg = bg.add(height);
            bg = bg.subtract(age);
        }
        //Physical activity
        if("Rare".equalsIgnoreCase(this.getActivitePhysique())) {
            bg = bg.multiply(BigDecimal.valueOf(1.375));
        }else if("1-3".equalsIgnoreCase(this.getActivitePhysique())) {
            bg = bg.multiply(BigDecimal.valueOf(1.56));
        }else if("4-5".equalsIgnoreCase(this.getActivitePhysique())) {
            bg = bg.multiply(BigDecimal.valueOf(1.64));
        }else{
            bg = bg.multiply(BigDecimal.valueOf(1.82));
        }

        return bg.doubleValue();

    }

    //Queries
    public static List<UtilisateurModel> getAllUtilisateurs(){
        return new Select().from(UtilisateurModel.class).execute();
    }

    //Getters & Setters
    public String getSexe() {
        return sexe;
    }
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
    public Double getPoids() {
        return poids;
    }
    public void setPoids(Double poids) {
        this.poids = poids;
    }
    public Double getTaille() {
        return taille;
    }
    public void setTaille(Double taille) {
        this.taille = taille;
    }
    public Date getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public String getActivitePhysique() {
        return activitePhysique;
    }
    public void setActivitePhysique(String activitePhysique) {
        this.activitePhysique = activitePhysique;
    }
    public String getObjectif() {
        return objectif;
    }
    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getNbRepas() {
        return nbRepas;
    }
    public void setNbRepas(String nbRepas) {
        this.nbRepas = nbRepas;
    }
}

