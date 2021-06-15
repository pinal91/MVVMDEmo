package com.demo.pinal.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.demo.pinal.R
import com.demo.pinal.helper.RPdfGenerator
import com.demo.pinal.helper.RPermissionHelper
import com.demo.pinal.model.RPdfGeneratorModel
import com.demo.pinal.model.RTransaction
import com.demo.pinal.model.RTransactionType
import com.demo.pinal.model.ResultsItem
import com.demo.pinal.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import net.ozaydin.serkan.easy_csv.EasyCsv
import net.ozaydin.serkan.easy_csv.FileCallback
import java.io.File


class MainActivity : AppCompatActivity() {

    lateinit var context: Context
    private var adapterMoviesList: MoviesListAdapter? = null
    val WRITE_PERMISSON_REQUEST_CODE = 1
    private var isGenerating = false
    lateinit var mainActivityViewModel: MainActivityViewModel
    var data: ArrayList<ResultsItem>? = null
    private lateinit var dummyInfo: RPdfGeneratorModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this@MainActivity
        val layoutManager: androidx.recyclerview.widget.LinearLayoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(this)
        rcyMovieList.layoutManager = layoutManager

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        wp7progressBar.showProgressBar()

        mainActivityViewModel.getUser()!!.observe(this, Observer { serviceSetterGetter ->

            wp7progressBar.hideProgressBar()

            data = serviceSetterGetter.results as ArrayList<ResultsItem>?
            adapterMoviesList = MoviesListAdapter(data as ArrayList<ResultsItem>?, this)
            rcyMovieList.adapter = adapterMoviesList

        })
        createPdf(false) //just ask permission for first time
        dummyInfo = dummyModel()

        btnCsv.setOnClickListener {
            CreateCsv(data)
        }
        btnPdf.setOnClickListener {
            createPdf(true)
        }

    }

    // to create CSV
    private fun CreateCsv(data: ArrayList<ResultsItem>?) {
        val easyCsv = EasyCsv(this@MainActivity)
        val headerList: ArrayList<String> = ArrayList()
        headerList.add("Movie Name.Rating.Image url-")

        val dataList: ArrayList<String> = ArrayList()
        for (i in 0 until data!!.size) {
            dataList.add(
                data[i].original_title.toString() + "~" + data[i].vote_average.toString() + "~"
                        + data[i].poster_path.toString()+"-"
            )

        }
        easyCsv.setSeparatorColumn("~")
        easyCsv.setSeperatorLine("-")
        val fileName = "Pinal"

        easyCsv.createCsvFile(
            fileName,
            headerList,
            dataList,
            WRITE_PERMISSON_REQUEST_CODE,
            object : FileCallback {
                override fun onSuccess(file: File) {
                    Toast.makeText(
                        this@MainActivity, """Saved file${file.absolutePath} 
                            |${file.path}""".trimMargin(), Toast.LENGTH_SHORT).show()
                    Log.d("PATH", """${file.absolutePath} ${file.path}"""
                    )
                    val csvIntent = Intent(Intent.ACTION_VIEW)
                    // csvIntent.addCategory(Intent.CATEGORY_OPENABLE);
                    csvIntent.setDataAndType(
                        FileProvider.getUriForFile(
                            applicationContext,
                            "com.demo.pinal", file
                        ), "*/*"
                    )
                    csvIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    csvIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(csvIntent)
                }

                override fun onFail(err: String) {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                    Log.d("err",err)
                }
            })

    }


    fun createPdf(download: Boolean) {

        val permissionHelper = RPermissionHelper(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
        permissionHelper.denied {
            if (it) {
                Log.d("Permission check", "Permission denied by system")
                permissionHelper.openAppDetailsActivity()
            } else {
                Log.d("Permission check", "Permission denied")
            }
        }

//Request all permission
        permissionHelper.requestAll {
            Log.d("Permission check", "All permission granted")

            if (!isGenerating && download) {
                isGenerating = true
                RPdfGenerator.generatePdf(this, dummyInfo)

                val handler = Handler()
                val runnable = Runnable {
                    //to avoid multiple generation at the same time. Set isGenerating = false on some delay
                    isGenerating = false
                }
                handler.postDelayed(runnable, 2000)
            }
        }

//Request individual permission
        permissionHelper.requestIndividual {
            Log.d("Permission check", "Individual Permission Granted")
        }
    }

    private fun dummyModel(): RPdfGeneratorModel {
        val list = dummyTransactions()
        val header = "MEDICARE"
        val dummy = RPdfGeneratorModel(list, header)
        return dummy
    }

    private fun dummyTransactions(): List<RTransaction> {

        val list = arrayListOf<RTransaction>()

        val i1 = RTransaction()
        i1.custName = "Johan Store"
        i1.itemName = "Snacks"
        i1.quantity = 4
        i1.pricePerUnit = 40.0
        i1.totalPrice = i1.quantity * i1.pricePerUnit
        i1.transactionDateStr = "10 Sep, 20"
        i1.transType = RTransactionType.plus
        list.add(i1)


        val i2 = RTransaction()
        i2.custName = "Alice Store"
        i2.itemName = "Chocolate"
        i2.quantity = 3
        i2.pricePerUnit = 79.0
        i2.totalPrice = i2.quantity * i2.pricePerUnit
        i2.transactionDateStr = "9 Sep, 20"
        i2.transType = RTransactionType.plus
        list.add(i2)

        val i3 = RTransaction()
        i3.custName = "Alexa Mall"
        i3.itemName = "Shoes"
        i3.quantity = 2
        i3.pricePerUnit = 177.0
        i3.totalPrice = i3.quantity * i3.pricePerUnit
        i3.transactionDateStr = "9 Sep, 20"
        i3.transType = RTransactionType.minus
        list.add(i3)

        val i4 = RTransaction()
        i4.custName = "Zainab Baba"
        i4.itemName = "Chips"
        i4.quantity = 5
        i4.pricePerUnit = 140.0
        i4.totalPrice = i4.quantity * i4.pricePerUnit
        i4.transactionDateStr = "8 Sep, 20"
        i4.transType = RTransactionType.plus
        list.add(i4)




        return list
    }

}
