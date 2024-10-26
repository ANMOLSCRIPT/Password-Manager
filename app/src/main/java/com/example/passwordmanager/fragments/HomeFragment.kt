package com.example.passwordmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.passwordmanager.MainActivity
import com.example.passwordmanager.R
import com.example.passwordmanager.adapter.PasswordAdapter
import com.example.passwordmanager.databinding.FragmentHomeBinding
import com.example.passwordmanager.model.Password
import com.example.passwordmanager.viewodel.PasswordViewModel


class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {

    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding ?: throw IllegalStateException("View binding is null")

    private lateinit var passwordsViewModel: PasswordViewModel
    private lateinit var passwordAdapter: PasswordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        passwordsViewModel = (activity as? MainActivity)?.passwordViewModel
            ?: throw IllegalStateException("ViewModel not found")

        setupHomeRecyclerView()

        binding.addPasswordFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addPasswordFragment)
        }
    }

    private fun updateUI(password: List<Password>?) {
        if (view != null && password != null) {
            if (password.isNotEmpty()) {
                binding.emptyPasswordsImage.visibility = View.GONE
                binding.homeRecyclerView.visibility = View.VISIBLE
            } else {
                binding.emptyPasswordsImage.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.GONE
            }
        }
    }

    private fun setupHomeRecyclerView() {
        passwordAdapter = PasswordAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = passwordAdapter
        }

        activity?.let {
            passwordsViewModel.getAllPasswords().observe(viewLifecycleOwner) { password ->
                passwordAdapter.differ.submitList(password)
                updateUI(password)
            }
        }
    }

    private fun searchPassword(query: String?) {
        val searchQuery = "%${query?.trim()}%"
        passwordsViewModel.searchPassword(searchQuery).observe(viewLifecycleOwner) { list ->
            passwordAdapter.differ.submitList(list)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchPassword(newText)
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeBinding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)

        val searchView = menu.findItem(R.id.searchMenu)?.actionView as? SearchView
        searchView?.let {
            it.isSubmitButtonEnabled = false
            it.setOnQueryTextListener(this)
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}
