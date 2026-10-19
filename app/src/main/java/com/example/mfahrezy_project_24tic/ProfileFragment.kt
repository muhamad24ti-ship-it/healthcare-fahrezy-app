package com.example.mfahrezy_project_24tic

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.mfahrezy_project_24tic.databinding.FragmentProfileBinding
import java.util.Locale

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var bmiPref: BmiPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bmiPref = BmiPreferences(requireContext())
        val username = bmiPref.username

        binding.tvProfileUsername.text = username
        binding.tvProfileEmail.text = String.format(Locale.US, "%s@seimbang.app", username.lowercase(Locale.US))
        binding.tvAvatarInitial.text = if (username.isNotEmpty()) username.substring(0, 1).uppercase(Locale.US) else "U"

        // Logout AlertDialog logic (fitur existing)
        binding.btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Konfirmasi LogOut")
            builder.setMessage("Apakah Anda yakin ingin LogOut?")

            builder.setPositiveButton("Ya") { dialog, _ ->
                dialog.dismiss()
                bmiPref.clearSession()

                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }

            builder.setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }

            builder.create().show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
