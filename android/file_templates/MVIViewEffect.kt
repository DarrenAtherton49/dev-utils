#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end

#parse("File Header.java")
sealed class ${MVI_TYPE}ViewEffect : BaseViewEffect
