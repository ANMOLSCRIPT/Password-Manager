package com.example.passwordmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.passwordmanager.MainActivity
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentAddPasswordBinding
import com.example.passwordmanager.model.Password
import com.example.passwordmanager.viewodel.PasswordViewModel

class AddPasswordFragment : Fragment(R.layout.fragment_add_password), MenuProvider {

    private var addPasswordBinding: FragmentAddPasswordBinding? = null
    private val binding get() = addPasswordBinding!!

    private lateinit var passwordsViewModel: PasswordViewModel
    private lateinit var addPasswordView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        addPasswordBinding = FragmentAddPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.STARTED)

        passwordsViewModel = (activity as? MainActivity)?.passwordViewModel
            ?: throw IllegalStateException("ViewModel not found")
        addPasswordView = view
    }

    private fun savePassword(view: View) {
        if (addPasswordBinding == null) return

        val website = binding.addPasswordWebsite.text.toString().trim() ?: ""
        val username = binding.addPasswordUsername.text.toString().trim() ?: ""
        val pass = binding.addPassword.text.toString().trim() ?: ""

        if (website.isNotEmpty()) {
            val password = Password(0, website, username, pass)  // Avoid shadowing
            passwordsViewModel.addPassword(password)

            Toast.makeText(addPasswordView.context, "Password Saved", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment, false)
        } else {
            Toast.makeText(addPasswordView.context, "Please enter website/app", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_password, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.saveMenu -> {
                savePassword(addPasswordView)
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        addPasswordBinding = null
    }
}

