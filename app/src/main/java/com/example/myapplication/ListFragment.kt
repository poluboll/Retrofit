package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.view.ViewCompat.canScrollVertically
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Rick.RickList
import com.example.myapplication.adapter.RickAdapter
import com.example.myapplication.databinding.FragmentListBinding
import com.example.myapplication.retrofit.RickApi
import com.example.myapplication.retrofit.RickService
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModels<ListFragmentViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ListFragmentViewModel(RickService.provideRetrofit().create()) as T
            }
        }
    }

    private val adapter = RickAdapter { rick ->
        findNavController().navigate(
            ListFragmentDirections.toInfo(rick.name)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentListBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(view.context)
            recyclerView.adapter = adapter
            viewModel
                .ricksFlow
                .onEach { rickList ->
                    adapter.submitList(rickList)
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


