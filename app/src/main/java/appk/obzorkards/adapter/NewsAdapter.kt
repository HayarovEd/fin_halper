package appk.obzorkards.adapter


import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import appk.obzorkards.R
import appk.obzorkards.model.NewsModel
import appk.obzorkards.model.PrivacyModel
import com.bumptech.glide.Glide


class NewsAdapter(private val callback: ((item: NewsModel) -> Unit)? = null) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    private var noteList: List<NewsModel> = listOf()

    fun setList(list: List<NewsModel>) {
        noteList = list
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var rootView: CardView = view.findViewById(R.id.rootView)
        var content: TextView = view.findViewById(R.id.content)
        var image: ImageView = view.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = noteList[position]
        holder.content.text = Html.fromHtml(note.title)
        Glide.with(holder.itemView.context).load(note.imageUrl).into(holder.image)

        holder.rootView.setOnClickListener {
            callback?.invoke(note)
        }

    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}
