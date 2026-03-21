package com.spring.ai.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Service
public class RAGService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Value("classpath:prompts/rag_template.st")
    private Resource orderSystemPrompt;

    public RAGService(VectorStore vectorStore, ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();

        Advisor memoryAdvisor = MessageChatMemoryAdvisor
                .builder(chatMemory)
                .build();

        this.chatClient = chatClientBuilder
                .defaultAdvisors(List.of(memoryAdvisor, loggerAdvisor))
                .build();

        this.vectorStore = vectorStore;
    }


    public String connectToRAGAI(String prompt, String username) {

        // R - Retrieval Query
        SearchRequest searchRequest = SearchRequest.builder()
                .query(prompt)
                .topK(5)
                .similarityThreshold(0.5)
                .build();

        // A - Augmentation
        List<Document> retrievedDocuments = vectorStore.similaritySearch(searchRequest);

        // extract string out of the retrieved documents
        List<String> similarString = retrievedDocuments.stream()
                .map(Document::getText)
                .toList();

        // G - Generation
        return chatClient.prompt()
                .system(promptSystemSpec -> promptSystemSpec.text(orderSystemPrompt)
                        .param("documents", similarString))
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username))
                .user(prompt)
                .call()
                .content();
    }
}
