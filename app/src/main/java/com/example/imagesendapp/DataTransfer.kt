package com.example.imagesendapp.ui.theme

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import java.net.URI

fun sendData(bitmap: List<List<Int>>){
    val host = URI("ws://192.168.4.1/ws")
    val client = object : WebSocketClient(host) {
        override fun onOpen(handshakedata: ServerHandshake?) {
            for ((k, v) in bitmap.withIndex()) {
                val jsonObject = JSONObject()
                jsonObject.put("$k", v)
                println(jsonObject)
                //send("{\"$k\":$v}")
                send(jsonObject.toString())
                println("{\"$k\":$v}")
                Thread.sleep(100)
            }
            close()
        }

        override fun onMessage(message: String?) {
            println(message)
        }

        override fun onClose(code: Int, reason: String?, remote: Boolean) {}

        override fun onError(ex: Exception?) {
            ex?.printStackTrace()
        }
    }
    client.connect()
}