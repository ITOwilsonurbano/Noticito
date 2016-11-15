package noticito.itosoft.wilson.com.noticito;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import noticito.itosoft.wilson.com.noticito.models.Noticias;

/**
 * Created by wilsonurbano on 10/11/16.
 */


public class ListaNoticiasAdapter extends RecyclerView.Adapter<ListaNoticiasAdapter.ViewHolder> {

    private ArrayList<Noticias> dataset;
    private Context context;

    public ListaNoticiasAdapter(Context context){
        this.context = context;
        dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticias, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Noticias n = dataset.get(position);
        holder.nombreTextView.setText(n.getTitulo());

        Glide.with(context)
                .load("http://api.inder.gov.co:8080/uploads/noticias/" + n.getId() + ".jpg")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaNoticias(ArrayList<Noticias> listaNoticias){
        dataset.addAll(listaNoticias);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView fotoImageView;
        private TextView nombreTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoImageView = (ImageView) itemView.findViewById(R.id.fotoImageView);
            nombreTextView = (TextView) itemView.findViewById(R.id.nombreTextView);
        }
    }
}
