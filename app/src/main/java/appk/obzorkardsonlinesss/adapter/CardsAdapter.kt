package appk.obzorkardsonlinesss.adapter


import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import appk.obzorkardsonlinesss.R
import appk.obzorkardsonlinesss.model.CardModel
import com.bumptech.glide.Glide


class CardsAdapter(private val callback: ((item: CardModel) -> Unit)? = null) : RecyclerView.Adapter<CardsAdapter.MyViewHolder>() {

    private var noteList: List<CardModel> = listOf()

    fun setList(list: List<CardModel>) {
        noteList = list
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var rootView: CardView = view.findViewById(R.id.rootView)
        var textSubmit: TextView = view.findViewById(R.id.textSubmit)
        var content: TextView = view.findViewById(R.id.content)
        var title: TextView = view.findViewById(R.id.title)
        var cardImage: ImageView = view.findViewById(R.id.cardImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = noteList[position]
        Glide.with(holder.itemView.context).load(note.imageUrl).into(holder.cardImage)
        holder.title.text = note.name
        holder.content.text = Html.fromHtml(note.text)
        holder.textSubmit.setOnClickListener { callback?.invoke(note) }

    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}
