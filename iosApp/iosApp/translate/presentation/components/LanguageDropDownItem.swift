//
//  LanguageDropDownItem.swift
//  iosApp
//
//  Created by Muzamil on 11/07/1445 AH.
//  Copyright © 1445 AH orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI


struct LanguageDropDownItem: View {
    var language : UiLanguage
    var onClick : ()->Void
    var body: some View {
        Button(action:onClick){
            // HStack equavilate to ROW in compose
            HStack{
                // imageName that exists in assests
                if let image = UIImage(named: language.imageName.lowercased()){
                    Image(uiImage: image)
                        .resizable()
                        .frame(width: 40,height: 40)
                        .padding(.trailing,5)
                }
                
                Text(language.language.langName)
                    .foregroundColor(.textBlack)
            
            }
        }
    }
}

#Preview {
    LanguageDropDownItem(
        language: UiLanguage(language: .arabic, imageName: "arabic"),onClick:{})
}


