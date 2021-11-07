package org.darihuan.chatsockets

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.darihuan.chatsockets.databinding.FragmentChatBinding
import java.io.DataInputStream
import java.io.DataOutput
import java.io.DataOutputStream
import java.io.EOFException
import java.net.Socket


class ChatFragment : Fragment() {
     var chat = ArrayList<String>();

    private var _binding: FragmentChatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChatBinding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var boton = binding.btnPlay;
        var texto = binding.miTexto;
        var inputTexto = binding.inputText;
        var usuario = binding.usuarioinput;



        boton.setOnClickListener(){
            var socket:Socket = Socket("192.168.0.18",8080);
            var datainput: DataInputStream = DataInputStream(socket.getInputStream());
            var dataOutput:DataOutputStream = DataOutputStream( socket.getOutputStream());

            var textchat= datainput.readUTF().split(";");

            var chatview = "";
            textchat.forEach{linea->chatview+=linea+"\n"}
            binding.miTexto.text = chatview;


            dataOutput.writeUTF(usuario.text.toString()+":"+inputTexto.text.toString());


            textchat= datainput.readUTF().split(";").map { linea->linea.replace("$","\n") };
            chatview = "";
            textchat.forEach{linea->chatview+=linea+"\n"}
            binding.miTexto.text = chatview;


        }
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


