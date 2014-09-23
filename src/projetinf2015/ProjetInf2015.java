/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetinf2015;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/*
 * 
 * @author achille
 */

/**
 *
 * @author Palassonde
 */

public class ProjetInf2015 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        String fichierEntre = args[0];
        String fichierSortie = args[1];
        JSONArray erreurs = new JSONArray();
        boolean complet = true;
        
        // Lecture du fichier JSON envoyé en entrée dans un string
        String jsonDeclaration = FileReader.loadFileIntoString("json/" + fichierEntre, "UTF-8");
        JSONObject declaration = (JSONObject) JSONSerializer.toJSON(jsonDeclaration);

        // Lecture de la liste des catégories acceptés
        String jsonCategories = FileReader.loadFileIntoString("json/listecategories.json", "UTF-8");
        JSONArray listeCategories = JSONArray.fromObject(jsonCategories);
        
        JSONArray activites = declaration.getJSONArray("activites");
        
        String numPermis = declaration.getString("numero_de_permis");
        String cycle = declaration.getString("cycle");
        int heures_cumulees = declaration.getInt("heures_transferees_du_cycle_precedent");
        
        // Traitement du cycle
        if (!cycle.equals("2012-2014")){
            complet = false;
            erreurs.add("Aucun autre cycle que 2012-2014 n'est supporté");
        }
        
        // Pré-Traitement des heures
        if (heures_cumulees > 7){
            heures_cumulees = 7;
            erreurs.add("Le nombre d'heures cumulees ne peut dépasser 7");
        }
        
        int nbrheuresTotal = 0;
        nbrheuresTotal += heures_cumulees;

        // Traitement des activités
        int nbActivites = activites.size();
        
        JSONObject activite;
        
        for(int i=0; i < nbActivites; i++){
            
            activite = activites.getJSONObject(i);
            String categorie = activite.getString("categorie");
            int heures = activite.getInt("heures");
            //Date date = activite.getString("date");
            
            if (listeCategories.contains(categorie)){
                nbrheuresTotal += heures;
            }
            else{
                erreurs.add("L'activité " + categorie + " est dans une catégorie non reconnue. Elle a été ignorée.");
            }
        }
        
        if (nbrheuresTotal < 40){
            complet = false;
            erreurs.add("Le nombre minimal d'heures à atteindre est de 40");
        }
        
        //Traitement du resultat et ecriture dans un fichier json
        JSONObject resultats = new JSONObject();
        
        resultats.accumulate("complet", complet);
        resultats.accumulate("erreurs", erreurs);
        
        FileWriter outputfile = new FileWriter("json/" + fichierSortie);
        outputfile.write(resultats.toString(2));
        outputfile.close();
   
    }
    
}
