package io.github.dotocan.raketa.domain.model.rest

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/*@Entity(tableName = "SERVER_INFO")
data class ServerInfo(
		@PrimaryKey(autoGenerate = true) val id: Long,
		@SerializedName("success") val success: Boolean,
		@SerializedName("info") @Embedded val info: Info
)

data class Info(
		@SerializedName("version") val version: String,
		@SerializedName("build") val build: Build,
		@SerializedName("commit") val commit: Commit
)

data class Build(
		@SerializedName("nodeVersion") val nodeVersion: String,
		@SerializedName("arch") val arch: String,
		@SerializedName("platform") val platform: String,
		@SerializedName("cpus") val cpus: Int
)

data class Commit(
		@SerializedName("hash") val hash: String,
		@SerializedName("date") val date: String,
		@SerializedName("author") val author: String,
		@SerializedName("subject") val subject: String,
		@SerializedName("tag") val tag: String,
		@SerializedName("branch") val branch: String
)*/