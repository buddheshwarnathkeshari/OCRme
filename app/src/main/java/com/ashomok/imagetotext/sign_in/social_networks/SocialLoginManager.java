package com.ashomok.imagetotext.sign_in.social_networks;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IntDef;

import com.ashomok.imagetotext.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import static com.ashomok.imagetotext.utils.LogUtil.DEV_TAG;


/**
 * Created by iuliia on 8/4/17.
 */

public class SocialLoginManager {

    private Context context;

    private boolean isSignedIn;

    //user email
    private String mSignedAs;

    private ArrayList<LoginProcessor> loginsInUseArray = new ArrayList<>();

    private static final String TAG = DEV_TAG + SocialLoginManager.class.getSimpleName();

    //[START define login mode]
    @IntDef({LOGIN_MODE_UNDEFINED, LOGIN_MODE_STANDARD, LOGIN_MODE_GOOGLE_PLUS, LOGIN_MODE_FACEBOOK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoginMode {
    }

    public static final int LOGIN_MODE_UNDEFINED = -1;
    public static final int LOGIN_MODE_STANDARD = 0;
    public static final int LOGIN_MODE_GOOGLE_PLUS = 1;
    public static final int LOGIN_MODE_FACEBOOK = 2;

    @LoginMode
    public int mLoginMode = LOGIN_MODE_UNDEFINED;

    @LoginMode
    public int getLoginMode() {
        return mLoginMode;
    }

    @LoginMode
    public int getLastLoginMode() {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        @LoginMode int defaultValue = LOGIN_MODE_GOOGLE_PLUS;
        @LoginMode int lastLoginMode = sharedPref.getInt(context.getString(R.string.last_login_mode), defaultValue);
        return lastLoginMode;
    }

    private void saveLoginMode() {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(context.getString(R.string.last_login_mode), mLoginMode);
        editor.apply();
    }
    //[END define login mode]

    /**
     * user login will store on the device.
     */
    private void saveUserEmail() {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.user_email), mSignedAs);
        editor.apply();
    }

    public SocialLoginManager(Context context) {
        this.context = context;
    }

    public void addLogin(LoginProcessor loginProcessor) {
        loginsInUseArray.add(loginProcessor);
    }

    /**
     * try sign in without user interaction using previous sign in data
     *
     * @return is sign in
     */
    public boolean trySignInAutomatically() {
        isSignedIn = false;
        for (LoginProcessor loginProcessor : loginsInUseArray) {
            loginProcessor.trySignIn();
        }

        //checking if user already signed in
        for (LoginProcessor loginProcessor : loginsInUseArray) {
            if (loginProcessor.isSignedIn()) {
                isSignedIn = true;
                @LoginMode int loginMode = getLoginMode(loginProcessor);
                @LoginMode int mLastLoginMode = getLastLoginMode();
                if (loginMode == mLastLoginMode || mLoginMode == LOGIN_MODE_UNDEFINED) {
                    mLoginMode = loginMode;
                    mSignedAs = getEmail(loginProcessor);
                }

            }
        }
        return isSignedIn;
    }


    private String getEmail(LoginProcessor loginProcessor) {
        return loginProcessor.getEmail();
    }

    @LoginMode
    private int getLoginMode(LoginProcessor loginProcessor) {
        if (loginProcessor instanceof LoginProcessorFacebook) {
            return LOGIN_MODE_FACEBOOK;
        } else if (loginProcessor instanceof LoginProcessorGoogle) {
            return LOGIN_MODE_GOOGLE_PLUS;
        } else {
            return LOGIN_MODE_UNDEFINED;
        }
    }
}
