package com.ezanetta.simplenews.ui.fragments


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.Toast
import com.ezanetta.simplenews.R
import com.ezanetta.simplenews.domain.model.Article
import com.ezanetta.simplenews.domain.model.Source
import com.ezanetta.simplenews.domain.responses.ArticlesResponse
import com.ezanetta.simplenews.networking.NewsApiClient
import com.ezanetta.simplenews.ui.activities.MainActivity
import com.ezanetta.simplenews.ui.adapters.ArticlesAdapter
import com.ezanetta.simplenews.ui.adapters.ArticlesItemDecoration
import com.ezanetta.simplenews.utils.ARTICLES_KEY
import com.ezanetta.simplenews.utils.CURRENT_SOURCE_KEY
import com.ezanetta.simplenews.utils.LIST_STATE_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ArticlesFragment :
        Fragment(),
        Callback<ArticlesResponse>,
        SourcesFragment.SourcesActions,
        ArticlesAdapter.OnArticlesAction {

    private val TAG: String = ArticlesFragment::class.java.simpleName
    private lateinit var news: RecyclerView
    private lateinit var loading: ProgressBar
    private lateinit var mainActivity: MainActivity
    private lateinit var articlesAdapter: ArticlesAdapter
    private val articles: MutableList<Article> = ArrayList()
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(activity) }
    private var currentSource: Source = Source.getDefaultSource()
    private var listState: Parcelable? = null

    override fun onResponse(call: Call<ArticlesResponse>, response: Response<ArticlesResponse>) {
        if (response.isSuccessful) {
            articles.addAll(response.body()!!.articles)
            setupNews(articles)
        }
    }

    override fun onFailure(call: Call<ArticlesResponse>, t: Throwable?) {
        Log.d(TAG, "Error ${t?.localizedMessage}")
    }

    override fun onSourceChange(source: Source) {
        currentSource = source
        mainActivity.changeTitle(source.name)
        articlesAdapter.cleanArticles()
        showLoading()
        resetState()
        getNews(source.id)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_articles, container, false)
        setHasOptionsMenu(true)

        with(view) {
            news = findViewById(R.id.news)
            loading = findViewById(R.id.progress)
        }

        setupNewsRecyclerView()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null && savedInstanceState.containsKey(ARTICLES_KEY)) {
            with(savedInstanceState) {
                articles.addAll(getParcelableArrayList(ARTICLES_KEY))
                currentSource = getParcelable(CURRENT_SOURCE_KEY)
                listState = getParcelable(LIST_STATE_KEY)
            }
            setupNews(articles)
        } else {
            getNews(currentSource.name)
        }

        mainActivity = (activity as MainActivity).apply {
            changeTitle(currentSource.name)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if (isAdded && outState != null && articles.isNotEmpty()) {
            listState = linearLayoutManager.onSaveInstanceState()
            with(outState) {
                putParcelable(LIST_STATE_KEY, listState)
                putParcelableArrayList(ARTICLES_KEY, articles as ArrayList<out Parcelable>)
                putParcelable(CURRENT_SOURCE_KEY, currentSource)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_news, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.source -> openNewsSources()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openNewsSources() {
        SourcesFragment().apply {
            show(mainActivity.supportFragmentManager, SourcesFragment::class.java.simpleName)
            setTargetFragment(this@ArticlesFragment, 1)
            setSourcesActions(this@ArticlesFragment)
        }
    }

    private fun getNews(source: String) {
        NewsApiClient.newsApi.getNews(source).enqueue(this)
    }

    private fun setupNewsRecyclerView() {
        with(news) {
            layoutManager = linearLayoutManager
            val space = activity.resources.getDimensionPixelOffset(R.dimen.mid_size)
            news.addItemDecoration(ArticlesItemDecoration(space))
        }
    }

    private fun setupAdapter() {
        articlesAdapter = ArticlesAdapter(this)
        restorePosition()
    }

    override fun onClickArticle(article: Article) = openBrowser(article.url)

    private fun restorePosition() = linearLayoutManager.onRestoreInstanceState(listState)

    private fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        try {
            startActivity(intent)
        } catch (exception: ActivityNotFoundException) {
            Toast.makeText(mainActivity, getString(R.string.no_browser_message),
                    Toast.LENGTH_LONG).show()
        }
    }

    private fun setupNews(articles: List<Article>) {
        setupAdapter()
        articlesAdapter.loadArticles(articles)
        news.adapter = articlesAdapter
        hideLoading()
    }

    private fun hideLoading() {
        loading.visibility = GONE
    }

    private fun showLoading() {
        loading.visibility = VISIBLE
    }

    private fun resetState() {
        articles.clear()
        listState = null
    }

    companion object {
        fun newInstance(): ArticlesFragment {
            return ArticlesFragment()
        }
    }
}
