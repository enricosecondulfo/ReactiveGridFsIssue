package com.fides.api.retake.modules.media

import org.springframework.http.HttpHeaders.CONTENT_TYPE

import org.springframework.http.MediaType.TEXT_PLAIN
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class MediaHandler(val mediaRepository: MediaRepository) {

    /* Fix problem gridfsresource downloadStream for file */
    fun getById(request: ServerRequest): Mono<ServerResponse> {
        val id: String = request.pathVariable("id")
        return mediaRepository.getById(id)
            .flatMap { resource ->
                ServerResponse.ok()
                    .header(CONTENT_TYPE, "image/jpeg")
                    .body(BodyInserters.fromDataBuffers(resource.downloadStream))
            }
            .switchIfEmpty(ServerResponse.notFound().build())
    }

    fun add(request: ServerRequest): Mono<ServerResponse> {
        return request.body(BodyExtractors.toMultipartData())
            .map { parts -> parts.toSingleValueMap() }
            .map { parts -> parts["image"] as FilePart }
            .flatMap { imagePart -> mediaRepository.add(imagePart)}
            .flatMap { id ->
                ServerResponse.ok()
                    .contentType(TEXT_PLAIN)
                    .body(Mono.just(id.toString()), String::class.java)
            }
    }
}