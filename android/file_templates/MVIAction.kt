#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end

import com.ww.roxie.BaseAction

#parse("File Header.java")
sealed class ${MVI_TYPE}Action : BaseAction {
    object Load : ${MVI_TYPE}Action()
}
