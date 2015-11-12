package com.ichsanfirdaus.mysqlcrud;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ichsan on 12/11/15.
 */
public class SemuaBukuTamu extends ListActivity{

    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> bukutamuList;
    private static String url_semua_bukutamu = "http://192.168.100.4/pendaftaran/get_all_pendaftaran.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PENDAFTARAN = "pendaftaran";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    JSONArray pendaftaran = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.semua_bukutamu);

        bukutamuList = new ArrayList<HashMap<String, String>>();
        new LoadSemuaBukuTamu().execute();

        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pid = ((TextView) view.findViewById(R.id.pid)).getText().toString();
                Intent in = new Intent(getApplicationContext(), EditBukuTamu.class);
                in.putExtra(TAG_PID, pid);
                startActivityForResult(in, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    class LoadSemuaBukuTamu extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SemuaBukuTamu.this);
            pDialog.setMessage("Loading data, please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jParser.makeHttpRequest(url_semua_bukutamu, "GET", params);
            Log.d("Semua Buku Tamu : ", json.toString());

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    pendaftaran = json.getJSONArray(TAG_PENDAFTARAN);
                    for (int i = 0; i < pendaftaran.length(); i++) {
                        JSONObject c = pendaftaran.getJSONObject(i);

                        String id = c.getString(TAG_PID);
                        String name = c.getString(TAG_NAME);

                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);

                        bukutamuList.add(map);
                    }
                } else {
                    Intent i = new Intent(getApplicationContext(), TambahBukuTamu.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(SemuaBukuTamu.this, bukutamuList, R.layout.list_pendaftaran, new String[] { TAG_PID, TAG_NAME }, new int[] { R.id.pid, R.id.name });
                    setListAdapter(adapter);
                }
            });
        }
    }
}
