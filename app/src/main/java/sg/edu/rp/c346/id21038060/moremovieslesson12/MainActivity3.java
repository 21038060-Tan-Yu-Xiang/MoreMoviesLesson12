package sg.edu.rp.c346.id21038060.moremovieslesson12;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {

    Button btnUpdate;
    Button btnDelete;
    Button btnCancel;
    EditText etTitle;
    EditText etGenre;
    EditText etYear;
    Spinner spnRatings;
    TextView tvMovieId;

    Movie data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        tvMovieId = findViewById(R.id.tvMovieId);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        spnRatings = findViewById(R.id.spnRatings);

        String[] yourDataArray = {"G", "PG", "PG13", "NC16", "M18", "R21"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_list, yourDataArray);
        spnRatings.setAdapter(adapter);

        DBHelper db = new DBHelper(MainActivity3.this);

        Intent i = getIntent();
        data = (Movie) i.getSerializableExtra("data");

        tvMovieId.setText("Movie ID: "+ data.getId());
        etTitle.setText(data.getTitle());
        etGenre.setText(data.getGenre());
        etYear.setText(String.valueOf(data.getYear()));

        switch (data.getRating()) {
            case "G":
                spnRatings.setSelection(0);
                break;
            case "PG":
                spnRatings.setSelection(1);
                break;
            case "PG13":
                spnRatings.setSelection(2);
                break;
            case "NC16":
                spnRatings.setSelection(3);
                break;
            case "M18":
                spnRatings.setSelection(4);
                break;
            default:  //R21
                spnRatings.setSelection(5);
                break;
        }

        btnUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (etTitle.getText().toString().trim().isEmpty() || etGenre.getText().toString().trim().isEmpty() || etYear.getText().toString().trim().isEmpty()){
                    Toast.makeText(MainActivity3.this, "Required fields are missing.", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Create the Dialog Builder
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity3.this);

                    //Set the dialog details
                    myBuilder.setTitle("ℹ️ Info");
                    myBuilder.setMessage("Are you sure you want to update the movie "+etTitle.getText().toString()+"?");
                    myBuilder.setCancelable(false);

                    //Configure the positive button
                    myBuilder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            data.setTitle(etTitle.getText().toString());
                            data.setGenre(etGenre.getText().toString());
                            data.setYear(Integer.parseInt(etYear.getText().toString()));
                            data.setRating(spnRatings.getSelectedItem().toString());
                            db.updateMovie(data);
                            db.close();
                            finish();

                            Toast.makeText(MainActivity3.this, "Movie info updated.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    //Configure the 'neutral' button
                    myBuilder.setNeutralButton("CANCEL", null);
                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Create the Dialog Builder
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity3.this);

                //Set the dialog details
                myBuilder.setTitle("⚠️ Danger");
                myBuilder.setMessage("Are you sure you want to delete the movie "+etTitle.getText().toString()+"?");
                myBuilder.setCancelable(false);

                //Configure the positive button
                myBuilder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteMovie(data.getId());
                        finish();

                        Toast.makeText(MainActivity3.this, "Movie Deleted: "+etTitle.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                //Configure the 'neutral' button
                myBuilder.setNeutralButton("CANCEL", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //Create the Dialog Builder
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity3.this);

                //Set the dialog details
                myBuilder.setTitle("⚠️ Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes?");
                myBuilder.setCancelable(false);

                //Configure the positive button
                myBuilder.setPositiveButton("DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                //Configure the 'neutral' button
                myBuilder.setNeutralButton("DO NOT DISCARD", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
    }
}