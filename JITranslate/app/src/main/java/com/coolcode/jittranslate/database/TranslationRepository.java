package com.coolcode.jittranslate.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coolcode.jittranslate.dbentities.TranslationApi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class TranslationRepository {
    private TranslationApi translationApi;
    private static MutableLiveData<TranslationApi.TranslationText> translationTextMutableLiveData = new MutableLiveData<>();

    private final OkHttpClient okHttpClient;
    private final static String CONNECTION = "https";
    private final static String TRANSLATIONHOST = "translate.yandex.net";
    private final static String APIKEY = "trnsl.1.1.20200525T111934Z.defb0c8747b49d53.ccd2fd863ff6852fe5e69cabf2c7345392bea066";

    public TranslationRepository() {
        okHttpClient = new OkHttpClient()
                .newBuilder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(new HttpUrl.Builder().scheme(CONNECTION)
                        .host(TRANSLATIONHOST)
                        .build())
                .client(okHttpClient)
                .build();

        translationApi = retrofit.create(TranslationApi.class);
    }

    public void translateWord(TranslationApi.TranslationText wordToTranslate) {
        translationApi.getTranslation("en-ru", APIKEY, wordToTranslate.getOriginalEncodedText())
                .enqueue(new Callback<TranslationApi.TranslationResponse>() {

                    @Override
                    public void onResponse(Call<TranslationApi.TranslationResponse> call, Response<TranslationApi.TranslationResponse> response) {
                        String word = response.body().getText().get(0);
                        Log.d("response", word);
                        wordToTranslate.setTranslatedText(word);
                        translationTextMutableLiveData.postValue(wordToTranslate);
                    }

                    @Override
                    public void onFailure(Call<TranslationApi.TranslationResponse> call, Throwable t) {
                        Log.d("response", t.getMessage());

                    }
                });
    }

    public LiveData<TranslationApi.TranslationText> getTranslationText() {
        return translationTextMutableLiveData;
    }
}