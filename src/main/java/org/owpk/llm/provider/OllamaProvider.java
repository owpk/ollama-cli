package org.owpk.llm.provider;

import java.util.List;
import java.util.Map;

import org.owpk.config.properties.model.LlmProviderProperties;
import org.owpk.llm.client.ollama.client.OllamaClient;
import org.owpk.llm.client.ollama.model.OllamaChatMessage;
import org.owpk.llm.client.ollama.model.OllamaChatRequest;
import org.owpk.llm.client.ollama.model.OllamaGenerateRequest;
import org.owpk.llm.provider.auth.ApiKeyProvider;
import org.owpk.llm.provider.auth.NoOpApiKeyProvider;
import org.owpk.llm.provider.mcp.McpMessage;
import org.owpk.llm.provider.model.ChatRequest;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class OllamaProvider extends LlmProvider<OllamaClient> {

	public OllamaProvider(OllamaClient ollamaClient, LlmProviderProperties properties) {
		super(ollamaClient, properties, new NoOpApiKeyProvider());
	}

	@Override
	protected void onClientUpdate(OllamaClient newClient, LlmProviderProperties newProperties) {
		// Implement client switching logic
	}

	@Override
	public Flux<String> generate(String prompt, String rolePrompt) {
		return getApiClient()
				.generate(OllamaGenerateRequest.builder()
						.model(getProperties().getModel())
						.prompt(prompt)
						.system(rolePrompt)
						.stream(true)
						.build())
				.map(response -> response.getResponse());
	}

	@Override
	public Flux<String> chat(List<ChatRequest> messages) {
		return getApiClient()
				.chat(OllamaChatRequest.builder()
						.model(getProperties().getModel())
						.messages(messages.stream()
								.map(it -> OllamaChatMessage.builder()
										.content(it.getMessage())
										.role(it.getRole().toLowerCase())
										.build())
								.toList())
						.stream(true)
						.build())
				.map(response -> response.getMessage().getContent());
	}

	@Override
	public Mono<double[]> embed(String text) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'embed'");
	}

	@Override
	protected Mono<Void> handleMcpMessage(McpMessage message) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'handleMcpMessage'");
	}

	@Override
	protected Map<String, Object> getMcpMetadata() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getMcpMetadata'");
	}

}
