package hu.petrik.betegszervapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class UjSzervActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ujszerv);

        Button UjszervLetrehozas = findViewById(R.id.ujszervletrehozas_gomb);
        final EditText tipus = findViewById(R.id.ujszerv_tipus);
        final Spinner szerv = findViewById(R.id.ujszerv_szerv);

        UjszervLetrehozas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        DbHelper helper = DbHelper.getInstsance(UjSzervActivity.this);
                        db = helper.getWritableDatabase();
                        Intent returnIntent = new Intent();

                        if(!(tipus.getText().toString().equals("")))
                        {
                            ContentValues ujszerv = new ContentValues();


                            ujszerv.put("szervid", (int) szerv.getSelectedItemId());
                            ujszerv.put("tipus",  tipus.getText().toString());

                            db.insert("szerv", null, ujszerv);




                            setResult(UjSzervActivity.RESULT_OK, returnIntent);
                            db.close();
                            finish();

                        }
                        else{


                            setResult(UjSzervActivity.RESULT_CANCELED, returnIntent);
                            db.close();
                            finish();


                        }

                    }
                });






            }
        });






    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
