package com.open8.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.open8.sdklib.AdActivityLifeCycleBroadcaster;
import com.open8.sdklib.ad.AdPresenter;
import com.open8.sdklib.ad.infeed.InFeedAdManager;
import com.open8.sdklib.ad.infeed.InFeedAdPresenter;
import com.open8.sdklib.ad.infeed.InFeedWideFitPanelView;

import java.util.HashMap;


public class InFeedAdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Integer nrOfArticles;
    private HashMap<Integer, AdPresenter> positionToPresenterMap;

    //For logging purpose only. True if the items in the recycler view are removable.
    private Boolean cellRemovable = false;

    //Enable developer to choose what type of ad to be displayed. null or empty if no area is specified
    private String area = "";

    private static final int TYPE_AD = 0;
    private static final int TYPE_CONTENT = 1;
    InFeedAdManager adManager;
    AdActivityLifeCycleBroadcaster broadcaster;

    InFeedAdAdapter(int nrOfArticles, int[] adPosition, InFeedAdManager adManager,
                    AdActivityLifeCycleBroadcaster broadcaster) {

        this.nrOfArticles = nrOfArticles;
        this.positionToPresenterMap = new HashMap<>();
        this.adManager = adManager;
        this.broadcaster = broadcaster;

        for (int pos: adPosition) {
            InFeedAdPresenter presenter = adManager.newPresenter(cellRemovable, area);
            //adManager.newPresenter(cellRemovable) if you don't wish to specify an area
            positionToPresenterMap.put(pos, presenter);
            presenter.requestAd();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_view, parent, false);
            ContentViewHolder vh = new ContentViewHolder(v);
            return vh;
        } else {
            InFeedWideFitPanelView inFeedWideFitPanelView = new InFeedWideFitPanelView(
                    parent.getContext(), null
            );

            //Customization of ad view. If not present, the default configuration will be used.
            //float fontSize = parent.getContext().getResources().getDimension(R.dimen.custom_size);
            //inFeedWideFitPanelView.customizeDescription(new O8Font(Typeface.SANS_SERIF, Color.BLUE, fontSize));
            //inFeedWideFitPanelView.customizeLandingPageButton(new O8Font(Typeface.SANS_SERIF, Color.RED, fontSize));
            //inFeedWideFitPanelView.customizeTitle(new O8Font(Typeface.SANS_SERIF, Color.GREEN, fontSize));

            AdViewHolder vh = new AdViewHolder(inFeedWideFitPanelView);
            broadcaster.registerListener(inFeedWideFitPanelView);
            return vh;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_CONTENT) {
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            TextView title = (TextView) contentViewHolder.articleView.findViewById(R.id.article_title);
            TextView description = (TextView) contentViewHolder.articleView.findViewById(R.id.article_description);
            title.setText("食材も器も贅沢！ 「星野リゾート　界 松本」の和牛づくし会席");
            description.setText("グルメ");
        } else {
            AdViewHolder adViewHolder = (AdViewHolder) holder;
            positionToPresenterMap.get(position).setOnAdAvailableListener(adViewHolder.adView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (positionToPresenterMap.containsKey(position)) {
            return TYPE_AD;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return nrOfArticles;
    }

    private static class ContentViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout articleView;
        ContentViewHolder(RelativeLayout v) {
            super(v);
            articleView = v;
        }
    }

    private static class AdViewHolder extends RecyclerView.ViewHolder {
        InFeedWideFitPanelView adView;
        AdViewHolder(InFeedWideFitPanelView v) {
            super(v);
            adView = v;
        }
    }
}

