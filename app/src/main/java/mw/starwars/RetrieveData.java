package mw.starwars;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mstowska on 7/10/2016.
 */
public class RetrieveData extends AsyncTask<Void, Void, String> {

    private Activity activityCall;
    private Layout layout;
    private TextView response;
    private EditText editText;
    private String query;
    private String urlString="";
    private FloatingActionButton plus;
    private String jsonString="";
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private String responseString;
    private String id;


    private Exception exception;

    public RetrieveData(Activity activityCall) {
        this.activityCall = activityCall;
       // activityCall.setContentView(R.layout.activity_main);

    }

    protected void onPreExecute() {
        activityCall.showDialog(MainActivity.PLEASE_WAIT_DIALOG);
        editText = (EditText) activityCall.findViewById(R.id.editText);
        query = editText.getText().toString();
        editText.setText("");
        response = (TextView) activityCall.findViewById(R.id.response);
        response.setText("");
        plus = (FloatingActionButton) activityCall.findViewById(R.id.plus);
        plus.setVisibility(View.VISIBLE);

    }

    protected String doInBackground(Void... urls) {



        try
        {
            int queryNum = Integer.parseInt(query);
            query = queryNum + "";
            urlString = queryNum + "/";
        }
        catch(NumberFormatException nfe)
        {
        }

        try {
            URL url = new URL("http://swapi.co/api/people/"+ urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {

                InputStream stream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();


                jsonString = stringBuilder.toString();

                if(!urlString.isEmpty()) {
                    try {

                        jsonObject = new JSONObject(jsonString);
                    } catch (JSONException e) {
                        Log.e("JSON Parser", "Error parsing data " + e.toString());
                    }

                    responseString= "NAME:       " + jsonObject.get("name")+"\n"+
                            "HEIGHT:       "+ jsonObject.getString("height")+"\n"+
                            "MASS:       "+ jsonObject.getString("mass")+"\n"+
                            "HAIR COLOR:       "+ jsonObject.getString("hair_color")+"\n"+
                            "SKIN COLOR:       "+ jsonObject.getString("skin_color")+"\n"+
                            "EYE COLOR:       "+ jsonObject.getString("eye_color")+"\n"+
                            "BIRTH YEAR:       "+ jsonObject.getString("birth_year")+"\n"+
                            "GENDER:       "+ jsonObject.getString("gender");
                }
                else {

                    jsonObject = new JSONObject(jsonString);
                    jsonArray = jsonObject.getJSONArray("results");
                    String stringTemp;

                    for (int i=0; i < jsonArray.length(); i++)
                    {
                        JSONObject oneObject = jsonArray.getJSONObject(i);
                        stringTemp = oneObject.getString("name");
                        if(stringTemp.equals(query))  {
                            responseString= "NAME:       " + stringTemp+"\n"+
                                    "HEIGHT:       "+ oneObject.getString("height")+"\n"+
                                    "MASS:       "+ oneObject.getString("mass")+"\n"+
                                    "HAIR COLOR:       "+ oneObject.getString("hair_color")+"\n"+
                                    "SKIN COLOR:       "+ oneObject.getString("skin_color")+"\n"+
                                    "EYE COLOR:       "+ oneObject.getString("eye_color")+"\n"+
                                    "BIRTH YEAR:       "+ oneObject.getString("birth_year")+"\n"+
                                    "GENDER:       "+ oneObject.getString("gender");
                            break;

                        }
                    }

                }


                return responseString;
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onProgressUpdate() {
        response.setText("testing");
    }

    protected void onPostExecute(String responseString) {


        if(responseString == null) {
            responseString = "WRONG NAME OR ID OF CHARACTER";
        }
        //progressBar.setVisibility(View.GONE);
        Log.i("INFO", responseString);
        response.setText(responseString);
        activityCall.removeDialog(MainActivity.PLEASE_WAIT_DIALOG);
    }
}