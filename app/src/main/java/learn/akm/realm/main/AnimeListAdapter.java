package learn.akm.realm.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import learn.akm.realm.R;
import learn.akm.realm.anime.NewAnimeActivity;

/**
 * Created by AKM on 1/15/18.
 */

public class AnimeListAdapter extends RecyclerView.Adapter<AnimeListAdapter.AnimeListViewHolder> {

    private List<Anime> animeList = new ArrayList<>();
    private Context context;

    public AnimeListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public AnimeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_anime_list, parent, false);

        return new AnimeListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnimeListViewHolder holder, int position) {
        Anime anime = animeList.get(position);
        holder.animeTittle.setText(anime.getTittle());
        holder.animeGenre.setText(anime.getGenre());
        holder.animeDescription.setText(anime.getDescription());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewAnimeActivity.class);
            intent.putExtra("animeId", anime.getId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public void addAnimeList(Anime anime) {
        animeList.add(anime);
        notifyDataSetChanged();
    }

    public void addAllAnimeList(List<Anime> animes) {
        animeList.addAll(animes);
        notifyDataSetChanged();
    }

    public class AnimeListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.anime_tittle)
        TextView animeTittle;
        @BindView(R.id.anime_genre)
        TextView animeGenre;
        @BindView(R.id.anime_description)
        TextView animeDescription;

        public AnimeListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
