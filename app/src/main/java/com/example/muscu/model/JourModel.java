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
    @Column(name = "repasEncas1")
    public RepasModel repasEncas1;
    @Column(name = "repasEncas2")
    public RepasModel repasEncas2;
    @Column(name = "repasEncas3")
    public RepasModel repasEncas3;

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

    public RepasModel getRepasEncas1() {
        return repasEncas1;
    }

    public void setRepasEncas1(RepasModel repasEncas1) {
        this.repasEncas1 = repasEncas1;
    }

    public RepasModel getRepasEncas2() {
        return repasEncas2;
    }

    public void setRepasEncas2(RepasModel repasEncas2) {
        this.repasEncas2 = repasEncas2;
    }

    public RepasModel getRepasEncas3() {
        return repasEncas3;
    }

    public void setRepasEncas3(RepasModel repasEncas3) {
        this.repasEncas3 = repasEncas3;
    }
}
