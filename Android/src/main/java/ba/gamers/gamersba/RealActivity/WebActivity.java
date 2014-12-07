package ba.gamers.gamersba.RealActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ba.gamers.gamersba.LoginScreen.Login;
import ba.gamers.gamersba.MyActivity;
import ba.gamers.gamersba.NotificationReminder.ReminderServicePoruke;


public class WebActivity extends ActionBarActivity {

    private boolean cancel=false;


    private void startstopAlarmPoruke() {
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderServicePoruke.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
        if (cancel == true){
           cancel = false;
           alarmManager.cancel(pendingIntent);
        } else if (!cancel) {
            cancel = true;
            long when = System.currentTimeMillis();         // notification time
            alarmManager.setRepeating(AlarmManager.RTC, when, (AlarmManager.INTERVAL_FIFTEEN_MINUTES / 25), pendingIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(ba.gamers.gamersba.R.menu.my, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Boolean[] loggedin = new Boolean[1];

        //WebView WebView = new WebView(this);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(ba.gamers.gamersba.R.layout.activity_my);

        class MyJavaScriptInterface {
            @JavascriptInterface
            public void handleHtml(String html) {
                // Use jsoup on this String here to search for content.
                Document doc = Jsoup.parse(html);

                // retrieve a div with id="username" here
                Elements porukeDiv = doc.select("div#logovan > a[header-pvtmsg-link]");
                String porukeText = porukeDiv.text();
                System.out.println("porukeDiv: " + porukeDiv);
                System.out.println("porukeText: " + porukeText);
                if (!porukeDiv.isEmpty()) {
                    System.out.println("porukeDiv NOT NULL! ");
                    if (porukeText.startsWith("Poruke (")) {
                        loggedin[0] = true;
                        startstopAlarmPoruke();
                    }
                }else{
                    loggedin[0] = false;
                    startstopAlarmPoruke();
                    System.out.println("porukeDiv NULL! ");
                    Intent myIntent2 = new Intent(WebActivity.this, Login.class); //view.getContext()
                    startActivity(myIntent2);
                    myIntent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // runs on UI thread
                            Toast.makeText(getApplicationContext(),
                                    "Logout u toku...!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }
            }
        }

        webView = (WebView) findViewById(ba.gamers.gamersba.R.id.webview);
        webView.setInitialScale(1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "HtmlHandler");
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.loadUrl("http://www.gamers.ba"); //treba loadati login page a ne ovaj
        // DODATI LOGIN S JAVASCRIPTOM
        // DODATI JSOUP CHECKER ZA USERNAME I PW, DRUGA OPCIJA JE CHECKIRATI currentUrl I AKO JE HOMEPAGE ONDA OK, AKO JE LOGIN PAGE ONDA VRATI DIALOG
        // TO-DOV2: DODATI REMEMBER ME CHECKBOX, DODATI LOGOUT
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                setProgressBarIndeterminateVisibility(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                setProgressBarIndeterminateVisibility(false);
                webView.loadUrl("javascript:window.HtmlHandler.handleHtml" +
                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                if (url.startsWith("http://www.gamers.ba/logout.php") || (url.startsWith("http://gamers.ba/logout.php"))) {
                    Intent myIntent2 = new Intent(WebActivity.this, MyActivity.class); //view.getContext()
                    startActivity(myIntent2);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // runs on UI thread
                            Toast.makeText(getApplicationContext(),
                                    "Logout u toku...!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        /*try{
            Document doc = Jsoup.connect("http://www.gamers.ba").get();

            Element link = doc.select("a").first()
        }*/

    }

    //** /** Called when the user touches the button */
    public boolean homePage(MenuItem item) {
        // Do something in response to button click
        setContentView(ba.gamers.gamersba.R.layout.activity_my);
        webView = (WebView) findViewById(ba.gamers.gamersba.R.id.webview);
        //getActionBar().setDisplayHomeAsUpEnabled(true); < --- sklonjeno zbog errora kad stisnes home button
        webView.setInitialScale(1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.loadUrl("http://www.gamers.ba");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        return false;
    }

    public boolean reloadPage (MenuItem item) {
        setContentView(ba.gamers.gamersba.R.layout.activity_my);
        String webUrl = webView.getUrl();
        webView = (WebView) findViewById(ba.gamers.gamersba.R.id.webview);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        webView.setInitialScale(1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.loadUrl(webUrl);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
                case KeyEvent.KEYCODE_BACK:
                    if(webView.canGoBack()){
                        webView.goBack();
                    }else{
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
