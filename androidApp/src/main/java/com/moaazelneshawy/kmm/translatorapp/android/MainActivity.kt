package com.moaazelneshawy.kmm.translatorapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.moaazelneshawy.kmm.translatorapp.android.core.presentation.Routes
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.AndroidTranslateViewModel
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.screens.LANGUAGE_CODE
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.screens.RecordAudioScreen
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.screens.TranslateScreen
import com.moaazelneshawy.kmm.translatorapp.translate.presentation.TranslateEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TranslatorAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    TranslateRoot()
                }
            }
        }
    }
}

@Composable
fun TranslateRoot() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = Routes.TRANSLATE, builder = {

            composable(route = Routes.TRANSLATE) {
                val viewModel = hiltViewModel<AndroidTranslateViewModel>()
                val state by viewModel.state.collectAsState()
                TranslateScreen(
                    state = state,
                    onEvent = { event ->
                        if (event == TranslateEvent.RecordAudio) {
                            navController.navigate(route = Routes.VOICE_TO_TEXT + "/${state.fromLanguage.language.langCode}")
                        } else viewModel.onEvent(event)
                    }
                )
            }

            composable(
                route = Routes.VOICE_TO_TEXT + "/{$LANGUAGE_CODE}",
                arguments = listOf(
                    navArgument(LANGUAGE_CODE) {
                        type = NavType.StringType
                        defaultValue = "en"
                    }
                )
            ) {
                RecordAudioScreen()
            }


        })
}
