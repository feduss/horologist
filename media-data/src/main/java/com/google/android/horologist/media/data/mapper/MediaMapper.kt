/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.horologist.media.data.mapper

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.google.android.horologist.media.data.ExperimentalHorologistMediaDataApi
import com.google.android.horologist.media.data.database.model.MediaEntity
import com.google.android.horologist.media.model.Media

/**
 * Functions to map models from other layers and / or packages into a [Media].
 */
@ExperimentalHorologistMediaDataApi
public class MediaMapper(
    private val mediaExtrasMapper: MediaExtrasMapper
) {

    /**
     * Maps from a [MediaItem].
     *
     * @param mediaItem [MediaItem] to be mapped.
     * @param defaultArtist value for [Media.artist] for when [MediaMetadata.artist] is null.
     */
    public fun map(
        mediaItem: MediaItem,
        defaultArtist: String = ""
    ): Media = Media(
        id = mediaItem.mediaId,
        uri = mediaItem.localConfiguration?.uri?.toString() ?: "",
        title = mediaItem.mediaMetadata.displayTitle?.toString() ?: "",
        artist = mediaItem.mediaMetadata.artist?.toString() ?: defaultArtist,
        artworkUri = mediaItem.mediaMetadata.artworkUri?.toString(),
        extras = mediaExtrasMapper.map(mediaItem)
    )

    /**
     * Maps from a [MediaEntity].
     */
    public fun map(media: MediaEntity): Media = Media(
        id = media.mediaId,
        uri = media.mediaUrl,
        title = media.title ?: "",
        artist = media.artist ?: "",
        artworkUri = media.artworkUrl
    )
}