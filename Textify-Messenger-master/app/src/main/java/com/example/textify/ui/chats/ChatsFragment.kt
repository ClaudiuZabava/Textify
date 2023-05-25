package com.example.textify.ui.chats

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.textify.activity.BaseFragments
import com.example.textify.activity.ChatActivity
import com.example.textify.adapters.ChatsAdapter
import com.example.textify.databinding.FragmentChatsBinding
import com.example.textify.models.ChatRooms
import com.example.textify.models.User
import com.example.textify.screenstate.ScreenState
import com.example.textify.utils.Constants
import com.example.textify.viewmodelfactory.ChatsViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatsFragment : BaseFragments() {

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: FirebaseFirestore
    private val senderid: String = FirebaseAuth.getInstance().uid!!
    private lateinit var sender: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val viewModel: ChatsViewModel = ViewModelProvider(this, ChatsViewModelFactory(senderid,""))[ChatsViewModel::class.java]
        database = FirebaseFirestore.getInstance()
        database.collection(Constants.KEY_COLLECTION_USER).document(senderid).addSnapshotListener { value, error ->
            sender = value?.toObject(User::class.java)!!
        }
        viewModel.chatLiveData.observe(viewLifecycleOwner) { state ->
            processChatList(state)
        }
        return root
    }

    private fun processChatList(state: ScreenState<List<ChatRooms>?>) {
        when(state) {
            is ScreenState.Loading -> {

            }
            is ScreenState.Success -> {

                if(state.data!=null) {
                    Log.e("Size of Contacts List", "${state.data.size}")
                    val adapter = ChatsAdapter(requireContext(),state.data)
                    adapter.setOnClickListener(object : ChatsAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val senderId = state.data[position].sender_id
                            val receiverId = state.data[position].receiver_id
                            val chat_room_id = state.data[position].id
                            val intent = Intent(context, ChatActivity::class.java)
                            intent.putExtra(Constants.KEY_SENDER_ID,senderId)
                            intent.putExtra(Constants.KEY_RECEIVER_ID,receiverId)
                            intent.putExtra(Constants.KEY_CHAT_ROOM_ID,chat_room_id)
                            startActivity(intent)
                        }
                    })
                    val rv = binding.chatscreenRvChats
                    rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                    rv.setHasFixedSize(true)
                    rv.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }
            is ScreenState.Error -> {

            }
        }
    }

}