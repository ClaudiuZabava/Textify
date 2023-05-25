package com.example.textify.ui.settingchatwallpaper

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.textify.R
import com.example.textify.databinding.FragmentSettingChatWallpaperBinding
import com.example.textify.screenstate.ScreenState
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SettingChatWallpaperFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSettingChatWallpaperBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SettingChatWallpaperViewModel
    private var imageUri: Uri? = null
    private lateinit var croppedImageUri: Uri
    private var flag: Int = 0


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.bottomSheetStyle_accountdetail)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSettingChatWallpaperBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SettingChatWallpaperViewModel::class.java]
        viewModel.wallpaperLiveData.observe(viewLifecycleOwner) { state ->
            if(flag==0) {
                processWallpaperDetail(state)
                showToast("Loaded")
            }
        }
        setUpUi()
        setUpListeners()
        return binding.root
    }

    private fun setUpUi() {
        val imageView = binding.settingchatwallpaperCvWallpaper
        val height = (imageView.layoutParams.width*16)/9
        imageView.layoutParams.height = height
        imageView.requestLayout()
    }

    private fun setUpListeners() {
        binding.settingchatwallpaperTvChange.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1980)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()

        }
        binding.settingchatwallpaperTvRemove.setOnClickListener {
            imageUri = null
            Glide
                .with(requireContext())
                .load("")
                .centerCrop()
                .into(binding.settingchatwallpaperIvWallpaper)
        }
        binding.settingchatwallpaperCvSet.setOnClickListener {
            flag = 0
            if(imageUri!=null) {
                val path: String = "USER_IMAGE"+System.currentTimeMillis()+"."+getFileExtension(imageUri!!)
                viewModel.uploadWallpaper(imageUri!!,path)
            } else {
                viewModel.uploadWallpaper(null,"")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val bottomSheetInternal = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheetInternal!!).apply {
                peekHeight = ViewGroup.LayoutParams.MATCH_PARENT
                isHideable = true
                state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            flag = 1
            imageUri = data?.data!!
            Glide
                .with(requireContext())
                .load(imageUri)
                .centerCrop()
                .into(binding.settingchatwallpaperIvWallpaper)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            flag = 0
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            flag = 0
            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun processWallpaperDetail(state: ScreenState<String?>) {
        when(state) {
            is ScreenState.Success -> {
                if(!state.data.isNullOrEmpty()) {
                    Glide
                        .with(requireContext())
                        .load(state.data)
                        .centerCrop()
                        .into(binding.settingchatwallpaperIvWallpaper)
                }
            }
            else -> {
                //TODO
            }
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(requireContext().contentResolver.getType(uri))
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val REQUEST_CROP_IMAGE = 123
    }

}