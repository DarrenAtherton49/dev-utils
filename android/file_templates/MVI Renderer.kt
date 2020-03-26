#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end

import android.content.Context
import dev.darrenatherton.growthjournal.presentation.util.groupie.Item
import dev.darrenatherton.growthjournal.util.injection.ViewScope
import javax.inject.Inject

@ViewScope
#parse("File Header.java")
class ${NAME} @Inject constructor(
    private val context: Context
) {

    fun render(state: ${MVI_TYPE}State) = emptyList<Item>()
}