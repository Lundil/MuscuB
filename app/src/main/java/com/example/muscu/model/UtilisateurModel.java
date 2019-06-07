package com.example.muscu.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

public class UtilisateurModel extends Model {

    @Column(name = "_id")
    public Long id;
    @Column(name = "age")
    public String age;
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

    public UtilisateurModel(){

    }

    public static List<UtilisateurModel> getAllUtilisateurs(){
        return new Select().from(UtilisateurModel.class).execute();
    }

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNbRepas() {
        return nbRepas;
    }

    public void setNbRepas(String nbRepas) {
        this.nbRepas = nbRepas;
    }
}

