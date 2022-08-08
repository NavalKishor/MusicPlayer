package com.concept.aop.ui.player

import android.media.AudioAttributes
import android.media.MediaPlayer
import com.concept.aop.ui.model.Song

object MusicPlayerManger {
    lateinit var song:Song
    lateinit var mediaPlayer: MediaPlayer
    var playerState: PlayerState? =null
    var isStop:Boolean?=null

    fun isPlaying():Boolean{
        if (!::mediaPlayer.isInitialized || isStop==true){
            return false
        }
        return mediaPlayer.isPlaying;
    }

    fun initMusic(){
        if (!::mediaPlayer.isInitialized || isStop==true) {
            mediaPlayer = MediaPlayer().apply {

                setOnPreparedListener {
                    start()
                    playerState?.started()
                }
                setOnCompletionListener {
                    //  release()
                    playerState?.completed()
                }
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
            }
            isStop=null
        }
    }
    fun playAudio(data: Song) {
        val audioUrl = data.linkSong
        if (!::mediaPlayer.isInitialized || isStop==true){
            initMusic()
        }
      //  if(isPlaying()) stop()


        try {
            mediaPlayer.setDataSource(audioUrl)
            mediaPlayer.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
            playerState?.completed()
        }
    }

    fun resume(){
        if (!MusicPlayerManger::mediaPlayer.isInitialized || isStop==true){
            return
        }
        isStop=false
        var pos=mediaPlayer.currentPosition
        if(pos==mediaPlayer.duration) pos=0
        mediaPlayer.seekTo(pos)
        mediaPlayer.start()
    }


    fun pause(){
        if (!MusicPlayerManger::mediaPlayer.isInitialized || isStop==true){
           return
        }
        mediaPlayer.pause()
        isStop=false

    }
    fun stop(){
        try {
            if (!::mediaPlayer.isInitialized){
               return
            }
            isStop=true
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
            playerState?.completed()
        } catch (e: Exception) {
            playerState?.completed()
        }
    }
}