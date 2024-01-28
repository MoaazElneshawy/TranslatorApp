//
//  TranslatedHistoryItem.swift
//  iosApp
//
//  Created by MoaazElneshawy on 16/07/1445 AH.
//  Copyright © 1445 AH orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslatedHistoryItem: View {
    let item : UiHistoryItem
    let onClick : () -> Void
    var body: some View {
        Button(action: onClick, label: {
            VStack(alignment:.leading){
                HStack(){
                    SmallLanguageIcon(language: item.fromLanguage)
                        .padding(.trailing)
                    Text(item.fromText)
                        .foregroundColor(.lightBlue)
                        .font(.body)
                        .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/,alignment:.leading)
                }.padding(.bottom)
                HStack{
                    SmallLanguageIcon(language: item.toLanguage)
                        .padding(.trailing)
                    Text(item.toText)
                        .foregroundColor(.onSurface)
                        .font(.body.weight(.semibold))
                        .frame(maxWidth: .infinity,alignment:.leading)
                        
                }
            }
            .frame(maxWidth: .infinity)
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .shadow(radius: 4)
            
        })
    }
}
