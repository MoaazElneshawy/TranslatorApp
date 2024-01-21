//
//  Colors.swift
//  iosApp
//
//  Created by Muzamil on 09/07/1445 AH.
//  Copyright © 1445 AH orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared


// int64 is the long in swift
// this extension is needed to use the colors file (with hexa code) in Shared module
extension Color{
    init(hex : Int64, alpha : Double = 1){
        self.init(
                .sRGB,
                red: Double((hex >> 16) & 0xff) / 255,
                green: Double((hex >> 08) & 0xff) / 255,
                blue: Double((hex >> 00) & 0xff) / 255,
                opacity: alpha
            )
    }
    
    // get the colors from shared module
    private static let colors = Colors()
    // now we will use the extension init we created above
    // converting the hex to actual Color object
    static let lightBlue = Color(hex:colors.LightBlue)
    static let lightBlueGray = Color(hex:colors.LightBlueGrey)
    static let textBlack = Color(hex:colors.TextBlack)
    static let accentViolate = Color(hex:colors.AccentViolet)
    static let darkGray = Color(hex: colors.DarkGrey, alpha: 1)
    
    // this calls the extension init below ( with light-dark)
    static let primary = Color(light: .accentViolate, dark: .accentViolate)
    static let background = Color(light:.lightBlueGray,dark: .darkGray)
    static let onPrimary = Color( light: .white, dark: .white)
    static let onBackground = Color( light: .textBlack, dark: .white)
    static let surface = Color( light: .white, dark: .darkGray)
    static let onSurface = Color(light: .textBlack, dark: .white)
}

private extension Color{
    init(light : Self, dark : Self){
        self.init(uiColor: UIColor(light: UIColor(light), dark: UIColor(dark)))
    }
}

// UIUserInterfaceStyle (tranis) gives us access to current theme (light/dark).
private extension UIColor{
    convenience init(light : UIColor , dark : UIColor){
        self.init{ tranis in
            switch tranis.userInterfaceStyle {
            case .light, .unspecified:
            return light
            case .dark:
                return dark
            @unknown default:
                return light
            }
        }
    }
}
