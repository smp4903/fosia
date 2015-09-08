package fosia.fosia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.parse.Parse;


public class SplashActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Activity thisActivity = this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView image = (ImageView) findViewById(R.id.imageView);
                image.animate().alpha(0).setDuration(1000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(thisActivity, MainActivity.class);
                        thisActivity.startActivity(i);
                    }
                }).start();
            }
        }, 1500);
    }

    @Override
    public void onResume(){
        super.onResume();
        Parse.initialize(this, "r9VHXEgJ5x715RTVzTd3uUs8WyIZ2NqT5BmhFcxa", "5iEkCmKnms48SMIDJdPMAnVonMjiMHVlNeS31Kq3");
    }

    @Override
    public void onBackPressed() {
        if(!this.getClass().equals(SplashActivity.class)) {
            Intent setIntent = new Intent(this, SplashActivity.class);
            startActivity(setIntent);
        } else {
            super.onBackPressed();
        }

    }
}
