package com.languages4u.view.quiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.languages4u.R
import com.languages4u.view.quiz.QuizListModel

class QuizAdapter() : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    var quizListModels: List<QuizListModel>? = null
    lateinit var onQuizListItemClicked : OnQuizListItemClicked

    constructor(onQuizListItemClicked: OnQuizListItemClicked) : this() {
        this.onQuizListItemClicked = onQuizListItemClicked
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.layout_single_item, parent, false)

        return QuizViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (quizListModels == null) {
            return 0
        }
        return quizListModels!!.size
    }

    override fun onBindViewHolder(holderQuiz: QuizViewHolder, position: Int) {
        holderQuiz.title.text = quizListModels!![position].name
        holderQuiz.level.text = quizListModels!![position].level

        val imageUrl = quizListModels!![position].image

        Glide.with(holderQuiz.itemView.context)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.placeholder_image)
            .into(holderQuiz.imageView)

        var quizDesc = quizListModels!![position].desc
        if (quizDesc.length > 150) {
            quizDesc = quizDesc.substring(0, 150)  + "..."
        }

        holderQuiz.desc.text = quizDesc
    }

    inner class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var imageView: ImageView = itemView.findViewById(R.id.list_image)
        var title: TextView = itemView.findViewById(R.id.list_title)
        var desc: TextView = itemView.findViewById(R.id.list_desc)
        var level: TextView = itemView.findViewById(R.id.list_difficulty)
        var btn: Button = itemView.findViewById(R.id.list_btn)

        init {
            btn.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onQuizListItemClicked.onItemClicked(adapterPosition)
        }
    }

    interface OnQuizListItemClicked {
        fun onItemClicked(position : Int)
    }

}