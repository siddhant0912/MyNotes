package com.example.mynotes

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.core.database.getShortOrNull
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {
    var listNotes= ArrayList<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Add Dummy data
        /*
        listNotes.add(Note(1,"Meet Prof","Myself siddhant and i like to do programming in kotlin, its a nice day, be happy and keep coding."))
        listNotes.add(Note(2,"Meet student","Myself siddhant and i like to do programming in kotlin, its a nice day, be happy and keep coding."))

        listNotes.add(Note(3,"Meet teacher","Myself siddhant and i like to do programming in kotlin, its a nice day, be happy and keep coding."))
         */


        //load from database
        LoadQuery("%")

    }
    fun LoadQuery(title:String){
        var dbManager = DbManager(this)
        var projections = arrayOf("ID","Title","Description")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.Query(projections,"Title like ?", selectionArgs,"Title")
        listNotes.clear()
        if(cursor.moveToFirst()){
            do{
                val ID =cursor.getInt(cursor.getColumnIndex("ID"))
                val title =cursor.getString(cursor.getColumnIndex("Title"))
                val des =cursor.getString(cursor.getColumnIndex("Description"))

                listNotes.add(Note(ID,title,des))

            }while (cursor.moveToNext())
        }
        var myNotesAdaptor = MyNotesAdaptor(this,listNotes)
        lvNotes.adapter = myNotesAdaptor

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val sv = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext,query,Toast.LENGTH_LONG).show()
                //TODO Search Database
                LoadQuery("%" + query +"%")
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addItem ->{
                //goto second page
                var intent = Intent(this,AddNotes::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class MyNotesAdaptor:BaseAdapter{
        var listNoteAdaptor = ArrayList<Note>()
        var context:Context ?=null
        constructor(context: Context,listNoteAdaptor:ArrayList<Note>):super(){
            this.listNoteAdaptor= listNoteAdaptor
            this.context =context

        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            var myView = layoutInflater.inflate(R.layout.ticket, null)
            var myNote = listNoteAdaptor[p0]
            myView.tvTitle.text =myNote.NoteName
            myView.tvDes.text=myNote.NoteDes
            myView.ivdelte.setOnClickListener(View.OnClickListener {
               var dbManager =DbManager(this.context!!)
                val selectionArgs = arrayOf(myNote.noteID.toString())
                dbManager.Delete("ID=?",selectionArgs)
                LoadQuery("%")
            })
            myView.ivedit.setOnClickListener(View.OnClickListener {
                GoToUpdate(myNote)
            })
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
    fun GoToUpdate(note:Note){
        var intent =Intent(this,AddNotes::class.java)
        intent.putExtra("ID", note.noteID)
        intent.putExtra("Title", note.NoteName)
        intent.putExtra("Description", note.NoteDes)
        startActivity(intent)
    }
}
