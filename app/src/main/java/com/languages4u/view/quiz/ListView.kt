package com.languages4u.view.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.languages4u.R
import com.languages4u.view.quiz.adapter.QuizAdapter
import com.languages4u.viewmodel.quiz.QuizListViewModel
import kotlinx.android.synthetic.main.fragment_list_view.*

class ListView : Fragment(), QuizAdapter.OnQuizListItemClicked {

    lateinit var navController: NavController
    lateinit var quizListViewModel : QuizListViewModel
    lateinit var mAdapter : QuizAdapter

//    Animations
    lateinit var fadeInAnim : Animation
    lateinit var fadeOutAnim : Animation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

//        Load animations
        fadeInAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fadeOutAnim = AnimationUtils.loadAnimation(context, R.anim.fade_out)

//        Init adapter and pass reference to onClick method
        mAdapter = QuizAdapter(this)

        list_view.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        quizListViewModel = ViewModelProvider(this).get(QuizListViewModel::class.java)

//        Observe for changes in list of Quizzes
        quizListViewModel.quizListModel.observe(viewLifecycleOwner, Observer { quiz ->

            list_view.startAnimation(fadeInAnim)
            list_progress.startAnimation(fadeOutAnim)

            mAdapter.quizListModels = quiz
            mAdapter.notifyDataSetChanged()
        })
    }

    override fun onItemClicked(position: Int) {

//        Quiz clicked -> navigate to Quiz details view
        val bundle = bundleOf("position" to position)
        navController.navigate(R.id.action_listView_to_detailsView, bundle)
    }
}
