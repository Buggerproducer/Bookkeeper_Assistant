package database

class BarBean {
    private var year:Int?=null
    private var month:Int?=null
    private var day:Int?=null
    private var total:Float?=null

    constructor()
    constructor(year: Int?, month: Int?, day: Int?, tital: Float?) {
        this.year = year
        this.month = month
        this.day = day
        this.total = tital
    }

    @JvmName("getYear1")
    fun getYear(): Int? {
        return this.year
    }

    @JvmName("getMonth1")
    fun getMonth(): Int?{
        return this.month
    }

    @JvmName("getDay1")
    fun getDay(): Int?{
        return  this.day
    }

    @JvmName("getTotal1")
    fun getTotal():Float?{
        return  this.total
    }

    fun setYear(y:Int){
        this.year = y
    }

    fun setMonth(y:Int){
        this.month = y
    }
    fun setDay(y:Int){
        this.year = y
    }
    fun setTotal(y:Float){
        this.total = y
    }






}