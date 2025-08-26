package ru.yandex.practicum.sprint11koh32

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.DateFormat

class NewsAdapter : RecyclerView.Adapter<NewsItemViewHolder>() {

    var items: List<NewsItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return when(viewType) {
            TYPE_SPORT ->  SportItemViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.v_sport_item, parent, false))
            TYPE_SINCE ->  SinceItemViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.v_since_item, parent, false))
            TYPE_UNKNOWN -> UnknownItemViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.v_sport_item, parent, false))
            else -> throw IllegalStateException("Unknown viewType $viewType")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            is NewsItem.Sport -> TYPE_SPORT
            is NewsItem.Science -> TYPE_SINCE
            is NewsItem.Unknown -> TYPE_UNKNOWN
        }
    }

    companion object {
        private const val TYPE_SPORT = 1
        private const val TYPE_SINCE = 2
        private const val TYPE_UNKNOWN= 3
    }
}

abstract class NewsItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    abstract fun  bind(item: NewsItem)
}

class UnknownItemViewHolder(
    itemView: View
) : NewsItemViewHolder(
    itemView
) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val created: TextView = itemView.findViewById(R.id.created)

    override fun bind(item: NewsItem) {
        val item = item as NewsItem.Unknown
        title.text = item.title
        created.text =
            DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT
            ).format(item.created)

    }
}

class SportItemViewHolder(
    itemView: View
) : NewsItemViewHolder(
    itemView
) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val created: TextView = itemView.findViewById(R.id.created)
    private val sportTeams: TextView = itemView.findViewById(R.id.sport_teams)

    override fun bind(item: NewsItem) {
        val item = item as NewsItem.Sport
        title.text = item.title
        created.text =
            DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT
            ).format(item.created)

        sportTeams.text = item.specificPropertyForSport
    }
}

class SinceItemViewHolder(
    itemView: View
) : NewsItemViewHolder(
    itemView
) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val created: TextView = itemView.findViewById(R.id.created)
    private val scienceImg: ImageView = itemView.findViewById(R.id.science_img)

    override fun bind(item: NewsItem) {
        val item = item as NewsItem.Science
        title.text = item.title
        created.text =
            DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT
            ).format(item.created)

        Glide.with(scienceImg)
            .load(item.specificPropertyForScience)
            .into(scienceImg)
    }
}