package com.open8.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open8.sdklib.AdActivityLifeCycleBroadcaster;
import com.open8.sdklib.ad.AdPresenter;
import com.open8.sdklib.ad.infeed.InFeedAdManager;

import java.util.HashMap;

public class InFeedAdInRecyclerViewFragment extends Fragment {
    private RecyclerView recylclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;
    AdActivityLifeCycleBroadcaster broadcaster;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcaster = new AdActivityLifeCycleBroadcaster();
        InFeedAdManager adManager = new InFeedAdManager();
        broadcaster.registerListener(adManager);

        View rootView = inflater.inflate(R.layout.infeed_recycler_view, container, false);
        recylclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        recylclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(getActivity());
        recylclerView.setLayoutManager(layoutManager);


        int nrOfArticles = 18;
        int[] adPositions = {6};

        adapter = new InFeedAdAdapter(nrOfArticles, adPositions, adManager, broadcaster);

        //For the current version of SDK, we don't support view recycling for ad view
        recylclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recylclerView.getContext(), layoutManager.getOrientation()
        );
        recylclerView.addItemDecoration(dividerItemDecoration);
        recylclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        broadcaster.onActivityPaused();
    }

    @Override
    public void onResume() {
        super.onResume();
        broadcaster.onActivityResumed();
    }

}

