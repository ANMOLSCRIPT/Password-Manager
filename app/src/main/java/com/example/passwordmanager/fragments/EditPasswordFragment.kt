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
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.passwordmanager.MainActivity
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentEditPasswordBinding
import com.example.passwordmanager.model.Password
import com.example.passwordmanager.viewodel.PasswordViewModel

class EditPasswordFragment : Fragment(R.layout.fragment_edit_password), MenuProvider {

    private var editPasswordBinding: FragmentEditPasswordBinding? = null
    private val binding get() = editPasswordBinding ?: throw IllegalStateException("View binding is null")

    private lateinit var passwordsViewModel: PasswordViewModel
    private lateinit var currentPassword: Password

    private val args: EditPasswordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        editPasswordBinding = FragmentEditPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        passwordsViewModel = (activity as? MainActivity)?.passwordViewModel
            ?: throw IllegalStateException("ViewModel not found")

        currentPassword = args.password ?: throw IllegalArgumentException("Password cannot be null")

        binding.editWebsite.setText(currentPassword.website)
        binding.editUsername.setText(currentPassword.username)
        binding.editPassword.setText(currentPassword.password)

        binding.editPasswordFab.setOnClickListener {
            val website = binding.editWebsite.text.toString().trim() ?: ""
            val username = binding.editUsername.text.toString().trim() ?: ""
            val password = binding.editPassword.text.toString().trim() ?: ""

            if (website.isNotEmpty()){
                val updatedPassword = Password(currentPassword.id, website, username, password)
                passwordsViewModel.updatePassword(updatedPassword)
                view.findNavController().popBackStack(R.id.homeFragment, false)
            } else {
                Toast.makeText(context, "Please Enter Website/App", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deletePassword(){
        AlertDialog.Builder(requireActivity()).apply {
            setTitle("Delete Password")
            setMessage("Do you want to delete this password?")
            setPositiveButton("Delete"){_,_ ->
                passwordsViewModel.deletePassword(currentPassword)
                Toast.makeText(context, "Password Deleted", Toast.LENGTH_SHORT).show()
                if (view != null) {
                    view?.findNavController()?.popBackStack(R.id.homeFragment, false)
                }
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_password, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                deletePassword()
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editPasswordBinding = null
    }
}
