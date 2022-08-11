package com.example.finalproject.ui.addComplex

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.adapter.RecyclerImageAdapter
import com.example.finalproject.databinding.FragmentAddingComplexBinding
import com.example.finalproject.model.ComplexRequest
import com.example.finalproject.utils.BaseResponse


class AddingComplexFragment : Fragment() {

    private var _binding: FragmentAddingComplexBinding? = null
    val REQUEST_IMAGE_CAPTURE = 1
    private val binding get() = _binding!!
    private lateinit var viewModel: ComplexViewModel
    private val adapter: RecyclerImageAdapter by lazy {
        RecyclerImageAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddingComplexBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ComplexViewModel::class.java]
        binding.rvImage.adapter = adapter

        binding.btnSave.setOnClickListener {
            saveComplex()
        }

        binding.circleImageView.setOnClickListener {
            imagePickDialog()
        }

        binding.btnClose.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        return binding.root
    }

    private fun saveComplex() {
        val addComplex = ComplexRequest(
            adapter.toString(),
            binding.etComplex.text.toString(),
            binding.etDescription.text.toString(),
            binding.etPhoneNumber.text.toString().toInt(),
            binding.etTelegram.text.toString())
        viewModel.saveComplex(addComplex)
        viewModel.complexLD.observe(viewLifecycleOwner, {
            when(it){
                is BaseResponse.Loading -> {}
                is BaseResponse.Success -> {
                    Toast.makeText(requireActivity(), "Complex Added", Toast.LENGTH_SHORT).show()
                }
                is BaseResponse.Error -> it.msg?.let { msg -> requireActivity() }
            }
        })
    }

    private fun imagePickDialog() {

        val options = AlertDialog.Builder(requireActivity())
        options.setTitle("Pick Image From :")

        options.setNeutralButton("Gallery") { dialog, which ->
            pickFromGallery()
        }

        options.setPositiveButton("Camera") { dialog, which ->
            pickFromCamera()
        }

        options.create()
        options.show()
    }

    private fun pickFromCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {

        }
    }

    private fun pickFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 123)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == RESULT_OK && null != data) {
            if (data.clipData != null) {
                val cout = data.clipData!!.itemCount
                for (i in 0 until cout) {
                    // adding imageuri in array
                    val imageurl = data.clipData!!.getItemAt(i).uri
                    adapter.addImage(imageurl)
                }
                // setting 1st selected image into image switcher
            } else {
                val imageurl: Uri? = data.data
                imageurl?.let { adapter.addImage(it) }
            }
        } else {
            // show this if no image is selected
            Toast.makeText(context, "You haven't picked Image", Toast.LENGTH_LONG).show()

        }
    }
}

