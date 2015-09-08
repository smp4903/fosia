package fosia.fosia;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.JsonReader;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.parse.ParsePushBroadcastReceiver;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.TreeMap;

/**
 * Created by smp on 04/07/15.
 */
public class PushReceiver extends ParsePushBroadcastReceiver {

    public static final String ACTION                       =   "com.example.package.MESSAGE";
    public static final String PARSE_EXTRA_DATA_KEY         =   "com.parse.Data";
    public static final String PARSE_JSON_CHANNEL_KEY       =   "com.parse.Channel";


    public static final String MESSAGES_PREFERENCE_KEY = "PARSE_MESSAGES";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context,intent);
        String action = intent.getAction();
        String channel = intent.getExtras().getString(PARSE_JSON_CHANNEL_KEY);
        JSONObject json = null;
        try {
            json = new JSONObject(intent.getExtras().getString(PARSE_EXTRA_DATA_KEY));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(json != null){

            Log.i("PARSE", "Action: " +  action);
            Log.i("PARSE", "Channel: " +  channel);
            Log.i("PARSE", "JSON: " + json.toString());

            String mString = PreferenceManager.getDefaultSharedPreferences(context).getString(MESSAGES_PREFERENCE_KEY, "");

            Messages messages;

            if(mString != null && !mString.isEmpty()){
                messages = gson.fromJson(mString, Messages.class);
            } else {
                messages = new Messages();
            }

            try {
                String alert = json.getString("alert");
                if(alert != null && !alert.isEmpty()) {
                    messages.getMessages().put(new Date().getTime() + "", channel != null ? channel + ";" + alert : "ALL;" + alert);
                }
                PreferenceManager.getDefaultSharedPreferences(context).edit().putString(MESSAGES_PREFERENCE_KEY, gson.toJson(messages)).apply();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("PARSE MESSAGES", mString);
            Log.i("PARSE MESSAGES", messages.getMessages().toString());

        }
    }
}
