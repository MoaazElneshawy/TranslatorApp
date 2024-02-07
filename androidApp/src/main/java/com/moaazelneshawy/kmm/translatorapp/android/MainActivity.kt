package com.moaazelneshawy.kmm.translatorapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.screens.TranslateScreen
import com.moaazelneshawy.kmm.translatorapp.android.voiceToText.presentation.AndroidVoiceToTextViewModel
import com.moaazelneshawy.kmm.translatorapp.android.voiceToText.presentation.LANGUAGE_CODE
import com.moaazelneshawy.kmm.translatorapp.android.voiceToText.presentation.VoiceToTextScreen
import com.moaazelneshawy.kmm.translatorapp.translate.presentation.TranslateEvent
import com.moaazelneshawy.kmm.translatorapp.voiceToText.presentation.VoiceToTextEvent
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

            composable(route = Routes.TRANSLATE) { backStackEntry ->
                val viewModel = hiltViewModel<AndroidTranslateViewModel>()
                val state by viewModel.state.collectAsState()

                // getting the value from saveState

                val spokenText by backStackEntry
                    .savedStateHandle
                    .getStateFlow<String?>(SPOKEN_TEXT, null)
                    .collectAsState()

                LaunchedEffect(spokenText) {
                    viewModel.onEvent(TranslateEvent.SubmitVoiceResult(spokenText))
                    // reset the value after using it
                    backStackEntry.savedStateHandle[SPOKEN_TEXT] = null
                }

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
            ) { backStackEntry ->
                val langCode = backStackEntry.arguments?.getString(LANGUAGE_CODE) ?: "en"
                val viewModel = hiltViewModel<AndroidVoiceToTextViewModel>()
                val state by viewModel.state.collectAsState()
                VoiceToTextScreen(
                    state = state,
                    languageCode = langCode,
                    onResult = { spokenText ->
                        // passing the result back to the previous screen
                        // via savedStateHandle
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(SPOKEN_TEXT, spokenText)
                        navController.popBackStack()
                    },
                    onEvent = { event ->
                        if (event == VoiceToTextEvent.Close) {
                            navController.popBackStack()
                        } else {
                            viewModel.onEvent(event)
                        }
                    })
            }


        })
}

const val SPOKEN_TEXT = "spokenText"