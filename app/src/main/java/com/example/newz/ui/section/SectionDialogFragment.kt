package com.example.newz.ui.section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.newz.databinding.DialogSectionBinding
import com.example.newz.utils.applyWindowInsets
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SectionDialogFragment : BottomSheetDialogFragment(), SectionAdapter.SectionListener {

    private lateinit var binding: DialogSectionBinding
    private lateinit var adapter: SectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSectionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.applyWindowInsets()
        initSectionList()
    }

    private fun initSectionList() {
        adapter = SectionAdapter(this)
        binding.sectionList.adapter = adapter
        SectionModel.sectionList.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
    }

    override fun onSectionClicked(section: Section) {
        if (SectionModel.setSectionItemChecked(section.type)) dialog?.cancel()
    }
}