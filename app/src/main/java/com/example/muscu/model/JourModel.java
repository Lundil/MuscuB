package com.example.muscu.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

@Table(name="Jour")
public class JourModel extends Model implements Serializable {

    @Column(name = "nom")
    public String nom;
    @Column(name = "ordre")
    public Integer ordre;
    @Column(name = "repasMatin")
    public RepasModel repasMatin;
    @Column(name = "repasMidi")
    public RepasModel repasMidi;
    @Column(name = "repasDiner")
    public RepasModel repasDiner;

    public JourModel() {
    }

    public static JourModel getJourById(Long id){
        return new Select().from(JourModel.class).where("id = ?", id).executeSingle();
    }

    public static List<JourModel> getAllJours(){
        return new Select().from(JourModel.class).orderBy("ordre").execute();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public RepasModel getRepasMatin() {
        return repasMatin;
    }

    public void setRepasMatin(RepasModel repasMatin) {
        this.repasMatin = repasMatin;
    }

    public RepasModel getRepasMidi() {
        return repasMidi;
    }

    public void setRepasMidi(RepasModel repasMidi) {
        this.repasMidi = repasMidi;
    }

    public RepasModel getRepasDiner() {
        return repasDiner;
    }

    public void setRepasDiner(RepasModel repasDiner) {
        this.repasDiner = repasDiner;
    }

}
