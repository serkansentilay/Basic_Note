package com.serkan.notesapp.adp

import android.graphics.BitmapFactory
import android.graphics.Color
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.serkan.notesapp.R
import com.serkan.notesapp.ent.Notes
import kotlinx.android.synthetic.main.notes_rvitem.view.*

class NotesAdapter():RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    var listener: onItemClickListener? = null
    var arryList =ArrayList<Notes>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.notes_rvitem,parent,false)

        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemView.tvTitle.text=arryList.get(position).title
        holder.itemView.tvDesc.text=arryList.get(position).noteText
        holder.itemView.tvDateTime.text=arryList.get(position).dateTime
        if(arryList[position].color!=null){
            holder.itemView.cv1.setCardBackgroundColor(Color.parseColor(arryList[position].color))
        }else{
            holder.itemView.cv1.setCardBackgroundColor(Color.parseColor(R.color.ColorDefaultNote.toString()))
        }
        if(arryList[position].imgPath !=null){
            holder.itemView.imgNote.setImageBitmap(BitmapFactory.decodeFile(arryList[position].imgPath))
            holder.itemView.imgNote.visibility = View.VISIBLE
        }else{
            holder.itemView.imgNote.visibility = View.GONE
        }

        holder.itemView.cv1.setOnClickListener {
            listener!!.onClicked(arryList[position].id!!)
        }
    }

    override fun getItemCount(): Int {
        return arryList.size
    }

    fun setData(arryNoteList:List<Notes>){
        arryList = arryNoteList as ArrayList<Notes>
    }

    fun setOnClickListener(listener1: onItemClickListener){
        listener = listener1
    }
    class NotesViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }


    interface onItemClickListener {
        fun onClicked(noteId:Int)
    }


}