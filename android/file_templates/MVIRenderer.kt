#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end

import android.content.Context
import javax.inject.Inject

@ViewScope
#parse("File Header.java")
class ${NAME} @Inject constructor(
    private val context: Context
) {

    fun render(state: ${MVI_TYPE}State) = emptyList<Item>()
}
