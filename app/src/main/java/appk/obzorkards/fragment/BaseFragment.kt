package appk.obzorkards.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import appk.obzorkards.network.ApiService
import appk.obzorkards.network.ServiceBuilder


abstract class BaseFragment : Fragment() {

    val request = ServiceBuilder.buildService(ApiService::class.java)


    fun goBack() { findNavController().navigateUp() }
}