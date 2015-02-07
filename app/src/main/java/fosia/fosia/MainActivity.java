package fosia.fosia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;


public class MainActivity extends Activity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // WebView Content
        final WebView wv = (WebView) findViewById(R.id.webView);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                //hide loading image
                final ImageView iv = (ImageView) findViewById(R.id.splashView);
                iv.animate().alpha(0f).setDuration(1000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        iv.setVisibility(View.GONE);
                        wv.setVisibility(View.VISIBLE);
                        wv.setAlpha(0f);
                        //show webview
                       wv.animate().alpha(100f).setDuration(2000);
                    }
                });

            }


        });
        wv.loadUrl("http://3s81gd.axshare.com/app_home.html");

        // Parse Pushing
        Parse.initialize(this, "r9VHXEgJ5x715RTVzTd3uUs8WyIZ2NqT5BmhFcxa", "5iEkCmKnms48SMIDJdPMAnVonMjiMHVlNeS31Kq3");
        ParsePush.subscribeInBackground("SocialInnovation", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!this.getClass().equals(MainActivity.class)) {
            Intent setIntent = new Intent(this, MainActivity.class);
            startActivity(setIntent);
        } else {
            super.onBackPressed();
        }

    }
}
