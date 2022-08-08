package com.concept.aop.repository.retrofit



import com.concept.aop.repository.retrofit.xmlparser.XStreamXmlConverterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import com.thoughtworks.xstream.mapper.CannotResolveClassException
import com.thoughtworks.xstream.mapper.MapperWrapper
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET


interface WebService {

    @GET("WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml")
    suspend fun getFeed(): Feed

    @GET("WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml")
    fun getFeedAsync(): Deferred<Response<Feed>>

    companion object {

        var BASE_URL = "http://ax.itunes.apple.com/"

        fun provideXStream():XStream = object : XStream(DomDriver()) {
            override fun wrapMapper(next: MapperWrapper): MapperWrapper {
                return object : MapperWrapper(next) {
                    override fun shouldSerializeMember(
                        definedIn: Class<*>,
                        fieldName: String
                    ): Boolean {
                        return try {
                             definedIn != Any::class.java || realClass(fieldName) != null
                           /*return if (definedIn === Any::class.java) {
                                false
                            } else */
                            super.shouldSerializeMember(definedIn, fieldName)
                        } catch (cnrce: Exception  ) {
                            false
                        }
                    }
                }
            }
        }
        fun create(okHttpClient:OkHttpClient,xStreamXmlConverterFactory:XStreamXmlConverterFactory) : WebService {

            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
//                .addConverterFactory(JaxbConverterFactory.create())
                .addConverterFactory(xStreamXmlConverterFactory)
//                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(WebService::class.java)

        }
    }
}