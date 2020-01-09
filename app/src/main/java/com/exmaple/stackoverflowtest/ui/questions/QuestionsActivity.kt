package com.exmaple.stackoverflowtest.ui.questions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.exmaple.stackoverflowtest.R
import com.exmaple.stackoverflowtest.ui.tags.TagsFragment

class QuestionsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TAG = "Tag"
    }

    private lateinit var modelQuestions: QuestionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questions_activity)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(EXTRA_TAG)

        modelQuestions = ViewModelProviders.of(this)[QuestionsViewModel::class.java]
        modelQuestions.tagName = intent.getStringExtra(EXTRA_TAG)!!

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, QuestionsFragment.newInstance())
                .commitNow()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
