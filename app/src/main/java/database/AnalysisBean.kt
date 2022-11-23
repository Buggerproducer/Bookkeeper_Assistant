package database

class AnalysisBean {
    var selectedImageID:Int ?= null
    var type: String? = null
    var percent: Float? = null
    var sumM: Float? = null
    constructor(
        selectedImageId:Int,
        typeName: String?,
        percent:Float,
        sumM:Float
    ){
        this.selectedImageID = selectedImageId
        this.type = typeName
        this.percent = percent
        this.sumM = sumM
    }
    constructor(){}
    @JvmName("getSelectedImageID1")
    fun getSelectedImageID(): Int? {
        return selectedImageID
    }
    @JvmName("getType1")
    fun getType():String?{
        return type
    }

    @JvmName("getPercent1")
    fun getPercent():Float?{
        return percent
    }

    @JvmName("getSumM1")
    fun getSumM():Float?{
        return sumM
    }

    fun setSelectedImageID(s :Int){
        selectedImageID = s
    }

    @JvmName("setType1")
    fun setType(t: String){
        type = t
    }

    fun setPecernt(p:Float){
        percent = p
    }

    fun setSumM(s:Float){
        sumM = s
    }

}