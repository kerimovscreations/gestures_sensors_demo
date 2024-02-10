package az.edu.bhos.android.l5_gestures_sensors

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PointF
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener,
    SensorEventListener {
    private var diffCoordinates: PointF = PointF(0f, 0f)

    lateinit var labelCenter: TextView
    lateinit var labelCenterBottom: TextView

    private lateinit var sensorManager: SensorManager
    private lateinit var lightSensor: Sensor
    private lateinit var accelerometer: Sensor

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val allSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

        println(allSensors)

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)!!
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!

        labelCenter = findViewById<TextView>(R.id.label_center)
//        labelCenterBottom = findViewById<TextView>(R.id.label_center_bottom)

//        labelCenter.setOnClickListener(this)
//        labelCenterBottom.setOnClickListener(this)

//        labelCenter.setOnLongClickListener {
//            labelCenter.text = "Long clicked"
//            return@setOnLongClickListener true
//        }


//        labelCenter.setOnTouchListener { view, motionEvent ->
//            val x = motionEvent.rawX
//            val y = motionEvent.rawY
//
//            when (motionEvent.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    diffCoordinates = PointF(
//                        labelCenter.x - x,
//                        labelCenter.y - y
//                    )
//                    showToast("Touch started")
//                }
//
//                MotionEvent.ACTION_UP -> {
//                    showToast("Touch completed")
//                }
//
//                MotionEvent.ACTION_MOVE -> {
//                    val dx = x + diffCoordinates.x
//                    val dy = y + diffCoordinates.y
//
////                    println("Coordinated ($x,$y)")
//
//                    view.animate()
//                        .x(dx)
//                        .y(dy) // uncomment this line to move y
//                        .setDuration(0)
//                        .start()
//                }
//            }
//
//            return@setOnTouchListener true
//        }


    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(view: View?) {
        when (view) {
            labelCenter -> (view as TextView).text = "Clicked from interface"
            labelCenterBottom -> (view as TextView).text = "Clicked from interface too"
        }
    }

    override fun onResume() {
        super.onResume()

        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()

        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor == lightSensor) {
            event.values?.let { valueArr ->
                val lightReading = valueArr[0]
                println("Light sensor reading $lightReading")
            }
        } else if (event?.sensor == accelerometer) {
            event.values?.let { valueArr ->
                println("Accelerometer sensor reading ${valueArr[0]}, ${valueArr[1]}, ${valueArr[2]}")
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        when (accuracy) {
            SensorManager.SENSOR_STATUS_ACCURACY_LOW -> println("accuracy is low")
            SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM -> println("accuracy is medium")
            SensorManager.SENSOR_STATUS_ACCURACY_HIGH -> println("accuracy is high")
        }

    }
}