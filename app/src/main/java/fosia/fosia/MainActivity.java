package fosia.fosia;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParsePush;
import com.parse.PushService;


public class MainActivity extends ActionBarActivity {

    private static final String PARSE_CHANNEL_KEY = "PARSE_CHANNEL_KEY";
    private Channel currentChannel;
    private WebView webView;
    private ConnectivityManager cm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        cm = (ConnectivityManager) this.getSystemService(Activity.CONNECTIVITY_SERVICE);
        String url = "http://3s81gd.axshare.com/app_home.html";

        if(cm.getActiveNetworkInfo().isConnected()){
            // Parse Pushing
            Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
            Parse.initialize(this, "r9VHXEgJ5x715RTVzTd3uUs8WyIZ2NqT5BmhFcxa", "5iEkCmKnms48SMIDJdPMAnVonMjiMHVlNeS31Kq3");

            currentChannel = Channel.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getString(PARSE_CHANNEL_KEY, "ALL"));

            if(currentChannel != null && !currentChannel.equals(Channel.NONE)){
                subsribe(currentChannel);
            }

            ParseAnalytics.trackAppOpenedInBackground(getIntent());

            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            webView.loadUrl(url);
        } else {
            Toast.makeText(this, "Ingen netværksforbindelse.", Toast.LENGTH_SHORT).show();
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webView.loadUrl(url);
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        Parse.initialize(this, "r9VHXEgJ5x715RTVzTd3uUs8WyIZ2NqT5BmhFcxa", "5iEkCmKnms48SMIDJdPMAnVonMjiMHVlNeS31Kq3");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public void onBackPressed() {
        webView.loadUrl("http://3s81gd.axshare.com/app_home.html");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(!item.isChecked()) {
            switch (item.getItemId()) {
                case R.id.case1:
                    subsribe(Channel.CASE1);
                    break;
                case R.id.case2:
                    subsribe(Channel.CASE2);
                    break;
                case R.id.case3:
                    subsribe(Channel.CASE3);
                    break;
                case R.id.all:
                    subsribe(Channel.ALL);
                    break;
                case R.id.none:
                    subsribe(Channel.NONE);
                    break;
            }
            item.setChecked(true);
            invalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        switch (currentChannel) {
            case CASE1:
                menu.findItem(R.id.case1).setChecked(true);
                break;
            case CASE2:
                menu.findItem(R.id.case2).setChecked(true);
                break;
            case CASE3:
                menu.findItem(R.id.case3).setChecked(true);
                break;
            case ALL:
                menu.findItem(R.id.all).setChecked(true);
                break;
            case NONE:
                menu.findItem(R.id.none).setChecked(true);
                break;
        }

        return true;
    }


    private void subsribe(Channel channel){
        unsubstribeAll();

        currentChannel = channel;

        ParsePush.subscribeInBackground(channel.name);
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString(PARSE_CHANNEL_KEY, channel.name).apply();

        Toast.makeText(this, "Abonnerer på kanal: " + channel.uiName, Toast.LENGTH_SHORT).show();
    }

    private void unsubstribeAll(){
        for(Channel c : Channel.values()){
            ParsePush.unsubscribeInBackground(c.name);
        }
    }

    public enum Channel {
        CASE1("CASE1", "Case 1"), CASE2("CASE2", "Case 2"), CASE3("CASE3", "Case 3"), ALL("ALL", "Alle"), NONE("NONE", "Ingen");

        private final String name;
        private final String uiName;

        Channel(String name, String uiName){
            this.name = name;
            this.uiName = uiName;
        }
    }

}
