package sg.edu.rp.c346.id21038060.moremovieslesson12;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    Spinner spnRatingsMain;
    TextView tvEmpty;
    ListView lvMovies;
    ArrayList<Movie> al;
    CustomAdapter adapter;

    DBHelper db = null;

    private void onSpinnerItemSelected(int position) {
        al.clear();
        switch (position) {
            case 0:
                al.addAll(db.getAllMoviesFliterByRating("G"));
                break;
            case 1:
                al.addAll(db.getAllMoviesFliterByRating("PG"));
                break;
            case 2:
                al.addAll(db.getAllMoviesFliterByRating("PG13"));
                break;
            case 3:
                al.addAll(db.getAllMoviesFliterByRating("NC16"));
                break;
            case 4:
                al.addAll(db.getAllMoviesFliterByRating("M18"));
                break;
            case 5:
                al.addAll(db.getAllMoviesFliterByRating("R21"));
                break;
        }
        adapter.notifyDataSetChanged();
        updateViewsVisibility();
    }

    private void updateViewsVisibility() {
        if (al.isEmpty()) {
            // ListView is empty
            tvEmpty.setVisibility(View.VISIBLE);
            lvMovies.setVisibility(View.GONE);
        } else {
            // ListView has entries
            tvEmpty.setVisibility(View.GONE);
            lvMovies.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new DBHelper(MainActivity2.this);

        al.clear();
        spnRatingsMain.setSelection(0);
        onSpinnerItemSelected(0);
        adapter.notifyDataSetChanged();

        // Restore the selected spinner index
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int selectedSpinnerIndex = preferences.getInt("selectedSpinnerIndex", 0);
        spnRatingsMain.setSelection(selectedSpinnerIndex);
        onSpinnerItemSelected(selectedSpinnerIndex);

        updateViewsVisibility();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        spnRatingsMain = findViewById(R.id.spnRatingsMain);
        lvMovies = findViewById(R.id.lvMovies);
        tvEmpty = findViewById(R.id.tvEmpty);

        String[] yourDataArray = {"G", "PG", "PG13", "NC16", "M18", "R21"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_list, yourDataArray);
        spnRatingsMain.setAdapter(adapter2);

        al = new ArrayList<>();
        adapter = new CustomAdapter(this, R.layout.row, al);
        lvMovies.setAdapter(adapter);

        db = new DBHelper(MainActivity2.this);

        al.clear();
        al.addAll(db.getMovies());
        adapter.notifyDataSetChanged();

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {

                // Save the selected spinner index
                int selectedSpinnerIndex = spnRatingsMain.getSelectedItemPosition();
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("selectedSpinnerIndex", selectedSpinnerIndex);
                editor.apply();

                //Launch MainActivity3
                Movie data = al.get(position);
                Intent i = new Intent(MainActivity2.this,
                        MainActivity3.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        spnRatingsMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onSpinnerItemSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}