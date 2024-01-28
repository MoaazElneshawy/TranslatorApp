//
//  TranslateTextField.swift
//  iosApp
//
//  Created by MoaazElneshawy on 12/07/1445 AH.
//  Copyright © 1445 AH orgName. All rights reserved.
//

import SwiftUI
import shared
import UniformTypeIdentifiers

struct TranslateTextField: View {
    // @Binding means this value will have set and get value
    // we need like a callback we have onValueChange in compse TextField
    @Binding var fromText : String
    var toText : String?
    var isTranslating : Bool
    var fromLanguage : UiLanguage
    var toLanguage : UiLanguage
    var onTranslateEvent : (TranslateEvent) -> Void
    
    var body: some View {
        if toText==nil || isTranslating {
            IdelTranslateTextField(
                fromText: $fromText,
                isTranslating: isTranslating,
                onTranslateEvent: onTranslateEvent)
            .gradientSurface()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: 4)
            
        }else {
            TranslatedTextField(
                fromText: fromText,
                toText: toText ?? "",
                fromLanguage: fromLanguage,
                toLanguage: toLanguage,
                onTranslateEvent: onTranslateEvent)
            .padding()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: 4)
            .onTapGesture {
                onTranslateEvent(TranslateEvent.EditTranslation())
            }
        }
    }
}

private extension TranslateTextField {
    
    struct IdelTranslateTextField : View {
        @Binding var fromText : String
        var isTranslating : Bool
        var onTranslateEvent : (TranslateEvent) -> Void
        var body: some View {
            // $ -> refers to the value of binding
            // this means we update the TextEditor when fromText changes
            // also we update the value of fromText when the user types something
           TextEditor(text: $fromText)
                .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/,
                       minHeight: 200,
                       alignment: .topLeading) // .topLeading to make the text starts from top|start
                .padding()
                .foregroundColor(Color.onSurface)
                // overlay means that you can draw a view above of this one and align this view
                .overlay(alignment: .bottomTrailing, content: {
                    ProgressButton(
                        text: "Translate",
                        isLoading: isTranslating,
                        onClick: {
                        onTranslateEvent(TranslateEvent.Translate())
                        })
                    .padding(.trailing)
                    .padding(.bottom)
                })
                
                .onAppear(perform: {
                    // we make the textField background transparent
                    UITextField.appearance().backgroundColor = .clear                   
                })
        }
    }
    
    struct TranslatedTextField : View{
        // we will not need @Binding because the fromText will not changed
        var fromText : String
        var toText : String
        var fromLanguage : UiLanguage
        var toLanguage : UiLanguage
        var onTranslateEvent : (TranslateEvent) -> Void
        
        var body: some View {
            VStack(alignment: .leading) {
                LanguageDisplay(language: fromLanguage)
                    .padding(.bottom)
                Text(fromText)
                    .foregroundColor(Color.onSurface)
                HStack {
                    Spacer()
                    Button(action: {
                        // copy to clipboard
                        UIPasteboard.general.setValue(fromText, forPasteboardType: UTType.playlist.identifier)
                    }, label: {
                        Image(uiImage: UIImage(named: "copy")!)
                            .renderingMode(.template) // to change tint of uiImage color
                            .foregroundColor(.lightBlue)
                    })
                    Button(action: {
                        onTranslateEvent(TranslateEvent.CloseTranslation())
                    }, label: {
                        Image(systemName:"xmark")
                            .foregroundColor(.lightBlue)
                    })
                }
                Divider()
                    .padding()
                LanguageDisplay(language: toLanguage)
                    .padding(.bottom)
                Text(toText)
                    .foregroundColor(Color.onSurface)
                HStack {
                    Spacer()
                    Button(action: {
                        // copy to clipboard
                        UIPasteboard.general.setValue(toText, forPasteboardType: UTType.playlist.identifier)
                    }, label: {
                        Image(uiImage: UIImage(named: "copy")!)
                            .renderingMode(.template) // to change tint of uiImage color
                            .foregroundColor(.lightBlue)
                    })
                    Button(action: {
                        // text to speech
                        let tts = TextToSpeech()
                        tts.speak(text: toText, languageCode: toLanguage.language.langCode)
                    }, label: {
                        Image(systemName:"speaker.wave.2")
                            .foregroundColor(.lightBlue)
                    })
                }
                
            }
        }
    }
    
    
}

