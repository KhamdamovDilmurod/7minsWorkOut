package khamdamov.dilmurod.a7minsworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import khamdamov.dilmurod.a7minsworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter( val list: MutableList<ExerciseModel>): RecyclerView.Adapter<ExerciseStatusAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: ItemExerciseStatusBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = list[position]
        holder.binding.tvItem.text = item.getId().toString()

        when{
            item.getIsSelected() -> {
                holder.binding.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.item_circular_thin_color_accent_border)
                holder.binding.tvItem.setTextColor(Color.parseColor("#212121"))
            }
            item.getIsCompleted() -> {
                holder.binding.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.item_circular_color_accent_background)
                holder.binding.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else -> {
                holder.binding.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.item_circular_color_gray_background)
                holder.binding.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}