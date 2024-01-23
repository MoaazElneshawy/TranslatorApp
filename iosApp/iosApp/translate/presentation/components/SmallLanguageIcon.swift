//
//  SmallLanguageIcon.swift
//  iosApp
//
//  Created by Muzamil on 11/07/1445 AH.
//  Copyright © 1445 AH orgName. All rights reserved.
//

import SwiftUI
import shared
struct SmallLanguageIcon: View {
    var language : UiLanguage
    var body: some View {
       Image(uiImage: UIImage(named: language.imageName.lowercased())!)
            .resizable()
            .frame(width: 30,height: 30)
            .foregroundColor(.lightBlue)
    }
}

