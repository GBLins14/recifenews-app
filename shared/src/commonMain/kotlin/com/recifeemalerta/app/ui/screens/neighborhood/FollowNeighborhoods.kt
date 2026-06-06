package com.recifeemalerta.app.ui.screens.neighborhood

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.recifeemalerta.app.di.LocalAppDependencies
import com.recifeemalerta.app.feature.neighborhood.ui.state.FollowNeighborhoodsStateHolder
import com.recifeemalerta.app.navigation.Screen
import com.recifeemalerta.app.ui.screens.neighborhood.components.MultiNeighborhoodList
import com.recifeemalerta.app.ui.screens.neighborhood.components.NeighborhoodFooterButton
import com.recifeemalerta.app.ui.screens.neighborhood.components.NeighborhoodSearchField
import com.recifeemalerta.app.ui.screens.neighborhood.components.NeighborhoodSetupScaffold
import com.recifeemalerta.app.ui.screens.neighborhood.components.NeighborhoodTitle
import com.recifeemalerta.app.ui.screens.neighborhood.components.SelectionSummary
import com.recifeemalerta.app.ui.preview.AppPreview

@Composable
fun FollowNeighborhoodsScreen(
    mainNeighborhood: String,
    onNavigate: (Screen) -> Unit,
    onBack: () -> Unit
) {
    val dependencies = LocalAppDependencies.current
    val stateHolder = remember(mainNeighborhood, dependencies.neighborhoodRepository) {
        FollowNeighborhoodsStateHolder(
            mainNeighborhood = mainNeighborhood,
            repository = dependencies.neighborhoodRepository
        )
    }
    val state = stateHolder.state

    NeighborhoodSetupScaffold(
        showSkip = true,
        onSkip = { onNavigate(Screen.Home) },
        onBack = onBack
    ) {
        NeighborhoodTitle(
            title = "Siga outros bairros ao vivo",
            subtitle = "Receba alertas e notícias de qualquer um dos 94 bairros. Seu principal é ${state.mainNeighborhood}."
        )

        NeighborhoodSearchField(
            value = state.query,
            onValueChange = stateHolder::onQueryChanged,
            placeholder = "Buscar bairro para seguir"
        )

        MultiNeighborhoodList(
            neighborhoods = state.filteredNeighborhoods,
            selectedNeighborhoods = state.selectedNeighborhoods,
            mainNeighborhood = state.mainNeighborhood,
            onToggle = stateHolder::toggleNeighborhood,
            modifier = Modifier.weight(1f)
        )

        if (state.selectedNeighborhoods.isNotEmpty()) {
            val count = state.selectedNeighborhoods.size
            SelectionSummary(
                text = "$count bairro${if (count == 1) "" else "s"} com alertas ao vivo."
            )
        }

        NeighborhoodFooterButton(
            text = if (state.selectedNeighborhoods.isEmpty()) "Agora não" else "Concluir",
            onClick = { onNavigate(Screen.Home) }
        )
    }
}

@Preview
@Composable
fun FollowNeighborhoodsScreenPreview() {
    AppPreview {
        FollowNeighborhoodsScreen(
            mainNeighborhood = "Boa Viagem",
            onNavigate = {},
            onBack = {}
        )
    }
}
