import Shared
import SwiftUI
import UIKit

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        let viewController = MainViewControllerKt.MainViewController()
        viewController.view.backgroundColor = UIColor.systemBackground
        viewController.view.isOpaque = true
        return viewController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

struct ContentView: View {
    var body: some View {
        ZStack {
            Color(uiColor: .systemBackground)
                .ignoresSafeArea()

            ComposeView()
        }
            .ignoresSafeArea()
    }
}
