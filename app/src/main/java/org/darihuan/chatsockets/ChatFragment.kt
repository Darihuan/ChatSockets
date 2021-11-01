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


        var socket:Socket = Socket("192.168.0.18",8080);
        var datainput: DataInputStream = DataInputStream(socket.getInputStream());
        var dataOutput:DataOutputStream = DataOutputStream( socket.getOutputStream());


        boton.setOnClickListener(){
            actualizarChat()
            if(!inputTexto.text.equals("")) {
                dataOutput.writeUTF(inputTexto.text.toString());
                chat.add(inputTexto.text.toString())
                inputTexto.text.clear()
                texto.text = chatTostring()
            }


        }
    }



    fun chatTostring():String {
        var chatstr ="";

        for (palabra in chat)
            chatstr += palabra+"\n"

        return chatstr
    }

    fun actualizarChat() {
        var socket:Socket = Socket("192.168.0.18",8080);
        var datainput: DataInputStream = DataInputStream(socket.getInputStream());
        var dataOutput:DataOutputStream = DataOutputStream( socket.getOutputStream());
        dataOutput.writeUTF("actualizar")
        try {
            var linea = datainput.readUTF();
            while(!linea.equals("")) {
                chat.add(linea)
                linea = datainput.readUTF()
            }
            socket.close()

        }catch (e: EOFException) {
            print("Se termino la actualizacion")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


