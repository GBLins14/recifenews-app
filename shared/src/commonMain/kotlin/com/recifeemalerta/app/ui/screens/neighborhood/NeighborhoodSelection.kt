package com.recifeemalerta.app.ui.screens.neighborhood

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recifeemalerta.app.di.LocalAppDependencies
import com.recifeemalerta.app.feature.neighborhood.ui.state.MainNeighborhoodStateHolder
import com.recifeemalerta.app.navigation.Screen
import com.recifeemalerta.app.ui.screens.neighborhood.components.NeighborhoodFooterButton
import com.recifeemalerta.app.ui.screens.neighborhood.components.NeighborhoodSearchField
import com.recifeemalerta.app.ui.screens.neighborhood.components.NeighborhoodSetupScaffold
import com.recifeemalerta.app.ui.screens.neighborhood.components.NeighborhoodTitle
import com.recifeemalerta.app.ui.screens.neighborhood.components.SelectionSummary
import com.recifeemalerta.app.ui.screens.neighborhood.components.SingleNeighborhoodList
import com.recifeemalerta.app.ui.preview.AppPreview

@Composable
fun NeighborhoodSelectionScreen(
    onNavigate: (Screen) -> Unit,
    onBack: () -> Unit
) {
    val dependencies = LocalAppDependencies.current
    val stateHolder = remember(dependencies.neighborhoodRepository) {
        MainNeighborhoodStateHolder(dependencies.neighborhoodRepository)
    }
    val state = stateHolder.state

    NeighborhoodSetupScaffold(onBack = onBack) {
        NeighborhoodTitle(
            title = "Escolha seu bairro principal",
            subtitle = "Este será o bairro que você verá primeiro entre os 94 bairros do Recife."
        )

        NeighborhoodSearchField(
            value = state.query,
            onValueChange = stateHolder::onQueryChanged,
            placeholder = "Buscar bairro"
        )

        SingleNeighborhoodList(
            neighborhoods = state.filteredNeighborhoods,
            selectedNeighborhood = state.selectedNeighborhood,
            onSelect = stateHolder::selectNeighborhood,
            modifier = Modifier.weight(1f)
        )

        state.selectedNeighborhood?.let { neighborhood ->
            SelectionSummary(text = "$neighborhood será seu bairro inicial.")
            Spacer(modifier = Modifier.height(8.dp))
        }

        NeighborhoodFooterButton(
            text = "Continuar",
            enabled = state.selectedNeighborhood != null,
            onClick = {
                state.selectedNeighborhood?.let { neighborhood ->
                    onNavigate(Screen.FollowNeighborhoods(mainNeighborhood = neighborhood))
                }
            }
        )
    }
}

@Preview
@Composable
fun NeighborhoodSelectionScreenPreview() {
    AppPreview {
        NeighborhoodSelectionScreen(onNavigate = {}, onBack = {})
    }
}
