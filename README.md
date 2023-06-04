# Notifications And AlarmManager

#### This is an Android application that demonstrates the use of notifications and the alarm manager. It includes features such as showing notifications with actions, setting alarms, and displaying advanced notifications.

## Prerequisites
#### <li>Android Studio: Install Android Studio to build and run the application.</li>
#### <li>Android device/emulator: Use an Android device or emulator to test the application.</li>

## Installation
1. Clone the repository or download the source code.
2. Open the project in Android Studio.
3. Build and run the application on your device/emulator.

## Usage 
The application provides the following functionality:

#### 1. The application provides the following functionality:
<ul>
<li>   Tap the "Set Timer" button to select a time using a time picker. 
<li>  The selected time will be displayed.
<li>    Tap the "Set Alarm" button to set the alarm at the selected time.
<li> The alarm will trigger a notification when the specified time is reached.</li></ul>
  
#### 2. Cancelling Alarms:
<ul>
<li> Tap the "Cancel Alarm" button to cancel the previously set alarm.
<li> The alarm will be cancelled, and no notification will be triggered.</li></ul>

#### 3. Advanced Notification:
<ul>
<li> Tap the "Advance Notif" button to display an advanced notification.
<li> The notification will include a counter, which increments each time the notification is displayed.
<li> Tapping the notification will open the "DestActivity" screen.</li></ul>

## Code Structure
The application consists of the following main components:
<li><b>MainActivity:</b> The main activity of the application that handles user interactions and manages alarms and notifications.</li>

``` kotlin
  private fun showTimePicker() {

        val picker: MaterialTimePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()

        picker.show(supportFragmentManager, "ALARM")

        //Setting Up the time to the TextView
        picker.addOnPositiveButtonClickListener {
            val selectedHour = if (picker.hour > 12) picker.hour - 12 else picker.hour
            val amPm = if (picker.hour >= 12) "PM" else "AM"
            val formattedTime = String.format("%02d : %02d %s", selectedHour, picker.minute, amPm)
            binding.tvTime.text = formattedTime
        }
            //Setting Up The Calendar
        calendar = Calendar.getInstance(Locale.getDefault())
        picker.addOnPositiveButtonClickListener {
            calendar.set(Calendar.HOUR_OF_DAY, picker.hour)
            calendar.set(Calendar.MINUTE, picker.minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            // Perform any additional actions with the selected time
            Log.d("CALENDAR", calendar.get(Calendar.HOUR_OF_DAY).toString())
        }

    }
    
    private fun setAlarm() {

        val intent = Intent(this, AlarmReciever::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            );
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            );
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent);
        }

        Toast.makeText(this, "Alarm Set Successfully", Toast.LENGTH_SHORT).show()

    }


```


