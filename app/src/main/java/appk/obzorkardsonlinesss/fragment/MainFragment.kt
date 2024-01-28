package appk.obzorkardsonlinesss.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import appk.obzorkardsonlinesss.R
import appk.obzorkardsonlinesss.databinding.MainFragmentBinding
import com.realpacific.clickshrinkeffect.applyClickShrink

class MainFragment : BaseFragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loansBtn.applyClickShrink()
        binding.loansBtn.setOnClickListener { findNavController().navigate(R.id.loansFragment) }

        binding.cardsBtn.applyClickShrink()
        binding.cardsBtn.setOnClickListener { findNavController().navigate(R.id.cardsFragment) }

        binding.newsBtn.applyClickShrink()
        binding.newsBtn.setOnClickListener { findNavController().navigate(R.id.newsFragment) }

        binding.calculatorBtn.applyClickShrink()
        binding.calculatorBtn.setOnClickListener { findNavController().navigate(R.id.loansFragment) }

        binding.privacyBtn.applyClickShrink()
        binding.privacyBtn.setOnClickListener { findNavController().navigate(R.id.privacyFragment) }
    }
}