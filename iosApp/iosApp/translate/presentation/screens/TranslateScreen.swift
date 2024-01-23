//
//  TranslateScreen.swift
//  iosApp
//
//  Created by MoaazElneshawy on 11/07/1445 AH.
//  Copyright © 1445 AH orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateScreen: View {
    
    private var hisotryDataSource : HistoryDataSource
    private var translateUseCase : TranslateUseCase
    //  @ObservedObject means this can listen to changes happen in viewModel which is ObserverObject
   @ObservedObject var viewModel : IOSTranslateViewModel
    
    init(hisotryDataSource: HistoryDataSource, translateUseCase: TranslateUseCase) {
        self.hisotryDataSource = hisotryDataSource
        self.translateUseCase = translateUseCase
        self.viewModel = IOSTranslateViewModel(historyDataSource: hisotryDataSource, translateUseCase: translateUseCase)
    }
    
    var body: some View {
        // similar to Box, draw views above of each others
        // will need it to draw the mic icon in the bottom
        ZStack{
            // list similar to LazyColumn in compose
            List{
                HStack(alignment: .center, content: {
                    LanguageDropDown(language: viewModel.state.fromLanguage, 
                                     isOpen: viewModel.state.isChoosingFromLanguage,
                                     onSelectLanguage: {language in
                                        viewModel.onEvent(event: TranslateEvent.ChooseFromLanguage(uiLanguage: language))
                    })
                    Spacer()
                    SwapLanguagesButton(onClick: {
                        viewModel.onEvent(event: TranslateEvent.SwapLanguage())
                    })
                    Spacer()
                    LanguageDropDown(language: viewModel.state.toLanguage,
                                     isOpen: viewModel.state.isChoosingToLanguage,
                                     onSelectLanguage: {language in
                                        viewModel.onEvent(event: TranslateEvent.ChooseToLanguage(uiLanguage: language))
                    })
                }).listRowSeparator(.hidden)
                    .listRowBackground(Color.background)
            }.listStyle(.plain)
                .buttonStyle(.plain)
        }.onAppear(perform: {
            viewModel.startObserving()
        })
        .onDisappear(perform: {
            viewModel.dispose()
        })
    }
}
