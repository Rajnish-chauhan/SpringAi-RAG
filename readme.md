# Spring AI RAG(retrieval-Augmented Generation)
This guide outlines the core Spring AI classes used to implement Retrieval-Augmented Generation (RAG), conversational memory, and meaning-based caching alongside Qdrant, Apache Tika, and Tavily.

## Component Breakdown ->
* Document Processing & Retrieval 
  * TikaDocumentReader: Extracts text from files (PDFs, DOCX, PPTX, etc.) using Apache Tika and converts them into Spring AI Document objects for ingestion.
  * WebSearchDocumentRetriever: Fetches real-time web search results to provide up-to-date context. In this setup, it is powered by Tavily via the TAVILY_SEARCH_API_KEY.
  * VectorStoreDocumentRetriever: Retrieves the most relevant, pre-ingested documents from the vector database based on similarity to the user's query.


* Embeddings & Vector Database
    * EmbeddingModel: The core interface that converts raw text (documents and user queries) into dense numerical vector representations.
    * QdrantClient: The low-level connection client used to communicate with the Qdrant vector database.
    * QdrantVectorStore: Spring AI's implementation of a vector store backed by Qdrant. It stores document embeddings and executes vector similarity searches.


* Advisors (Chat Interceptors)
    * RetrievalAugmentationAdvisor: The primary orchestrator for the RAG flow. It intercepts the user's prompt, triggers the retrievers, and augments the prompt with the retrieved context before calling the LLM.
    * MessageChatMemoryAdvisor: Manages conversational state. It retrieves previous chat messages from memory and injects them into the current prompt to ensure context-aware, human-like responses.
    * SemanticCacheAdvisor: A performance layer that intercepts incoming chat requests and checks the cache for semantically similar previous questions. If a match is found, it instantly returns the cached answer and prevents an unnecessary LLM call.


* Caching
    * emanticCache / DefaultSemanticCache: The interface and default implementation for semantic caching. Unlike traditional caches requiring exact text matches, this uses vector embeddings to recognize when two differently worded questions mean the same thing.