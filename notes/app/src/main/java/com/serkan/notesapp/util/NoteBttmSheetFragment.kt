package com.serkan.notesapp.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.serkan.notesapp.R
import kotlinx.android.synthetic.main.fragment_notes_bttmsheet.*

class NoteBttmSheetFragment:BottomSheetDialogFragment() {
    var selectedColor = "#171C26"


    companion object{
        var noteId=-1
        fun newInstane(id:Int):NoteBttmSheetFragment{
            val args = Bundle()
            val fragment = NoteBttmSheetFragment()
            fragment.arguments = args
            noteId=id
            return fragment
        }
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_notes_bttmsheet,null)
        dialog.setContentView(view)

        val param =(view.parent as View).layoutParams as CoordinatorLayout.LayoutParams

        val behavior = param.behavior
        if(behavior is BottomSheetBehavior<*>){
            behavior.setBottomSheetCallback(object:BottomSheetBehavior.BottomSheetCallback(){
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    var state = ""
                    when(newState){
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            state="COLLAPSED"
                        }
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            state="DRAGGING"
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            state="EXPANDED"
                        }
                        BottomSheetBehavior.STATE_HIDDEN-> {
                            state="HIDDEN"
                            dismiss()
                            behavior.state=BottomSheetBehavior.STATE_COLLAPSED
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                            state="SETTLING"
                        }


                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    TODO("Not yet implemented")
                }

            })
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes_bttmsheet,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(noteId !=-1){
            layoutDelNote.visibility = View.VISIBLE
        }else{
            layoutDelNote.visibility = View.GONE
        }
        setListener()
    }

    private fun setListener(){

        fnote1.setOnClickListener {
            img1.setImageResource(R.drawable.ic_check)
            img2.setImageResource(0)
            img3.setImageResource(0)
            img4.setImageResource(0)
            img5.setImageResource(0)
            img6.setImageResource(0)
            img7.setImageResource(0)
            selectedColor = "#4e33ff"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","Blue")
            intent.putExtra("selectedColor",selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }
        fnote2.setOnClickListener {
            img1.setImageResource(0)
            img2.setImageResource(R.drawable.ic_check)
            img3.setImageResource(0)
            img4.setImageResource(0)
            img5.setImageResource(0)
            img6.setImageResource(0)
            img7.setImageResource(0)
            selectedColor = "#ffd633"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","Yellow")
            intent.putExtra("selectedColor",selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }
        fnote3.setOnClickListener {
            img1.setImageResource(0)
            img2.setImageResource(0)
            img3.setImageResource(R.drawable.ic_check)
            img4.setImageResource(0)
            img5.setImageResource(0)
            img6.setImageResource(0)
            img7.setImageResource(0)
            selectedColor = "#ffffff"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","White")
            intent.putExtra("selectedColor",selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }
        fnote4.setOnClickListener {
            img1.setImageResource(0)
            img2.setImageResource(0)
            img3.setImageResource(0)
            img4.setImageResource(R.drawable.ic_check)
            img5.setImageResource(0)
            img6.setImageResource(0)
            img7.setImageResource(0)
            selectedColor = "#ae3b76"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","Purple")
            intent.putExtra("selectedColor",selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }
        fnote5.setOnClickListener {
            img1.setImageResource(0)
            img2.setImageResource(0)
            img3.setImageResource(0)
            img4.setImageResource(0)
            img5.setImageResource(R.drawable.ic_check)
            img6.setImageResource(0)
            img7.setImageResource(0)
            selectedColor = "#0aebaf"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","Green")
            intent.putExtra("selectedColor",selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }
        fnote6.setOnClickListener {
            img1.setImageResource(0)
            img2.setImageResource(0)
            img3.setImageResource(0)
            img4.setImageResource(0)
            img5.setImageResource(0)
            img6.setImageResource(R.drawable.ic_check)
            img7.setImageResource(0)
            selectedColor = "#ff7746"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","Orange")
            intent.putExtra("selectedColor",selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }
        fnote7.setOnClickListener {
            img1.setImageResource(0)
            img2.setImageResource(0)
            img3.setImageResource(0)
            img4.setImageResource(0)
            img5.setImageResource(0)
            img6.setImageResource(0)
            img7.setImageResource(R.drawable.ic_check)
            selectedColor = "#202734"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","Black")
            intent.putExtra("selectedColor",selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }
        layoutAddImage.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","Image")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }

        layoutDelNote.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","DelNote")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }



    }


}