package com.spring.ai.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RAGSampleDataLoader {

    private final VectorStore vectorStore;

    public RAGSampleDataLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadSampleDataToVectorStore() {
       List<String> sampleData = getSampleData();
       List<Document> documents = sampleData.stream()
               .map(Document::new)
               .toList();
       vectorStore.add(documents);
    }

    private List<String> getSampleData() {
        return List.of(
          // Technology
          "Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible.",
          "Spring Framework is an application framework and inversion of control container for the Java platform.",

          // Science
          "The Earth revolves around the Sun in approximately 365.25 days.",
          "Water is composed of two hydrogen atoms and one oxygen atom, giving it the chemical formula H2O.",

          // History
          "The Renaissance was a period in European history marking the transition from the Middle Ages to modernity, spanning roughly the 14th to the 17th century.",
          "The Industrial Revolution was a period of major industrialization that took place during the late 1700s and early 1800s, starting in Great Britain.",

          // World Events
          "The COVID-19 pandemic, caused by the SARS-CoV-2 virus, was first identified in December 2019 and has had a profound impact on global health and economies.",
          "The 2020 U.S. Presidential Election was held on November 3, 2020, resulting in the election of Joe Biden as the 46th President of the United States."
        );
    }
}
