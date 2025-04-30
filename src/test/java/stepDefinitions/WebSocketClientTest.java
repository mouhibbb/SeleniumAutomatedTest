package stepDefinitions;

import java.io.IOException;
import java.net.URI;

import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import WebSocket.WebSocketClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WebSocketClientTest {
	@Given("le WebSocket est connecté")
	public void le_web_socket_est_connecté() throws Exception {
	}

	@When("je reçois un message de type {string} avec l URL {string}")
	public void je_reçois_un_message_de_type_avec_l_url(String string, String string2) {
		System.out.println(1);

	}

	@Then("je navigue vers {string}")
	public void je_navigue_vers(String string) {
		System.out.println(1);

	}

	@Then("je récupère les champs du formulaire disponibles")
	public void je_récupère_les_champs_du_formulaire_disponibles() {
		System.out.println(1);

	}

	@Then("j envoie les données du formulaire via WebSocket")
	public void j_envoie_les_données_du_formulaire_via_web_socket() {
		System.out.println(1);

	}

	@Given("je suis sur la page {string}")
	public void je_suis_sur_la_page(String string) {
		System.out.println(1);

	}

	@When("je reçois un message de type {string} avec les valeurs suivantes")
	public void je_reçois_un_message_de_type_avec_les_valeurs_suivantes(String string) {
		System.out.println(1);

	}

	@Then("je remplis le champ {string} avec {string}")
	public void je_remplis_le_champ_avec(String string, String string2) {
		System.out.println(1);

	}

	@Then("je clique sur le bouton {string}")
	public void je_clique_sur_le_bouton(String string) {
		System.out.println(1);

	}
}
