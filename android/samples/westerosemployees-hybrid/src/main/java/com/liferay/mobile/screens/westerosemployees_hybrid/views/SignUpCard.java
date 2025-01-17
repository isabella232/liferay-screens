package com.liferay.mobile.screens.westerosemployees_hybrid.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayListener;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayScreenlet;
import com.liferay.mobile.screens.westerosemployees_hybrid.R;

/**
 * @author Víctor Galán Grande
 */
public class SignUpCard extends Card implements View.OnClickListener, WebContentDisplayListener {

    private WebContentDisplayScreenlet webContentDisplayScreenlet;
    private boolean isTermsAndConditionLoaded;

    public SignUpCard(Context context) {
        super(context);
    }

    public SignUpCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SignUpCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SignUpCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public WebContent onWebContentReceived(WebContent webContent) {
        SessionContext.logout();
        return webContent;
    }

    @Override
    public boolean onUrlClicked(String url) {
        return true;
    }

    @Override
    public boolean onWebContentTouched(View view, MotionEvent event) {
        return false;
    }

    @Override
    public void error(Exception e, String userAction) {

    }

    @Override
    public void onClick(View v) {
        if (!isTermsAndConditionLoaded) {
            SessionContext.createBasicSession(getResources().getString(R.string.liferay_anonymousApiUserName),
                getResources().getString(R.string.liferay_anonymousApiPassword));
            isTermsAndConditionLoaded = true;
            webContentDisplayScreenlet.setCustomCssFile(R.raw.webcontent_westeros_terms);
            webContentDisplayScreenlet.load();
        }

        goRight();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        findViewById(R.id.terms).setOnClickListener(this);

        webContentDisplayScreenlet = findViewById(R.id.web_content_display_screenlet);
    }
}
