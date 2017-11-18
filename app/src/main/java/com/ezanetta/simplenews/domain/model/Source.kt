package com.ezanetta.simplenews.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Source(@SerializedName("id") val id: String,
                  @SerializedName("name") val name: String,
                  private @SerializedName("url") val url: String): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Source> {
        override fun createFromParcel(parcel: Parcel): Source {
            return Source(parcel)
        }

        override fun newArray(size: Int): Array<Source?> {
            return arrayOfNulls(size)
        }

        fun getDefaultSource(): Source {
            return Source("techcrunch", "TechCrunch", "http://techcrunch.com")
        }
    }

    fun getLogoImage(): String = "https://icons.better-idea.org/icon?url=$url&size=70..120..200"
}