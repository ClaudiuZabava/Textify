package com.example.textify.ui.setting

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.textify.activity.MainActivity
import com.example.textify.R
import com.example.textify.activity.BaseFragments
import com.example.textify.activity.IntroActivity
import com.example.textify.databinding.FragmentSettingsBinding
import com.example.textify.models.User
import com.example.textify.screenstate.ScreenState
import com.example.textify.ui.accountdetails.AccountDetailFragment
import com.example.textify.ui.themesetting.ThemeSettingFragment
import com.example.textify.utils.PreferenceManager
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : BaseFragments() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val viewModel: SettingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        val root: View = binding.root
        preferenceManager = PreferenceManager(activity?.applicationContext!!)
        viewModel.userLiveData.observe(viewLifecycleOwner) { state ->
            setUpUi(state)
        }
        setListeners()
        return root
    }

    private fun setListeners() {
        binding.btnLogout.setOnClickListener {
            signOut()
        }
        binding.settingScreenCvAccountdetailicon.setOnClickListener {
            val bottomSheetFragment = AccountDetailFragment()
            bottomSheetFragment.show(requireActivity().supportFragmentManager, "bottomSheetTag")
        }
        binding.settingscreenCvAccount.setOnClickListener {
            val bottomSheetFragment = AccountDetailFragment()
            bottomSheetFragment.show(requireActivity().supportFragmentManager, "bottomSheetTag")
        }
        binding.settingscreenCvSettingtheme.setOnClickListener {
            val bottomSheetFragment = ThemeSettingFragment()
            bottomSheetFragment.show(requireActivity().supportFragmentManager, "bottomSheetTag")
        }
    }

    private fun setUpUi(state: ScreenState<User?>) {
        binding.settingscreenTvProfilename.text = state.data?.name
        binding.settingscreenTvProfileemail.text = state.data?.email
        binding.settingscreenTvProfilephone.text = state.data?.phone
        Glide
            .with(requireContext())
            .load(state.data?.image)
            .centerCrop()
            .placeholder(R.drawable.profile_icon_placeholder_1)
            .into(binding.settingscreenIvProfile)
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        preferenceManager.clear()
        val intent = Intent(activity, IntroActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        activity?.finish()
        MainActivity().finish()
    }

}