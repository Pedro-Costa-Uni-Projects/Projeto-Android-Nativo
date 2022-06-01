package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import pt.ulusofona.deisi.cm2122.g21904825_21904341.databinding.ItemExpressionBinding

class ListAdapter (private val onFireClick: (Fire) -> Unit,
                   private var items: List<Fire> = listOf()) : RecyclerView.Adapter<ListAdapter.ListViewHolder>(), Filterable {

    class ListViewHolder(val binding: ItemExpressionBinding) : RecyclerView.ViewHolder(binding.root)

    var untouchedList = arrayListOf<Fire>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            ItemExpressionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            onFireClick(items[position])
        }
        val fire = items[position]
        holder.binding.textData.text = getData(fire.getTimestamp())
        holder.binding.textLocalization.text = resourcesStatic!!.getString(R.string.localization_list_details, fire.getDistrict(), fire.getCounty(), fire.getParish())
        holder.binding.textStatus.text = fire.getState()

    }

    override fun getItemCount() = items.size

    fun updateItems(items: List<Fire>) {
        this.items = items
        this.untouchedList = items as ArrayList<Fire>
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if(charSearch.isEmpty()) {
                    items = untouchedList
                } else {
                    val resultList = ArrayList<Fire>()
                    for (fire in items) {
                        if(fire.getDistrict().lowercase().contains(constraint.toString().lowercase())) {
                            resultList.add(fire)
                        }
                    }
                    items = resultList
                }
                val filteredResults = FilterResults()
                filteredResults.values = items
                return filteredResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                items = results?.values as ArrayList<Fire>
                notifyDataSetChanged()
            }
        }
    }
}