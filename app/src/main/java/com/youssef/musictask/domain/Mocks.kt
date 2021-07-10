package com.youssef.musictask.domain

import com.youssef.musictask.domain.models.Song
import com.youssef.musictask.utils.Utils

object Mocks {
    fun mockSongs() = mutableListOf(
        mockSong1(),
        mockSong2(),
        mockSong3(),
        mockSong4(),
        mockSong5(),
        mockSong6(),
        mockSong7(),
        mockSong8()
    )

    private fun mockSong1() = Song(
        id = Utils.getRandomString(1),
        title = "Bloom1",
        type = "album",
        artistName = "Radiohead",
        image = "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
        publishingDate = "30 April 2008",
        duration = "5:15",
        trackNum = "1"
    )

    private fun mockSong2() = Song(
        id = Utils.getRandomString(2),
        title = "Nylon Smile2",
        type = "song",
        artistName = "Joey",
        image = "https://picsum.photos/200/300/?blur",
        publishingDate = "30 April 2009",
        duration = "3:16",
        trackNum = "2"
    )

    private fun mockSong3() = Song(
        id = Utils.getRandomString(3),
        title = "Magic Doors3",
        type = "song",
        artistName = "Dadow mero",
        image = "https://homepages.cae.wisc.edu/~ece533/images/fruits.png",
        publishingDate = "30 April 2020",
        duration = "1:16",
        trackNum = "7"
    )

    private fun mockSong4() = Song(
        id = Utils.getRandomString(4),
        title = "Deep Water4",
        type = "album",
        artistName = "Dawe",
        image = "https://picsum.photos/id/100/2500/1656",
        publishingDate = "25 April 2009",
        duration = "3:16",
        trackNum = "5"
    )

    private fun mockSong5() = Song(
        id = Utils.getRandomString(5),
        title = "Bloom5",
        type = "album",
        artistName = "Radiohead",
        image = "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
        publishingDate = "30 April 2008",
        duration = "5:15",
        trackNum = "1"
    )

    private fun mockSong6() = Song(
        id = Utils.getRandomString(6),
        title = "Nylon Smile6",
        type = "song",
        artistName = "Joey",
        image = "https://picsum.photos/200/300/?blur",
        publishingDate = "30 April 2009",
        duration = "3:16",
        trackNum = "2"
    )

    private fun mockSong7() = Song(
        id = Utils.getRandomString(7),
        title = "Magic Doors7",
        type = "song",
        artistName = "Dadow mero",
        image = "https://homepages.cae.wisc.edu/~ece533/images/fruits.png",
        publishingDate = "30 April 2020",
        duration = "1:16",
        trackNum = "7"
    )

    private fun mockSong8() = Song(
        id = Utils.getRandomString(8),
        title = "Deep Water8",
        type = "album",
        artistName = "Dawe",
        image = "https://picsum.photos/id/100/2500/1656",
        publishingDate = "25 April 2009",
        duration = "3:16",
        trackNum = "5"
    )
}