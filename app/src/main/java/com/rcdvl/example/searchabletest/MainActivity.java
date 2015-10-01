package com.rcdvl.example.searchabletest;

import com.activeandroid.content.ContentProvider;
import com.rcdvl.example.searchabletest.model.Author;
import com.rcdvl.example.searchabletest.model.Book;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final String PARAM_QUERY_STRING = "query-string";
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private TextView mSearchQueryText;
    private TextView mClearSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        createBooksAndAuthors();

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSearchQueryText = (TextView)findViewById(R.id.search_query_text);
        mClearSearchButton = (TextView)findViewById(R.id.btn_clear_search);

        mClearSearchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setQueryTextVisibility(View.GONE);
                getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)MenuItemCompat
                .getActionView(menu.findItem(R.id.action_search));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            setQueryTextVisibility(View.VISIBLE);

            CharSequence text = getString(R.string.search_query_text);
            SpannableString spannablecontent = new SpannableString(text + query);
            spannablecontent
                    .setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), text.length(),
                            spannablecontent.length(), 0);
            mSearchQueryText.setText(spannablecontent);

            Bundle args = new Bundle();
            args.putString(PARAM_QUERY_STRING, query);

            getSupportLoaderManager().restartLoader(0, args, this);
        }

    }

    private void setQueryTextVisibility(int visibility) {
        mSearchQueryText.setVisibility(visibility);
        mClearSearchButton.setVisibility(visibility);

        if (visibility == View.GONE) {
            getSupportActionBar().collapseActionView();
        }
    }

    private void createBooksAndAuthors() {
        Author author = new Author();
        author.firstName = "William";
        author.lastName = "Shakespeare";
        author.save();

        Book book = new Book();
        book.name = "Hamlet";
        book.author = author;
        book.save();

        book = new Book();
        book.name = "King Lear";
        book.author = author;
        book.save();

        book = new Book();
        book.name = "MacBeth";
        book.author = author;
        book.save();

        author = new Author();
        author.firstName = "Jules";
        author.lastName = "Verne";
        author.save();

        book = new Book();
        book.name = "Journey to the Center of the Earth";
        book.author = author;
        book.save();

        book = new Book();
        book.name = "20,000 Leagues Under the Sea";
        book.author = author;
        book.save();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if (bundle != null && bundle.containsKey(PARAM_QUERY_STRING)) {
            String where = "name like '%" + bundle.getString(PARAM_QUERY_STRING) + "%'";
            return new CursorLoader(MainActivity.this, ContentProvider.createUri(Book.class, null),
                    null, where, null, null);
        }

        return new CursorLoader(MainActivity.this, ContentProvider.createUri(Book.class, null),
                null, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter = new MyAdapter(MainActivity.this, cursor);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
