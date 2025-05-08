package com.juan.greenrate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juan.legacy_ui.LegacyActivity

class ChooserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChooserScreen(
                onComposeClick = {
                    startActivity(MainActivity.getIntent(this))
                },
                onLegacyClick = {
                    startActivity(LegacyActivity.getIntent(this))
                }
            )
        }
    }
}

@Composable
private fun ChooserScreen(
    onComposeClick: () -> Unit,
    onLegacyClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onComposeClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Versión Full Compose")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onLegacyClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Versión Legacy + RecyclerView")
        }
    }
}