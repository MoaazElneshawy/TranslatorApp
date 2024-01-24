//
//  GradientSurface.swift
//  iosApp
//
//  Created by MoaazElneshawy on 12/07/1445 AH.
//  Copyright © 1445 AH orgName. All rights reserved.
//

import SwiftUI

struct GradientSurface : ViewModifier{
    // @Environment is equavilant to Context and gives us access to System
    @Environment(\.colorScheme) var colorSchema
    
    func body(content: Content) -> some View {
        if colorSchema == .dark {
            let gradientStart = Color(hex: 0xFF23262E)
            let gradientEnd = Color(hex: 0xFF212329)
            
            content.background(
                LinearGradient(
                    gradient: Gradient(colors: [gradientStart, gradientEnd]),
                    startPoint: .top,
                    endPoint: .bottom)
            )
            
        } else {
            content.background(Color.surface)
        }
    }
    
}


extension View {
    // create an extension modifier to the view
    func gradientSurface () -> some View {
        modifier(GradientSurface())
    }
}
