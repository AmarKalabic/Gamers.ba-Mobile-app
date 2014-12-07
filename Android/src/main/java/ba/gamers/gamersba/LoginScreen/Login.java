package ba.gamers.gamersba.LoginScreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ba.gamers.gamersba.R;
import ba.gamers.gamersba.RealActivity.WebActivity;

/**
 * Created by Amar on 19.11.2014.
 */
public class Login extends Activity {

    private Boolean clicked = false;

    private EditText username = null;
    private EditText password = null;
    private TextView attempts;
    private EditText time;
    private TextView finalResult;
    private Button login;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    int counter = 3;
    private String username1, password1;

    private ProgressBar spinner;

    /*private class MyJavaScriptInterface {
        @JavascriptInterface
        public void handleHtml(String html) {
            // Use jsoup on this String here to search for your content.
            Document doc = Jsoup.parse(html);

            // Now you can, for example, retrieve a div with id="username" here
            Element usernameDiv = doc.select("#logovan").first();
            if (usernameDiv != null) {

            }
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Connection.Response loginForm = Jsoup.connect("http://gamers.ba/q/login")
                .method(Connection.Method.GET)
                .execute();

        Document document = Jsoup.connect("http://gamers.ba/q/login")
                .data("cookieexists", "false")
                .data("username", "username")
                .data("password", "pass")
                .data("submit", "Login")
                .cookies(loginForm.cookies())
                .post();*/

        setContentView(R.layout.activity_main);
        //time = (EditText) findViewById(R.id.et_time);

        spinner = (ProgressBar)findViewById(R.id.loader);
        spinner.setVisibility(View.GONE);

        saveLoginCheckBox = (CheckBox) findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        finalResult = (TextView) findViewById(R.id.tv_result);
        username = (EditText) findViewById(R.id.editText1);
        password = (EditText) findViewById(R.id.editText2);
        attempts = (TextView) findViewById(R.id.textView5);
        attempts.setText(Integer.toString(counter));

        if (saveLogin == true) {
            username.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }

