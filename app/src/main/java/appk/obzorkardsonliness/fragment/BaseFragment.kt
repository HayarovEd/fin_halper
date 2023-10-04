package appk.obzorkardsonliness.fragment

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import appk.obzorkardsonliness.network.ApiService
import appk.obzorkardsonliness.network.ServiceBuilder


abstract class BaseFragment : Fragment() {

    val request = ServiceBuilder.buildService(ApiService::class.java)


    fun goBack() { findNavController().navigateUp() }
}