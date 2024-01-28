import SwiftUI

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
            // simialar to nav host in android
            NavigationView{
                ContentView()
            }
		}
	}
}
