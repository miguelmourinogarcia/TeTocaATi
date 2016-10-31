package com.udc.fic.ipm.mb.tetocaati.modelo;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class AlmacenCategoriaModelo {
    private static List<CategoriaModelo> listaCategorias;

    public AlmacenCategoriaModelo(SharedPreferences sP) {
        SharedPreferences mPrefs = sP;
        Gson gson = new Gson();
        boolean esnulo;
        String json = mPrefs.getString("AlmacenCategoriaModelo", "");
        AlmacenCategoriaModelo aCM = gson.fromJson(json, AlmacenCategoriaModelo.class);
        if (aCM != null) {
            listaCategorias = (List<CategoriaModelo>) aCM.getListaCategorias();
        }
        try{
            listaCategorias.isEmpty();
        }
        catch (Exception e){
            listaCategorias = new ArrayList<>();
        }
    }

    public void saveAlmacenCategoriaModelo(SharedPreferences sP, AlmacenCategoriaModelo aCM){
        SharedPreferences mPrefs = sP;
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(aCM);
        prefsEditor.putString("AlmacenCategoriaModelo", json);
        prefsEditor.commit();
    }



    public List<CategoriaModelo> getListaCategorias() {
        return listaCategorias;
    }

    public List<String> getListaCategoriasNombre() {
        List<String> lCategoriasNombre = new ArrayList<>();
        for (CategoriaModelo cM: listaCategorias)
            {
                lCategoriasNombre.add(cM.getNombreCategoria());
            }
        return lCategoriasNombre;
    }

    public CategoriaModelo getCategoria(int index){
        return listaCategorias.get(index);
    }

    public void addCategoria(CategoriaModelo categoriaModelo){
        listaCategorias.add(categoriaModelo);
    }

    public boolean delCategoria(String nombreCategoria){
        List<String> lCategoriasNombre = this.getListaCategoriasNombre();
        int i = 0;
        for (String s: lCategoriasNombre)
        {
            if (s.equals(nombreCategoria)) {
                listaCategorias.remove(i);
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean delCategoria(int index){
        try{
            listaCategorias.remove(index);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean editCategoria(int index, String nombreCategoria){
        CategoriaModelo cM = new CategoriaModelo(nombreCategoria);

        try{
            listaCategorias.set(index, cM);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public void initDefault(){

        listaCategorias = new ArrayList<>();
        int i = 0;
        while(i<10) {
            CategoriaModelo cM = new CategoriaModelo("Cocina");
            this.addCategoria(cM);
            i++;
        }
    }
}
