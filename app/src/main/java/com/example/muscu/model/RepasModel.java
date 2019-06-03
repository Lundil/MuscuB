package com.example.muscu.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.example.muscu.enumeration.TypeRepas;

import java.util.List;

@Table(name="Repas")
public class RepasModel extends Model {

    @Column(name = "_id")
    public Long id;

    @Column(name = "nom")
    public String nom;

    @Column(name = "typeRepas")
    public String typeRepas;

    @Column(name = "proteineTotal")
    public Double proteineTotal;

    @Column(name = "glucideTotal")
    public Double glucideTotal;

    @Column(name = "lipideTotal")
    public Double lipideTotal;

    @Column(name = "aliments")
    public List<AlimentModel> alimentModels;

    public RepasModel(){

    }

    public RepasModel(String nom, String typeRepas, Double proteineTotal, Double glucideTotal, Double lipideTotal, List<AlimentModel> alimentModels) {
        this.nom = nom;
        this.typeRepas = typeRepas;
        this.proteineTotal = proteineTotal;
        this.glucideTotal = glucideTotal;
        this.lipideTotal = lipideTotal;
        this.alimentModels = alimentModels;
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
}
