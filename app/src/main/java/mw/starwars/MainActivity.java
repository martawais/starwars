package mw.starwars;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    public static final int PLEASE_WAIT_DIALOG = 1;


    private EditText editText;
    private Button button_search;
    private TextView response;
    private TextView responseLibrary;
    private FloatingActionButton plus;
    private DatabaseAdapter databaseAdapter;
    private TextView library;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText = (EditText) findViewById(R.id.editText);
        button_search = (Button) findViewById(R.id.button);
        response = (TextView) findViewById(R.id.response);
        responseLibrary = (TextView) findViewById(R.id.response_library);
        library = (TextView) findViewById(R.id.library);

        databaseAdapter = new DatabaseAdapter(getApplicationContext());
        databaseAdapter.open();

        databaseAdapter.close();

       /* button_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.v("EditText", editText.getText().toString());
                editText.setText("");

                new RetrieveData(this).execute();
            }

        });*/

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        plus = (FloatingActionButton) findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //editText.setHint("");
                Snackbar.make(view, "Add to favourites", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                plus.setVisibility(View.INVISIBLE);


                if(!response.getText().equals("") && !response.getText().equals("WRONG NAME OR ID OF CHARACTER")) {
                    databaseAdapter.open();
                    String description = response.getText().toString();
                    String[] lines = description.split("\n");
                    lines[0] = lines[0].replace("NAME:       ","");
                    databaseAdapter.wstawKrotkeDoTabeli(lines[0], 0, description);
                    //String[] kolumn =  databaseAdapter.wypisanieWszystkichKolumnTabeli();

                   /* for(int i=0; i<kolumn.length; i++) {

                        Log.i("INFO", kolumn[i]);
                    }*/
                    databaseAdapter.close();
                }

            }
        });
    }

    public void buttonClick(View view) {
        new RetrieveData(this).execute();
    }

    @Override
    public Dialog onCreateDialog(int dialogId) {

        switch (dialogId) {
            case PLEASE_WAIT_DIALOG:
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setTitle("Loading");
                dialog.setMessage("Please wait....");
                dialog.setCancelable(true);
                return dialog;

            default:
                break;
        }

        return null;
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
        if (id == R.id.action_library) {
            library.setVisibility(View.VISIBLE);
            response.setVisibility(View.INVISIBLE);
            responseLibrary.setVisibility(View.VISIBLE);
            plus.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.INVISIBLE);
            button_search.setVisibility(View.INVISIBLE);
            databaseAdapter.open();
            String library = "";
            String[] kolumn =  databaseAdapter.wypisanieWszystkichKolumnTabeli();

                for(int i=0; i<kolumn.length; i++) {
                    library = library +"\n\n"+kolumn[i];
                }
            databaseAdapter.close();
            responseLibrary.setText(library);

            return true;
        }
        if (id == R.id.action_search) {
            library.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.VISIBLE);
            plus.setVisibility(View.VISIBLE);
            button_search.setVisibility(View.VISIBLE);
            response.setVisibility(View.VISIBLE);
            responseLibrary.setVisibility(View.INVISIBLE);
            response.setText("");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
