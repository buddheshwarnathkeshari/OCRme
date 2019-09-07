package com.ashomok.ocrme.crop_image;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ashomok.ocrme.crop_image.tab_fragments.simple.SimpleCropFragment;
import com.ashomok.ocrme.ocr.ocr_task.OcrResult;
import com.ashomok.ocrme.ocr_result.tab_fragments.image_pdf.ImagePdfFragment;
import com.ashomok.ocrme.ocr_result.tab_fragments.searchable_pdf.SearchablePdfFragment;
import com.ashomok.ocrme.ocr_result.tab_fragments.text.TextFragment;

import java.util.ArrayList;
import java.util.List;

import static com.ashomok.ocrme.crop_image.CropImageActivity.EXTRA_IMAGE_URI;
import static com.ashomok.ocrme.ocr_result.tab_fragments.text.TextFragment.EXTRA_IMAGE_URL;
import static com.ashomok.ocrme.ocr_result.tab_fragments.text.TextFragment.EXTRA_LANGUAGES;
import static com.ashomok.ocrme.ocr_result.tab_fragments.text.TextFragment.EXTRA_TEXT;
import static com.ashomok.ocrme.utils.LogUtil.DEV_TAG;

/**
 * Created by iuliia on 5/31/17.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private static int ITEM_COUNT = 2;
    private Uri imageUri;

    public static final String TAG = DEV_TAG + MyPagerAdapter.class.getSimpleName();


    MyPagerAdapter(FragmentManager fm, Uri imageUri) {
        super(fm);
        this.imageUri = imageUri;
    }

    @Override
    @Nullable
    public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return initSimpleCropFragment(imageUri);
                case 1:
                    return initSimpleCropFragment(imageUri); //todo
//                    return initAdvancedCropFragment();
                default:
                    return null;
            }
    }

//    private AdvancedCropFragment initAdvancedCropFragment() {
//        AdvancedCropFragment fragment = new AdvancedCropFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(SearchablePdfFragment.EXTRA_PDF_GS_URL, ocrResult.getPdfResultGsUrl());
//        bundle.putString(SearchablePdfFragment.EXTRA_PDF_MEDIA_URL, ocrResult.getPdfResultMediaUrl());
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    private SimpleCropFragment initSimpleCropFragment(Uri imageUri) {

        SimpleCropFragment fragment = new SimpleCropFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_IMAGE_URI, imageUri);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }
}