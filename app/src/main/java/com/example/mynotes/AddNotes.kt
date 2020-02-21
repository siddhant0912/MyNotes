 package com.example.mynotes

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
import java.lang.Exception

 class AddNotes : AppCompatActivity() {
     var id =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        try{
            var bundle: Bundle? = intent.extras
            id =bundle!!.getInt("ID",0)
            if(id!=0) {
                etTitle.setText(bundle.getString("Title"))
                etDes.setText(bundle.getString("Description"))
            }
        }catch (ex:Exception){

        }
    }
    fun buAdd(view:View){
        var dbManager =DbManager(this)
        var value =ContentValues()
        value.put("Title",etTitle.text.toString())
        value.put("Description",etDes.text.toString())

        if(id==0) {
            val ID =dbManager.Insert(value)
            if (ID > 0) {
                Toast.makeText(this, "Note is Added", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Failed  To Add Notes", Toast.LENGTH_LONG).show()
            }
        }else{
            var selectionArgs = arrayOf(id.toString())
            val ID = dbManager.Update(value,"ID=?",selectionArgs)
            if (ID > 0) {
                Toast.makeText(this, "Note is updated", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Failed  To update Notes", Toast.LENGTH_LONG).show()
            }
        }

    }
}
