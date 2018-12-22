package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Log.d(TAG, "onCreate: called");
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            Log.d(TAG, "onCreate: if (intent == null)");
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.d(TAG, "onCreate: if (position == DEFAULT_POSITION)");
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Log.d(TAG, "onCreate: Json: " + json);
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        Log.d(TAG, "onCreate: sandwich name: " + sandwich.getMainName());

        if (sandwich == null) {
            // Sandwich data unavailable
            Log.d(TAG, "onCreate: if (sandwich == null)");
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView alsoKnownAsTV = findViewById(R.id.also_known_tv);
        TextView placeOfOriginTV = findViewById(R.id.origin_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);
        TextView description = findViewById(R.id.description_tv);
        StringBuilder stringBuilder = new StringBuilder();

        List<String> alsoKnownAsArray = sandwich.getAlsoKnownAs();
        if (alsoKnownAsArray != null && alsoKnownAsArray.size() > 0) {
            for (int i = 0; i < alsoKnownAsArray.size() - 1; i++) {
                stringBuilder.append(alsoKnownAsArray.get(i));
                stringBuilder.append(", ");
            }
            stringBuilder.append(alsoKnownAsArray.get(alsoKnownAsArray.size() - 1));
            alsoKnownAsTV.setText(stringBuilder.toString());
        }

        placeOfOriginTV.setText(sandwich.getPlaceOfOrigin());

        stringBuilder = new StringBuilder();
        List<String> ingredientsArray = sandwich.getIngredients();
        if (ingredientsArray != null && ingredientsArray.size() > 0) {
            for (int i = 0; i < ingredientsArray.size() - 1; i++) {
                stringBuilder.append(ingredientsArray.get(i));
                stringBuilder.append(", ");
            }
            stringBuilder.append(ingredientsArray.get(ingredientsArray.size() - 1));
            ingredients.setText(stringBuilder.toString());
        }

        description.setText(sandwich.getDescription());

    }
}
