package com.coolcode.jittranslate.ui.study;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.coolcode.jittranslate.R;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

@Layout(R.layout.card_view)
public class Card {

    @View(R.id.word)
    private TextView word;

    @View(R.id.translate)
    private TextView translate;

    @View(R.id.language)
    private TextView language;

    private Word mWord;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public Card(Context context, Word word, SwipePlaceHolderView swipeView) {
        mContext = context;
        mWord = word;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved(){
        word.setText(mWord.getWord());
        translate.setText(mWord.getTranslate());
        language.setText(mWord.getLanguage());
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }
}
