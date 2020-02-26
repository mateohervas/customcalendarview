package com.shadows.customcalendarrange

import android.content.res.Resources
import com.prolificinteractive.materialcalendarview.*
import java.text.SimpleDateFormat
import java.util.*

class CustomCalendar(private val calendarView: MaterialCalendarView, private val resources: Resources){

    val dateArray = ArrayList<CalendarDay>()
    var singleDate: CalendarDay? = null

    fun createCalendar(){
        setTitles()
        setDecorators()
    }

    private fun setTitles(){

        calendarView.setTitleFormatter {
            val pattern = "MMM, YYYY"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val date: Calendar = Calendar.getInstance()
            date.set(it.year,it.month-1,it.day)
            simpleDateFormat.format(date.time)
        }
    }

    private fun setDecorators(){

        calendarView.setOnRangeSelectedListener { widget, dates ->
            dateArray.clear()
            dateArray.addAll(dates)
            singleDate = null
            widget.addDecorator(object : DayViewDecorator {
                override fun shouldDecorate(day: CalendarDay?): Boolean {
                    return dateArray.isNotEmpty() && day?.isInRange(dateArray[1], dateArray[dateArray.size-2])!!
                }
                override fun decorate(view: DayViewFacade?) {
                    view?.setSelectionDrawable(resources.getDrawable(R.drawable.sky_blue_alpha_rectangle,null))
                }
            })
        }
        calendarView.setOnDateChangedListener(object: OnDateSelectedListener {
            override fun onDateSelected(
                widget: MaterialCalendarView,
                date: CalendarDay,
                selected: Boolean
            ) {
                singleDate = date
                widget.addDecorator(object : DayViewDecorator {
                    override fun shouldDecorate(day: CalendarDay?): Boolean {
                        if(dateArray.isNotEmpty() && calendarView.selectedDates.size<2){
                            dateArray.clear()
                            return true
                        }
                        return dateArray.isEmpty() || (dateArray.isNotEmpty() && (day == dateArray[dateArray.size -1] || day == dateArray[0]))
                    }
                    override fun decorate(view: DayViewFacade?) {
                        view?.setBackgroundDrawable(resources.getDrawable(R.drawable.blue_date_selector,null))
                    }
                })
            }

        })

    }



}