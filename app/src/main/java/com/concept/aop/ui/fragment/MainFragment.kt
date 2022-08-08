package com.concept.aop.ui.fragment

//import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.concept.aop.R
import com.concept.aop.databinding.FragmentMainBinding
import com.concept.aop.ui.adapter.SongListAdapter
import com.concept.aop.ui.model.Song
import com.concept.aop.ui.player.MusicPlayerManger
import com.concept.aop.ui.vm.MainViewModel
import com.concept.aop.utils.replace
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding?=null
    private val binding get() = _binding!!
    private val mainVM by inject<MainViewModel>()

    companion object {
        fun newInstance() = MainFragment()
    }
    var songList =  mutableListOf<Song>()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding= FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //create vm
        mainVM.songListLD.observe(viewLifecycleOwner, Observer { mysongList->
        // from data base
            mysongList?.let {
                val current= songList.size
                songList.clear()
                songList.addAll(it)
                binding.rvSongs.adapter?.run{
                    //tell the recycler view that all the old items are gone
                    notifyItemRangeRemoved(0, current)
                    //tell the recycler view how many new items we added
                    notifyItemRangeInserted(0, songList.size);
                }

//                binding.rvSongs.adapter?.notifyItemRangeInserted(0, songList.size);
            }

        })

        mainVM.getFeedData()
        val linearLayoutManager = LinearLayoutManager(requireContext().applicationContext)
        with(binding.rvSongs){
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = SongListAdapter(songList,getOnItemClickListner())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }
    private fun getOnItemClickListner(): SongListAdapter.OnItemClickListener {
        return object : SongListAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                MusicPlayerManger.song=songList[position]
                replace(R.id.container,PlaySongFragment.newInstance())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}