//
//  LanguageDisplay.swift
//  iosApp
//
//  Created by MoaazElneshawy on 12/07/1445 AH.
//  Copyright © 1445 AH orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDisplay: View {
    let language : UiLanguage
    var body: some View {
       SmallLanguageIcon(language: language)
            .padding(.trailing,5)
        Text(language.language.langName)
            .foregroundColor(.lightBlue)
    }
}


