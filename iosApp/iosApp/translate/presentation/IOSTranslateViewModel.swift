//
//  IOSTranslateViewModel.swift
//  iosApp
//
//  Created by MoaazElneshawy on 11/07/1445 AH.
//  Copyright © 1445 AH orgName. All rights reserved.
//

import Foundation
import shared

// extension to make sure the viewModel will be used only inside the TranslateScreen
extension TranslateScreen {
    // @MainActor > the operations will be executed in the main thread
    // ObservableObject > a defination to the class which has properties that will be observed
    @MainActor class IOSTranslateViewModel : ObservableObject {
        private var historyDataSource : HistoryDataSource
        private var translateUseCase : TranslateUseCase
        
        private let viewModel : TranslateViewModel
        
        // Publisher > the concept of emit changes and observe these changes in iOS
        // we need to initiate a default value to the state
        @Published var state : TranslateState = TranslateState(fromText: "",
                                                               toText: nil,
                                                               isTranslating: false,
                                                               fromLanguage: UiLanguage(language: .english, imageName: "english"),
                                                               toLanguage: UiLanguage(language: .arabic, imageName: "arabic"),
                                                               isChoosingFromLanguage: false,
                                                               isChoosingToLanguage: false,
                                                               error: nil,
                                                               history: [])
        
     
        init(historyDataSource: HistoryDataSource, translateUseCase: TranslateUseCase) {
            self.historyDataSource = historyDataSource
            self.translateUseCase = translateUseCase
            self.viewModel = TranslateViewModel(
                                                translate: translateUseCase,
                                                historyDataSource: historyDataSource,
                                                coroutineScope: nil)
        }
        
        // there's no lifeCycle awarness in iOS, so we need to start and dispose observer
        private var handle : DisposableHandle?

        func startObserving(){
            // we need to collect emit to the flow
            handle = viewModel.state.subscribe(onCollect: { vmState in
                if let state = vmState{
                    self.state = state
                }
            })
           
        }
        
        func dispose(){
            handle?.dispose()
        }
        
        func onEvent(event:TranslateEvent){
            self.viewModel.onEvent(event: event)
        }
    }
}
