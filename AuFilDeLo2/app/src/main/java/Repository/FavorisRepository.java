package Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import androidx.preference.PreferenceManager;

// Classe intermediaire la ville déposée et choisie par l'utilisateur et l'enregistrement Android
public class FavorisRepository extends Repository {

    // Constructeur
    public FavorisRepository(Context context) {
        super(context);
    }

    // Enregistre le favoris dans les SharedPreferences
    public void setFavoris(String infoFavoris) {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(Repository.context);
        Editor prefsEditor = appSharedPrefs.edit();
        String favoris="";

        if(isFavorisConfigured("favoris")){
            favoris = getFavoris("favoris");
            favoris+="-" + infoFavoris + "-";
        }
        else{
            favoris = "-" + infoFavoris + "-";
        }
        prefsEditor.putString("favoris",favoris);
        prefsEditor.commit();
    }

    // Supprime le favoris
    public void unsetFavoris(String infoFavoris) {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(Repository.context);
        Editor prefsEditor = appSharedPrefs.edit();
        String favoris = "";
        if (isFavorisConfigured("favoris")){
            favoris = getFavoris("favoris").replace("-" + infoFavoris + "-", "");
        }

        prefsEditor.putString("favoris", favoris);
        prefsEditor.commit();
    }

    // Indique si une ville est configurée ou non
    public boolean isFavorisConfigured(String infoFavoris) {
        FavorisRepository favorisRepository = new FavorisRepository(Repository.context);
        String leFavoris = favorisRepository.getFavoris(infoFavoris);

        if (leFavoris.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    // Recupere la ville de l'utilisateur
    public String getFavoris(String cleInfoFavoris)	{
        SharedPreferences app = PreferenceManager.getDefaultSharedPreferences(Repository.context);
        return app.getString(cleInfoFavoris, "");
    }
}
