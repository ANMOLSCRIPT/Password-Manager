package com.example.passwordmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.databinding.PasswordLayoutBinding
import com.example.passwordmanager.fragments.HomeFragmentDirections
import com.example.passwordmanager.model.Password

class PasswordAdapter : RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder>() {

    class PasswordViewHolder(val itemBinding: PasswordLayoutBinding): RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Password>(){
        override fun areItemsTheSame(oldItem: Password, newItem: Password): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.username == newItem.username &&
                    oldItem.website == newItem.website &&
                    oldItem.password == newItem.password
        }

        override fun areContentsTheSame(oldItem: Password, newItem: Password): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        return PasswordViewHolder(
            PasswordLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        val currentPassword = differ.currentList[position]

        holder.itemBinding.website.text = currentPassword.website
        holder.itemBinding.username.text = currentPassword.username
        holder.itemBinding.password.text = currentPassword.password

        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToEditPasswordFragment(currentPassword)
            it.findNavController().navigate(direction)
        }
    }

}