#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end

#parse("File Header.java")
sealed class ${MVI_TYPE}Change {
    object Loading : ${MVI_TYPE}Change()
    object NoEffect : ${MVI_TYPE}Change() // use this when we will already receive results via observable queries
    data class Result(val string: String) : ${MVI_TYPE}Change()
}
