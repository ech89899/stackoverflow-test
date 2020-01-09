package com.exmaple.stackoverflowtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.exmaple.stackoverflowtest.data.models.Tag
import com.exmaple.stackoverflowtest.ui.questions.QuestionsActivity
import com.exmaple.stackoverflowtest.ui.questions.QuestionsActivity.Companion.EXTRA_TAG
import com.exmaple.stackoverflowtest.ui.tags.TagsFragment
import com.exmaple.stackoverflowtest.ui.tags.TagsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var modelTags: TagsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        modelTags = ViewModelProviders.of(this)[TagsViewModel::class.java]
        modelTags.selctedTag.observe(this, Observer { tag -> switchToQuestionsScreen(tag) })

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TagsFragment.newInstance())
                .commitNow()
        }
    }

    private fun switchToQuestionsScreen(tag: Tag?) {
        if (tag == null) return
        val intent = Intent(this, QuestionsActivity::class.java).apply {
            putExtra(EXTRA_TAG, tag.name)
        }
        startActivity(intent)
        modelTags.onTagSelected(null)
    }

}
