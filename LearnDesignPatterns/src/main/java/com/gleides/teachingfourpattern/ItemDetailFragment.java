package com.gleides.teachingfourpattern;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gleides.teachingfourpattern.dummy.PatternContent;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    public PatternContent.PatternItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    private static final String URL_SINGLETON = "https://pt.wikipedia.org/wiki/Singleton";
    private static final String URL_FACTORY = "https://pt.wikipedia.org/wiki/Factory_Method";
    private static final String URL_ABSTRACT_FACTORY = "https://pt.wikipedia.org/wiki/Abstract_Factory";
    private static final String URL_BUILDER = "https://pt.wikipedia.org/wiki/Builder";
    private static final String URL_PROTOTYPE = "https://pt.wikipedia.org/wiki/Prototype";
    private WebView webView;
    private ProgressBar progressBar;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = PatternContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                String title = getString(R.string.subTile);
                appBarLayout.setTitle(title);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.item_detail, container, false);

        View view = inflater.inflate(R.layout.fragment_site_parttern, container, false);
        webView = (WebView) view.findViewById(R.id.web_view_pattern);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        setWebViewClient(webView);

        // webView.loadUrl(URL_FACTORY);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            loadPage();
            //((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.details);
        }

        return view;
    }

    private void setWebViewClient(WebView webView) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //liga o progressBar
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //desliga o progressBar
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void loadPage(){
        switch (mItem.id) {
            //carrega a pagina
            case "1":
                webView.loadUrl(URL_SINGLETON);
                break;
            case "2":
                webView.loadUrl(URL_FACTORY);
                break;
            case "3":
                webView.loadUrl(URL_ABSTRACT_FACTORY);
                break;
            case "4":
                webView.loadUrl(URL_BUILDER);
                break;
            default:
                webView.loadUrl(URL_PROTOTYPE);
                break;
        }
    }
}
