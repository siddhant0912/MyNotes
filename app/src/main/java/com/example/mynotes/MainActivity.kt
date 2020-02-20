package com.example.mynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {
    var listNotes= ArrayList<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Add Dummy data
        listNotes.add(Note(1,"Meet Prof","Myself siddhant and i like to do programming in kotlin, its a nice day, be happy and keep coding."))
        listNotes.add(Note(2,"Meet student","Myself siddhant and i like to do programming in kotlin, its a nice day, be happy and keep coding."))
        listNotes.add(Note(3,"Meet teacher","Myself siddhant and i like to do programming in kotlin, its a nice day, be happy and keep coding."))
        var myNotesAdaptor = MyNotesAdaptor(listNotes)
        lvNotes.adapter = myNotesAdaptor
    }
    inner class MyNotesAdaptor:BaseAdapter{
        var listNoteAdaptor = ArrayList<Note>()
        constructor(listNoteAdaptor:ArrayList<Note>):super(){
            this.listNoteAdaptor= listNoteAdaptor

        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.ticket, null)
            var myNote = listNoteAdaptor[p0]
            myView.tvTitle.text =myNote.NoteName
            myView.tvDes.text=myNote.NoteDes
            return myView
        }
        override fun getItem(p0: Int): Any {
           return listNoteAdaptor[p0]
        }
        override fun getItemId(position: Int): Long {
            return  position.toLong()
        }
        override fun getCount(): Int {
            return  listNoteAdaptor.size
        }
    }
}
