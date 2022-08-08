package com.concept.aop.ui.vm

import android.util.Log
import androidx.lifecycle.*
import com.concept.aop.repository.Repository
import com.concept.aop.repository.retrofit.Entry
import com.concept.aop.ui.model.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel ( private val repository: Repository) : ViewModel() {

    private val _songListMLD = MutableLiveData<List<Song>>()
    val songListLD: LiveData<List<Song>> get() = _songListMLD

//    val songDao: SongDao by inject()
    val feedLiveData: LiveData<List<Entry>> =  Transformations.map(repository.feedLiveData) { items ->
        Log.i("TAG", "${items?.entryList?.size}")

    // from server
        items?.entryList
}

    private val observerSong= Observer<List<Entry>> {
        it?.let { list->
            prepareSongDataAddToDB(list)
        }

    }

    init {
        feedLiveData.observeForever(observerSong)
    }
    //call from server
    private fun prepareSongDataAddToDB(list:List<Entry>){
        viewModelScope.launch(Dispatchers.IO) {
            val songList = mutableListOf<Song>()
            val dataLen=list.size/2
            list.forEachIndexed { i, entry ->
                Log.i("TAG", "${entry}")
                var link =""
                 entry.link?.forEach {
                   if (it.type?.contains("audio"))
                   {
                       link=it.href
                   }
                 }


                val song = Song(
                    id = i + 1, title = entry.title,
                    desc = "${entry.run { "Description:Song $headTitle\nArtist:$artist \nPrice:$price \nRights:$rights \nReleaseDate:$releaseDate" }}",
                    linkSong = link, linkImg = entry?.image?.get(2) ?:""
                )
                Log.i("TAG", "${song}")
                songList.add(song)
                if(dataLen==i)
                 _songListMLD.postValue(songList)

                val (id, title,desc, linkSong, linkImg)=song
                repository.addInDB(id, title,desc, linkSong?:"", linkImg)
            }
            _songListMLD.postValue(songList)
        }
    }

    private fun fetchFeed() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchFeed()
        }
    }

    fun getFeedData()  {
        viewModelScope.launch(Dispatchers.IO) {
           if (repository.isFeedAvailableInDB()){
               //pick from db
                val dataList=repository.songDao.getSongs()
               val dataLen=dataList.size/2
               val songList = mutableListOf<Song>()
               dataList.forEachIndexed { i, entry ->
                   val (id, title,desc, linkSong, linkImg)=entry
                   val song = Song(id=id, title=title,desc=desc, linkSong=linkSong, linkImg=linkImg)
                   songList.add(song)
                   if(dataLen==i)
                       _songListMLD.postValue(songList)
               }
               _songListMLD.postValue(songList)
           }else
           {
               fetchFeed()
              // dummyData()
           }

        }
    }

    private fun dummyData(){
        val songList =  mutableListOf<Song>()
        for (i in 0..10){
            songList.add(Song(i+1,"title $i","desc $i","link $i","link image $i"))
        }
        _songListMLD.postValue(songList)
    }

    override fun onCleared() {
        feedLiveData.removeObserver(observerSong)
        viewModelScope.cancel()
    }



}