        login = (Button) findViewById(R.id.button1);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button1:
                        //DO something
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(username.getWindowToken(), 0);
                        username1 = username.getText().toString();
                        password1 = password.getText().toString();
                        if (saveLoginCheckBox.isChecked()) {
                            loginPrefsEditor.putBoolean("saveLogin", true);
                            loginPrefsEditor.putString("username", username1);
                            loginPrefsEditor.putString("password", password1);
                            loginPrefsEditor.commit();
                        } else {
                            loginPrefsEditor.clear();
                            loginPrefsEditor.commit();
                        }
                        spinner.setVisibility(View.VISIBLE);
                        AsyncTaskRunner runner = new AsyncTaskRunner();
                        //String username = username.getText().toString();
                        //String password = password.getText().toString();
                        runner.execute(getUsername(), getPassword(), String.valueOf(clicked));
                        break;
                    //});
                }
            }
        });
    }

    public static String lineOut() {
        int level = 3;
        StackTraceElement[] traces;
        traces = Thread.currentThread().getStackTrace();
        return (" at " + traces[level] + " ");
    }

    public String getUsername() {
        return username.getText().toString();
    }

    public String getPassword() {
        return password.getText().toString();
    }

    /*private View.OnClickListener OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.button1:
                    //DO something
                    /*Intent intent = new Intent(MyActivity.this, doInBackground.class);
                    intent.putExtra("aKey", username.getText().toString());
                    startActivity(intent);
                    Intent intent1 = new Intent(MyActivity.this, doInBackground.class);
                    intent1.putExtra("bKey", password.getText().toString());
                    startActivity(intent1);
                    Intent myIntent = new Intent(MyActivity.this, doInBackground.class); //view.getContext()
                    startActivity(myIntent);
                    AsyncTaskRunner runner = new AsyncTaskRunner();
                    //String username = username.getText().toString();
                    //String password = password.getText().toString();
                    runner.execute(getUsername(), getPassword());
                    break;
            }
        }
    };*/

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        public WebView webView;
        private Boolean loggedin;

        private boolean running = true;

        @Override
        protected void onCancelled() {
            running = false;
        }

            /*public class MyJavaScriptInterface {
                @JavascriptInterface
                public void handleHtml(String html) {
                    // Use jsoup on this String here to search for your content.
                    Document doc = Jsoup.parse(html);

                    // Now you can, for example, retrieve a div with id="username" here
                    Element usernameDiv = doc.select("#logovan").first();
                    if (usernameDiv != null) {
                        loggedin = true;
                    } else {
                        loggedin = false;
                    }
                }
            }*/


        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
            loggedin = false;

            /*class MyJavaScriptInterface {
                @JavascriptInterface
                public void handleHtml(String html) {
                    // Use jsoup on this String here to search for your content.
                    Document doc = Jsoup.parse(html);

                    // Now you can, for example, retrieve a div with id="username" here
                    Element usernameDiv = doc.select("#logovan").first();
                    if (usernameDiv != null) {
                        loggedin = true;
                    }else{
                        loggedin = false;
                    }
                }
            }*/


        }

        /*loggedin = false;

        private class MyJavaScriptInterface {
            @JavascriptInterface
            public void handleHtml(String html) {
                // Use jsoup on this String here to search for your content.
                Document doc = Jsoup.parse(html);

                // Now you can, for example, retrieve a div with id="username" here
                Element usernameDiv = doc.select("#logovan").first();
                if (usernameDiv != null) {
                   loggedin = true;
                }else{
                    loggedin = false;
                }
            }
        }*/

        @Override
        protected String doInBackground(String... params) {

            class MyJavaScriptInterface {
                @JavascriptInterface
                public void handleHtml(String html) {
                    // Jsoup string
                    Document doc = Jsoup.parse(html);

                    // Div search
                    Element usernameDiv = doc.select("#logovan").first();
                    if (usernameDiv != null) {
                        loggedin = true;
                        System.out.println("User je logovan!");
                        running = false;
                        Intent myIntent = new Intent(Login.this, WebActivity.class); //view.getContext()
                        startActivity(myIntent);
                        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                // runs on UI thread
                                Toast.makeText(getApplicationContext(),
                                        "Login uspjesan!", Toast.LENGTH_LONG).show();
                                webView.setVisibility(View.GONE);
                                //spinner.setVisibility(View.GONE);
                                finish();
                            }
                        });
                    } else {
                        System.out.println("User NIJE logovan!");
                        loggedin = false;
                    }
                }
            }
            while (running == true) {
                publishProgress("Login na sajt u toku..."); // Calls onProgressUpdate()
                try {
                    //Toast.makeText(getApplicationContext(), "Try started!", Toast.LENGTH_LONG).show();
                    // Do long operations here and return the result
                    //int time = Integer.parseInt(params[0]);
                    // Sleeping for given time period
                    //Thread.sleep(time);
                    //resp = "Slept for " + time + " milliseconds";
                    //boolean loadhomepage = false;
                    final String usernamee = params[0];
                    final String passwordd = params[1];
                    final boolean[] clicked = {Boolean.parseBoolean(params[2])};
                    final String possibleUrl = "http://www.gamers.ba/";
                    final String possibleUrl1 = "http://gamers.ba/";
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //stuff that update ui
                        webView = (WebView) findViewById(ba.gamers.gamersba.R.id.webview1);
                        setContentView(R.layout.activity_main);

                    }
                });*/
                    runOnUiThread(new Runnable() {
                        public void run() {
                            webView = (WebView) findViewById(ba.gamers.gamersba.R.id.webview1);
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.loadUrl("http://www.gamers.ba/q/user/login");
                            webView.addJavascriptInterface(new MyJavaScriptInterface(), "HtmlHandler");
                            //android:visibility="gone"
                            //webView.setVisibility(View.GONE);
                            final String currentUrl = webView.getUrl();
                            final boolean[] loadedOnce = new boolean[1];
                            loadedOnce[0] = false;
                            webView.setWebViewClient(new WebViewClient() {

                                public void onPageFinished(WebView view, String url) {
                                    //Toast.makeText(getApplicationContext(), "onPageFinished OK!", Toast.LENGTH_LONG).show();
                                    //webView.loadUrl("javascript:(function(){document.getElementById('" + usernamee + "').value='MyUsername';document.getElementById('" + passwordd + "').value='MyPassword';document.forms.loginForma.submit.click();})();");
                                    //Toast.makeText(getApplicationContext(), "Javacsript loaded!", Toast.LENGTH_LONG).show();
                                    //System.out.println( "Finished loading: " + url );
                                    webView.loadUrl("javascript:window.HtmlHandler.handleHtml" +
                                            "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                                    System.out.println("Finished loading: " + url);
                                    if (clicked[0] = true) {
                                        if (url.equals(possibleUrl) || (url.equals(possibleUrl1))) {
                                            // TREBA OVDJE PROVJERAVATI LOGGED IN = TRUE/FALSE AKO JE TRUE ONDA CONTINUE, A AKO JE FALSE ONDA OVO DOLE
                                            if (loggedin = true) {
                                            } else if (loggedin = false) {
                                                Intent myIntent = new Intent(Login.this, WebActivity.class); //view.getContext()
                                                startActivity(myIntent);
                                                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                runOnUiThread(new Runnable() {
                                                    public void run() {
                                                        // runs on UI thread
                                                        Toast.makeText(getApplicationContext(),
                                                                "Login uspjesan!", Toast.LENGTH_LONG).show();
                                                        webView.setVisibility(View.GONE);
                                                        finish();
                                                    }
                                                });
                                                //Toast.makeText(getApplicationContext(),
                                                //        "Login uspjesan!", Toast.LENGTH_LONG).show();
                                                //Toast.makeText(getApplicationContext(),
                                                //        "Login uspjesan!", Toast.LENGTH_LONG).show();
                                            }
                                        } else if (url.equals("http://www.gamers.ba/q/user/login")) {
                                            webView.getSettings().setJavaScriptEnabled(true);
                                            webView.loadUrl("javascript:(function(){document.getElementById('username').value='" + usernamee + "';document.getElementById('password').value='" + passwordd + "';document.forms.loginForma.submit.click();})();");
                                            loadedOnce[0] = true;
                                        } else if (loadedOnce[0] == true && url.equals("http://www.gamers.ba/q/user/login")) {
                                            Intent myIntent1 = new Intent(Login.this, Login.class); //view.getContext()
                                            startActivity(myIntent1);
                                            myIntent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    // runs on UI thread
                                                    Toast.makeText(getApplicationContext(),
                                                            "Pogresan username ili password!", Toast.LENGTH_LONG).show();
                                                    loadedOnce[0] = false;
                                                    finish();
                                                }
                                            });
                                        } else if (url.startsWith("http://www.gamers.ba/logout.php") || (url.startsWith("http://gamers.ba/logout.php"))) {
                                            //moras prvo killati ovaj class
                                            Intent myIntent2 = new Intent(Login.this, Login.class); //view.getContext()
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
                                        } else

                                        {
                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    // runs on UI thread
                                                    Toast.makeText(getApplicationContext(),
                                                            "Doslo je do greske pri loginu!", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }
                    });

                    //} catch (InterruptedException e) {
                    //    e.printStackTrace();
                    //    resp = e.getMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                    resp = e.getMessage();
                }
                return resp;
            }

            return null;
        }


        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        //@Override
        protected void onPostExecute(String result) {
            spinner = (ProgressBar) findViewById(R.id.loader);
            // execution of result of Long time consuming operation
            finalResult.setText(result);
        }


        ///*
        // * (non-Javadoc)
        // *
        // * @see android.os.AsyncTask#onPreExecute()
        // */
        /*@Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
            loggedin = false;

            public class MyJavaScriptInterface {
                @JavascriptInterface
                public void handleHtml(String html) {
                    // Use jsoup on this String here to search for your content.
                    Document doc = Jsoup.parse(html);

                    // Now you can, for example, retrieve a div with id="username" here
                    Element usernameDiv = doc.select("#logovan").first();
                    if (usernameDiv != null) {
                        loggedin = true;
                    }else{
                        loggedin = false;
                    }
                }
            }*/


        ///*
        // * (non-Javadoc)
        // *
        // * @see android.os.AsyncTask#onProgressUpdate(Progress[])
        // */
        //@Override
        protected void onProgressUpdate(String... text) {
            finalResult.setText(text[0]);
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }

    /*private WebView webView;

    @Override
    public void onStart() {
        super.onStart();
        webView = (WebView) findViewById(ba.gamers.gamersba.R.id.webview1);
        webView.loadUrl("http://www.gamers.ba/");
    }

    @Override
    public void onStop() {
        super.onStop();
        stopMakingThatPeriodicRestCall();
    }*/

    }
}

    //public void login(View view){
    //    Intent myIntent = new Intent(this, WebActivity.class); //view.getContext()
    //    startActivity(myIntent); //startActivityForResult(myIntent, 0);
        /*if(username.getText().toString().equals("admin") &&
                password.getText().toString().equals("admin")){
            Toast.makeText(getApplicationContext(), "Redirecting...",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Wrong Credentials",
                    Toast.LENGTH_SHORT).show();
            attempts.setBackgroundColor(Color.RED);
            counter--;
            attempts.setText(Integer.toString(counter));
            if(counter==0){
                login.setEnabled(false);
            }*/
