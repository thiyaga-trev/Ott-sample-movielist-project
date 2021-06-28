package com.thiyaga.ottsampleproject

import android.app.SearchManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thiyaga.ottsampleproject.databinding.ActivityMainBinding
import com.thiyaga.ottsampleproject.utils.Orientation
import com.thiyaga.ottsampleproject.utils.TAG
import com.thiyaga.ottsampleproject.viewmodel.MainViewModel
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate: " )
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = this.getString(R.string.toolbar_title)
        setSupportActionBar(binding.toolbar)
        // calling the action bar
        var actionBar: ActionBar? = supportActionBar
        // Customize the back button
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        // showing the back button in action bar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setMinimumColumnCount()
        initRecycler()
        initViewModelReceiver()

    }
    private fun setMinimumColumnCount() {
        if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            mainViewModel.setOrientation(Orientation.LANDSCAPE)
        }else{
            mainViewModel.setOrientation(Orientation.PORTRAIT)
        }

    }

    private fun initRecycler() {

        binding.mrecycler1.apply {
            adapter = MovieListAdapter()
            layoutManager = GridLayoutManager(context,mainViewModel.minimumItemSize.value!!)
            if(itemDecorationCount<2) {
                addItemDecoration(mainViewModel.spaceDecorationH.value!!)
                addItemDecoration(mainViewModel.spaceDecorationV.value!!)
            }

            (adapter as MovieListAdapter).registerObserverToScrollView(layoutManager)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        val layoutManager = layoutManager as GridLayoutManager
                        val visibleItemCount = layoutManager.findLastCompletelyVisibleItemPosition() + 1
                        if (visibleItemCount == layoutManager.itemCount) {
                            //Load more data
                            mainViewModel.addNextMovieListItems()
                        }
                    }
                }
            })
        }
        (binding.mrecycler1.adapter as? MovieListAdapter)?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

    }

    private fun initViewModelReceiver() {
        mainViewModel.init()
        mainViewModel.mutableMovieList1.observe(this,{
            (binding.mrecycler1.adapter as? MovieListAdapter)?.submitList(it)
            (binding.mrecycler1.adapter as? MovieListAdapter)?.notifyDataSetChanged()
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val newOrientation: Int = newConfig.orientation
        Log.d(TAG, "onConfigurationChanged() Called" + newOrientation)
        if (newOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Do certain things when the user has switched to landscape.
            mainViewModel.setOrientation(Orientation.LANDSCAPE)
        } else {
            mainViewModel.setOrientation(Orientation.PORTRAIT)
        }

        binding.mrecycler1.apply {
        adapter = MovieListAdapter()
        layoutManager = GridLayoutManager(context, mainViewModel.minimumItemSize.value!!)
            if(itemDecorationCount<2) {
                addItemDecoration(mainViewModel.spaceDecorationH.value!!)
                addItemDecoration(mainViewModel.spaceDecorationV.value!!)
            }
        }
        (binding.mrecycler1.adapter as? MovieListAdapter)?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        (binding.mrecycler1.adapter as? MovieListAdapter)?.submitList(mainViewModel.mutableMovieList1.value)
        (binding.mrecycler1.adapter as? MovieListAdapter)?.notifyDataSetChanged()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem?.actionView as SearchView?
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.setIconifiedByDefault(true)
        val searchEditText =
            searchView!!.findViewById<View>( androidx.appcompat.R.id.search_src_text) as EditText
        searchEditText.setTextColor(ContextCompat.getColor(this, R.color.white))
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.white))
        val searchClose: ImageView = searchView.findViewById<View>(androidx.appcompat.R.id.search_close_btn) as ImageView
        searchClose.setImageResource(R.drawable.search_cancel)
        searchClose.setOnClickListener({
            Log.e(TAG, "onCreateOptionsMenu: search close" )
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                (binding.mrecycler1.adapter as? MovieListAdapter)!!.filter.filter(newText)
                return false
            }
        })
        searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                searchView.maxWidth = Int.MAX_VALUE
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }



}