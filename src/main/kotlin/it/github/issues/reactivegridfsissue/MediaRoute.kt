package com.fides.api.retake.modules.media

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

class MediaRoute(val mediaHandler: MediaHandler) {

    fun router() = router {
        (accept(MediaType.MULTIPART_FORM_DATA) and "/media").nest {
            GET("/{id}", mediaHandler::getById)
            POST("/", mediaHandler::add)
        }
    }
}