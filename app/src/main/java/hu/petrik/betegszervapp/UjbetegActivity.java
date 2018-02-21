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

public class UjbetegActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ujbeteg);

        Button UjbetegLetrehozas = findViewById(R.id.ujbeteg_gomb);
        final EditText name = findViewById(R.id.ujbetegname);
        final EditText taj = findViewById(R.id.ujbetegtaj);
        final EditText tipus = findViewById(R.id.ujbeteg_tipus);
        final Spinner szerv = findViewById(R.id.ujbeteg_szerv);

        UjbetegLetrehozas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        DbHelper helper = DbHelper.getInstsance(UjbetegActivity.this);
                        db = helper.getWritableDatabase();
                        Intent returnIntent = new Intent();
                        if(!(name.getText().toString().equals("")) &&
                                !(taj.getText().toString().equals("")) &&
                                !(tipus.getText().toString().equals(""))
                                )
                        {
                            ContentValues ujbeteg = new ContentValues();
                            ujbeteg.put("nev", name.getText().toString());
                            ujbeteg.put("taj",  taj.getText().toString());
                            ujbeteg.put("szerv", szerv.getSelectedItem().toString());
                            ujbeteg.put("tipus",  tipus.getText().toString());
                            ujbeteg.putNull("szerv_id");
                            db.insert("beteg", null, ujbeteg);




                            setResult(UjbetegActivity.RESULT_OK, returnIntent);
                            db.close();
                            finish();
                        }
                        else{

                            setResult(UjbetegActivity.RESULT_CANCELED, returnIntent);
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
