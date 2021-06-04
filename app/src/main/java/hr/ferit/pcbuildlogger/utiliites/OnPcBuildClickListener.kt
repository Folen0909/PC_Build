package hr.ferit.pcbuildlogger.utiliites

import hr.ferit.pcbuildlogger.data.model.PcBuild

interface OnPcBuildClickListener {
    fun onPcBuildDelete(pcBuild: PcBuild)
    fun onPcBuildDetails(pcBuild: PcBuild)
}