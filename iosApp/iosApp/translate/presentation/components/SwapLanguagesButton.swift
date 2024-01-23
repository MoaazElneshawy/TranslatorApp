//
//  SwapLanguagesButton.swift
//  iosApp
//
//  Created by Muzamil on 11/07/1445 AH.
//  Copyright © 1445 AH orgName. All rights reserved.
//

import SwiftUI

struct SwapLanguagesButton: View {
    var onClick : () -> Void
    var body: some View {
        Button(action:onClick){
            Image(uiImage: UIImage(named: "swap_languages")!)
                .padding()
                .background(Color.primaryColor)
                .clipShape(Circle())
        }
        }
}

#Preview {
    SwapLanguagesButton(onClick: {})
}
