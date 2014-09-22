
package json;

import java.io.FileNotFoundException;
import java.io.IOException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSON {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        String json = FileReader.loadFileIntoString("json/employe.json", "UTF-8");
        JSONArray employes = JSONArray.fromObject(json);
        
        int nbEmployes = employes.size();
        for(int i=0; i<nbEmployes; i++){
            System.out.println(employes.get(i));
        }
        
    }
}
