package com.beone.kevin.ui.login


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.beone.kevin.R
import com.beone.kevin.remote.model.StatusLogin
import kotlinx.android.synthetic.main.logins_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class LoginsFragment : Fragment() {

    companion object {
        private const val TAG = "LoginsFragment"
        fun newInstance() = LoginsFragment()
    }

    private val vm: LoginsViewModel by viewModel<LoginsViewModel>()
    private lateinit var arrayAdapter: ArrayAdapter<TypeLoginEnum>
    private lateinit var typeLoginEnum: TypeLoginEnum

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.logins_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(LoginsViewModel::class.java)
        Log.d(TAG, "onActivityCreated: ${vm.getRetrofitServiceHash()}")



        val loginObserver = vm.initLiveDataLogin().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it.isFailedFetch == true){
                Toast.makeText(this.requireContext(), "gagal", Toast.LENGTH_SHORT).show()
            }else{
                if (it.iduser.equals("") && it.username.equals("")){
                    Toast.makeText(this.requireContext(), "Berhasil Login type=${it.TypeLogin.type} + id ${it.iduser} + username ${it.username}", Toast.LENGTH_SHORT).show()
                    when(it.TypeLogin){
                        TypeLoginEnum.TKI->{
                            //todo add navgraph user
                        }
                        TypeLoginEnum.PELATIH->{
                            //todo add navgraph pelatih
                        }
                        TypeLoginEnum.PEGAWAI->{
                            //todo add navgraph pegawai
                        }
                    }
                }
            }
        })

        btn_register.setOnClickListener {
            view?.findNavController()?.navigate(R.id.register_action)
        }

        btn_login.setOnClickListener{
            when(spr_categorylogin.selectedItemPosition){
                TypeLoginEnum.PILIH_LOGIN.jenis -> {
                    Toast.makeText(this.requireContext(), "Pilih Jenis Login", Toast.LENGTH_SHORT).show()
                }
                TypeLoginEnum.TKI.jenis -> {
                    Log.d(TAG, "onActivityCreated: ${TypeLoginEnum.TKI.type}")
                    vm.loginUser(edt_username.text.toString(),edt_password.text.toString())
                }
                TypeLoginEnum.PELATIH.jenis -> {
                    Log.d(TAG, "onActivityCreated: ${TypeLoginEnum.PELATIH.type}")
                    vm.loginPelatih(edt_username.text.toString(),edt_password.text.toString())
                }
                TypeLoginEnum.PEGAWAI.jenis ->
                {
                    Log.d(TAG, "onActivityCreated: ${TypeLoginEnum.PEGAWAI.type}")
                    vm.loginPegawai(edt_username.text.toString(),edt_password.text.toString())
                }
                else -> { // Note the block
                    Log.w(TAG, "onItemClick: Not Have Object Selected On Enum" )
                }
            }
        }

        arrayAdapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            TypeLoginEnum.values()
        )
        spr_categorylogin.dropDownVerticalOffset = 20
        spr_categorylogin.adapter = arrayAdapter

    }






}