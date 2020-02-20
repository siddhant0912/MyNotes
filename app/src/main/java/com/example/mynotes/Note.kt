package com.example.mynotes

class Note {

    var noteID: Int ?=null
    var NoteName:String ?=null
    var NoteDes:String ?= null
    constructor(noteID:Int, NoteName:String,NoteDes :String ){
        this.noteID =noteID
        this.NoteName =NoteName
        this.NoteDes =NoteDes
    }
}