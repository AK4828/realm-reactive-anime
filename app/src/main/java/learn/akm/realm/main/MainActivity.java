package learn.akm.realm.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import learn.akm.realm.R;
import learn.akm.realm.anime.NewAnimeActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.anime_list)
    RecyclerView animeList;

    private AnimeListAdapter animeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        animeListAdapter = new AnimeListAdapter(this);
        animeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        animeList.setItemAnimator(new DefaultItemAnimator());
        animeList.setHasFixedSize(true);
        animeList.setAdapter(animeListAdapter);

        Realm realm = Realm.getDefaultInstance();

        RealmResults<Anime> getAllAnimes = realm.where(Anime.class).findAll();
        animeListAdapter.addAllAnimeList(getAllAnimes);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick(R.id.fab)
    public void onClickAdd() {
        startActivity(new Intent(this, NewAnimeActivity.class));
    }
}
