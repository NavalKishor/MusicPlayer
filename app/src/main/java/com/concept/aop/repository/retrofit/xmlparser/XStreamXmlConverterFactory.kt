/*
 * Copyright (c) 2022, CTL and/or its affiliates. All rights reserved.
 * Created by nkjha on 2022-08-05.
 */package com.concept.aop.repository.retrofit.xmlparser

import com.thoughtworks.xstream.XStream
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.OutputStreamWriter
import java.lang.reflect.Type


class XStreamXmlConverterFactory private constructor(val xStream: XStream) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {
        if (type !is Class<*>) {
            return null
        }
        val cls = type as Class<*>?
        return XStreamXmlResponseBodyConverter<Any>(cls, xStream)
    }

    override fun requestBodyConverter(
        type: Type?,
        parameterAnnotations: Array<Annotation?>?,
        methodAnnotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<*, RequestBody>? {
        return if (type !is Class<*>) {
            null
        } else XStreamXmlRequestBodyConverter<Any>(xStream)
    }

    companion object {
        /** Create an instance using `xStream` for conversion.  */
        /** Create an instance using a default [com.thoughtworks.xstream.XStream] instance for conversion.  */
        @JvmOverloads
        fun create(xStream: XStream = XStream()): XStreamXmlConverterFactory {
            return XStreamXmlConverterFactory(xStream)
        }
    }

}

class XStreamXmlResponseBodyConverter <T>(private val cls: Class<*>?, private val xStream: XStream) : Converter<ResponseBody, T>{

    @Throws(Exception::class)
    override fun convert(value: ResponseBody): T {
       /* return try {
            xStream.processAnnotations(cls)
            val `object` = xStream.fromXML(value.byteStream())
            `object` as T
        }
        finally {
            value.close()
        }*/
        return value.use { value ->
            xStream.processAnnotations(cls)
            val `object` = xStream.fromXML(value.byteStream())
            `object` as T
        }
    }

}
class XStreamXmlRequestBodyConverter<T>(private val xStream: XStream) :
    Converter<T, RequestBody> {
    @Throws(Exception::class)
    override fun convert(value: T): RequestBody {
        val buffer = Buffer()
        try {
            val osw = OutputStreamWriter(buffer.outputStream(), CHARSET)
            xStream.toXML(value, osw)
            osw.flush()
        } catch (e: java.lang.Exception) {
            throw RuntimeException(e)
        }
        return buffer.readByteString().toRequestBody(MEDIA_TYPE)
    }

    companion object {
        private val MEDIA_TYPE: MediaType? = "application/xml; charset=UTF-8".toMediaTypeOrNull()
        private const val CHARSET = "UTF-8"
    }
}