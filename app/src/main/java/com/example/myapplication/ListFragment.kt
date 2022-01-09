package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.view.ViewCompat.canScrollVertically
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Rick.RickList
import com.example.myapplication.adapter.RickAdapter
import com.example.myapplication.databinding.FragmentListBinding
import com.example.myapplication.retrofit.RickService
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = requireNotNull(_binding) {

        }

    private var page = 1

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

        loadRick()

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(
                view.context
            )
            recyclerView.adapter = adapter


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadRick(onFinishLoading: () -> Unit = {}) {
        RickService.rickApi.getRick(page)
            .enqueue(object : Callback<RickList> {
                override fun onResponse(
                    call: Call<RickList>,
                    response: Response<RickList>
                ) {
                    if (response.isSuccessful) {
                        adapter.submitList(response.body()?.results)
                    }
                    onFinishLoading()
                }

                override fun onFailure(call: Call<RickList>, t: Throwable) {

                    onFinishLoading()
                    Snackbar.make(binding.root, t.message ?: "", Snackbar.LENGTH_SHORT)
                        .show()
                }
            })
    }

}
