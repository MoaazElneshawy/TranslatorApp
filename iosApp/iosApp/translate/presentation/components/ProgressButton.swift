//
//  ProgressButton.swift
//  iosApp
//
//  Created by MoaazElneshawy on 12/07/1445 AH.
//  Copyright © 1445 AH orgName. All rights reserved.
//

import SwiftUI
import shared

struct ProgressButton: View {
    let text: String
    var isLoading : Bool
    var onClick : () -> Void
    
    var body: some View {
    Button (action: {
        if !isLoading {
            onClick()
        }
    }, label: {
        if isLoading {
            ProgressView()
                .animation(.easeInOut,value: isLoading)
                .padding(5)
                .background(Color.primaryColor)
                .cornerRadius(100)
                .progressViewStyle(CircularProgressViewStyle(tint: .onPrimary))
        } else {
            Text(text)
                .animation(.easeInOut, value: isLoading)
                .padding(.horizontal)
                .padding(.vertical,5)
                .font(.body.weight(.bold))
                .background(Color.primaryColor)
                .foregroundColor(.onPrimary)
                .cornerRadius(100)

        }
    })
    }
}


