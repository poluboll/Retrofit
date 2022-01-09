package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myapplication.Rick.RickList
import com.example.myapplication.Rick.RickListInfo
import com.example.myapplication.databinding.FragmentInfoBinding
import com.example.myapplication.retrofit.RickService
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args: InfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentInfoBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadRickInfo()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadRickInfo(onFinishLoading: () -> Unit = {}) {
        RickService.rickApi.getRickInfo(args.rickname)
            .enqueue(object : Callback<RickListInfo> {
                override fun onResponse(
                    call: Call<RickListInfo>,
                    response: Response<RickListInfo>
                ) {
                    if (response.isSuccessful) {
                        with(binding) {
                            textViewNameRick.text = response.body()?.results?.get(0)?.name
                            imageViewRickImage.load(response.body()?.results?.get(0)?.imageUrl)
                            textViewRickSpecies.text = response.body()?.results?.get(0)?.species
                            textViewRickStatus.text = response.body()?.results?.get(0)?.status
                        }
                    }
                    onFinishLoading()
                }

                override fun onFailure(call: Call<RickListInfo>, t: Throwable) {

                    onFinishLoading()
                    Snackbar.make(binding.root, t.message ?: "", Snackbar.LENGTH_SHORT)
                        .show()
                }
            })
    }
}