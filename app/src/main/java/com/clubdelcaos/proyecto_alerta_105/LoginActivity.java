package com.clubdelcaos.proyecto_alerta_105;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    String BASE_URL = "http://www.clubdelcaos.com/test_app/proyecto_alerta105/api_android/";
    EditText et_user, et_pass;
    Button btn_login, btn_registrar;
    CheckBox ch_sesion;
    private ProgressDialog barprog;
    SQLiteDatabase db;
    Cursor rows_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_user = (EditText) findViewById(R.id.usuario_input);
        et_pass = (EditText) findViewById(R.id.clave_input);
        btn_login = (Button) findViewById(R.id.ingresar);
        btn_registrar = (Button) findViewById(R.id.registrar);
        ch_sesion = (CheckBox) findViewById(R.id.check_sesion);
        AdminSQLiteOpenHelper manager = new AdminSQLiteOpenHelper(getApplicationContext(),"administracion",null,1);
        db = manager.getWritableDatabase();
        rows_usuario = db.rawQuery("SELECT * FROM usuario WHERE idSesion=1",null);
        if (rows_usuario.moveToFirst())
        {
            et_user.setText(rows_usuario.getString(2));
        }
        else
        {
            et_user.setText("");
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarLogin();
            }
        });
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarOpen();
            }
        });
    }

    private void registrarOpen() {
        startActivity(new Intent(LoginActivity.this,RegistroActivity.class));
    }

    private void validarLogin() {
        if("".equals(et_user.getText().toString()) || "".equals(et_pass.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"Campos vacios.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            barprog = new ProgressDialog(LoginActivity.this);
            barprog.setCancelable(false);
            barprog.setMessage("Cargando...");
            barprog.setMax(100);
            barprog.setProgress(0);
            barprog.show();
            AsyncHttpClient cliente =  new AsyncHttpClient();
            RequestParams parametros = new RequestParams();
            parametros.put("usuario",et_user.getText().toString());
            parametros.put("clave",et_pass.getText().toString());
            parametros.add("operacion","login");
            parametros.put("llave","E10ADC3949BA59ABBE56E057F20F883E");
            cliente.post(getApplicationContext(), BASE_URL, parametros, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if(statusCode == 200)
                    {
                        barprog.dismiss();
                        try {
                            JSONArray usuarioDB = new JSONArray(new String(responseBody));
                            if(usuarioDB.length() > 0)
                            {
                                if(rows_usuario.moveToFirst())
                                {
                                    actualizarSQLite(usuarioDB);
                                }
                                else
                                {
                                    cargarSQLite(usuarioDB);
                                }
                                accederMain();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"ERROR, Datos Incorrectos.",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            String respuesta = new String(responseBody);
                            Toast.makeText(getApplicationContext(),"ERROR, respuesta del servidor: "+respuesta,Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        barprog.dismiss();
                        String status = String.valueOf(statusCode);
                        Toast.makeText(getApplicationContext(),"ERROR, Server status "+status,Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    barprog.dismiss();
                    Toast.makeText(getApplicationContext(),"ERROR, Sin conexion al servidor.",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void accederMain()
    {
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }
    private void cargarSQLite(JSONArray usuarioJSON)
    {
        try {
            JSONObject usuario = usuarioJSON.getJSONObject(0);
            ContentValues nuevoregistro = new ContentValues();
            nuevoregistro.put("idSesion",1);
            nuevoregistro.put("idUsuario",usuario.getString("idUsuario"));
            nuevoregistro.put("usuario",usuario.getString("usuario"));
            nuevoregistro.put("clave",usuario.getString("clave"));
            nuevoregistro.put("correo",usuario.getString("correo"));
            nuevoregistro.put("nivel",usuario.getString("nivel"));
            if(ch_sesion.isChecked())
            {
                nuevoregistro.put("estado","on");
            }
            else
            {
                nuevoregistro.put("estado","off");
            }
            db.insert("usuario",null,nuevoregistro);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
            if(ch_sesion.isChecked())
            {
                nuevoregistro.put("estado","on");
            }
            else
            {
                nuevoregistro.put("estado","off");
            }
            db.update("usuario",nuevoregistro,"idSesion=1",null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
