package hr.ferit.pcbuildlogger.ui.pcbuild

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.pcbuildlogger.data.model.PcBuild
import hr.ferit.pcbuildlogger.databinding.ItemPcBuildBinding
import hr.ferit.pcbuildlogger.utiliites.OnPcBuildClickListener

class PcBuildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(pcBuild: PcBuild, clickListener: OnPcBuildClickListener) {
        val itemBinding = ItemPcBuildBinding.bind(itemView)
        itemView.setOnClickListener { clickListener.onPcBuildDetails(pcBuild) }
        itemBinding.tvBuildName.text = pcBuild.name
        itemBinding.btnDelete.setOnClickListener { clickListener.onPcBuildDelete(pcBuild) }
    }
}