package sg.edu.rp.c346.id21038060.moremovieslesson12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etGenre, etYear;
    Spinner spnRatings;
    Button btnInsert, btnShow;

    String rating = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        spnRatings = findViewById(R.id.spnRatings);
        btnInsert = findViewById(R.id.btnUpdate);
        btnShow = findViewById(R.id.btnDelete);

        String[] yourDataArray = {"G", "PG", "PG13", "NC16", "M18", "R21"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_list, yourDataArray);
        spnRatings.setAdapter(adapter);

        // Create the DBHelper object, passing in the activity's Context
        DBHelper db = new DBHelper(MainActivity.this);

        spnRatings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rating = "";
                switch (i) {
                    case 0:
                        rating = "G";
                        break;
                    case 1:
                        rating = "PG";
                        break;
                    case 2:
                        rating = "PG13";
                        break;
                    case 3:
                        rating = "NC16";
                        break;
                    case 4:
                        rating = "M18";
                        break;
                    case 5:
                        rating = "R21";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Insert a song
        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (etTitle.getText().toString().trim().isEmpty() || etGenre.getText().toString().trim().isEmpty() || etYear.getText().toString().trim().isEmpty()){
                    Toast.makeText(MainActivity.this, "Required fields are missing.", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.insertMovie(etTitle.getText().toString(), etGenre.getText().toString(), Integer.parseInt(String.valueOf(etYear.getText())), rating);

                    etTitle.setText("");
                    etGenre.setText("");
                    etYear.setText("");
                    spnRatings.setSelection(0);

                    Toast.makeText(MainActivity.this, "Movie added successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });
    }
}