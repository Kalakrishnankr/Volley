package com.beachpartnerllc.beachpartner.utils;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by seq-kala on 8/5/18.
 */

public interface ServiceClass {
    @Multipart
   @POST("storage/uploadProfileData")
    Call<UploadObject> uploadFile(@Part MultipartBody file);
   // @Multipart
   // @POST("/storage/uploadProfileData")
   // Call<UploadObject> uploadSingleFile(@Part("userId") RequestBody name);
    //Call<UploadObject> uploadMultiFile(@Part String file1, @Part String file2, @Part String file3);

    @Multipart
    @POST("storage/uploadProfileData")
    Call<UploadObject> uploadMultiFile(@Part MultipartBody.Part file, @Part MultipartBody.Part file1, @Part("userId") RequestBody name);

}
