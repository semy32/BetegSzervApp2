package hu.petrik.betegszervapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Toolbar toolbar =  findViewById(R.id.main_toolbar);
       setSupportActionBar(toolbar);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {


                select();

            }
        });


    }
    @Override
    protected void onActivityResult(int requestcode,int resultcode,Intent data){
        if(requestcode== 1){
            if(resultcode == Activity.RESULT_OK){
                Toast.makeText(this,"Új beteg felvétele megtörtént!",Toast.LENGTH_LONG).show();
                select();

            }
            else{
                Toast.makeText(this,"Hibás adatok lettek megadva",Toast.LENGTH_LONG).show();
            }


            }

            else if(requestcode== 2){

            if(resultcode == Activity.RESULT_OK){
                Toast.makeText(this,"Új szerv felvétele megtörtént!",Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(this,"Hibás adatok lettek megadva",Toast.LENGTH_LONG).show();
            }

        }
        else{

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_ujbeteg:
                Intent intent = new Intent(MainActivity.this,UjbetegActivity.class);
                MainActivity.this.startActivityForResult(intent,1);
                return true;
            case R.id.menu_ujszerv:
                Intent intent2 = new Intent(MainActivity.this,UjSzervActivity.class);
                MainActivity.this.startActivityForResult(intent2,2);
                return true;
            case R.id.menu_refresh:
                Toast.makeText(this,"Adatok Frissítve!",Toast.LENGTH_LONG).show();
                select();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    public void select(){
        ListView betegLista = (ListView)findViewById(R.id.beteg_lista);
        DbHelper helper = DbHelper.getInstsance(MainActivity.this);
        SQLiteDatabase db;
        db = helper.getWritableDatabase();

        ArrayList<Beteg> lista = new ArrayList<>();
        Cursor cursor = db.query(
                "beteg",
                new String[] { "id", "nev", "taj", "szerv", "tipus", "szerv_id" },
                null,
                null,
                null,
                null,
                "nev",
                null);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            String nev = cursor.getString(1);
            String taj = cursor.getString(2);
            String szerv = cursor.getString(3);
            String tipus = cursor.getString(4);
            long szerv_id = cursor.getLong(5);
            lista.add(new Beteg(id, nev, taj, szerv, tipus, szerv_id));
        }
        cursor.close();

        ArrayAdapter<Beteg> adapter = new ArrayAdapter<Beteg>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                lista
        );

        betegLista.setAdapter(adapter);
        db.close();

    }

}
