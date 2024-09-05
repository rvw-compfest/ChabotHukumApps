package com.compfest.chatbothukum.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.compfest.chatbothukum.core.domain.model.Message
import com.compfest.chatbothukum.databinding.FragmentHomeBinding
import com.compfest.chatbothukum.ui.ChatAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var chatAdapter: ChatAdapter
    private val messageList = ArrayList<Message>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setupRecyclerView()
        setupSendMessage()
        fetchMessages()
        setupKeyboardInsetsHandling()
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(messageList)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = chatAdapter
    }

    private fun fetchMessages() {
        val dummyMessage = Message(
            id = "1",
            message = "Hello! How can I assist you today?",
            createdAt = "",
            isUser = false
        )
        messageList.add(dummyMessage)
        chatAdapter.notifyItemInserted(messageList.size - 1)
    }

    private fun setupSendMessage() {
        binding.textInputLayout.setEndIconOnClickListener {
            val messageText = binding.inputMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                sendMessage(messageText)
                binding.inputMessage.text?.clear()
            }
        }
    }

    private fun sendMessage(text: String) {
        val userMessage = Message(
            id = "2",
            message = text,
            createdAt = "",
            isUser = true
        )
        messageList.add(userMessage)
        chatAdapter.notifyItemInserted(messageList.size - 1)
        binding.recyclerView.scrollToPosition(messageList.size - 1)

        simulateBotResponse(text)
    }

    private fun simulateBotResponse(userText: String) {
        Handler(Looper.getMainLooper()).postDelayed({
            val botResponse = Message(
                id = "3",
                message = "This is a simulated response to: $userText",
                createdAt = "",
                isUser = false
            )
            messageList.add(botResponse)
            chatAdapter.notifyItemInserted(messageList.size - 1)
            binding.recyclerView.scrollToPosition(messageList.size - 1)
        }, 1000)
    }

    private fun setupKeyboardInsetsHandling() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            if (imeVisible) {
                binding.messageInputLayout.setPadding(0, 0, 0, imeHeight)
                binding.recyclerView.post {
                    binding.recyclerView.scrollToPosition(messageList.size - 1)
                }
            } else {
                binding.messageInputLayout.setPadding(0, 0, 0, 0)
            }
            insets
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
