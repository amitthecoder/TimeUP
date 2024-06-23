package com.amitthecoder.timeup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amitthecoder.timeup.ui.theme.TimeUpTheme

class MainActivity : ComponentActivity() {
    private val timerViewModel by viewModels<TimerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeUpTheme {
                TimerScreen(timerViewModel = timerViewModel)
            }
        }
    }
}



// Create custom font variable for clock

val clockFont = FontFamily(
    Font (R.font.poppinsmedium)
)



@Composable
fun TimerScreen(timerViewModel: TimerViewModel = viewModel()) {
    val time by timerViewModel.time.observeAsState(0)
    val isRunning by timerViewModel.isRunning.observeAsState(false)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Text(
            text = "${time / 3600000 % 24}H : ${time / 60000 % 60}M : ${time / 1000 % 60}S",
            modifier = Modifier.padding(16.dp), fontSize = 40.sp, fontFamily = clockFont
        )

        Row (verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 50.dp, 0.dp, 50.dp),
            horizontalArrangement = Arrangement.SpaceEvenly) {

            FilledTonalButton(onClick = { timerViewModel.startOrPause() }) {
                Text(text = if (isRunning) "Pause" else "Start",fontSize = 25.sp)
            }

            FilledTonalButton(onClick = { timerViewModel.reset() }) {
                Text(text = "Reset", fontSize = 25.sp)
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun TimerScreenPreview() {
    TimerScreen()
}