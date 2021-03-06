package com.beone.kevin.ui.pelatih.schedulepelatih

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.beone.kevin.R
import com.beone.kevin.SharedPreferenceUtils
import kotlinx.android.synthetic.main.schedule_pelatih_fragment.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SchedulePelatihFragment : Fragment(),
    ItemOnClick {

    companion object {
        fun newInstance() =
            SchedulePelatihFragment()

        private const val TAG = "SchedulePelatihFragment"
    }

    private val viewModel: SchedulePelatihViewModel by sharedViewModel<SchedulePelatihViewModel>()
    private val sharedPreferenceUtils: SharedPreferenceUtils by inject<SharedPreferenceUtils>()

    private val adapter: JadwalAdapter =
        JadwalAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.schedule_pelatih_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.initData().observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onActivityCreated: VM OBSERVE")
            adapter.swapData(it)
        })

        viewModel.getData(sharedPreferenceUtils.getIdUser);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcv_jadwal.adapter = adapter

        btn_add.setOnClickListener {

            this.findNavController()
                .navigate(R.id.action_schedulePelatihFragment_to_addSchedulePelatihDialogFragment)
        }
    }

    override fun onDelete(id: String?) {
        Toast.makeText(requireContext(), "Delete Item ${id}", Toast.LENGTH_SHORT).show()
        viewModel.deleteData(sharedPreferenceUtils.getIdUser, id)
    }

    override fun onDetail(id: String?) {
//        Desti
        if (id != null) {
          val action =    SchedulePelatihFragmentDirections.actionSchedulePelatihFragmentToSelectTkiForTrainingFragment(id)
            this.findNavController().navigate(action)
        }

    }

}