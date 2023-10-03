package appk.obzorkards.fragment

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appk.obzorkards.databinding.LoansFragmentBinding
import appk.obzorkards.databinding.NewsDetailFragmentBinding
import appk.obzorkards.databinding.NewsFragmentBinding
import appk.obzorkards.model.NewsModel
import com.bumptech.glide.Glide

class NewsDetailFragment : BaseFragment() {

    private var _binding: NewsDetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewsDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener { goBack() }

        val data = arguments?.getSerializable("data") as NewsModel

        Glide.with(requireContext()).load(data.imageUrl).into(binding.image)
        binding.text.text = data.title
        binding.textContent.text = Html.fromHtml(data.text)
    }
}