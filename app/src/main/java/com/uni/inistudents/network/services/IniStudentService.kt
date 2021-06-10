package com.uni.inistudents.network.services

import com.uni.inistudents.model.FirebaseTokenBody
import com.uni.inistudents.model.Result
import com.uni.inistudents.model.StudentAuth
import com.uni.inistudents.model.UniversitiesDto
import io.reactivex.Single
import retrofit2.http.*

interface IniStudentService {
    @GET("/")
    fun getUniversityList(@Query("action") action: String = "list") : Single<UniversitiesDto>

    @POST("/api/studentAuth")
    fun studentAuth(@Query("username") userName: String, @Query("password") password: String, @Query("uni") uni: String) : Single<StudentAuth>

    @POST("/api/updateFireToken")
    fun updateFirebaseToken(@Header("Authorization") authToken: String, @Body token: FirebaseTokenBody) : Single<Result>
}