package com.clubdelcaos.proyecto_alerta_105;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SplashActivity extends AppCompatActivity implements SplashView{
    String BASE_URL = "http://www.clubdelcaos.com/test_app/proyecto_alerta105/api_android/";
    SQLiteDatabase db;
    Cursor rows_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AdminSQLiteOpenHelper manager = new AdminSQLiteOpenHelper(getApplicationContext(),"administracion",null,1);
        db = manager.getWritableDatabase();
        rows_usuario = db.rawQuery("SELECT * FROM usuario WHERE idSesion=1",null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(rows_usuario.moveToFirst())
                {
                    saltarLogin();
                }
                else
                {
                    accederLogin();
                }
            }
        },500);
    }
    @Override
    public void saltarLogin() {
        if("on".equals(rows_usuario.getString(6)))
        {
            AsyncHttpClient cliente = new AsyncHttpClient();
            RequestParams parametros = new RequestParams();
            parametros.add("usuario",rows_usuario.getString(2));
            parametros.add("clave",rows_usuario.getString(3));
            parametros.add("operacion","login");
            parametros.add("llave","E10ADC3949BA59ABBE56E057F20F883E");
            cliente.post(getApplicationContext(), BASE_URL, parametros, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200) {
                        try {
                            JSONArray usuarioDB = new JSONArray(new String(responseBody));
                            if (usuarioDB.length() > 0) {
                                actualizarSQLite(usuarioDB);
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "ERROR, Datos Incorrectos", Toast.LENGTH_LONG).show();
                                accederLogin();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            String respuesta = new String(responseBody);
                            Toast.makeText(getApplicationContext(),"ERROR, respuesta del servidor: "+respuesta,Toast.LENGTH_LONG).show();
                        }
                    } else {
                        String status = String.valueOf(statusCode);
                        Toast.makeText(getApplicationContext(), "ERROR, Server status " + status, Toast.LENGTH_LONG).show();
                        accederLogin();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(getApplicationContext(), "ERROR, Sin conexion al servidor.", Toast.LENGTH_LONG).show();
                    accederLogin();
                }
            });
        }
        else if("off".equals(rows_usuario.getString(6)))
        {
            accederLogin();
        }
    }
    @Override
    public void accederLogin() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
    private void actualizarSQLite(JSONArray usuarioJSON)
    {
        try {
            JSONObject usuario = usuarioJSON.getJSONObject(0);
            ContentValues nuevoregistro = new ContentValues();
            nuevoregistro.put("idUsuario",usuario.getString("idUsuario"));
            nuevoregistro.put("usuario",usuario.getString("usuario"));
            nuevoregistro.put("clave",usuario.getString("clave"));
            nuevoregistro.put("correo",usuario.getString("correo"));
            nuevoregistro.put("nivel",usuario.getString("nivel"));
            nuevoregistro.put("estado","on");
            db.update("usuario",nuevoregistro,"idSesion=1",null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}