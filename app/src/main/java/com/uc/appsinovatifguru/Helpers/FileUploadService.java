package com.uc.appsinovatifguru.Helpers;

import com.android.volley.Response;
import com.uc.appsinovatifguru.Model.FileUpload;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileUploadService {
    @POST("uploadFile")
    @FormUrlEncoded
    Call<FileUpload> uploadFile(
            @Field("file") String base64,
            @Field("type") String type
    );
}
