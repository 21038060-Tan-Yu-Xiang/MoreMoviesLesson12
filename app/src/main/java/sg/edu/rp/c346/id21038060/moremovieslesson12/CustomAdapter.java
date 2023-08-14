package sg.edu.rp.c346.id21038060.moremovieslesson12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Movie> movieList;

    public CustomAdapter(Context context, int resource, ArrayList<Movie> objects) {
        super(context,resource, objects);

        parent_context = context;
        layout_id = resource;
        movieList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvTitle = rowView.findViewById(R.id.textViewTitle);
        TextView tvGenre = rowView.findViewById(R.id.textViewGenre);
        TextView tvYear = rowView.findViewById(R.id.textViewYear);
        ImageView imageViewRating = rowView.findViewById(R.id.imageViewRating);

        // Obtain the Android Version information based on the position
        Movie currentMovie = movieList.get(position);

        // Set values to the TextView to display the corresponding information
        tvTitle.setText(currentMovie.getTitle());
        tvGenre.setText(currentMovie.getGenre());
        tvYear.setText(String.valueOf(currentMovie.getYear()));

        switch (currentMovie.getRating()) {
            case "G":
                Picasso.with(parent_context).load("https://www.imda.gov.sg/-/media/imda/images/content/regulation-licensing-and-consultations/content-standards-and-classification/classification-rating/general-rating.webp").into(imageViewRating);
                break;
            case "PG":
                Picasso.with(parent_context).load("https://www.imda.gov.sg/-/media/imda/images/content/regulation-licensing-and-consultations/content-standards-and-classification/classification-rating/pg-rating.webp").into(imageViewRating);
                break;
            case "PG13":
                Picasso.with(parent_context).load("https://www.imda.gov.sg/-/media/imda/images/content/regulation-licensing-and-consultations/content-standards-and-classification/classification-rating/pg13-rating.webp").into(imageViewRating);
                break;
            case "NC16":
                Picasso.with(parent_context).load("https://www.imda.gov.sg/-/media/imda/images/content/regulation-licensing-and-consultations/content-standards-and-classification/classification-rating/nc16-rating.webp").into(imageViewRating);
                break;
            case "M18":
                Picasso.with(parent_context).load("https://www.imda.gov.sg/-/media/imda/images/content/regulation-licensing-and-consultations/content-standards-and-classification/classification-rating/m18-rating.webp").into(imageViewRating);
                break;
            default:  //R21
                Picasso.with(parent_context).load("https://www.imda.gov.sg/-/media/imda/images/content/regulation-licensing-and-consultations/content-standards-and-classification/classification-rating/r21-rating.webp").into(imageViewRating);
                break;
        }
        return rowView;
    }
}
