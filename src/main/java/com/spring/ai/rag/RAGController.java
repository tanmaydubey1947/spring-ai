package com.spring.ai.rag;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/rag")
public class RAGController {

    private final RAGService ragService;

    public RAGController(RAGService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/query")
    public String queryRAG(@RequestParam String prompt,
                           @RequestHeader String username) {
        return ragService.connectToRAGAI(prompt, username);
    }
}
