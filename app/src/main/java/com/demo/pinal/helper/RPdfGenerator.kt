package com.demo.pinal.helper

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import com.demo.pinal.R
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.action.PdfAction
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.element.Text
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.UnitValue
import com.demo.pinal.model.RPdfGeneratorModel
import com.demo.pinal.model.RTransaction
import com.demo.pinal.model.ResultsItem
import com.itextpdf.layout.property.VerticalAlignment
import java.io.File
import java.io.FileOutputStream

object RPdfGenerator {

   // private val linkSample = "https://github.com/rheyansh/RPdfGenerator"

    fun generatePdf(context: Context, info: RPdfGeneratorModel) {

        val FILENAME = info.header + ".pdf"
        val filePath = getAppPath(context) + FILENAME

        if (File(filePath).exists()) {
            File(filePath).delete()
        }

        val fOut = FileOutputStream(filePath)
        val pdfWriter = PdfWriter(fOut)

        // Creating a PdfDocument
        val pdfDocument =
            PdfDocument(pdfWriter)
        val layoutDocument = Document(pdfDocument)

        // title
        addTitle(layoutDocument, info.header)

        //add empty line
        addEmptyLine(layoutDocument,1)

        //Add sub heading
        val appName = "RPdfGenerator"
       // addSubHeading(layoutDocument, "Generated via: ${appName}")
       // addLink(layoutDocument, linkSample)

        //add empty line
        addEmptyLine(layoutDocument,1)

        // customer reference information
        //addDebitCredit(layoutDocument, info)

        //add empty line
        addEmptyLine(layoutDocument,1)

        //Add sub heading
        addSubHeading(layoutDocument, "Transactions")

        //Add list
        addTable(layoutDocument, info.list)

        layoutDocument.close()
        Toast.makeText(context, "Pdf saved successfully to location $filePath", Toast.LENGTH_LONG).show()

        //FileUtils.openFile(context, File(filePath))

        val csvIntent = Intent(Intent.ACTION_VIEW)
        // csvIntent.addCategory(Intent.CATEGORY_OPENABLE);
        // csvIntent.addCategory(Intent.CATEGORY_OPENABLE);
        csvIntent.setDataAndType(
            FileProvider.getUriForFile(
                context.applicationContext,
                "com.demo.pinal", File(filePath)
            ), "application/pdf"
        )
        csvIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        csvIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(csvIntent)

    }

    private fun getAppPath(context: Context): String {
        val dir = File(
            Environment.getExternalStorageDirectory()
                .toString() + File.separator
                    + context.resources.getString(R.string.app_name)
                    + File.separator
        )
        if (!dir.exists()) {
            dir.mkdir()
        }
        return dir.path + File.separator
    }

    private fun addTable(layoutDocument: Document, items: List<ResultsItem>) {

        val table = Table(
            UnitValue.createPointArray(
                floatArrayOf(
                    100f,
                    180f,
                    80f
                )
            )
        )

        // headers
        //table.addCell(Paragraph("S.N.O.").setBold())
        table.addCell(Paragraph("Movi Name").setBold()).setVerticalAlignment(VerticalAlignment.MIDDLE)
        table.addCell(Paragraph("Rating").setBold())
        table.addCell(Paragraph("Image Url").setBold())
        /*table.addCell(Paragraph("Price/Q").setBold())
        table.addCell(Paragraph("Total").setBold())
        table.addCell(Paragraph("Date").setBold())*/

        // items
        for (a in items) {
//            table.addCell(Paragraph(a.SNO.toString() + ""))
            table.addCell(Paragraph(a.original_title + ""))
            table.addCell(Paragraph(a.vote_average + ""))
            table.addCell(Paragraph(a.poster_path.toString() + ""))
            /*table.addCell(Paragraph(a.pricePerUnit.toString() + ""))
            table.addCell(Paragraph((a.quantity * a.pricePerUnit).toString() + ""))
            table.addCell(Paragraph(a.transactionDateStr + ""))*/
        }
        layoutDocument.add(table)
    }

    private fun addEmptyLine(layoutDocument: Document, number: Int) {
        for (i in 0 until number) {
            layoutDocument.add(Paragraph(" "))
        }
    }


    private fun addSubHeading(layoutDocument: Document, text: String) {
        layoutDocument.add(
            Paragraph(text).setBold()
                .setTextAlignment(TextAlignment.CENTER)
        )
    }


    private fun addTitle(layoutDocument: Document, text: String) {
        layoutDocument.add(
            Paragraph(text).setBold().setUnderline()
                .setTextAlignment(TextAlignment.CENTER)
        )
    }
}