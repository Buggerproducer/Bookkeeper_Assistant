package database

import java.time.Year

//describe the content of a piece of the record
class BillBean {
    var id = 0
    var typeName: String ?= null
    var notes: String ?= null
    var dateTime:String ?= null
    var selectedImageId = 0
    var money = 0f

    //record the y,m,d, kind in order to search
    var year = 0
    var month = 0
    var day = 0
    var kind = 0

    constructor(
        id: Int,
        typeName: String?,
        notes:String?,
        dateTIme:String?,
        imageId:Int,
        money:Float,
        year: Int,
        month: Int,
        day: Int,
        kind: Int,
        ){
        this.id = id
        this.typeName = typeName
        this.notes = notes
        this.dateTime = dateTIme
        this.selectedImageId = imageId
        this.money = money
        this.year = year
        this.month = month
        this.day = day
        this.kind = kind
    }
    constructor(){}

    //this method is used to get image
    fun getSelectedImage():Int{
        return selectedImageId
    }

    //this method is used to set image
    fun setSelectedImage(i:Int){
        this.selectedImageId = i
    }


}