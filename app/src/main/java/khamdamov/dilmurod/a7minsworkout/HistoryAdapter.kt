package khamdamov.dilmurod.a7minsworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import khamdamov.dilmurod.a7minsworkout.databinding.ItemHistoryRowBinding

class HistoryAdapter(private val items: ArrayList<String>) :
    RecyclerView.Adapter<HistoryAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: ItemHistoryRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemHistoryRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val date: String = items.get(position)
        holder.binding.tvPosition.text = (position + 1).toString()
        holder.binding.tvItem.text = date

        if (position % 2 == 0){
            holder.binding.llHistoryItemMain.setBackgroundColor(Color.parseColor("#EBEBEB"))
        } else {
            holder.binding.llHistoryItemMain.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}