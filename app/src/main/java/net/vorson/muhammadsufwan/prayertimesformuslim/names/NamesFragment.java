package net.vorson.muhammadsufwan.prayertimesformuslim.names;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.vorson.muhammadsufwan.prayertimesformuslim.HomeActivity;
import net.vorson.muhammadsufwan.prayertimesformuslim.R;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.Utils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.annotations.Nullable;

public class NamesFragment extends HomeActivity.MainFragment implements SearchView.OnQueryTextListener {

    private RecyclerView mRecyclerView;
    private Adapter.Item[] mValues;
    private GridLayoutManager mLayoutManager;
    private int mCols;

    @SuppressLint("NewApi")
    private static String normalize(CharSequence str) {
        String string = Normalizer.normalize(str, Normalizer.Form.NFD);
        string = string.replaceAll("[^\\p{ASCII}]", "");
        return string.toLowerCase(Locale.ENGLISH);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.names_main, container, false);

        mRecyclerView = v.findViewById(android.R.id.list);
        DisplayMetrics dimension = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dimension);

        float w = Utils.convertPixelsToDp(dimension.widthPixels, getActivity());

        mCols = (int) (w / 300);
        mLayoutManager = new GridLayoutManager(getActivity(), mCols);

        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? mCols : 1;
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mValues = new Adapter.Item[99];

        String[] ar = getResources().getStringArray(R.array.names_ar);
        String[] name = getResources().getStringArray(R.array.names_name);
        String[] desc = getResources().getStringArray(R.array.names_desc);

        for (int i = 0; i < 99; i++) {
            Adapter.Item item = new Adapter.Item();
            item.arabic = ar[i];
            if (name.length > i) item.name = name[i];
            if (desc.length > i) item.desc = desc[i];
            mValues[i] = item;
        }
        mRecyclerView.setAdapter(new Adapter(getActivity(), mValues, mCols > 1));
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Adapter.Item> values = new ArrayList<>();
        for (Adapter.Item val : mValues) {
            if (normalize(val.toString()).contains(normalize(newText))) {
                values.add(val);
            }
        }

        mRecyclerView.setAdapter(new Adapter(getActivity(), values.toArray(new Adapter.Item[values.size()]), mCols > 1));
        return false;
    }


}
