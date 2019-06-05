package com.example.muscu.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name="Aliments")
public class AlimentModel extends Model {

    @Column(name = "_id")
    public Long id;
    @Column(name = "nom")
    public String nom;
    @Column(name = "isMatin")
    public Boolean isMatin;
    @Column(name = "isMidi")
    public Boolean isMidi;
    @Column(name = "isDiner")
    public Boolean isDiner;
    @Column(name = "isEncas")
    public Boolean isEncas;
    @Column(name = "typeAliment")
    public String typeAliment;
    @Column(name = "proteine")
    public Double proteine;
    @Column(name = "glucide")
    public Double glucide;
    @Column(name = "lipide")
    public Double lipide;

    public AlimentModel(){

    }

    public static List<AlimentModel> getAllAliments(){
        return new Select().from(AlimentModel.class).orderBy("nom").execute();
    }

    public static List<AlimentModel> getAlimentsByType(String type){
        return new Select().from(AlimentModel.class).where("typeAliment = ?", type).orderBy("nom").execute();
    }

    public static List<AlimentModel> getAlimentsByIsMatin(Boolean isMatin){
        return new Select().from(AlimentModel.class).where("isMatin = ?", isMatin).orderBy("nom").execute();
    }

    public static List<AlimentModel> getAlimentsByIsMidi(Boolean isMidi){
        return new Select().from(AlimentModel.class).where("isMidi = ?", isMidi).orderBy("nom").execute();
    }

    public static List<AlimentModel> getAlimentsByIsDiner(Boolean isDiner){
        return new Select().from(AlimentModel.class).where("isDiner = ?", isDiner).orderBy("nom").execute();
    }

    public static List<AlimentModel> getAlimentsByIsEncas(Boolean isEncas){
        return new Select().from(AlimentModel.class).where("isEncas = ?", isEncas).orderBy("nom").execute();
    }

    public static void deleteAllAliments(){
        List<AlimentModel> a = getAllAliments();
        for (AlimentModel az: a) {
            az.delete();
        }
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean getMatin() {
        return isMatin;
    }

    public void setMatin(Boolean isMatin) {
        this.isMatin = isMatin;
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

    public String getTypeAliment() {
        return typeAliment;
    }

    public void setTypeAliment(String typeAliment) {
        this.typeAliment = typeAliment;
    }

    public Double getProteine() {
        return proteine;
    }

    public void setProteine(Double proteine) {
        this.proteine = proteine;
    }

    public Double getGlucide() {
        return glucide;
    }

    public void setGlucide(Double glucide) {
        this.glucide = glucide;
    }

    public Double getLipide() {
        return lipide;
    }

    public void setLipide(Double lipide) {
        this.lipide = lipide;
    }
}
