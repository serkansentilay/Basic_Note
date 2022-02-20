package com.serkan.notesapp

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import com.serkan.notesapp.db.DataBaseNotes
import com.serkan.notesapp.ent.Notes
import com.serkan.notesapp.util.NoteBttmSheetFragment
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.notes_rvitem.view.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : BaseFragment(),EasyPermissions.PermissionCallbacks,EasyPermissions.RationaleCallbacks {
    var currentDate :String?=null
    var selectedColor = "#171C26"
    private var READ_STORAGE_PERM = 123
    private var REQUEST_CODE_IMAGE = 456
    private var SELECTED_IMAGE_PATH = ""
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteId = requireArguments().getInt("noteId",-1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(noteId != -1){
            launch {
                context?.let {
                    var notes = DataBaseNotes.getDatabase(it).noteDao().getIdNotes(noteId)
                    viewLine.setBackgroundColor(Color.parseColor(notes.color))
                    NoteTitle.setText(notes.title)
                    SubTitle.setText(notes.subTitle)
                    NoteDesc.setText(notes.noteText)
                    if(notes.imgPath !=""){
                        SELECTED_IMAGE_PATH = notes.imgPath!!
                        imgNote.setImageBitmap(BitmapFactory.decodeFile(notes.imgPath))
                        relativeimg.visibility = View.VISIBLE
                        imgNote.visibility = View.VISIBLE
                        imgDel.visibility = View.VISIBLE
                    }else{
                        relativeimg.visibility = View.GONE
                        imgNote.visibility = View.GONE
                        imgDel.visibility = View.GONE
                    }
                }
            }

        }

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            BroadcastReceiver, IntentFilter("bottom_sheet_action")
        )



        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())

        NoteDatetime.text = currentDate
        viewLine.setBackgroundColor(Color.parseColor(selectedColor))

        imgDone.setOnClickListener {
            if(noteId != -1){
                updateNote()
            }else{
                saveNote()
            }

        }

        imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        imgMore.setOnClickListener {
            var noteBttmSheetFragment = NoteBttmSheetFragment.newInstane(noteId)
            noteBttmSheetFragment.show(requireActivity().supportFragmentManager,"Note Bottom Sheet Fragment")
        }

        imgDel.setOnClickListener {
            SELECTED_IMAGE_PATH = ""
            relativeimg.visibility = View.GONE
        }


    }

    private fun updateNote(){
        launch {

            context?.let {
                var notes = DataBaseNotes.getDatabase(it).noteDao().getIdNotes(noteId)
                notes.title=NoteTitle.text.toString()
                notes.subTitle=SubTitle.text.toString()
                notes.noteText=NoteDesc.text.toString()
                notes.dateTime=currentDate
                notes.color = selectedColor
                notes.imgPath=SELECTED_IMAGE_PATH

                DataBaseNotes.getDatabase(it).noteDao().updateNote(notes)
                NoteTitle.setText("")
                SubTitle.setText("")
                NoteDesc.setText("")
                imgNote.visibility = View.GONE
                relativeimg.visibility = View.GONE
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }


    private fun saveNote(){
        if(NoteTitle.text.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Write anything to Title",Toast.LENGTH_SHORT).show()

        }
       else if(SubTitle.text.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Write anything to Sub Title",Toast.LENGTH_SHORT).show()

        }
      else if(NoteDesc.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Write anything to Description", Toast.LENGTH_SHORT)
                .show()
        }

    else {
            launch {
                var notes = Notes()
                notes.title=NoteTitle.text.toString()
                notes.subTitle=SubTitle.text.toString()
                notes.noteText=NoteDesc.text.toString()
                notes.dateTime=currentDate
                notes.color = selectedColor
                notes.imgPath=SELECTED_IMAGE_PATH
                context?.let {
                    DataBaseNotes.getDatabase(it).noteDao().insertNotes(notes)
                    NoteTitle.setText("")
                    SubTitle.setText("")
                    NoteDesc.setText("")
                    imgNote.visibility = View.GONE
                    relativeimg.visibility = View.GONE
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }

    }

    private val BroadcastReceiver :BroadcastReceiver=object:BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
                var actionColor = p1!!.getStringExtra("action")
                when (actionColor!!){
                    "Blue" -> {
                            selectedColor = p1.getStringExtra("selectedColor")!!
                            viewLine!!.setBackgroundColor(Color.parseColor(selectedColor))

                    }
                    "Yellow" -> {
                        selectedColor = p1.getStringExtra("selectedColor")!!
                        viewLine!!.setBackgroundColor(Color.parseColor(selectedColor))
                    }
                    "White" -> {
                        selectedColor = p1.getStringExtra("selectedColor")!!
                        viewLine!!.setBackgroundColor(Color.parseColor(selectedColor))
                    }
                    "Purple" -> {
                        selectedColor = p1.getStringExtra("selectedColor")!!
                        viewLine!!.setBackgroundColor(Color.parseColor(selectedColor))
                    }
                    "Green" -> {
                        selectedColor = p1.getStringExtra("selectedColor")!!
                        viewLine!!.setBackgroundColor(Color.parseColor(selectedColor))
                    }
                    "Orange" -> {
                        selectedColor = p1.getStringExtra("selectedColor")!!
                        viewLine!!.setBackgroundColor(Color.parseColor(selectedColor))}

                    "Black" -> {
                            selectedColor = p1.getStringExtra("selectedColor")!!
                        viewLine!!.setBackgroundColor(Color.parseColor(selectedColor))
                    }

                    "Image" -> {
                            readStorageTask()

                    }
                    "DelNote" -> {
                        deleteNote()
                    }

                    else -> {
                        imgNote.visibility = View.GONE
                        relativeimg.visibility = View.GONE
                        selectedColor = p1.getStringExtra("selectedColor")!!
                        viewLine!!.setBackgroundColor(Color.parseColor(selectedColor))
                    }
                }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)

    }

    private fun hasReadStoragePerm():Boolean{
        return EasyPermissions.hasPermissions(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
    }

   // private fun hasWriteStoragePerm():Boolean{
   //     return EasyPermissions.hasPermissions(requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
   // }

    private fun readStorageTask(){
        if(hasReadStoragePerm()){
            pickImageFromGallery()
        }
        else{
            EasyPermissions.requestPermissions(requireActivity(),getString(R.string.storage_permission_text),
                READ_STORAGE_PERM, Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

    private fun pickImageFromGallery(){
        var intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if(intent.resolveActivity(requireActivity().packageManager)!=null){
            startActivityForResult(intent,REQUEST_CODE_IMAGE)
        }
    }

    private fun getPathFromUri(contentUri: Uri):String?{
        var filePath:String? = null
        var cursor = requireActivity().contentResolver.query(contentUri,null,null,null,null)
        if(cursor == null){
            filePath = contentUri.path
        }else{
            cursor.moveToFirst()
            var index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath
    }


    private fun deleteNote(){
        launch {
            context?.let {
                DataBaseNotes.getDatabase(it).noteDao().deleteIdNote(noteId)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK){
            if(data!=null){
                var selectedImageUrl = data.data
                if(selectedImageUrl !=null){
                    try{
                        var inputStream = requireActivity().contentResolver.openInputStream(selectedImageUrl)
                        var bitmap = BitmapFactory.decodeStream(inputStream)
                        imgNote.setImageBitmap(bitmap)
                        imgNote.visibility = View.VISIBLE
                        relativeimg.visibility = View.VISIBLE

                        SELECTED_IMAGE_PATH=getPathFromUri(selectedImageUrl)!!

                    }catch (e:Exception) {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,requireActivity())
    }


    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(requireActivity(),perms)){
            AppSettingsDialog.Builder(requireActivity()).build().show()
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {

    }

    override fun onRationaleDenied(requestCode: Int) {

    }



}