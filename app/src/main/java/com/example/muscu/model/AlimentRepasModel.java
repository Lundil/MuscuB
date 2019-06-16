package com.example.muscu.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name="AlimentRepas")
public class AlimentRepasModel extends Model {

    @Column(name = "aliment", onDelete = Column.ForeignKeyAction.CASCADE)
    public Long alimentModel;
    @Column(name = "repas", onDelete = Column.ForeignKeyAction.CASCADE)
    public Long repasModel;
    @Column(name = "quantite")
    public Double quantite;

    public static List<AlimentRepasModel> getAlimentRepasModelByRepas(Long id){
        return new Select().from(AlimentRepasModel.class).where("repas = ?", id).execute();
    }

    public List<AlimentModel> listAlimentModels(Long id) {
        return new Select(new String[]{"aliment"}).from(AlimentRepasModel.class).where("repas = ?", id).execute();
    }
    public List<RepasModel> listRepasModels() {
        return getMany(RepasModel.class, "repas");
    }

}
