//
//  LanguageDropDown.swift
//  iosApp
//
//  Created by Muzamil on 11/07/1445 AH.
//  Copyright © 1445 AH orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropDown: View {
    var language : UiLanguage
    var isOpen : Bool
    var onSelectLanguage : (UiLanguage) -> Void
    var body: some View {
        // to create a drop down we use Menu
        Menu{
            // here, the code refers how we create the menu
            VStack{
                ForEach(UiLanguage.Companion().allLanguages, id: \.self.language.langCode){ language in
                LanguageDropDownItem(language: language,
                                     onClick: {onSelectLanguage(language)})
            }
            }
        } label: {
            // here, the code which should appear when the menu is closed
            HStack{
                SmallLanguageIcon(language: language)
                Text(language.language.langName)
                    .foregroundColor(.lightBlue)
                Image(systemName: isOpen ? "chevron.up" : "chevron.down")
                    .foregroundColor(.lightBlue)
            }
        }
    }
}

