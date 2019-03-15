package com.fides.api.retake.modules.media

import org.bson.types.ObjectId
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.ReactiveGridFsOperations
import org.springframework.data.mongodb.gridfs.ReactiveGridFsResource
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.codec.multipart.FilePart
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

data class FileInfo(val filename: String, val contentType: String? = null)

class MediaRepository(
    private val gridFsOperations: ReactiveGridFsOperations
) {

    fun getById(id: String): Mono<ReactiveGridFsResource> {
        return gridFsOperations.findFirst(Query(Criteria.where("_id").`is`(id)))
            .flatMap { file ->
                gridFsOperations.getResource(file)
            }
    }

    fun add(filePart: FilePart): Mono<ObjectId> {
        return Mono.just(filePart)
            .zipWith(getMediaInfo(filePart))
            .flatMap { filePart ->

                val (filename, contentType) = filePart.t2
                gridFsOperations.store(
                    filePart.t1.content(),
                    filename,
                    contentType)
            }
    }

    private fun getMediaInfo(filePart: FilePart): Mono<FileInfo> {
        val contentType: String? = filePart.headers()[CONTENT_TYPE]?.get(0)

        return Mono.just(FileInfo(filePart.filename(), contentType ?: "Unknown"))
    }
}