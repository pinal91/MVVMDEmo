package com.demo.pinal.model

class RPdfGeneratorModel(list: ArrayList<ResultsItem>?, header: String) {

    var list = emptyList<ResultsItem>()
    var header = ""
    var totalCredit = ""
    var totalDebit = ""
    var totalProfit = ""

    init {
        if (list != null) {
            this.list = list
        }
        this.header = header
    }


}