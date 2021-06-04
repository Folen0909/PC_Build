package hr.ferit.pcbuildlogger.ui.pcbuild

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.pcbuildlogger.R
import hr.ferit.pcbuildlogger.data.model.PcBuild
import hr.ferit.pcbuildlogger.utiliites.OnPcBuildClickListener

class PcBuildAdapter(pcBuilds: List<PcBuild>,
                     private val clickListener: OnPcBuildClickListener)
    : RecyclerView.Adapter<PcBuildViewHolder>() {

    private val pcBuilds: MutableList<PcBuild> = mutableListOf()

    init {
        refreshData(pcBuilds)
    }

    fun refreshData(pcBuilds: List<PcBuild>) {
        this.pcBuilds.clear()
        this.pcBuilds.addAll(pcBuilds)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PcBuildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pc_build, parent, false)
        return PcBuildViewHolder(view)
    }

    override fun onBindViewHolder(holder: PcBuildViewHolder, position: Int) {
        val pcBuild = pcBuilds[position]
        holder.bind(pcBuild, clickListener)
    }

    override fun getItemCount(): Int {
        return pcBuilds.size
    }
}