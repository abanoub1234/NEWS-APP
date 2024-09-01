package com.example.news_app;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.news_app.Models.NewsHeadlines;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<CustomeViewHolder>
{
    private Context context;
    private List<NewsHeadlines> headlines;
    private selectListener listener;

    public CustomAdapter(Context context, List<NewsHeadlines> headlines , selectListener listener)
    {
        this.context = context;
        this.headlines = headlines;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public CustomeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        return new CustomeViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CustomeViewHolder holder, int position)
    {
       holder.text_title.setText(headlines.get(position).getTitle());
       holder.text_source.setText(headlines.get(position).getSource().getName());

       if(headlines.get(position).getUrlToImage() != null)
       {
           Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.img_headline);
       }

       holder.cardView.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View view)
           {
               listener.OnNewsClicked(headlines.get(position));
           }
       });
    }

    @Override
    public int getItemCount()
    {
        return headlines.size();
    }
}
