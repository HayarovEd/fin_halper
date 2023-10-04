package appk.obzorkardsonliness.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import appk.obzorkardsonliness.R
import appk.obzorkardsonliness.databinding.StartFragmentBinding
import com.pixplicity.easyprefs.library.Prefs
import com.realpacific.clickshrinkeffect.applyClickShrink

class StartFragment : BaseFragment() {

    private var _binding: StartFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.privacyText.setOnClickListener {
            findNavController().navigate(R.id.privacyFragment)
        }

        binding.privacyCheck.setOnCheckedChangeListener { _, b ->
            Log.d ("ASDFG", "b - $b")
            Prefs.putBoolean("accepted", b)
        }

        binding.acceptBtn.applyClickShrink()
        binding.acceptBtn.setOnClickListener {
            if (binding.privacyCheck.isChecked){
                findNavController().navigate(R.id.action_startFragment_to_mainFragment)
            } else {
                Toast.makeText(requireContext(), requireContext().getString(R.string.privacy_error), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

    }
}