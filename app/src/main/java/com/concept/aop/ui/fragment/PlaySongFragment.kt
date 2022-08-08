package com.concept.aop.ui.fragment

//import androidx.fragment.app.activityViewModels
//import androidx.fragment.app.viewModels

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.concept.aop.R
import com.concept.aop.databinding.FragmentPlaySongBinding
import com.concept.aop.databinding.RvSongRowItemBinding
import com.concept.aop.ui.model.Song
import com.concept.aop.ui.player.MusicPlayerManger
import com.concept.aop.ui.player.PlayerState
import com.concept.aop.ui.vm.PlaySongViewModel
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject


class PlaySongFragment : Fragment() {

    companion object {
        fun newInstance() = PlaySongFragment()
    }

    private var _binding: FragmentPlaySongBinding?=null
    private val binding get() = _binding!!
    private var _bindingRow: RvSongRowItemBinding?=null
    private val bindingRow get() = _bindingRow!!


    private val mediaFileLengthInMilliseconds = 0
    val data: Song = MusicPlayerManger.song
    private  val viewModel  by inject<PlaySongViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentPlaySongBinding.inflate(inflater)
        _bindingRow= binding.detailLayout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(bindingRow){
            tvDesc.text=data.desc
            tvTitle.text=data.title
            try {
                if(data.linkImg.contains("http"))
                    Picasso.get().load(data.linkImg).into(ivPic)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        MusicPlayerManger.playerState=object : PlayerState{
            override fun started() {
                binding.playOrPause.isEnabled=true
                binding.playOrPause.text=getText(R.string.txt_pause)
            }

            override fun completed() {
                binding.playOrPause.isEnabled=true
                binding.playOrPause.text=getText(R.string.txt_play)
            }

        }

        binding.btnStop.setOnClickListener{
            MusicPlayerManger.stop()
        }
        binding.playOrPause.setOnClickListener{
            MusicPlayerManger.initMusic()
            if (MusicPlayerManger.isPlaying()) {
                MusicPlayerManger.pause()
                binding.playOrPause.text=getText(R.string.txt_play)
                binding.playOrPause.isEnabled=true

            }else
            {
                binding.playOrPause.text=getText(R.string.txt_pause)

               if(MusicPlayerManger.isStop==null ||MusicPlayerManger.isStop==true){
                   MusicPlayerManger.playAudio(data)
                   binding.playOrPause.isEnabled=false
               }else{
                   MusicPlayerManger.resume()
                   binding.playOrPause.isEnabled=true
               }
            }
            /*initMusic()
            if (mediaPlayer.isPlaying) {
                stop()
                binding.playOrPause.text=getText(R.string.txt_play)
                binding.playOrPause.isEnabled=true

            }else
            {
                binding.playOrPause.text=getText(R.string.txt_pause)
                binding.playOrPause.isEnabled=false
                playAudio(data)
            }*/
        }
    }



    override fun onPause() {
        super.onPause()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
    lateinit var mediaPlayer: MediaPlayer
    fun initMusic(){
        if (!::mediaPlayer.isInitialized)
            mediaPlayer = MediaPlayer().apply {
                setOnPreparedListener {
                    binding.playOrPause.isEnabled=true
                    binding.playOrPause.text=getText(R.string.txt_pause)
                    start()
                }
                setOnCompletionListener {
                    binding.playOrPause.isEnabled=true
                    binding.playOrPause.text=getText(R.string.txt_play)
                }
            }
    }
    fun playAudio(data:Song) {
        val audioUrl = data.linkSong

        if (!::mediaPlayer.isInitialized){
            initMusic()
        }
        if(mediaPlayer.isPlaying) stop()
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        )

        try {
            mediaPlayer.setDataSource(audioUrl)

            mediaPlayer.prepareAsync()
            binding.playOrPause.text=getText(R.string.txt_pause)
            binding.playOrPause.isEnabled=false

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun stop(){
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        binding.playOrPause.isEnabled=true
        binding.playOrPause.text=getText(R.string.txt_play)
    }
}