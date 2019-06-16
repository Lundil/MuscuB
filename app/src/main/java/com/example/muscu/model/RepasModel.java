package com.example.muscu.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

@Table(name="Repas")
public class RepasModel extends Model implements Serializable {

    @Column(name = "nom")
    public String nom;
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

    public RepasModel(){

    }

    public RepasModel(String nom, String description, Double proteineTotal, Double glucideTotal, Double lipideTotal, Boolean isMatin, Boolean isMidi, Boolean isDiner, Boolean isEncas) {
        this.nom = nom;
        this.description = description;
        this.proteineTotal = proteineTotal;
        this.glucideTotal = glucideTotal;
        this.lipideTotal = lipideTotal;
        this.isMatin = isMatin;
        this.isMidi = isMidi;
        this.isDiner = isDiner;
        this.isEncas = isEncas;
    }

    public static RepasModel getRepasById(Long id){
        return new Select().from(RepasModel.class).where("id = ?", id).executeSingle();
    }

    public static List<RepasModel> getRepasByIsMidi(Boolean isMidi){
        return new Select().from(RepasModel.class).where("isMidi = ?", isMidi).orderBy("nom").execute();
    }
    public static List<RepasModel> getRepasByIsMatin(Boolean isMatin){
        return new Select().from(RepasModel.class).where("isMidi = ?", isMatin).orderBy("nom").execute();
    }
    public static List<RepasModel> getRepasByIsDiner(Boolean isDiner){
        return new Select().from(RepasModel.class).where("isMidi = ?", isDiner).orderBy("nom").execute();
    }

    public static List<RepasModel> getAllRepas(){
        return new Select().from(RepasModel.class).orderBy("nom").execute();
    }

}
