package com.gleides.myappfirebase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gleides.myappfirebase.Livro;
import com.gleides.myappfirebase.R;

import java.util.ArrayList;

/**
 * Created by gleides on 10/10/16.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    ArrayList<Livro> bookList;
    private Context mContext;
    private AdapterListener mAdapterListener;

    public AdapterListener getAdapterListener() {
        return mAdapterListener;
    }

    public void setAdapterListener(AdapterListener mAdapterListener) {
        this.mAdapterListener = mAdapterListener;
    }

    public ListAdapter(Context context, ArrayList<Livro> bookList) {
        this.bookList = bookList;
        this.mContext = context;
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_book, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        Livro itemBookList = bookList.get(position);

        //Seta os valores do livro para o layout dentro do holder
        holder
                .setTxtTitle(itemBookList.getTitulo())
                .setTxtAutor(itemBookList.getAutor())
                .setTxtPagina(itemBookList.getPagina())
                .setTxtAno(itemBookList.getAno());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public interface AdapterListener{
        void onItemClick(View view, int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtTitle;
        private TextView txtAutor;
        private TextView txtPagina;
        private TextView txtAno;
        private ImageView imgCapa;
        //ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);
            //Recupera as referencias do layout
            txtTitle = (TextView) itemView.findViewById(R.id.detalhesTitulo);
            txtAutor = (TextView) itemView.findViewById(R.id.detalhesAutor);
            txtPagina = (TextView) itemView.findViewById(R.id.detalhesPagina);
            txtAno = (TextView) itemView.findViewById(R.id.detalhesAno);
            //imgCapa = (ImageView) itemView.findViewById(R.id.imageBook);
            //progressBar = (ProgressBar) itemView.findViewById(R.id.my_progress_bar);

            //Registra o Listener no item view
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getPosition();
            if (mAdapterListener != null){
                mAdapterListener.onItemClick(view, pos);
            }
        }

        public ViewHolder setTxtTitle(String title) {
            if (txtTitle == null) return this;
            txtTitle.setText(title);
            return this;
        }

        public ViewHolder setTxtAutor(String autor) {
            if (txtAutor == null) return this;
            txtAutor.setText(autor);
            return this;
        }

        public ViewHolder setTxtPagina(int pagina) {
            if (txtPagina == null) return this;
            txtPagina.setText(pagina+"");
            return this;
        }

        public ViewHolder setTxtAno(int ano) {
            if (txtAno == null) return this;
            txtAno.setText(ano+"");
            return this;
        }

        /*public ViewHolder setTImgBook(String imgUrl) {
            if (imgCapa == null) return this;
            //new DownloadImageTask(capa).execute(imgUrl);
            //Picasso.with(context).load(urlBookCover).resize(200,200).centerCrop().into(capa);
            *//*Glide.with(mContext).load(imgUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(capa);*//*
            Ocean
                    .glide(mContext)
                    .load(imgUrl)
                    .build(GlideRequest.BITMAP)
                    .resize(100,100)
                    .circle()
                    .into(imgCapa);
            return this;
        }*/

        /*private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView imageView;

            public DownloadImageTask(ImageView imageView) {
                this.imageView = imageView;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            protected Bitmap doInBackground(String... urls) {
                String urlDisplay = urls[0];
                Bitmap mImage = null;
                try {
                    InputStream in = new java.net.URL(urlDisplay).openStream();
                    mImage = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mImage;
            }

            protected void onPostExecute(Bitmap result) {
                imageView.setImageBitmap(result);
                progressBar.setVisibility(View.INVISIBLE);
            }

        }*/

    }
}
