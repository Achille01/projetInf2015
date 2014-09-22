
package json;

import java.io.FileNotFoundException;
import java.io.IOException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class JSON {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        //String fichierEntree = args[0];
        //String fichierSortie = args[1];
        
        String json = FileReader.loadFileIntoString("json/declaration.json", "UTF-8");
        JSONObject declaration = (JSONObject) JSONSerializer.toJSON(json);
        
        System.out.println(declaration.getString("numero_de_permis"));
        
        JSONArray activites = declaration.getJSONArray("activites");
       
        int nbActivites = activites.size();
        
        int hrsprec = declaration.getInt("heures_transferees_du_cycle_precedent");
        
        int nbrheuresTotal = 0 + hrsprec;
        JSONObject activite;
        for(int i=0; i < nbActivites; i++){
            activite = activites.getJSONObject(i);
            nbrheuresTotal += activite.getInt("heures");
        }
        
        System.out.println(nbrheuresTotal);
   
    }
}
