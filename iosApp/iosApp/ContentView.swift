import SwiftUI
import shared

struct ContentView: View {
    
 private let appModule = AppModule()
	
    var body: some View {
        // this will control the window color and background
        ZStack{
            Color.background
                .ignoresSafeArea()
            
            TranslateScreen(
                hisotryDataSource: appModule.historyDataSource,
                translateUseCase: appModule.translateUseCase)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
