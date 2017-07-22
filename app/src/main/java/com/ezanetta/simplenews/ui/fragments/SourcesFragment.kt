package com.ezanetta.simplenews.ui.fragments


import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.ezanetta.simplenews.R
import com.ezanetta.simplenews.domain.model.Source
import com.ezanetta.simplenews.domain.responses.SourcesResponse
import com.ezanetta.simplenews.networking.NewsApiClient
import com.ezanetta.simplenews.ui.adapters.SourcesAdapter
import com.ezanetta.simplenews.utils.LIST_STATE_KEY
import com.ezanetta.simplenews.utils.SOURCES_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class SourcesFragment : DialogFragment(), Callback<SourcesResponse> {

    private lateinit var sources: RecyclerView
    private lateinit var loading: ProgressBar
    private val TAG: String = SourcesFragment::class.java.simpleName
    private lateinit var sourcesActions: SourcesActions
    private var listState: Parcelable? = null
    private val sourcesList: MutableList<Source> = ArrayList()
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(activity) }

    interface SourcesActions {
        fun onSourceChange(source: Source)
    }

    fun setSourcesActions(sourcesActions: SourcesActions) {
        this.sourcesActions = sourcesActions
    }

    override fun onResponse(call: Call<SourcesResponse>?, response: Response<SourcesResponse>) {
        if (response.isSuccessful) {
            sourcesList.addAll(response.body()!!.sources)
            setupSources(sourcesList)
        }
    }

    override fun onFailure(call: Call<SourcesResponse>?, t: Throwable) {
        Log.d(TAG, "Error ${t.localizedMessage}")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_sources, container, false)

        with(view) {
            sources = findViewById(R.id.sources) as RecyclerView
            loading = findViewById(R.id.progress) as ProgressBar
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null && savedInstanceState.containsKey(SOURCES_KEY)) {
            with(savedInstanceState) {
                sourcesList.addAll(getParcelableArrayList(SOURCES_KEY))
                listState = getParcelable(LIST_STATE_KEY)
            }
            setupSources(sourcesList)
            setSourcesActions(targetFragment as ArticlesFragment)
        } else {
            getSources()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if (isAdded && outState != null) {
            with(outState) {
                putParcelable(LIST_STATE_KEY, listState)
                putParcelableArrayList(SOURCES_KEY, sourcesList as ArrayList<out Parcelable>)
            }
        }
    }

    private fun getSources() = NewsApiClient.newsApi.getSources().enqueue(this)

    private fun setupSources(sourcesList: List<Source>) {
        val sourcesAdapter = SourcesAdapter(sourcesList, { sourceListener(it) })

        with(sources) {
            layoutManager = linearLayoutManager
            adapter = sourcesAdapter
        }

        restorePosition()
        hideLoading()
    }

    private fun restorePosition() = linearLayoutManager.onRestoreInstanceState(listState)

    private fun sourceListener(source: Source) {
        sourcesActions.onSourceChange(source)
        dismiss()
    }

    private fun hideLoading() {
        loading.visibility = View.GONE
    }
}
