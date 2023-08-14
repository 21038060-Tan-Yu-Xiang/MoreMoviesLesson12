package sg.edu.rp.c346.id21038060.moremovieslesson12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    Button btnShowMain;
    Spinner spnRatingsMain;
    ListView lvMovies;
    ArrayList<Movie> al;
    CustomAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper db = new DBHelper(MainActivity2.this);
        al.clear();
        al.addAll(db.getMovies());
        adapter.notifyDataSetChanged();

        btnShowMain.performClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnShowMain = findViewById(R.id.btnShowMain);
        spnRatingsMain = findViewById(R.id.spnRatingsMain);
        lvMovies = findViewById(R.id.lvMovies);

        String[] yourDataArray = {"G", "PG", "PG13", "NC16", "M18", "R21"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_list, yourDataArray);
        spnRatingsMain.setAdapter(adapter2);

        al = new ArrayList<>();
        adapter = new CustomAdapter(this, R.layout.row, al);
        lvMovies.setAdapter(adapter);

        DBHelper db = new DBHelper(MainActivity2.this);

        al.clear();
        al.addAll(db.getMovies());
        adapter.notifyDataSetChanged();

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Movie data = al.get(position);
                Intent i = new Intent(MainActivity2.this,
                        MainActivity3.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        btnShowMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                al.clear();
                al.addAll(db.getAllMoviesFliterByRating(spnRatingsMain.getSelectedItem().toString()));
                adapter.notifyDataSetChanged();
            }
        });
    }
}