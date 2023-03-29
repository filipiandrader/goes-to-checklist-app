package com.far.goestochecklist.domain.model

import android.os.Parcel
import android.os.Parcelable

/*
 * Created by Filipi Andrade Rocha on 23/03/2023.
 */

data class Film(
	var id: Int,
	var year: String,
	var filmId: String,
	var name: String,
	var description: String,
	var releaseDate: String,
	var posterImage: String,
	var whereToWatch: List<String>,
	var duration: String,
	var category: List<String>,
	var watched: Boolean = false
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readInt(),
		parcel.readString().orEmpty(),
		parcel.readString().orEmpty(),
		parcel.readString().orEmpty(),
		parcel.readString().orEmpty(),
		parcel.readString().orEmpty(),
		parcel.readString().orEmpty(),
		parcel.createStringArrayList().orEmpty(),
		parcel.readString().orEmpty(),
		parcel.createStringArrayList().orEmpty(),
		parcel.readByte() != 0.toByte()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeInt(id)
		parcel.writeString(year)
		parcel.writeString(filmId)
		parcel.writeString(name)
		parcel.writeString(description)
		parcel.writeString(releaseDate)
		parcel.writeString(posterImage)
		parcel.writeStringList(whereToWatch)
		parcel.writeString(duration)
		parcel.writeStringList(category)
		parcel.writeByte(if (watched) 1 else 0)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Film> {
		override fun createFromParcel(parcel: Parcel): Film {
			return Film(parcel)
		}

		override fun newArray(size: Int): Array<Film?> {
			return arrayOfNulls(size)
		}
	}
}
