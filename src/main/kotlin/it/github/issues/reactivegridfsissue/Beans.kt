package it.github.issues.reactivegridfsissue

import com.fides.api.retake.modules.media.MediaHandler
import com.fides.api.retake.modules.media.MediaRepository
import com.fides.api.retake.modules.media.MediaRoute
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate

class Beans: ApplicationContextInitializer<GenericApplicationContext> {

    private val beans = beans {

        /* Core */
        bean { ReactiveGridFsTemplate(ref(), ref()) }

        /* Media */
        bean { MediaRepository(ref()) }
        bean { MediaHandler(ref()) }
        bean { MediaRoute(ref()).router() }
    }

    override fun initialize(applicationContext: GenericApplicationContext) =
            beans.initialize(applicationContext)
}