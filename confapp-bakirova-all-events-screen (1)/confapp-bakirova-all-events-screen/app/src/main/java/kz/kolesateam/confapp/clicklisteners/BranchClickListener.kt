package kz.kolesateam.confapp.clicklisteners

import kz.kolesateam.confapp.model.BranchData

interface BranchClickListener {
    fun onBranchClick(
        branchData: BranchData,
    )
}