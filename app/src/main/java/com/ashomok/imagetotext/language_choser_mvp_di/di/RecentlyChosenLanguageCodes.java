package com.ashomok.imagetotext.language_choser_mvp_di.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by iuliia on 11/24/17.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface RecentlyChosenLanguageCodes {
}