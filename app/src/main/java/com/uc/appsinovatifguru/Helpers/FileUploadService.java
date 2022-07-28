package com.uc.appsinovatifguru.Helpers;

import com.android.volley.Response;
import com.uc.appsinovatifguru.Model.FileUpload;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

public interface FileUploadService {
    @Multipart
    @POST("uploadFile")
    Call<FileUpload> uploadFile(
            @Part
            MultipartBody.Part file
    );
}
