package com.dinesh.ituneschase;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.VolleyError;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements VolleySearch.ResultListener {

    private static final ArrayList<Entity> entites = Entity.getAll();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tracksRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RadioGroup entityRG = (RadioGroup) findViewById(R.id.entityRG);
        for (Entity e : entites) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setTag(e);
            radioButton.setText(e.toDisplayString());
//            radioButton.setTextColor(getResources().getColor(R.color.colorPrimary));
            entityRG.addView(radioButton);
        }
        entityRG.check(entityRG.getChildAt(0).getId());
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        volleySearch("jack johnson",Entity.musicVideo);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheet));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setHideable(true);
        final RadioGroup entityRG = (RadioGroup) findViewById(R.id.entityRG);
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
                    String search = ((EditText) view.getRootView().findViewById(R.id.searchET))
                            .getText().toString().trim();
                    InputMethodManager inputManager =
                            (InputMethodManager) MainActivity.this.
                                    getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(
                            MainActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    if (search == null || search.equals("") || search.equals(" "))
                        return;
                    Entity e = Entity.allArtist;
                    if (entityRG.getCheckedRadioButtonId() - 1 < entites.size())
                        e = entites.get(entityRG.getCheckedRadioButtonId() - 1);
                    Log.d("radio", e + "");
                    volleySearch(search, e);
                }
                bottomSheetBehavior.setState(bottomSheetBehavior.getState() ==
                        BottomSheetBehavior.STATE_HIDDEN ? BottomSheetBehavior.STATE_COLLAPSED : BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }

    /**
     * This method is used to intate the search request
     *
     * @param search Search string
     * @param entity entity
     */
    private void volleySearch(final String search, final Entity entity) {
        VolleySearch.getInstance(MainActivity.this).search(search, entity, MainActivity.this);
    }

    /**
     * Overrides the onResults to load the RecyclerView with track data.
     *
     * @param tracks
     */
    @Override
    public void onResults(Track[] tracks) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tracksRV);
        recyclerView.setAdapter(new TrackAdapter(tracks));
    }

    /**
     * This is method is called when there is a error in retrieving data from url and shows a
     * snack bar with No results
     *
     * @param error error.
     */
    @Override
    public void onError(VolleyError error) {
        Snackbar.make(findViewById(android.R.id.content), "No Results", Snackbar.LENGTH_LONG).show();
    }

}
