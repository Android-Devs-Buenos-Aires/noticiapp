package com.ezanetta.simplenews.ui.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.ezanetta.simplenews.R
import com.ezanetta.simplenews.ui.fragments.ArticlesFragment
import com.ezanetta.simplenews.utils.ARTICLES_FRAGMENT_TAG

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        setInitialFragment(savedInstanceState)
    }

    private fun setInitialFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, getNewsFragment(), ARTICLES_FRAGMENT_TAG)
                    .commit()
        }
    }

    private fun getNewsFragment() = ArticlesFragment.newInstance()

    fun changeTitle(title: String) {
        toolbar.title = title
    }
}
