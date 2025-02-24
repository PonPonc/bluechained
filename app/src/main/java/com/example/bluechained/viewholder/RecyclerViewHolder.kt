package com.example.bluechained.viewholder

import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bluechained.R

class RecyclerViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txtIndex: TextView = itemView.findViewById(R.id.txtIndex)
    val txtHash: TextView = itemView.findViewById(R.id.txtHash)
    val txtPreviousHash: TextView = itemView.findViewById(R.id.txtPreviousHash)
    val txtTimestamp: TextView = itemView.findViewById(R.id.txtTimestamp)
}
