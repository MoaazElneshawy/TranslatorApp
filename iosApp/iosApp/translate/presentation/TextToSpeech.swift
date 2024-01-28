//
//  TextToSpeech.swift
//  iosApp
//
//  Created by MoaazElneshawy on 16/07/1445 AH.
//  Copyright © 1445 AH orgName. All rights reserved.
//

import Foundation
// import AVFoundation to have access to speech
import AVFoundation

struct TextToSpeech {
    
    // defice synthesizer from AVSpeechSyntherzier which have control to speak
 private let synthesizer = AVSpeechSynthesizer()
    
    func speak(text:String , languageCode: String){
        // AVSpeechUtterance is the object we pass to synthesizer with the text and languageCode of this text
        let utterance = AVSpeechUtterance(string: text)
        // specify the language code which will speck with
        utterance.voice = AVSpeechSynthesisVoice(language: languageCode)
        // we specify the volume to 1 (full)
        utterance.volume = 1
        synthesizer.speak( utterance)
    }
}
