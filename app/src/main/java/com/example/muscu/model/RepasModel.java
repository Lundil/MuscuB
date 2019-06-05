package com.example.muscu.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name="Repas")
public class RepasModel extends Model {

    @Column(name = "_id")
    public Long id;

    @Column(name = "nom")
    public String nom;

    @Column(name = "typeRepas")
    public String typeRepas;

    @Column(name = "description")
    public String description;

    @Column(name = "proteineTotal")
    public Double proteineTotal;

    @Column(name = "glucideTotal")
    public Double glucideTotal;

    @Column(name = "lipideTotal")
    public Double lipideTotal;

    @Column(name = "isMatin")
    public Boolean isMatin;
    @Column(name = "isMidi")
    public Boolean isMidi;
    @Column(name = "isDiner")
    public Boolean isDiner;
    @Column(name = "isEncas")
    public Boolean isEncas;

    @Column(name = "aliments")
    public List<AlimentModel> alimentModels;

    public RepasModel(){

    }

    public static List<RepasModel> getAllRepas(){
        return new Select().from(RepasModel.class).orderBy("nom").execute();
    }
    public static void deleteAllRepas(){
        List<RepasModel> a = getAllRepas();
        for (RepasModel az: a) {
            az.delete();
        }
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTypeRepas() {
        return typeRepas;
    }

    public void setTypeRepas(String typeRepas) {
        this.typeRepas = typeRepas;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getProteineTotal() {
        return proteineTotal;
    }

    public void setProteineTotal(Double proteineTotal) {
        this.proteineTotal = proteineTotal;
    }

    public Double getGlucideTotal() {
        return glucideTotal;
    }

    public void setGlucideTotal(Double glucideTotal) {
        this.glucideTotal = glucideTotal;
    }

    public Double getLipideTotal() {
        return lipideTotal;
    }

    public void setLipideTotal(Double lipideTotal) {
        this.lipideTotal = lipideTotal;
    }

    public List<AlimentModel> getAlimentModels() {
        return alimentModels;
    }

    public Boolean getMatin() {
        return isMatin;
    }

    public void setMatin(Boolean matin) {
        isMatin = matin;
    }

    public Boolean getMidi() {
        return isMidi;
    }

    public void setMidi(Boolean midi) {
        isMidi = midi;
    }

    public Boolean getDiner() {
        return isDiner;
    }

    public void setDiner(Boolean diner) {
        isDiner = diner;
    }

    public Boolean getEncas() {
        return isEncas;
    }

    public void setEncas(Boolean encas) {
        isEncas = encas;
    }

    public void setAlimentModels(List<AlimentModel> alimentModels) {
        this.alimentModels = alimentModels;
    }
}
