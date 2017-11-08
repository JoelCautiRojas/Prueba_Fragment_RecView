package com.clubdelcaos.proyecto_alerta_105;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Cursor rows_usuario;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdminSQLiteOpenHelper manager = new AdminSQLiteOpenHelper(getApplicationContext(),"administracion",null,1);
        db = manager.getWritableDatabase();
        rows_usuario = db.rawQuery("SELECT * FROM usuario WHERE idSesion=1",null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

        switch (item.getItemId())
        {
            case R.id.opcion1:
                if (rows_usuario.moveToFirst()) {
                    db.delete("usuario", "idSesion=1", null);
                }
                volverLogin();
                return true;
            case R.id.opcion2:
                if (rows_usuario.moveToFirst())
                {
                    ContentValues nuevoregistro = new ContentValues();
                    nuevoregistro.put("idUsuario","");
                    nuevoregistro.put("clave","");
                    nuevoregistro.put("correo","");
                    nuevoregistro.put("nivel","");
                    nuevoregistro.put("estado","off");
                    db.update("usuario",nuevoregistro,"idSesion=1",null);
                }
                volverLogin();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void volverLogin() {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        String[] titulos = {"DENUNCIAS","EVENTOS","NOTICIAS"};

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            switch (position)
            {
                case 0:return new Tab_AlertasFragment();
                case 1:return new Tab_HistorialFragment();
                case 2:return new Tab_DirectorioFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return titulos[position];
        }
    }
}
