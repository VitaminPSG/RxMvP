package com.android.rxmvp.data.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.rxmvp.domain.models.Version

@Keep
@Entity
data class VersionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "updatedTime") override val lastUpdatedTime: Long,
) : Version {

    constructor(lastUpdatedTime: Long) : this(0, lastUpdatedTime)
}