package appk.obzorkardsonlinesss.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import appk.obzorkardsonlinesss.R
import appk.obzorkardsonlinesss.model.LoanModel
import com.bumptech.glide.Glide


class LoansAdapter(private val callbackAbout: ((item: LoanModel) -> Unit)? = null,
                   private val callback: ((item: LoanModel) -> Unit)? = null) : RecyclerView.Adapter<LoansAdapter.MyViewHolder>() {

    private var noteList: List<LoanModel> = listOf()

    fun setList(list: List<LoanModel>) {
        noteList = list
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var rootView: CardView = view.findViewById(R.id.rootView)
        var sumTo: TextView = view.findViewById(R.id.sumTo)
        var percentage: TextView = view.findViewById(R.id.percentage)
        var ages: TextView = view.findViewById(R.id.ages)
        var getText: TextView = view.findViewById(R.id.getText)
        var aboutText: TextView = view.findViewById(R.id.aboutText)
        var logo: ImageView = view.findViewById(R.id.logo)
        var st1: ImageView = view.findViewById(R.id.st1)
        var st2: ImageView = view.findViewById(R.id.st2)
        var st3: ImageView = view.findViewById(R.id.st3)
        var st4: ImageView = view.findViewById(R.id.st4)
        var st5: ImageView = view.findViewById(R.id.st5)
        var yandexImg: ImageView = view.findViewById(R.id.yandexImg)
        var qiwiImg: ImageView = view.findViewById(R.id.qiwiImg)
        var worldImg: ImageView = view.findViewById(R.id.worldImg)
        var masterImg: ImageView = view.findViewById(R.id.masterImg)
        var visaImg: ImageView = view.findViewById(R.id.visaImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.loan_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = noteList[position]

        Glide.with(holder.itemView.context).load(note.imageUrl).into(holder.logo)
        when(note.rating){
            1-> {
                holder.st1.setImageResource(R.drawable.ic_star)
                holder.st2.setImageResource(R.drawable.ic_star_disable)
                holder.st3.setImageResource(R.drawable.ic_star_disable)
                holder.st4.setImageResource(R.drawable.ic_star_disable)
                holder.st5.setImageResource(R.drawable.ic_star_disable)
            }
            2-> {
                holder.st1.setImageResource(R.drawable.ic_star)
                holder.st2.setImageResource(R.drawable.ic_star)
                holder.st3.setImageResource(R.drawable.ic_star_disable)
                holder.st4.setImageResource(R.drawable.ic_star_disable)
                holder.st5.setImageResource(R.drawable.ic_star_disable)
            }
            3-> {
                holder.st1.setImageResource(R.drawable.ic_star)
                holder.st2.setImageResource(R.drawable.ic_star)
                holder.st3.setImageResource(R.drawable.ic_star)
                holder.st4.setImageResource(R.drawable.ic_star_disable)
                holder.st5.setImageResource(R.drawable.ic_star_disable)
            }
            4-> {
                holder.st1.setImageResource(R.drawable.ic_star)
                holder.st2.setImageResource(R.drawable.ic_star)
                holder.st3.setImageResource(R.drawable.ic_star)
                holder.st4.setImageResource(R.drawable.ic_star)
                holder.st5.setImageResource(R.drawable.ic_star_disable)
            }
            5-> {
                holder.st1.setImageResource(R.drawable.ic_star)
                holder.st2.setImageResource(R.drawable.ic_star)
                holder.st3.setImageResource(R.drawable.ic_star)
                holder.st4.setImageResource(R.drawable.ic_star)
                holder.st5.setImageResource(R.drawable.ic_star)
            }
            else -> {
                holder.st1.setImageResource(R.drawable.ic_star_disable)
                holder.st2.setImageResource(R.drawable.ic_star_disable)
                holder.st3.setImageResource(R.drawable.ic_star_disable)
                holder.st4.setImageResource(R.drawable.ic_star_disable)
                holder.st5.setImageResource(R.drawable.ic_star_disable)
            }
        }

        holder.sumTo.text = "до ${note.sumOne}"
        holder.percentage.text = "от ${note.percent} в день"
        holder.ages.text = "возраст ${note.age} лет"

        if (note.visa!!){
            holder.visaImg.alpha = 1f
        } else {
            holder.visaImg.alpha = 0.5f
        }

        if (note.mastercard!!){
            holder.masterImg.alpha = 1f
        } else {
            holder.masterImg.alpha = 0.5f
        }

        if (note.mir!!){
            holder.worldImg.alpha = 1f
        } else {
            holder.worldImg.alpha = 0.5f
        }

        if (note.qiwi!!){
            holder.qiwiImg.alpha = 1f
        } else {
            holder.qiwiImg.alpha = 0.5f
        }

        if (note.yandex!!){
            holder.yandexImg.alpha = 1f
        } else {
            holder.yandexImg.alpha = 0.5f
        }

        holder.aboutText.setOnClickListener {
            callbackAbout?.invoke(note)
        }

        holder.getText.setOnClickListener {
            callback?.invoke(note)
        }

        holder.rootView.setOnClickListener {
            callbackAbout?.invoke(note)
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}
