package com.example.contactlistsuresht

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage

class PersonAdapter(options: FirebaseRecyclerOptions<Person>) :
    FirebaseRecyclerAdapter<Person, PersonAdapter.PersonViewHolder>(options) {
    class PersonViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.row_layout, parent, false)) {
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtRole: TextView = itemView.findViewById(R.id.txtRole)
        val txtMood: TextView = itemView.findViewById(R.id.txtMood)
        val imgPhoto: ImageView = itemView.findViewById(R.id.imgPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PersonViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int, model: Person) {
        holder.txtName.text = model.name
        holder.txtRole.text = model.role
        holder.txtMood.text = model.mood
        val theImage: String = model.photo

        // if it is google storage image then get the reference
        if (theImage.indexOf("gs://")>-1) {
            val storageReference = FirebaseStorage.getInstance().getReference(theImage)
            Glide.with(holder.imgPhoto.context)
                .load(storageReference)
                .into(holder.imgPhoto)
        // if normal image just display it
        } else {
            Glide.with(holder.imgPhoto.context)
                .load(theImage)
                .into(holder.imgPhoto)
        }
    }
}