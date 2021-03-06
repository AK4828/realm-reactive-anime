package learn.akm.realm.anime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import learn.akm.realm.R;
import learn.akm.realm.main.Anime;
import learn.akm.realm.main.MainActivity;

/**
 * Created by AKM on 1/16/18.
 */

public class NewAnimeActivity extends AppCompatActivity {

    @BindView(R.id.title_input)
    EditText titleInput;
    @BindView(R.id.genre_input)
    EditText genreInput;
    @BindView(R.id.description_input)
    EditText descriptionInput;
    @BindView(R.id.mainCoordinator)
    ConstraintLayout mainCoordinator;

    private Realm realm;
    private Anime anime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_anime);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();

        int animeId = getIntent().getIntExtra("animeId", 0);
        if (animeId != 0) {
            anime = realm.where(Anime.class).equalTo("id", animeId).findFirst();
            if (anime != null) {
                titleInput.setText(anime.getTittle());
                genreInput.setText(anime.getGenre());
                descriptionInput.setText(anime.getDescription());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.save_anime_button)
    public void onSaveClicked() {
        boolean isvalid = true;
        if (StringUtils.isEmpty(titleInput.getText())) {
            Snackbar snackbar = Snackbar.make(mainCoordinator, getResources().getString(R.string.label_title_empty_warning), Snackbar.LENGTH_LONG);
            snackbar.show();
            isvalid = false;
        } else if (StringUtils.isEmpty(genreInput.getText())) {
            Snackbar snackbar = Snackbar.make(mainCoordinator, getResources().getString(R.string.label_genre_empty_warning), Snackbar.LENGTH_LONG);
            snackbar.show();
            isvalid = false;
        }

        if (isvalid) {
            if (anime == null) {
                realm.executeTransactionAsync(realm -> {
                    Number currentId = realm.where(Anime.class).max("id");
                    int nextId;
                    if (currentId == null) {
                        nextId = 1;
                    } else {
                        nextId = currentId.intValue() + 1;
                    }

                    Anime newAnime = realm.createObject(Anime.class, nextId);
                    newAnime.setTittle(titleInput.getText().toString());
                    newAnime.setGenre(genreInput.getText().toString());
                    newAnime.setDescription(descriptionInput.getText().toString());

                }, () -> {
                    Toast.makeText(NewAnimeActivity.this, getResources().getString(R.string.label_success_save), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(NewAnimeActivity.this, MainActivity.class));
                    realm.close();
                }, error -> {
                    error.printStackTrace();
                    Toast.makeText(NewAnimeActivity.this, getResources().getString(R.string.label_fail_save), Toast.LENGTH_SHORT).show();
                });
            } else {
                realm.executeTransaction(realm -> {
                    anime.setTittle(titleInput.getText().toString());
                    anime.setGenre(genreInput.getText().toString());
                    anime.setDescription(descriptionInput.getText().toString());
                    realm.insertOrUpdate(anime);

                    startActivity(new Intent(NewAnimeActivity.this, MainActivity.class));
                    realm.close();
                });
            }
        }
    }
}
