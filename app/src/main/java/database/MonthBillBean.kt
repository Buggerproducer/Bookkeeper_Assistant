package database

class MonthBillBean {
    var month_str:String ?= null
    var expense:Float ?= 0f
    var income:Float ?= 0f

    constructor(
        month:Int,
        expense:Float,
        income:Float
    ){
        when(month){
            1 -> month_str = "Jan"
            2 -> month_str = "Feb"
            3 -> month_str = "Mar"
            4 -> month_str = "Apr"
            5 -> month_str = "May"
            6 -> month_str = "Jun"
            7 -> month_str = "Jul"
            8 -> month_str = "Aug"
            9 -> month_str = "Sept"
            10 -> month_str = "Oct"
            11 -> month_str = "Nov"
            12 -> month_str = "Dec"
        }
        this.expense = expense
        this.income = income

    }
}