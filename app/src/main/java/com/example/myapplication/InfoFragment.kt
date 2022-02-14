package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myapplication.Rick.RickList
import com.example.myapplication.Rick.RickListInfo
import com.example.myapplication.databinding.FragmentInfoBinding
import com.example.myapplication.retrofit.RickService
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create


class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args: InfoFragmentArgs by navArgs()

    private val viewModel by viewModels<InfoFragmentViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return InfoFragmentViewModel(
                    rickApi = RickService.provideRetrofit().create(),
                    name = args.rickname
                ) as T
            }
        }
    }

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
        viewModel
            .rickDetailsFlow
            .onEach { rickListInfo ->
                val data = rickListInfo.results.get(0)
                with(binding) {
                    textViewNameRick.text = data.name
                    imageViewRickImage.load(data.imageUrl)
                    textViewRickSpecies.text = data.species
                    textViewRickStatus.text = data.status
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}